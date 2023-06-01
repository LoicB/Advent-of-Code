package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 implements Day<List<Valve>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<Valve>> de = new DayExecutor<>(new Day16InputTransformer());
        de.execute(new Day16());
    }

    @Override
    public Integer partOne(List<Valve> valves) {
        List<ContractedValve> extendedValves = valves.stream().map(this::getContractedValve).toList();
        Map<String, ContractedValve> nameToValves = extendedValves.stream().collect(Collectors.toMap(valve -> valve.valve().name(), valve -> valve));
        Map<Set<Valve>, Integer> result = new HashMap<>();
        explore(nameToValves.get("AA"), nameToValves, countValveToOpen(valves), 0, 30, new HashSet<>(), result);
        return result.values().stream().mapToInt(x -> x).max().orElse(0);
    }

    private long countValveToOpen(List<Valve> valves) {
        return valves.stream().filter(valve -> valve.flowRate() != 0).count();
    }

    private void explore(ContractedValve valve, Map<String, ContractedValve> valves, long numberOfValves, int score, int remainingTime, Set<Valve> opened, Map<Set<Valve>, Integer> result) {
        if (remainingTime >= 0 && opened.size() < numberOfValves) {
            int currentScore = getReleasingPressure(opened);
            valve.tunnelDistances().keySet().forEach(nextValveName -> {
                ContractedValve newValve = valves.get(nextValveName);
                int distance = valve.tunnelDistances().get(newValve.valve().name());
                int updatedScore = (1 + distance) * currentScore;
                Set<Valve> newOpened = new HashSet<>(opened);
                newOpened.add(newValve.valve());
                explore(newValve, valves, numberOfValves, updatedScore + score, remainingTime - valve.tunnelDistances().get(nextValveName) - 1, newOpened, result);
            });
        } else if (opened.size() > numberOfValves / 3) {
            addScore(result, opened, score + remainingTime * getReleasingPressure(opened));
        }
    }

    private void addScore(Map<Set<Valve>, Integer> result, Set<Valve> open, int newScore) {
        if (result.containsKey(open)) {
            result.put(open, Math.max(newScore, result.get(open)));
        } else {
            result.put(open, newScore);
        }
    }


    private int getReleasingPressure(Set<Valve> opened) {
        return opened.stream().mapToInt(Valve::flowRate).sum();
    }


    private ContractedValve getContractedValve(Valve valve) {
        Set<Valve> visited = new HashSet<>();
        LinkedList<List<Valve>> next = new LinkedList<>();
        visited.add(valve);
        next.add(valve.tunnels());
        Map<String, Integer> distances = new HashMap<>();
        int distance = 1;
        while (!next.isEmpty()) {
            List<Valve> currents = next.poll();
            final int currentDistance = distance;
            currents.stream().filter(this::shouldOpen).filter(valve1 -> !visited.contains(valve1)).forEach(valve1 -> distances.put(valve1.name(), currentDistance));
            List<Valve> newList = new ArrayList<>(currents.stream().filter(valve1 -> !visited.contains(valve1)).map(Valve::tunnels).flatMap(List::stream).toList());
            if (!newList.isEmpty()) {
                next.add(newList);
            }
            distance++;
            visited.addAll(currents);
        }
        return new ContractedValve(valve, distances);
    }


    private boolean shouldOpen(Valve valve) {
        return valve.flowRate() > 0;
    }

    @Override
    public Integer partTwo(List<Valve> valves) {
        List<ContractedValve> extendedValves = valves.stream().map(this::getContractedValve).toList();
        Map<String, ContractedValve> nameToValves = extendedValves.stream().collect(Collectors.toMap(valve -> valve.valve().name(), valve -> valve));
        Map<Set<Valve>, Integer> result = new HashMap<>();
        explore(nameToValves.get("AA"), nameToValves, countValveToOpen(valves), 0, 26, new HashSet<>(), result);
        return result.entrySet().stream()
                .flatMapToInt(entry -> result.entrySet().stream()
                        .filter(entry1 -> disjoint(entry.getKey(), entry1.getKey()))
                        .mapToInt(entry1 -> entry.getValue() + entry1.getValue()))
                .max().orElse(0);
    }

    private boolean disjoint(Set<Valve> valve1, Set<Valve> valve2) {
        Set<Valve> set = new HashSet<>();
        set.addAll(valve1);
        set.addAll(valve2);
        return set.size() == valve1.size() + valve2.size();
    }
}


record ContractedValve(Valve valve, Map<String, Integer> tunnelDistances) {
}

record Valve(String name, int flowRate, List<Valve> tunnels) {


    @Override
    public String toString() {
        return name + " [" + flowRate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Valve valve = (Valve) o;

        if (flowRate != valve.flowRate) return false;
        return Objects.equals(name, valve.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + flowRate;
        return result;
    }
}


class Day16InputTransformer implements InputTransformer<List<Valve>> {
    @Override
    public List<Valve> transform(String rawInput) {
        String[] lines = rawInput.split("\\r?\\n");
        List<Valve> valves = Arrays.stream(lines)
                .map(this::transformLine)
                .toList();
        Map<String, Valve> nameToValve = valves.stream().collect(Collectors.toMap(Valve::name, item -> item));
        IntStream.range(0, valves.size()).forEach(operand -> getNextValves(lines[operand])
                .stream()
                .map(nameToValve::get)
                .forEach(valve -> valves.get(operand).tunnels().add(valve)));
        return valves;
    }

    private Valve transformLine(String line) {
        String[] parts = line.split(" ");
        String name = parts[1];
        return new Valve(name, Integer.parseInt(parts[4].split("[^-?0-9]+")[1]), new ArrayList<>());
    }


    private List<String> getNextValves(String line) {
        String[] parts = line.split(" ");
        return IntStream.range(9, parts.length).mapToObj(operand -> parts[operand].substring(0, 2)).toList();
    }


}