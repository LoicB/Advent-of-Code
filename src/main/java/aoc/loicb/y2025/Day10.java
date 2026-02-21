package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day10 implements Day<List<FactoryMachine>, Integer> {
    private final static double EPS = 1e-12;

    public static void main(String[] args) {
        DayExecutor<List<FactoryMachine>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public FactoryMachine transformObject(String string) {
                return FactoryMachine.fromLine(string);
            }
        });
        de.execute(new Day10());
    }

    @Override
    public Integer partOne(List<FactoryMachine> input) {
        return input.stream().mapToInt(this::findFewestButtonPresses).sum();
    }

    int findFewestButtonPresses(FactoryMachine factoryMachine) {
        return findFewestButtonPresses(factoryMachine.lightDiagram(), factoryMachine.buttons());
    }

    int findFewestButtonPresses(char[] lightDiagram, List<Button> buttons) {
        char[] lights = new char[lightDiagram.length];
        Arrays.fill(lights, '.');
        Queue<LightsState> queue = new LinkedList<>();
        queue.add(new LightsState(lights, 0));
        Set<String> visited = new HashSet<>();
        visited.add(Arrays.toString(lights));
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (Arrays.equals(current.v, lightDiagram)) {
                return current.steps;
            }
            buttons.forEach(button -> {
                var newLights = pushButton(current.v, button.indexes());
                String key = Arrays.toString(newLights);
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.add(new LightsState(newLights, current.steps + 1));
                }
            });
        }
        return -1;
    }

    char[] pushButton(char[] indicatorLights, int[] buttonIndexes) {
        var newLights = new char[indicatorLights.length];
        System.arraycopy(indicatorLights, 0, newLights, 0, indicatorLights.length);
        for (int index : buttonIndexes) {
            newLights[index] = switchLight(newLights[index]);
        }
        return newLights;
    }


    private char switchLight(char c) {
        return c == '.' ? '#' : '.';
    }


    @Override
    public Integer partTwo(List<FactoryMachine> input) {
        return input.stream().mapToInt(this::findFewestButtonPressesJoltage).sum();
    }

    int findFewestButtonPressesJoltage(FactoryMachine factoryMachine) {
        return findFewestButtonPressesJoltage(factoryMachine.joltages(), factoryMachine.buttons());
    }

    int findFewestButtonPressesJoltage(int[] targetJoltages, List<Button> buttons) {
        var mat = createMatrix(targetJoltages, buttons);
        double[] current = new double[mat[0].length - 1];
        Arrays.fill(current, Double.NaN);
        var reducedMat = gaussianElimination(mat);
        var result = solveEquation(reducedMat, current);
        return (int) result;
    }

    private double solveEquation(double[][] mat, double[] start) {
        double solution = Double.MAX_VALUE;
        Deque<SolutionWrapper> queue = new ArrayDeque<>();
        queue.add(new SolutionWrapper(start));
        Set<SolutionWrapper> history = new HashSet<>();
        while (!queue.isEmpty()) {
            var current = queue.poll();
            var currSolution = current.data();
            var partialSum = partialSum(currSolution);
            if (partialSum.value() >= solution) continue;
            if (partialSum.complete()) {
                solution = partialSum.value();
            } else {
                for (int row = mat.length - 1; row >= 0; row--) {
                    List<Integer> unknowns = new ArrayList<>();
                    List<Double> values = new ArrayList<>();
                    double rhs = mat[row][mat[row].length - 1];
                    boolean allNonNegative = true;
                    boolean anyNonZero = false;
                    for (int c = 0; c < mat[0].length - 1 && allNonNegative; c++) {
                        if (Math.abs(mat[row][c]) > EPS && Double.isNaN(currSolution[c])) {
                            unknowns.add(c);
                            values.add(mat[row][c]);
                            if (mat[row][c] < 0) allNonNegative = false;
                            if (Math.abs(mat[row][c]) > EPS) anyNonZero = true;
                        } else if (!Double.isNaN(currSolution[c])) {
                            rhs -= mat[row][c] * currSolution[c];
                        }
                    }
                    if (allNonNegative && anyNonZero) {
                        List<SolutionWrapper> solutions = new ArrayList<>();
                        solveEquation(values, unknowns, 0, rhs, currSolution, solutions);
                        solutions.forEach(sol -> {
                            if (history.add(sol)) queue.add(sol);
                        });
                    }
                }
            }
        }
        return solution;

    }

    private PartialSum partialSum(double[] current) {
        var sum = 0L;
        boolean complete = true;
        for (double dbl : current) {
            if (!Double.isNaN(dbl)) {
                sum += Math.round(dbl);
            } else {
                complete = false;
            }
        }
        return new PartialSum(sum, complete);
    }

    void solveEquation(List<Double> values,
                       List<Integer> unknowns,
                       int index,
                       double rhs,
                       double[] current,
                       List<SolutionWrapper> solutions) {
        if (index == values.size() - 1) {
            var a = values.get(index);
            if (Math.abs(a) < EPS) return;
            var sol = solveEquation(a, rhs);
            if (!Double.isNaN(sol)) {
                current[unknowns.get(index)] = sol;
                solutions.add(new SolutionWrapper(current));
            }
        } else {
            var a = values.get(index);
            if (Math.abs(a) < EPS) return;
            long maxX = (long) Math.floor((rhs + EPS) / a);
            for (long x = 0; x <= maxX; x++) {
                double remaining = rhs - a * x;
                if (remaining < -EPS) break;
                current[unknowns.get(index)] = x;
                solveEquation(values, unknowns, index + 1, remaining, current, solutions);
            }
        }
    }

    private double solveEquation(double lhs, double rhs) {
        double value = rhs / lhs;
        long rounded = Math.round(value);
        if (rounded >= 0 && Math.abs(value - rounded) < EPS) {
            return rounded;
        }
        return Double.NaN;
    }

