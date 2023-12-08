package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day8 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day8());

    }

    @Override
    public Number partOne(List<String> input) {
        String instructions = input.get(0);
        var network = createNetworkIndex(input);
        return searchForTarget("AAA", instructions, network);
    }


    long searchForTarget(String id, String instructions, Map<String, Node> network) {
        int index = 0;
        Node currentNode = network.get(id);
        while (!isFinishingNode(currentNode.id())) {
            currentNode = network.get(getNextId(currentNode, instructions.charAt(index % instructions.length())));
            index++;
        }
        return index;
    }

    String getNextId(Node currentNode, char instruction) {
        if (instruction == 'L') return currentNode.leftId();
        return currentNode.rightId();
    }

    Map<String, Node> createNetworkIndex(List<String> input) {
        return input
                .stream()
                .filter(this::isNode)
                .map(this::createNode)
                .collect(Collectors.toMap(Node::id, node -> node));
    }

    private boolean isNode(String input) {
        return input.length() > 4 && input.charAt(4) == '=';
    }

    private Node createNode(String input) {
        return new Node(input.substring(0, 3), input.substring(7, 10), input.substring(12, 15));
    }


    @Override
    public Number partTwo(List<String> input) {
        String instructions = input.get(0);
        var network = createNetworkIndex(input);
        var currentIds = startingPoints(network);
        return lcm(currentIds.stream().map(id -> searchForTarget(id, instructions, network)).collect(Collectors.toList()));
    }

    private long lcm(List<Long> numbers) {
        return lcm(numbers, 0);
    }

    private long lcm(List<Long> numbers, int index) {
        if (index == numbers.size() - 2) return lcm(numbers.get(index), numbers.get(index + 1));
        return lcm(numbers.get(index), lcm(numbers, index + 1));
    }

    private long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    private long gcd(long n1, long n2) {
        if (n2 == 0L) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    List<String> startingPoints(Map<String, Node> network) {
        return network
                .keySet()
                .stream()
                .filter(this::isStartingNode)
                .collect(Collectors.toList());
    }

    private boolean isStartingNode(String id) {
        return id.charAt(2) == 'A';
    }

    private boolean isFinishingNode(String id) {
        return id.charAt(2) == 'Z';
    }

    record Node(String id, String leftId, String rightId) {
    }
}
