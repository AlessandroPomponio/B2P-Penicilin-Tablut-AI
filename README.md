# Team B2P - PCN Tablut AI

![Java CI](https://github.com/AlessandroPomponio/B2P-Penicilin-Tablut-AI/workflows/Java%20CI/badge.svg?branch=master)
![Javadoc](https://img.shields.io/badge/Javadoc-available-brightgreen)

<p align="center"> 
   <img src="logo/logo.png">
</p>

Penicillin (PCN) is a Java-based intelligent agent built to take part in the 2020 students' challenge
of the Fundamentals of AI course held at the University of Bologna by prof. Paola Mello and prof. Federico Chesani. 
 
The agent is designed to play Tablut with the Ashton rules ([learn more here](https://www.heroicage.org/issues/13/ashton.php))
using Andrea Galassi's server available in [this repository](https://github.com/AGalassi/TablutCompetition).

## Challenge results

Penicillin placed 2nd out of 11 teams in the challenge. Congratulations to [Team Fritto Misto](https://github.com/virtualms/Tablut2020_FrittoMisto) for winning!  
Penicillin also became the "Reaper" record holder, for the highest average captures: 6.31 (the previous record was 5.75).

## A brief description

 - The game state is implemented via a [BitSet](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/BitSet.html)
    to minimize memory occupation needed to represent the board.
 - The [Minimax with alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) search algorithm is made concurrent through the use of [Futures](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/concurrent/Future.html)
 - A simple heuristic guarantees a deep exploration of the game tree during the [iterative deepening](https://en.wikipedia.org/wiki/Iterative_deepening_depth-first_search) process.

## How to run

You can download the latest jar file from the GitHub Actions pipeline [here](https://github.com/AlessandroPomponio/B2P-Penicilin-Tablut-AI/actions?query=workflow%3A%22Java+CI%22).  
You can download the latest **stable release** from the Releases section [here](https://github.com/AlessandroPomponio/B2P-Penicilin-Tablut-AI/releases).

You will then simply need to run:

```
java -jar B2P-Penicillin.jar <black|white> <timeout-in-seconds> <server-ip>
```

## Javadoc

The Javadoc documentation is available [here](https://alessandropomponio.github.io/B2P-Penicilin-Tablut-AI/)

## Team B2P

- [Alessandro Buldini](https://github.com/occhialidaleso) 
- [Alessandro Pomponio](https://github.com/AlessandroPomponio)
- [Federico Zanini](https://github.com/federicozanini)

## Credits

Icons by [Flat Icons](https://www.flaticon.com/authors/flat-icons) from [Flaticon](https://www.flaticon.com/)  
Font Pixeboy by [Abas Creative](https://www.fontspace.com/abascreative) from [Fontspace](https://www.fontspace.com/)
