package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

public class Day21 implements Day<int[], Number> {

    public static void main(String[] args) {
        DayExecutor<int[]> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            int positionPlayerOne = Integer.parseInt(inputLines[0].split(":")[1].trim());
            int positionPlayerTwo = Integer.parseInt(inputLines[1].split(":")[1].trim());
            return new int[]{positionPlayerOne, positionPlayerTwo};
        });
        de.execute(new Day21());
    }

    @Override
    public Integer partOne(int[] positions) {
        return playGame(positions[0], positions[1]);
    }

    private int playGame(int playerOne, int playerTwo) {
        int[] positions = new int[]{playerOne, playerTwo};
        int[] scores = new int[]{0, 0};
        int currentPlayer = 0;
        int dice = 1;
        while (scores[0] < 1000 && scores[1] < 1000) {
            int diceScore = 3 * dice + 3;
            dice += 3;
            positions[currentPlayer] = (positions[currentPlayer] + diceScore - 1) % 10 + 1;
            scores[currentPlayer] = (positions[currentPlayer] + scores[currentPlayer]);
            currentPlayer = 1 - currentPlayer;
        }
        return (dice - 1) * Math.min(scores[0], scores[1]);
    }

    @Override
    public Long partTwo(int[] positions) {
        int[] diceSums = {0, 0, 0, 1, 3, 6, 7, 6, 3, 1};

        long[] score = playDirac(new Player(positions[0], 0), new Player(positions[1], 0), 0, diceSums);
        return Math.max(score[0], score[1]);
    }


    private long[] playDirac(Player playerA, Player playerB, int diceRound, int[] diceSums) {
        if (playerA.score() >= 21) {
            return new long[]{1L, 0L};
        } else if (playerB.score() >= 21) {
            return new long[]{0L, 1L};
        } else {
            var score = new long[]{0L, 0L};
            for (int i = 3; i < 10; i++) {
                var roundScore = playDirac(
                        (diceRound % 2 == 0) ? playerA.move(i) : playerA,
                        (diceRound % 2 == 1) ? playerB.move(i) : playerB, diceRound + 1, diceSums);
                score[0] += roundScore[0] * diceSums[i];
                score[1] += roundScore[1] * diceSums[i];
            }
            return score;
        }
    }
}


record Player(int position, int score) {
    Player move(int numberOfCases) {
        int newPosition = (position + numberOfCases - 1) % 10 + 1;
        return new Player(newPosition, score + newPosition);
    }
}