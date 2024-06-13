package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day19());
    }

    @Override
    public Number partOne(List<String> input) {
        List<Part> parts = input
                .stream()
                .filter(this::isLinePart)
                .map(this::extractPart)
                .toList();
        Map<String, Function<Part, String>> keyToFunction = input
                .stream()
                .filter(this::isFunctionPart)
                .collect(Collectors.toMap(this::getWorkflowKey, this::getWorkflowOperations));
        return parts
                .stream()
                .filter(part -> validatePart(part, keyToFunction))
                .mapToInt(this::sumPart)
                .sum();
    }

    private boolean isFunctionPart(String line) {
        return !line.isEmpty() && !line.startsWith("{");
    }

    private boolean isLinePart(String line) {
        return line.startsWith("{");
    }

    String getWorkflowKey(String line) {
        return line.substring(0, line.indexOf('{'));
    }

    Function<Part, String> getWorkflowOperations(String line) {
        List<Operation> operations = getOperations(line);
        return part -> operations
                .stream()
                .filter(operation -> operationMatches(operation, part))
                .findFirst().orElseThrow().outputKey();
    }

    Function<RangePart, List<RangePart>> getWorkflowOperationsPartTwo(String line) {
        List<Operation> operations = getOperations(line);
        return part -> {
            List<RangePart> output = new ArrayList<>();
            Optional<RangePart> currentPart = Optional.of(part);
            for (int i = 0; currentPart.isPresent() && i < operations.size(); i++) {
                Operation operation = operations.get(i);
                RangePart matching = operationMatches(operation, currentPart.get());
                output.add(matching);
                currentPart = operationDoesNotMatch(operation, currentPart.get());
            }
            return output;

        };
    }

    private RangePart operationMatches(Operation operation, RangePart part) {
        if (operation.symbol() == Symbol.NONE) {
            return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS());
        } else if (operation.symbol() == Symbol.SUP) {
            switch (operation.category()) {
                case "x":
                    return new RangePart(operation.outputKey(), operation.targetValue() + 1, part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS());
                case "m":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), operation.targetValue() + 1, part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS());
                case "a":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), operation.targetValue() + 1, part.toA(), part.fromS(), part.toS());
                case "s":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), operation.targetValue() + 1, part.toS());
            }
        } else if (operation.symbol() == Symbol.INF) {
            switch (operation.category()) {
                case "x":
                    return new RangePart(operation.outputKey(), part.fromX(), operation.targetValue() - 1, part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS());
                case "m":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), operation.targetValue() - 1, part.fromA(), part.toA(), part.fromS(), part.toS());
                case "a":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), operation.targetValue() - 1, part.fromS(), part.toS());
                case "s":
                    return new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), operation.targetValue() - 1);
            }
        }
        return part;
    }

    private Optional<RangePart> operationDoesNotMatch(Operation operation, RangePart part) {
        Symbol symbol = operation.symbol();
        String category = operation.category();

        if (symbol == Symbol.NONE) {
            return Optional.empty();
        }

        switch (symbol) {
            case INF:
                switch (category) {
                    case "x":
                        return Optional.of(new RangePart(operation.outputKey(), operation.targetValue(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS()));
                    case "m":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), operation.targetValue(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS()));
                    case "a":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), operation.targetValue(), part.toA(), part.fromS(), part.toS()));
                    case "s":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), operation.targetValue(), part.toS()));
                }
                break;
            case SUP:
                switch (category) {
                    case "x":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), operation.targetValue(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), part.toS()));
                    case "m":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), operation.targetValue(), part.fromA(), part.toA(), part.fromS(), part.toS()));
                    case "a":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), operation.targetValue(), part.fromS(), part.toS()));
                    case "s":
                        return Optional.of(new RangePart(operation.outputKey(), part.fromX(), part.toX(), part.fromM(), part.toM(), part.fromA(), part.toA(), part.fromS(), operation.targetValue()));
                }
                break;
        }
        return Optional.empty();
    }

    private boolean operationMatches(Operation operation, Part part) {
        if (operation.symbol() == Symbol.NONE) return true;
        if (operation.symbol() == Symbol.SUP) return getValue(operation, part) > operation.targetValue();
        if (operation.symbol() == Symbol.INF) return getValue(operation, part) < operation.targetValue();
        return false;
    }

    private int getValue(Operation operation, Part part) {
        return switch (operation.category()) {
            case "x" -> part.x();
            case "a" -> part.a();
            case "m" -> part.m();
            case "s" -> part.s();
            default -> 0;
        };
    }

    private List<Operation> getOperations(String line) {
        List<Operation> operations = new ArrayList<>();
        String[] operationArray = line.substring(line.indexOf('{') + 1, line.indexOf('}')).split(",");

        for (String operation : operationArray) {
            if (!operation.contains(">") && !operation.contains("<")) {
                Operation defaultOperation = new Operation(
                        "*",
                        0,
                        Symbol.NONE,
                        operation.substring(operation.indexOf(':') + 1));
                operations.add(defaultOperation);
            } else if (operation.contains(">")) {
                String key = operation.substring(0, operation.indexOf('>'));
                Operation supOperation = new Operation(
                        key,
                        Integer.parseInt(operation.substring(operation.indexOf('>') + 1, operation.indexOf(':'))),
                        Symbol.SUP,
                        operation.substring(operation.indexOf(':') + 1));
                operations.add(supOperation);
            } else if (operation.contains("<")) {
                String key = operation.substring(0, operation.indexOf('<'));
                Operation infOperation = new Operation(
                        key,
                        Integer.parseInt(operation.substring(operation.indexOf('<') + 1, operation.indexOf(':'))),
                        Symbol.INF,
                        operation.substring(operation.indexOf(':') + 1));
                operations.add(infOperation);
            }
        }

        return operations;
    }

    int sumPart(Part part) {
        return part.x() + part.m() + part.a() + part.s();
    }

    boolean validatePart(Part part, Map<String, Function<Part, String>> keyToFunction) {
        String key = getNextKey(part, keyToFunction.get("in"));
        while (!validatePart(key) && !isRejected(key)) {
            key = getNextKey(part, keyToFunction.get(key));
        }
        return validatePart(key);
    }

    String getNextKey(Part part, Function<Part, String> function) {
        return function.apply(part);
    }


    Part extractPart(String line) {
        List<Integer> numbers = getNumbers(line);
        return new Part(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3));
    }

    private List<Integer> getNumbers(String input) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        List<Integer> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

    private List<RangePart> parseRangePart(RangePart rangePart, Map<String, Function<RangePart, List<RangePart>>> keyToFunction) {
        if (validatePart(rangePart.currentKey())) return List.of(rangePart);
        if (isRejected(rangePart.currentKey())) return List.of();
        return keyToFunction.get(rangePart.currentKey())
                .apply(rangePart);
    }

    @Override
    public Number partTwo(List<String> input) {
        RangePart part = RangePart.startingRange();
        List<RangePart> rangeParts = List.of(part);
        Map<String, Function<RangePart, List<RangePart>>> keyToFunction = input
                .stream()
                .filter(this::isFunctionPart)
                .collect(Collectors.toMap(this::getWorkflowKey, this::getWorkflowOperationsPartTwo));
        while (!rangeParts.isEmpty() && rangeParts.stream().anyMatch(rangePart -> !validatePart(rangePart.currentKey()))) {
            rangeParts = rangeParts
                    .stream()
                    .map(rangePart -> parseRangePart(rangePart, keyToFunction))
                    .flatMap(List::stream)
                    .toList();
        }
        return rangeParts.stream().mapToLong(RangePart::size).sum();
    }

    private boolean validatePart(String key) {
        return "A".equals(key);
    }

    private boolean isRejected(String key) {
        return "R".equals(key);
    }


    enum Symbol {
        INF, SUP, NONE
    }

    record RangePart(String currentKey, int fromX, int toX, int fromM, int toM, int fromA, int toA, int fromS, int toS) {
        static RangePart startingRange() {
            return new RangePart("in", 1, 4000, 1, 4000, 1, 4000, 1, 4000);
        }

        long size() {
            return (long) (toX - fromX + 1) * (toM - fromM + 1) * (toA - fromA + 1) * (toS - fromS + 1);
        }
    }

    record Operation(String category, int targetValue, Symbol symbol, String outputKey) {
    }

    record Part(int x, int m, int a, int s) {
    }
}

