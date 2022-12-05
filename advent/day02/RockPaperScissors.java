package advent.day02;

import advent.common.util.InputReader;

public class RockPaperScissors {

    private static final int WIN = 6;
    private static final int DRAW = 3;
    private static final int LOSE = 0;

    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    private static int scoreRound(String you, String me) {
        int score = 0;
        if (me.equals("X")) {
            score += ROCK;
            if (you.equals("A")) {
                score += DRAW;
            } else if (you.equals("B")) {
                score += LOSE;
            } else {
                score += WIN;
            }
        } else if (me.equals("Y")) {
            score += PAPER;
            if (you.equals("A")) {
                score += WIN;
            } else if (you.equals("B")) {
                score += DRAW;
            } else {
                score += LOSE;
            }
        } else {
            score += SCISSORS;
            if (you.equals("A")) {
                score += LOSE;
            } else if (you.equals("B")) {
                score += WIN;
            } else {
                score += DRAW;
            }
        }
        return score;
    }

    private static int scoreRoundAlternate(String you, String me) {
        int score = 0;
        if (me.equals("X")) {
            score += LOSE;
            if (you.equals("A")) {
                score += SCISSORS;
            } else if (you.equals("B")) {
                score += ROCK;
            } else {
                score += PAPER;
            }
        } else if (me.equals("Y")) {
            score += DRAW;
            if (you.equals("A")) {
                score += ROCK;
            } else if (you.equals("B")) {
                score += PAPER;
            } else {
                score += SCISSORS;
            }
        } else {
            score += WIN;
            if (you.equals("A")) {
                score += PAPER;
            } else if (you.equals("B")) {
                score += SCISSORS;
            } else {
                score += ROCK;
            }
        }
        return score;
    }

    public static void main(String[] args) {
        int score = 0;
        for (String line : InputReader.readLinesFromInput("advent/day02/input1.txt")) {
            score += scoreRoundAlternate(line.split(" ")[0], line.split(" ")[1]);
        }
        System.out.println("Total: " + score);
    }
}