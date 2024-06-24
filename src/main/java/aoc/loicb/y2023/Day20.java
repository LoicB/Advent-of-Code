package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day20 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day20());
    }

    @Override
    public Long partOne(List<String> input) {
        var pulse = pressButton(1000, input);
        return pulse.stream().filter(pulse1 -> pulse1 == Pulse.HIGH).count() * pulse.stream().filter(pulse1 -> pulse1 == Pulse.LOW).count();
    }

    List<Pulse> pressButton(int numberOfPress, List<String> input) {
        var modulesMap = createModules(input);
        var flipFlopMap = createFlipFlopModules(input);
        var conjunctionMap = createConjunctionModules(input, modulesMap);
        List<Pulse> pulses = new ArrayList<>();

        for (int press = 0; press < numberOfPress; press++) {
            var communicationQueue = new LinkedList<Communication>();
            communicationQueue.add(new Communication("button", "broadcaster", Pulse.LOW));

            while (!communicationQueue.isEmpty()) {
                var currentCommunication = communicationQueue.poll();
                var originKey = currentCommunication.from();
                var destinationKey = currentCommunication.to();
                var currentPulse = currentCommunication.pulse();

                pulses.add(currentPulse);

                var nextKeys = modulesMap.get(destinationKey);

                if (flipFlopMap.containsKey(destinationKey)) {
                    Boolean currentState = flipFlopMap.get(destinationKey);

                    for (String nextKey : nextKeys) {
                        if (currentPulse == Pulse.LOW) {
                            communicationQueue.add(new Communication(destinationKey, nextKey, currentState ? Pulse.LOW : Pulse.HIGH));
                            flipFlopMap.put(destinationKey, !currentState);
                        }
                    }
                } else if (conjunctionMap.containsKey(destinationKey)) {
                    for (String nextKey : nextKeys) {
                        conjunctionMap.get(destinationKey).put(originKey, currentPulse);
                        var nextPulse = conjunctionMap.get(destinationKey).values().stream().allMatch(p -> p == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
                        communicationQueue.add(new Communication(destinationKey, nextKey, nextPulse));
                    }
                } else if (nextKeys != null && !nextKeys.isEmpty()) {
                    for (String nextKey : nextKeys) {
                        communicationQueue.add(new Communication(destinationKey, nextKey, currentPulse));
                    }
                }
            }
        }

        return pulses;
    }


    Map<String, Boolean> createFlipFlopModules(List<String> input) {
        return input
                .stream()
                .filter(this::isFlipFlop)
                .map(this::createModuleKey)
                .collect(Collectors
                        .toMap(string -> string, string -> Boolean.FALSE));
    }

    Map<String, Map<String, Pulse>> createConjunctionModules(List<String> input, Map<String, List<String>> modules) {
        return input
                .stream()
                .filter(this::isConjunction)
                .map(this::createModuleKey)
                .collect(Collectors
                        .toMap(string -> string, string -> mapConnectedInputModules(findConnectedInputModules(string, modules))));
    }

    Map<String, Pulse> mapConnectedInputModules(List<String> connectedModules) {
        return connectedModules
                .stream()
                .collect(Collectors
                        .toMap(string -> string, string -> Pulse.LOW));
    }

    List<String> findConnectedInputModules(String module, Map<String, List<String>> modules) {
        return modules
                .keySet()
                .stream()
                .filter(string -> modules.get(string).contains(module))
                .toList();
    }

    boolean isFlipFlop(String line) {
        return line
                .startsWith("%");
    }

    boolean isConjunction(String line) {
        return line
                .startsWith("&");
    }

    Map<String, List<String>> createModules(List<String> input) {
        return input
                .stream()
                .collect(Collectors
                        .toMap(this::createModuleKey, this::createModuleDestinations));
    }

    String createModuleKey(String line) {
        String key = line
                .split(" -> ")[0];
        if (key.charAt(0) == '%' || key.charAt(0) == '&') return key.substring(1);
        return key;
    }

    List<String> createModuleDestinations(String line) {
        return List.of(line.split(" -> ")[1].split(", "));
    }

    @Override
    public Long partTwo(List<String> input) {
        long number1 = waitingForRx(input, 0);
        long number2 = waitingForRx(input, 1);
        long number3 = waitingForRx(input, 2);
        long number4 = waitingForRx(input, 3);
        return lcm(List.of(number1, number2, number3, number4));
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

    int waitingForRx(List<String> input, int index) {
        var modulesMap = createModules(input);
        var flipFlopMap = createFlipFlopModules(input);
        var conjunctionMap = createConjunctionModules(input, modulesMap);
        int pressCount = 0;

        while (true) {
            pressCount++;
            var communicationQueue = new LinkedList<Communication>();
            communicationQueue.add(new Communication("button", "broadcaster", Pulse.LOW));

            while (!communicationQueue.isEmpty()) {
                var currentCommunication = communicationQueue.poll();
                var originKey = currentCommunication.from();
                var destinationKey = currentCommunication.to();
                var currentPulse = currentCommunication.pulse();
                var nextKeys = modulesMap.get(destinationKey);

                if (flipFlopMap.containsKey(destinationKey)) {
                    Boolean currentState = flipFlopMap.get(destinationKey);

                    for (String nextKey : nextKeys) {
                        if (currentPulse == Pulse.LOW) {
                            communicationQueue.add(new Communication(destinationKey, nextKey, currentState ? Pulse.LOW : Pulse.HIGH));
                            flipFlopMap.put(destinationKey, !currentState);
                        }
                    }
                } else if (conjunctionMap.containsKey(destinationKey)) {
                    for (String nextKey : nextKeys) {
                        conjunctionMap.get(destinationKey).put(originKey, currentPulse);
                        var nextPulse = conjunctionMap.get(destinationKey).values().stream().allMatch(p -> p == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
                        communicationQueue.add(new Communication(destinationKey, nextKey, nextPulse));

                        if (nextKey.equals("rx") && conjunctionMap.get(destinationKey).get(new ArrayList<>(conjunctionMap.get(destinationKey).keySet()).get(index)) == Pulse.HIGH) {
                            return pressCount;
                        }
                    }
                } else if (nextKeys != null && !nextKeys.isEmpty()) {
                    for (String nextKey : nextKeys) {
                        communicationQueue.add(new Communication(destinationKey, nextKey, currentPulse));
                    }
                }
            }
        }
    }

    enum Pulse {
        HIGH, LOW
    }

    record Communication(String from, String to, Pulse pulse) {
        @Override
        public String toString() {
            return from + " -" + pulse + "-> " + to;
        }
    }
}
