package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 implements Day<List<Equation>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<Equation>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Equation transformObject(String string) {
                String[] input = string.split(":");
                long testValue = Long.parseLong(input[0]);
                List<Integer> values = Arrays
                        .stream(input[1].trim().split(" "))
                        .map(Integer::parseInt)
                        .toList();
                return new Equation(testValue, values);
            }
        });
        de.execute(new Day7());
    }

    @Override
    public Long partOne(List<Equation> input) {
        return input
                .stream()
                .filter(this::isEquationValid)
                .mapToLong(Equation::testValue)
                .sum();
    }

    protected boolean isEquationValid(Equation equation) {
        return calculateSolutions(equation.values())
                .stream()
                .anyMatch(integer -> integer == equation.testValue());
    }

    protected List<Long> calculateSolutions(List<Integer> values) {
        List<Long> results = new ArrayList<>();
        calculateSolutions(values.get(0), 1, values, results);
        return results;
    }

    private void calculateSolutions(long score, int index, List<Integer> values, List<Long> results) {
        if (index == values.size()) {
            results.add(score);
        } else {
            calculateSolutions(score + values.get(index), index + 1, values, results);
            calculateSolutions(score * values.get(index), index + 1, values, results);
        }
    }


    @Override
    public Long partTwo(List<Equation> input) {
        return input
                .stream()
                .filter(this::isEquationValidPartTwo)
                .mapToLong(Equation::testValue)
                .sum();
    }


    protected boolean isEquationValidPartTwo(Equation equation) {
        return calculateSolutionsPartTwo(equation)
                .stream()
                .anyMatch(integer -> integer == equation.testValue());
    }

    protected List<Long> calculateSolutionsPartTwo(Equation equation) {
        List<Long> results = new ArrayList<>();
        calculateSolutionsWithConcat(equation.values().get(0), 1, equation, results);
        return results;
    }

    private void calculateSolutionsWithConcat(long score, int index, Equation equation, List<Long> results) {
        if (score <= equation.testValue()) {
            if (index == equation.values().size()) {
                results.add(score);
            } else {
                calculateSolutionsWithConcat(score + equation.values().get(index), index + 1, equation, results);
                calculateSolutionsWithConcat(score * equation.values().get(index), index + 1, equation, results);
                calculateSolutionsWithConcat(concat(score, equation.values().get(index)), index + 1, equation, results);
            }
        }
    }

    private long concat(long number1, int number2) {
        return (long) (number1 * Math.pow(10, numDigits(number2))) + number2;
    }

    int numDigits(long number) {
        if (number == 0) return 0;
        return 1 + (int) (Math.log(number) / Math.log(10));
    }

}

record Equation(long testValue, List<Integer> values) {
}

