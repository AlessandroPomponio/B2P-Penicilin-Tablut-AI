/**
 * 
 */
package it.unibo.ai.didattica.competition.tablut.logreader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

/**
 * @author Andrea Galassi
 *
 */
public class LogReader {

	private static BufferedReader br;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Map<String, Integer> wins = new HashMap<String, Integer>();
		Map<String, Integer> draws = new HashMap<String, Integer>();
		Map<String, Integer> losses = new HashMap<String, Integer>();
		Map<String, Integer> winmoves = new HashMap<String, Integer>();
		Map<String, Integer> lossmoves = new HashMap<String, Integer>();
		Map<String, Integer> captures = new HashMap<String, Integer>();
		Map<String, Integer> captured = new HashMap<String, Integer>();
		Map<String, Integer> games = new HashMap<String, Integer>();
		Map<String, Integer> moves = new HashMap<String, Integer>();

		File game_out_file = new File("games.txt");
		if (!game_out_file.exists()) {
			game_out_file.createNewFile();
		}

		PrintWriter game_out = new PrintWriter(game_out_file);
		game_out.write("White\tBlack\tEnding\tMoves\tWhite Captured\tBlack Captured\tWhite moves\tBlack moves\n\n");

		File players_out_file = new File("players.txt");
		if (!players_out_file.exists()) {
			players_out_file.createNewFile();
		}

		PrintWriter players_out = new PrintWriter(players_out_file);
		players_out
				.write("Player\tPoints\tWins\tLosses\tDraws\tCaptures\tCaptured\tMove points\tWin moves\tLoss moves\tTot moves\t"
						+ "AVG Points\tAVG Wins\tAVG Losses\tAVG Draws\tAVG Captures\tAVG Captured\tAVG moves"
						+ "\n\n");

		try (Stream<Path> path_stream = Files.list(Paths.get("logs").toAbsolutePath())) {
			List<String> path_list = path_stream.filter(Files::isRegularFile).map(x -> x.toString())
					.collect(Collectors.toList());

			for (String file_path : path_list) {
				if (file_path.contains("_vs_")) {
					File file = new File(file_path);

					br = new BufferedReader(new FileReader(file));

					String whiteP = "whiteP";
					String blackP = "blackP";
					int blackcaptured = 0;
					int whitecaptured = 0;

					int turn_counter = 0;

					Turn ending = null;

					String line;
					while ((line = br.readLine()) != null) {

						line = new String(line.getBytes(), "UTF-8");

						if (line.contains("Players")) {
							System.out.println(line);
							String[] splits = line.split(":");
							int len = splits.length;
							line = line.split(":")[len - 1];
							splits = line.split("vs");
							len = splits.length;
							whiteP = splits[0].replaceAll("_", "").replaceAll("\t", "");

							blackP = splits[1].replaceAll("_", "").replaceAll("\t", "");

							// TODO: this is unnecessary if the server writes
							// things
							// properly
							String temp = "";
							for (int i = 0; i < blackP.length() && i < 10; i++) {
								char c = blackP.charAt(i);
								if (Character.isAlphabetic(c) || Character.isDigit(c))
									temp += c;
							}
							blackP = temp;

						} else if (line.contains("Turn"))
							turn_counter++;
						else if (line.contains("bianca rimossa"))
							whitecaptured++;
						else if (line.contains("nera rimossa"))
							blackcaptured++;
						else if (line.contains("D"))
							ending = Turn.DRAW;
						else if (line.contains("BW"))
							ending = Turn.BLACKWIN;
						else if (line.contains("WW"))
							ending = Turn.WHITEWIN;
						else if (line.contains("W"))
							ending = Turn.BLACKWIN;
						else if (line.contains("B"))
							ending = Turn.WHITEWIN;
					}

					int black_turns = turn_counter / 2;
					int white_turns = turn_counter / 2 + (turn_counter % 2);

					for (Map<String, Integer> map : new Map[] { wins, draws, losses, winmoves, lossmoves, captures,
							moves, games, captured }) {
						if (!map.containsKey(whiteP)) {
							map.put(whiteP, 0);
						}
						if (!map.containsKey(blackP)) {

							map.put(blackP, 0);
						}
					}

					if (ending != null) {

						captures.put(whiteP, captures.get(whiteP) + blackcaptured);
						captured.put(blackP, captured.get(blackP) + blackcaptured);
						captures.put(blackP, captures.get(blackP) + whitecaptured);
						captured.put(whiteP, captured.get(whiteP) + whitecaptured);

						games.put(blackP, games.get(blackP) + 1);
						games.put(whiteP, games.get(whiteP) + 1);

						moves.put(blackP, moves.get(blackP) + black_turns);
						moves.put(whiteP, moves.get(whiteP) + white_turns);

						switch (ending) {
						case DRAW:
							draws.put(whiteP, draws.get(whiteP) + 1);
							draws.put(blackP, draws.get(blackP) + 1);
							break;
						case BLACKWIN:
							losses.put(whiteP, losses.get(whiteP) + 1);
							wins.put(blackP, wins.get(blackP) + 1);
							lossmoves.put(whiteP, lossmoves.get(whiteP) + white_turns);
							winmoves.put(blackP, winmoves.get(blackP) + black_turns);
							break;
						case WHITEWIN:
							wins.put(whiteP, wins.get(whiteP) + 1);
							losses.put(blackP, losses.get(blackP) + 1);
							winmoves.put(whiteP, winmoves.get(whiteP) + white_turns);
							lossmoves.put(blackP, lossmoves.get(blackP) + black_turns);
							break;
						default:
							break;
						}

						game_out.write(
								whiteP + "\t" + blackP + "\t" + ending + "\t" + turn_counter + "\t" + whitecaptured
										+ "\t" + blackcaptured + "\t" + white_turns + "\t" + black_turns + "\n");
					} else
						game_out.write("ERROR IN " + whiteP + " vs " + blackP + "\n");

					game_out.flush();
					br.close();

				}
			}

			game_out.close();

			for (String name : wins.keySet()) {
				int num_losses = losses.get(name);
				int norm_loss_moves = 0;
				if (num_losses > 0)
					norm_loss_moves = lossmoves.get(name) / num_losses;
				int num_wins = wins.get(name);
				int norm_win_moves = 0;
				if (num_wins > 0)
					norm_win_moves = winmoves.get(name) / num_wins;

				players_out.write(name + "\t" + (wins.get(name) * 3 + draws.get(name)) + "\t" + wins.get(name) + "\t"
						+ losses.get(name) + "\t" + draws.get(name) + "\t" + captures.get(name) + "\t"
						+ captured.get(name) + "\t" + (norm_loss_moves - norm_win_moves) + "\t" + (norm_win_moves)
						+ "\t" + (norm_loss_moves) + "\t" + (moves.get(name)) + "\t"
						+ (wins.get(name) * 3 + draws.get(name)) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ wins.get(name) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ losses.get(name) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ draws.get(name) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ captures.get(name) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ captured.get(name) * 1.0 / (games.get(name) * 1.0) + "\t"
						+ (moves.get(name) * 1.0 / (games.get(name) * 1.0)) + "\n");
			}
			players_out.close();

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

}
