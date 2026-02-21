package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 implements Day<List<JunctionBox>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<JunctionBox>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public JunctionBox transformObject(String string) {
                var split = string.split(",");
                return new JunctionBox(
                        Integer.parseInt(split[0]),
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2])
                );
            }
        });
        de.execute(new Day8());
    }

    @Override
    public Number partOne(List<JunctionBox> input) {
        return partOne(input, 1000);
    }

    Number partOne(List<JunctionBox> input, int numberOfOfConnections) {
        var distancesToPair = calculateDistanceToPair(input);
        var circuits = createCircuits(distancesToPair, numberOfOfConnections);
        circuits.sort((junctionBoxes1, junctionBoxes2) -> junctionBoxes2.size() - junctionBoxes1.size());
        return circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
    }

    private List<Set<JunctionBox>> createCircuits(
            Map<Double, Pair<JunctionBox, JunctionBox>> distancesToPair,
            int numberOfOfConnections) {
        Set<Set<JunctionBox>> circuits = new HashSet<>();
        SortedSet<Double> distances = new TreeSet<>(distancesToPair.keySet());
        for (int i = 0; i < numberOfOfConnections; i++) {
            Double distance = distances.first();
            var pair = distancesToPair.get(distance);
            var connectingCircuits = circuits
                    .stream()
                    .filter(junctionBoxes -> junctionBoxes.contains(pair.a) || junctionBoxes.contains(pair.b))
                    .collect(Collectors.toSet());
            if (connectingCircuits.isEmpty()) {
                circuits.add(new HashSet<>(List.of(pair.a, pair.b)));
            } else {
                circuits.removeAll(connectingCircuits);
                Set<JunctionBox> newCircuit = connectingCircuits
                        .stream()
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
                newCircuit.add(pair.a);
                newCircuit.add(pair.b);
                circuits.add(newCircuit);
            }
            distances.removeFirst();
        }
        return new ArrayList<>(circuits);

    }

    private Map<Double, Pair<JunctionBox, JunctionBox>> calculateDistanceToPair(List<JunctionBox> junctionBoxes) {
        Map<Double, Pair<JunctionBox, JunctionBox>> distancesToPair = new HashMap<>();
        for (int i = 0; i < junctionBoxes.size(); i++) {
            for (int j = i + 1; j < junctionBoxes.size(); j++) {
                var distance = calcStraightLineDistance(junctionBoxes.get(i), junctionBoxes.get(j));
                distancesToPair.put(distance, new Pair<>(junctionBoxes.get(i), junctionBoxes.get(j)));
            }
        }
        return distancesToPair;
    }

    double calcStraightLineDistance(JunctionBox junctionBox1, JunctionBox junctionBox2) {
        var a = Math.pow(junctionBox2.x() - junctionBox1.x(), 2.0);
        var b = Math.pow(junctionBox2.y() - junctionBox1.y(), 2.0);
        var c = Math.pow(junctionBox2.z() - junctionBox1.z(), 2.0);
        return Math.sqrt(a + b + c);
    }

    @Override
    public Number partTwo(List<JunctionBox> input) {
        var distancesToPair = calculateDistanceToPair(input);
        var boxes = findUltimateCircuit(distancesToPair, input.size());
        return ((long) boxes.a.x()) * ((long) boxes.b.x());
    }


    private Pair<JunctionBox, JunctionBox> findUltimateCircuit(
            Map<Double, Pair<JunctionBox, JunctionBox>> distancesToPair,
            int numberOfBoxes) {
        List<Set<JunctionBox>> circuits = new ArrayList<>();
        SortedSet<Double> distances = new TreeSet<>(distancesToPair.keySet());
        Double distance = distances.first();
        Pair<JunctionBox, JunctionBox> curr = distancesToPair.get(distance);
        while (circuits.size() != 1 || circuits.get(0).size() != numberOfBoxes) {
            distance = distances.first();
            var pair = distancesToPair.get(distance);
            curr = pair;
            var connectingCircuits = circuits
                    .stream()
                    .filter(junctionBoxes -> junctionBoxes.contains(pair.a) || junctionBoxes.contains(pair.b))
                    .collect(Collectors.toSet());
            if (connectingCircuits.isEmpty()) {
                circuits.add(new HashSet<>(List.of(pair.a, pair.b)));
            } else {
                circuits.removeAll(connectingCircuits);
                Set<JunctionBox> newCircuit = connectingCircuits
                        .stream()
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
                newCircuit.add(pair.a);
                newCircuit.add(pair.b);
                circuits.add(newCircuit);
            }
            distances.removeFirst();
        }
        return curr;

    }
}

record JunctionBox(int x, int y, int z) {
}

