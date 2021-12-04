package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day4 implements Day<Bingo, Integer> {
    public static void main(String[] args) {
        DayExecutor<Bingo> de = new DayExecutor<>(new InputToBingo());
        de.execute(new Day4());
    }

    @Override
    public Integer partOne(Bingo bingo) {
        Optional<Board> winningBoard = Optional.empty();
        int index = 0;
        int lastDrawnNumber = 0;
        while (winningBoard.isEmpty()) {
            lastDrawnNumber = bingo.drawnNumbers().get(index++);
            addDrawnNumber(lastDrawnNumber, bingo.boards());
            winningBoard = bingo.boards().stream().filter(Board::hasWin).findAny();
        }
        return lastDrawnNumber * winningBoard.get().score();
    }

    private void addDrawnNumber(int drawnNumber, List<Board> boards) {
        boards.forEach(board -> addDrawnNumber(drawnNumber, board));
    }


    private void addDrawnNumber(int drawnNumber, Board board) {
        boolean found = false;
        for (int i = 0; i < board.getNumbers().length && !found; i++) {
            for (int j = 0; j < board.getNumbers()[i].length && !found; j++) {
                if (board.getNumbers()[i][j] == drawnNumber) {
                    found = true;
                    board.getMarked()[i][j] = true;
                }
            }
        }
    }

    @Override
    public Integer partTwo(Bingo bingo) {
        boolean winner = false;
        int index = 0;
        int lastDrawnNumber = 0;
        int score = 0;
        while (!winner) {
            lastDrawnNumber = bingo.drawnNumbers().get(index++);
            addDrawnNumber(lastDrawnNumber, bingo.boards());
            winner = bingo.boards().stream().allMatch(Board::hasWin);
            if (!winner) score = bingo.boards().stream().filter(board -> !board.hasWin()).mapToInt(Board::score).sum();
        }
        return (score - lastDrawnNumber) * lastDrawnNumber;
    }
}

record Bingo(List<Integer> drawnNumbers, List<Board> boards) {
}

class Board {
    private final int[][] numbers;
    private final boolean[][] marked;

    Board(int[][] numbers) {
        boolean[][] marked = new boolean[numbers.length][];
        for (int i = 0; i < numbers.length; i++) {
            marked[i] = new boolean[numbers[i].length];
        }
        this.marked = marked;
        this.numbers = numbers;
    }

    int[][] getNumbers() {
        return numbers;
    }

    boolean[][] getMarked() {
        return marked;
    }

    int score() {
        int score = 0;
        for (int i = 0; i < marked.length; i++) {
            for (int j = 0; j < marked[i].length; j++) {
                if (!marked[i][j]) score += numbers[i][j];
            }
        }
        return score;
    }

    boolean hasWin() {
        for (boolean[] row : marked) {
            if (isRowWinning(row)) return true;
        }
        boolean[][] markedTransposed = transpose(marked);
        for (boolean[] row : markedTransposed) {
            if (isRowWinning(row)) return true;
        }
        return false;
    }

    private boolean isRowWinning(boolean[] row) {
        for (boolean b : row) {
            if (!b) return false;
        }
        return true;
    }

    private boolean[][] transpose(boolean[][] marked) {
        int m = marked.length;
        int n = marked[0].length;
        boolean[][] transposed = new boolean[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = marked[i][j];
            }
        }
        return transposed;
    }
}

class InputToBingo implements InputTransformer<Bingo> {

    @Override
    public Bingo transform(String rawInput) {
        String[] arr = rawInput.split("\\n\\n");
        List<Integer> drawnNumbers = Arrays
                .stream(arr[0].split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Board> boards = new ArrayList<>(arr.length - 1);
        for (int i = 1; i < arr.length; i++) {
            int[][] numbers = new int[5][];
            String[] grid = arr[i].split("\n");
            for (int j = 0; j < grid.length; j++) {
                numbers[j] = Arrays.stream(grid[j].split(" ")).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
            }
            boards.add(new Board(numbers));
        }
        return new Bingo(drawnNumbers, boards);
    }


}