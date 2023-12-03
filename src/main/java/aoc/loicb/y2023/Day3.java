package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
import java.util.stream.IntStream;

public class Day3 implements Day<char[][], Integer> {
    public static void main(String[] args) {
        DayExecutor<char[][]> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            char[][] input = new char[inputLines.length][];
            IntStream.range(0, inputLines.length).forEach(value -> input[value] = inputLines[value].toCharArray());
            return input;
        });
        de.execute(new Day3());

    }

    @Override
    public Integer partOne(char[][] schematic) {
        // 523948
        // 525119
        // 596757
        boolean[][] valid = new boolean[schematic.length][];
        for (int i = 0; i < schematic.length; i++) {
            valid[i] = new boolean[schematic[i].length];
            for (int j = 0; j < schematic[i].length; j++) {
                valid[i][j] = false;
            }
        }
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                validatePosition(valid, schematic, i, j);
            }
        }
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                System.out.print(valid[i][j] ? '.' : schematic[i][j]);
            }
            System.out.println();
        }
        int sum = 0;
        for (int i = 0; i < schematic.length; i++) {
            int number = 0;
            boolean isNumberValid = false;
            for (int j = 0; j < schematic[i].length; j++) {
//                System.out.println(i + " " + j + " " + schematic[i][j]);
                if (Character.isDigit(schematic[i][j])) {
                    isNumberValid |= valid[i][j];
//                    System.out.println("Before "+number);
//                    System.out.println( schematic[i][j] );
                    number = 10 * number + schematic[i][j] - '0';
//                    System.out.println("After "+number);
                } else {
                    if (Character.isDigit(schematic[i][j])) {
                        System.out.println(i + " " + j + " " + schematic[i][j]);
                    }
                    if (isNumberValid) sum += number;
                    isNumberValid = false;
                    number = 0;
                }
            }
            if (isNumberValid) sum += number;
        }
        return sum;
    }

    private Map<Position, List<Integer>> indexing(char[][] schematic) {
        Map<Position, List<Integer>> index = new HashMap<>();
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                if (isSymbol(schematic[i][j])) {
                    index.put(new Position(i, j), getAdjacentNumbers(schematic, i, j));
                }
            }
        }
        return index;
    }

    List<Integer> getAdjacentNumbers(char[][] schematic, int x, int y) {
        System.out.println(schematic[x][y]);
        List<Integer> numbers = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            int number = 0;
            for (int j = Math.max(0, y - 3); j < Math.min(schematic[x].length, y + 3); j++) {
                if (Character.isDigit(schematic[i][j])) {
                    number = 10 * number + (schematic[i][j] - '0');
                } else {
                    if (number != 0) numbers.add(number);
                    number = 0;
                }
            }
            if (number != 0) numbers.add(number);
        }
        return numbers;
    }

    boolean validatePosition(boolean[][] valid, char[][] schematic, int x, int y) {
        if (Character.isDigit(schematic[x][y]) || schematic[x][y] == '.') return false;
        System.out.println(x + " " + y);
        valid[x - 1][y - 1] = true;
        valid[x - 1][y] = true;
        valid[x - 1][y + 1] = true;
        valid[x][y - 1] = true;
//        valid[x][y]= true;
        valid[x][y + 1] = true;
        valid[x + 1][y - 1] = true;
        valid[x + 1][y] = true;
        valid[x + 1][y + 1] = true;
        return true;
    }

    boolean validatePositionOld(boolean[][] valid, char[][] schematic, int x, int y) {
        if (Character.isDigit(schematic[x][y]) || schematic[x][y] == '.') return false;
        return validatePositionCycle(valid, schematic, x - 1, y - 1)
                | validatePositionCycle(valid, schematic, x - 1, y)
                | validatePositionCycle(valid, schematic, x - 1, y + 1)
                | validatePositionCycle(valid, schematic, x, y - 1)
                | validatePositionCycle(valid, schematic, x, y + 1)
                | validatePositionCycle(valid, schematic, x + 1, y - 1)
                | validatePositionCycle(valid, schematic, x + 1, y)
                | validatePositionCycle(valid, schematic, x + 1, y + 1)
                ;
    }

    boolean validatePositionCycle(boolean[][] valid, char[][] schematic, int x, int y) {
        try {
            System.out.println(schematic[x][y]);
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
        System.out.println(x + " " + y);
        if (valid[x][y]) return true;
        if (schematic[x][y] == '.') return false;
        if (Character.isDigit(schematic[x][y])) {
            valid[x][y] = true;
            validatePositionCycle(valid, schematic, x - 1, y - 1);
            validatePositionCycle(valid, schematic, x - 1, y);
            validatePositionCycle(valid, schematic, x - 1, y + 1);
            validatePositionCycle(valid, schematic, x, y - 1);
            validatePositionCycle(valid, schematic, x, y + 1);
            validatePositionCycle(valid, schematic, x + 1, y - 1);
            validatePositionCycle(valid, schematic, x + 1, y);
            validatePositionCycle(valid, schematic, x + 1, y + 1);
            return true;
        }
        return false;
    }

    boolean validatePart(char[][] schematic, int x, int y) {
        if (findAdjacent(schematic, x, y).stream().anyMatch(this::isSymbol)) return true;
        if (y < schematic[x].length - 1 && Character.isDigit(schematic[x][y + 1]))
            return validatePart(schematic, x, y + 1);
        return false;
    }

    private List<Character> findAdjacent(char[][] schematic, int x, int y) {
        List<Character> adjacents = new ArrayList<>();
        for (int i = Math.max(0, x - 1); i <= Math.min(schematic.length - 1, x + 1); i++) {
            if (y > 0) adjacents.add(schematic[i][y - 1]);
            if (i != x) adjacents.add(schematic[i][y]);
            if (y < schematic[x].length - 1) adjacents.add(schematic[i][y + 1]);
        }
        return adjacents;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }


    Optional<Integer> extractPart(char[][] schematic, int x, int y) {
        return Optional.empty();
    }

    @Override
    public Integer partTwo(char[][] schematic) {
        int[][] valid = new int[schematic.length][];
        for (int i = 0; i < schematic.length; i++) {
            valid[i] = new int[schematic[i].length];
            for (int j = 0; j < schematic[i].length; j++) {
                valid[i][j] = 0;
            }
        }
        int count = 1;
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                if (validatePositionPart2(valid, schematic, i, j, count)) {
                    count++;
                }
            }
        }
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                System.out.print(valid[i][j] != 0 ? '.' : schematic[i][j]);
            }
            System.out.println();
        }
        int sum = 1;
        int[] gearRatios = new int[count];
        Arrays.fill(gearRatios, 1);
        for (int i = 0; i < schematic.length; i++) {
            int number = 0;
            int isNumberValid = 0;
            for (int j = 0; j < schematic[i].length; j++) {
//                System.out.println(i + " " + j + " " + schematic[i][j]);
                if (Character.isDigit(schematic[i][j])) {
                    if (isNumberValid == 0) isNumberValid += valid[i][j];
//                    System.out.println("Before "+number);
//                    System.out.println( schematic[i][j] );
                    number = 10 * number + schematic[i][j] - '0';
//                    System.out.println("After "+number);
                } else {
                    if (Character.isDigit(schematic[i][j])) {
                        System.out.println(i + " " + j + " " + schematic[i][j]);
                    }
                    if (isNumberValid != 0) {
                        System.out.println(number);
                        sum *= number;
                        gearRatios[isNumberValid] *= number;
                    }
                    isNumberValid = 0;
                    number = 0;
                }
            }
//            System.out.println(number);
            if (isNumberValid != 0) {
                System.out.println(number);
                sum *= number;
                gearRatios[isNumberValid] *= number;
            }
        }
        System.out.println(Arrays.toString(gearRatios));
        return Arrays.stream(gearRatios).sum() - 1;
    }


    boolean validatePositionPart2(int[][] valid, char[][] schematic, int x, int y, int id) {
        if (schematic[x][y] != '*') return false;

        int count = 0;
        int tmpCount = 0;
        tmpCount += Character.isDigit(schematic[x - 1][y - 1]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x - 1][y]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x - 1][y + 1]) ? 1 : 0;
        if (tmpCount == 3) count++;
        if (tmpCount == 2) count += Character.isDigit(schematic[x - 1][y]) ? 1 : 2;
        if (tmpCount == 1) count++;

        tmpCount = 0;
        tmpCount += Character.isDigit(schematic[x][y - 1]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x][y]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x][y + 1]) ? 1 : 0;
        if (tmpCount == 3) count++;
        if (tmpCount == 2) count += Character.isDigit(schematic[x][y]) ? 1 : 2;
        if (tmpCount == 1) count++;

        tmpCount = 0;
        tmpCount += Character.isDigit(schematic[x + 1][y - 1]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x + 1][y]) ? 1 : 0;
        tmpCount += Character.isDigit(schematic[x + 1][y + 1]) ? 1 : 0;
        if (tmpCount == 3) count++;
        if (tmpCount == 2) count += Character.isDigit(schematic[x + 1][y]) ? 1 : 2;
        if (tmpCount == 1) count++;
        if (count == 2) {
            valid[x - 1][y - 1] = id;
            valid[x - 1][y] = id;
            valid[x - 1][y + 1] = id;
            valid[x][y - 1] = id;
            valid[x][y] = id;
            valid[x][y + 1] = id;
            valid[x + 1][y - 1] = id;
            valid[x + 1][y] = id;
            valid[x + 1][y + 1] = id;
        }
        return count == 2;
    }

    record Position(int x, int y) {
    }
}
