package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;
import java.util.stream.IntStream;

public class Day7 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day7());

    }

    @Override
    public Number partOne(List<String> input) {
        List<Hand> hands = input
                .stream()
                .map(this::createHand)
                .sorted((hand1, hand2) -> {
                    int score1 = calculateScore(hand1.cards());
                    int score2 = calculateScore(hand2.cards());
                    if (score1 != score2) return score1 - score2;
                    int index = 0;
                    int diff = 0;
                    while (diff == 0 && index < hand2.cards().length()) {
                        diff = getCardValue(hand1.cards().charAt(index)) - getCardValue(hand2.cards().charAt(index));
                        index++;
                    }
                    return diff;
                }).toList();
        return IntStream.range(0, hands.size()).map(index -> (index + 1) * hands.get(index).bet()).sum();
    }


    Hand createHand(String input) {
        String[] inputPart = input.split(" ");
        return new Hand(inputPart[0], Integer.parseInt(inputPart[1]));
    }

    int calculateScore(String cards) {
        int[] count = countCards(cards);
        if (isFiveAKind(count)) return 6;
        if (isFourAKind(count)) return 5;
        if (isFullHouse(count)) return 4;
        if (isThreeAKind(count)) return 3;
        if (isTwoPair(count)) return 2;
        if (isOnePair(count)) return 1;
        return 0;
    }

    boolean isFiveAKind(int[] count) {
        for (int j : count) {
            if (j == 5) return true;
            if (j > 0 && j < 5) return false;
        }
        return false;
    }

    boolean isFourAKind(int[] count) {
        for (int j : count) {
            if (j == 4) return true;
            if (j > 1 && j < 4) return false;
        }
        return false;
    }

    boolean isFullHouse(int[] count) {
        return isThreeAKind(count) && isOnePair(count);
    }


    boolean isThreeAKind(int[] count) {
        for (int j : count) {
            if (j == 3) return true;
        }
        return false;
    }

    boolean isTwoPair(int[] count) {
        int numberOfPair = 0;
        for (int j : count) {
            if (j == 2) numberOfPair++;
        }
        return numberOfPair == 2;
    }

    boolean isOnePair(int[] count) {
        int numberOfPair = 0;
        for (int j : count) {
            if (j == 2) numberOfPair++;
        }
        return numberOfPair == 1;
    }

    private int[] countCards(String cards) {
        int[] count = new int[15];
        for (char c : cards.toCharArray()) {
            count[getCardValue(c)]++;
        }
        return count;
    }

    private int getCardValue(char card) {
        switch (card) {
            case 'T' -> {
                return 10;
            }
            case 'J' -> {
                return 11;
            }
            case 'Q' -> {
                return 12;
            }
            case 'K' -> {
                return 13;
            }
            case 'A' -> {
                return 14;
            }
            default -> {
                return card - '0';
            }
        }
    }


    @Override
    public Number partTwo(List<String> input) {
        List<Hand> hands = input
                .stream()
                .map(this::createHand)
                .sorted((hand1, hand2) -> {
                    int score1 = calculateScorePartTwo(hand1.cards());
                    int score2 = calculateScorePartTwo(hand2.cards());
                    if (score1 != score2) return score1 - score2;
                    int index = 0;
                    int diff = 0;
                    while (diff == 0 && index < hand2.cards().length()) {
                        diff = getCardValuePartTwo(hand1.cards().charAt(index)) - getCardValuePartTwo(hand2.cards().charAt(index));
                        index++;
                    }
                    return diff;
                }).toList();
        return IntStream.range(0, hands.size()).map(index -> (index + 1) * hands.get(index).bet()).sum();
    }

    int calculateScorePartTwo(String cards) {
        int[] count = countCardsPartTwo(cards);
        if (isFiveAKindPartTwo(count)) return 6;
        if (isFourAKindPartTwo(count)) return 5;
        if (isFullHousePartTwo(count)) return 4;
        if (isThreeAKindPartTwo(count)) return 3;
        if (isTwoPairPartTwo(count)) return 2;
        if (isOnePairPartTwo(count)) return 1;
        return 0;
    }


    boolean isFiveAKindPartTwo(int[] count) {
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 5 || (count[i] + count[0] == 5)) return true;
        }
        return false;
    }

    boolean isFourAKindPartTwo(int[] count) {
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 4 || (count[i] + count[0] == 4)) return true;
        }
        return false;
    }

    boolean isFullHousePartTwo(int[] count) {
        int numberOfPair = 0;
        int numberOfThree = 0;
        int numberOfJ = count[0];
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 3 || (count[i] + numberOfJ >= 3)) {
                numberOfThree++;
                numberOfJ--;
            } else if (count[i] == 2 || (count[i] + numberOfJ >= 2)) {
                numberOfPair++;
                numberOfJ--;
            }
        }
        return numberOfThree == 1 && numberOfPair == 1;
    }


    boolean isThreeAKindPartTwo(int[] count) {
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 3 || (count[i] + count[0] == 3)) return true;
        }
        return false;
    }

    boolean isTwoPairPartTwo(int[] count) {
        return countPairs(count) == 2;
    }

    boolean isOnePairPartTwo(int[] count) {
        return countPairs(count) == 1;
    }

    private int countPairs(int[] count) {
        int numberOfPair = 0;
        int numberOfJ = count[0];
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 2 || (count[i] + numberOfJ >= 2)) {
                numberOfPair++;
                numberOfJ--;
            }
        }
        return numberOfPair;
    }


    private int[] countCardsPartTwo(String cards) {
        int[] count = new int[15];
        for (char c : cards.toCharArray()) {
            count[getCardValuePartTwo(c)]++;
        }
        return count;
    }

    private int getCardValuePartTwo(char card) {
        switch (card) {
            case 'T' -> {
                return 10;
            }
            case 'J' -> {
                return 0;
            }
            case 'Q' -> {
                return 12;
            }
            case 'K' -> {
                return 13;
            }
            case 'A' -> {
                return 14;
            }
            default -> {
                return card - '0';
            }
        }
    }

    record Hand(String cards, int bet) {
    }
}
