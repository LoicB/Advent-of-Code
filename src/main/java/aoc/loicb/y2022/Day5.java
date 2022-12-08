package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 implements Day<Cargo, String> {
    public static void main(String[] args) {
        DayExecutor<Cargo> de = new DayExecutor<>(new Day5InputTransformer());

        de.execute(new Day5());
    }

    @Override
    public String partOne(Cargo input) {
        input
                .instructions()
                .forEach(instruction -> applyInstructionPart1(input.stacks(), instruction));
        return input.stacks()
                .stream()
                .map(LinkedList::getLast)
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    private void applyInstructionPart1(List<LinkedList<Character>> stacks, Instruction instruction) {
        for (int i = 0; i < instruction.move(); i++) {
            stacks.get(instruction.to() - 1).add(stacks.get(instruction.from() - 1).removeLast());
        }
    }

    @Override
    public String partTwo(Cargo input) {
        input
                .instructions()
                .forEach(instruction -> applyInstructionPart2(input.stacks(), instruction));
        return input.stacks()
                .stream()
                .map(LinkedList::getLast)
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    private void applyInstructionPart2(List<LinkedList<Character>> stacks, Instruction instruction) {
        List<Character> toMove = new ArrayList<>();
        for (int i = 0; i < instruction.move(); i++) {
            toMove.add(0, stacks.get(instruction.from() - 1).removeLast());
        }
        stacks.get(instruction.to() - 1).addAll(toMove);
    }
}


record Instruction(int move, int from, int to) {

}

record Cargo(List<LinkedList<Character>> stacks, List<Instruction> instructions) {

}


class Day5InputTransformer implements InputTransformer<Cargo> {
    @Override
    public Cargo transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        int stackIndicesIndex = IntStream
                .range(0, inputLines.length)
                .filter(value -> inputLines[value].startsWith(" 1"))
                .findFirst()
                .orElseThrow();
        int numberOfStack = inputLines[stackIndicesIndex].trim().split(" +").length;
        List<LinkedList<Character>> stacks = new ArrayList<>(numberOfStack);
        for (int i = 0; i < numberOfStack; i++) {
            stacks.add(new LinkedList<>());
        }

        for (int i = stackIndicesIndex - 1; i >= 0; i--) {
            char[] chars = inputLines[i].toCharArray();
            for (int j = 0; j < stacks.size(); j++) {
                int index = 4 * j + 1;
                if (index < chars.length && chars[index] != ' ') stacks.get(j).add(chars[index]);
            }
        }

        List<Instruction> instructions = IntStream.range(stackIndicesIndex + 2, inputLines.length).mapToObj(operand -> createInstruction(inputLines[operand])).toList();

        return new Cargo(stacks, instructions);
    }


    private Instruction createInstruction(String inputLine) {
        String[] line = inputLine.split(" ");
        return new Instruction(Integer.parseInt(line[1]), Integer.parseInt(line[3]), Integer.parseInt(line[5]));
    }
}