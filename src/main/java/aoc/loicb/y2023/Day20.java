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
        var modules = createModules(input);
        var flipFlop = createFlipFlopModuless(input);
        var conjunction = createConjunctionModuless(input, modules);
        List<Pulse> pulses = new ArrayList<>();
        for (int i = 0; i < numberOfPress; i++) {
            var queue = new LinkedList<Communication>();
            queue.add(new Communication("button", "broadcaster", Pulse.LOW));
            while (!queue.isEmpty()) {
                var next = queue.poll();
                var origin = next.from();
                var key = next.to();
                var pulse = next.pulse();
                pulses.add(pulse);
                var nextKeys = modules.get(key);
                if (flipFlop.containsKey(key)) {
                    Boolean current = flipFlop.get(key);
                    for (String nextKey : nextKeys) {
                        if (pulse == Pulse.LOW) {
                            queue.add(new Communication(key, nextKey, current ? Pulse.LOW : Pulse.HIGH));
                            flipFlop.put(key, !current);
                        }
                    }
                } else if (conjunction.containsKey(key)) {
                    for (String nextKey : nextKeys) {
                        conjunction.get(key).put(origin, pulse);
                        var nextPulse = conjunction.get(key).values().stream().allMatch(pulse1 -> pulse1 == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
                        queue.add(new Communication(key, nextKey, nextPulse));
                    }
                } else if (nextKeys != null && !nextKeys.isEmpty()) {
                    for (String nextKey : nextKeys) {
                        queue.add(new Communication(key, nextKey, pulse));
                    }
                }
            }
        }
        return pulses;
    }


    Map<String, Boolean> createFlipFlopModuless(List<String> input) {
        return input
                .stream()
                .filter(this::isFlipFlop)
                .map(this::createModuleKey)
                .collect(Collectors
                        .toMap(string -> string, string -> Boolean.FALSE));
    }

    Map<String, Map<String, Pulse>> createConjunctionModuless(List<String> input, Map<String, List<String>> modules) {
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
        var modules = createModules(input);
        var flipFlop = createFlipFlopModuless(input);
        var conjunction = createConjunctionModuless(input, modules);
        int pressCount = 0;
        while (true) {
            pressCount++;
            var queue = new LinkedList<Communication>();
            queue.add(new Communication("button", "broadcaster", Pulse.LOW));
            while (!queue.isEmpty()) {
                var next = queue.poll();
                var origin = next.from();
                var key = next.to();
                var pulse = next.pulse();
                var nextKeys = modules.get(key);
                if (flipFlop.containsKey(key)) {
                    Boolean current = flipFlop.get(key);
                    for (String nextKey : nextKeys) {
                        if (pulse == Pulse.LOW) {
                            queue.add(new Communication(key, nextKey, current ? Pulse.LOW : Pulse.HIGH));
                            flipFlop.put(key, !current);
                        }
                    }
                } else if (conjunction.containsKey(key)) {
                    for (String nextKey : nextKeys) {
                        conjunction.get(key).put(origin, pulse);
                        var nextPulse = conjunction.get(key).values().stream().allMatch(pulse1 -> pulse1 == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
                        queue.add(new Communication(key, nextKey, nextPulse));

                        if (nextKey.equals("rx") && conjunction.get(key).get((new ArrayList<>(conjunction.get(key).keySet())).get(index)) == Pulse.HIGH) {
                            return pressCount;
                        }
                    }
                } else if (nextKeys != null && !nextKeys.isEmpty()) {
                    for (String nextKey : nextKeys) {
                        queue.add(new Communication(key, nextKey, pulse));
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