//    private Optional<Double> solveEquation(double lhs, double rhs) {
//        double value = rhs / lhs;
//        long rounded = Math.round(value);
//        if (rounded >= 0 && Math.abs(value - rounded) < EPS) {
//            return Optional.of(value);
//        }
//        return Optional.empty();
//    }

    double[][] createMatrix(int[] targetJoltages, List<Button> buttons) {
        int rows = targetJoltages.length;
        int cols = buttons.size();
        double[][] mat = new double[rows][cols + 1];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int idx : buttons.get(c).indexes()) {
                    if (idx == r) {
                        mat[r][c] = 1.0;
                        break;
                    }
                }
            }
            mat[r][cols] = targetJoltages[r];
        }
        return mat;
    }

    private double[][] gaussianElimination(double[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int r = 0;
        for (int c = 0; c < cols - 1 && r < rows; c++) {

            int pivotIndex = findPivot(mat, r, c);
            if (Math.abs(mat[pivotIndex][c]) > EPS) {
                if (pivotIndex != r) {
                    swapRows(mat, pivotIndex, r);
                }


                double pivotVal = mat[r][c];
                divideRow(mat[r], pivotVal);
                subtractRows(mat, r, c);
                r++;
            }
        }

        return mat;
    }

    private int findPivot(double[][] mat, int row, int column) {
        int pivotIndex = row;
        double maxAbs = Math.abs(mat[row][column]);
        for (int i = row + 1; i < mat.length; i++) {
            double v = Math.abs(mat[i][column]);
            if (v > maxAbs) {
                maxAbs = v;
                pivotIndex = i;
            }
        }
        return pivotIndex;
    }

    private void swapRows(double[][] mat, int row1, int row2) {
        if (row1 != row2) {
            for (int j = 0; j < mat[0].length; j++) {
                double tmp = mat[row1][j];
                mat[row1][j] = mat[row2][j];
                mat[row2][j] = tmp;
            }
        }
    }

    void divideRow(double[] row, double div) {
        for (int i = 0; i < row.length; i++) {
            row[i] /= div;
        }
    }

    void subtractRows(double[][] mat, int row, int column) {
        for (int i = 0; i < mat.length; i++) {
            if (i != row) {
                double factor = mat[i][column] / mat[row][column];
                subtractRows(mat[row], mat[i], factor);
            }
        }
    }

    void subtractRows(double[] row1, double[] row2, double factor) {
        for (int i = 0; i < row1.length; i++) {
            row2[i] -= (factor * row1[i]);
        }
    }

    record SolutionWrapper(double[] data, int hash) {
        SolutionWrapper(double[] data) {
            this(data.clone(), Arrays.hashCode(data));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SolutionWrapper other
                    && Arrays.equals(this.data, other.data);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    record PartialSum(double value, boolean complete) {
    }

    private static class LightsState {
        char[] v;
        int steps;

        LightsState(char[] v, int steps) {
            this.v = v;
            this.steps = steps;
        }
    }

}

record Button(int... indexes) {
}


record FactoryMachine(char[] lightDiagram, List<Button> buttons, int[] joltages) {
    static FactoryMachine fromLine(String line) {
        var split = line.split(" ");
        char[] lightDiagram = line.substring(1, line.indexOf(']'))
                .toCharArray();
        List<Button> buttons = new ArrayList<>();
        for (int i = 1; i < split.length - 1; i++) {
            var numbers = split[i].substring(1, split[i].length() - 1).split(",");
            int[] buttonNumber = new int[numbers.length];
            for (int j = 0; j < numbers.length; j++) {
                buttonNumber[j] = Integer.parseInt(numbers[j]);
            }
            buttons.add(new Button(buttonNumber));
        }
        List<Integer> joltages = Arrays.stream(split[split.length - 1].substring(1, split[split.length - 1].length() - 1).split(",")).mapToInt(Integer::parseInt).boxed().toList();
        int[] jolt = new int[joltages.size()];
        for (int i = 0; i < joltages.size(); i++) {
            jolt[i] = joltages.get(i);
        }
        return new FactoryMachine(lightDiagram, buttons, jolt);
    }

    @Override
    public String toString() {
        StringBuilder sbLights = new StringBuilder();
        for (char c : lightDiagram) {
            sbLights.append(c);
        }
        StringBuilder sbButtons = new StringBuilder();
        buttons.forEach(integers -> sbButtons.append('(').append(integers).append(')').append(' '));
        StringBuilder sbJolt = new StringBuilder();
        for (int nb : joltages) {
            sbJolt.append(nb).append(',');
        }
        return "[" + sbLights + "]"
                + sbButtons
                + "{" + sbJolt + "}"
                ;
    }
}

