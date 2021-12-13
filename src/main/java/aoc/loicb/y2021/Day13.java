package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements Day<TransparentPaper, Integer> {
    public static void main(String[] args) {
        DayExecutor<TransparentPaper> de = new DayExecutor<>(rawInput -> {
            List<Point> dots = new ArrayList<>();
            List<FoldingInstruction> foldingInstructions = new ArrayList<>();
            String[] inputLines = rawInput.split("\\r?\\n");
            for (String inputLine : inputLines) {
                if (inputLine.contains(",")) {
                    String[] dotElement = inputLine.split(",");
                    dots.add(new Point(Integer.parseInt(dotElement[0]), Integer.parseInt(dotElement[1])));
                } else if (inputLine.startsWith("fold along")) {
                    String[] instructions = inputLine.split("[ =]");
                    foldingInstructions.add(new FoldingInstruction(instructions[2].charAt(0), Integer.parseInt(instructions[3])));
                }
            }
            return new TransparentPaper(dots, foldingInstructions);
        });
        de.execute(new Day13());
    }

    @Override
    public Integer partOne(TransparentPaper transparentPaper) {
        boolean[][] grid = generateGrid(transparentPaper);
        return calculateNumberOfDots(foldPaper(grid, transparentPaper.foldingInstructions().get(0)));
    }

    protected boolean[][] generateGrid(TransparentPaper transparentPaper) {
        int maxX = transparentPaper.foldingInstructions()
                .stream()
                .filter(foldingInstruction -> foldingInstruction.axis() == 'x')
                .mapToInt(value -> 2 * value.line())
                .max()
                .orElseThrow();
        int maxY = transparentPaper.foldingInstructions()
                .stream()
                .filter(foldingInstruction -> foldingInstruction.axis() == 'y')
                .mapToInt(value -> 2 * value.line())
                .max()
                .orElseThrow();
        boolean[][] grid = initGird(maxY + 1, maxX + 1);
        transparentPaper.dots().forEach(point -> grid[point.y()][point.x()] = true);
        return grid;
    }

    protected boolean[][] generateGrid(List<Point> dots) {
        int maxX = dots.stream().mapToInt(Point::x).max().orElseThrow();
        int maxY = dots.stream().mapToInt(Point::y).max().orElseThrow();
        boolean[][] grid = initGird(maxY + 1, maxX + 1);
        dots.forEach(point -> grid[point.y()][point.x()] = true);
        return grid;
    }

    protected boolean[][] foldPaper(boolean[][] grid, FoldingInstruction foldingInstruction) {
        if (foldingInstruction.axis() == 'x') return foldVertically(grid, foldingInstruction.line());
        return foldHorizontally(grid, foldingInstruction.line());
    }

    protected boolean[][] foldHorizontally(boolean[][] grid, int line) {
        boolean[][] newGrid = initGird(line, grid[0].length);
        for (int i = 0; i < newGrid.length; i++) {
            for (int j = 0; j < newGrid[i].length; j++) {
                newGrid[i][j] = grid[i][j] || grid[grid.length - i - 1][j];
            }
        }
        return newGrid;
    }


    protected boolean[][] foldVertically(boolean[][] grid, int line) {
        int newGridHeight = grid.length;
        int newGridWidth = grid[0].length / 2;
        boolean[][] newGrid = initGird(newGridHeight, newGridWidth);

        for (int i = 0; i < newGrid.length; i++) {
            for (int j = 0; j < newGrid[i].length; j++) {
                newGrid[i][j] = grid[i][j] || grid[i][grid[i].length - j - 1];
            }
        }
        return newGrid;
    }

    protected void printGrid(boolean[][] grid) {
        for (boolean[] line : grid) {
            for (boolean dot : line) {
                System.out.print(dot ? "#" : ".");
            }
            System.out.println();
        }
    }


    private boolean[][] initGird(int height, int width) {
        boolean[][] grid = new boolean[height][];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new boolean[width];
            for (int j = 0; j < width; j++) {
                grid[i][j] = false;
            }
        }
        return grid;
    }

    private int calculateNumberOfDots(boolean[][] grid) {

        int count = 0;
        for (boolean[] line : grid) {
            for (boolean dot : line) {
                if (dot) count++;
            }
        }
        return count;
    }

    @Override
    public Integer partTwo(TransparentPaper transparentPaper) {
        boolean[][] grid = generateGrid(transparentPaper);
        for (FoldingInstruction foldingInstruction : transparentPaper.foldingInstructions()) {
            grid = foldPaper(grid, foldingInstruction);
        }
        printGrid(grid);
        return calculateNumberOfDots(grid);
    }
}


record TransparentPaper(List<Point> dots, List<FoldingInstruction> foldingInstructions) {
}

record FoldingInstruction(char axis, int line) {
}