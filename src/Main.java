import b2p.client.B2PTablutClient;

public class Main {

    public static void main(String[] args) {

        String player = "black";
        int timeout = 30000;

        if (args.length > 0) {
            player = args[0];
        }

        if (args.length > 1) {
            timeout = Integer.parseInt(args[1]);
        }

        try {
            B2PTablutClient client = new B2PTablutClient(player, timeout);
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}