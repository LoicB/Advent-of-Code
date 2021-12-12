package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 implements Day<List<Connection>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<Connection>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Connection transformObject(String string) {
                String[] input = string.split("-");
                return new Connection(input[0], input[1]);
            }
        });
        de.execute(new Day12());
    }

    @Override
    public Integer partOne(List<Connection> connections) {
        Cave start = buildCaveNetwork(connections);
        return calculateNumberOfPathNew(start, new HashSet<>(List.of(start.getId())), 0);
    }

    private Cave buildCaveNetwork(List<Connection> connections) {
        Cave start = null;
        Map<String, Cave> idToCave = new HashMap<>();
        for (Connection connection : connections) {
            Cave cave1 = getCave(connection.from(), idToCave);
            Cave cave2 = getCave(connection.to(), idToCave);
            cave1.addConnectedCave(cave2);
            cave2.addConnectedCave(cave1);
            if (cave1.isStart()) start = cave1;
            if (cave2.isStart()) start = cave2;
        }
        return start;
    }

    private Cave getCave(String id, Map<String, Cave> idToCave) {
        if (idToCave.containsKey(id)) return idToCave.get(id);
        Cave newCave = new Cave(id);
        idToCave.put(id, newCave);
        return newCave;
    }

    @Override
    public Integer partTwo(List<Connection> connections) {
        Cave start = buildCaveNetwork(connections);
        return calculateNumberOfPathNew(start, new HashSet<>(List.of(start.getId())), 1);
    }

    private int calculateNumberOfPathNew(Cave startingCave, Set<String> traversedCaves, int smallCavesAllowance) {
        int number = 0;
        if (startingCave.isEnd()) return 1;
        for (Cave cave : startingCave.getConnectedCaves()) {
            int cavesAllowance = smallCavesAllowance;
            if (cave.isBig() || !traversedCaves.contains(cave.getId()) || (!cave.isStartOrEnd() && cavesAllowance-- > 0)) {
                Set<String> newTraversed = new HashSet<>(traversedCaves);
                newTraversed.add(cave.getId());
                number += calculateNumberOfPathNew(cave, newTraversed, cavesAllowance);
            }
        }
        return number;
    }
}


record Connection(String from, String to) {
}

class Cave {
    private final String id;
    private final List<Cave> connectedCaves;

    public Cave(String id) {
        this.id = id;
        this.connectedCaves = new ArrayList<>();
    }

    public void addConnectedCave(Cave cave) {
        connectedCaves.add(cave);
    }

    public boolean isBig() {
        return id.toUpperCase().equals(id);
    }

    public boolean isStartOrEnd() {
        return isStart() || isEnd();
    }

    public boolean isStart() {
        return "start".equals(id);
    }

    public boolean isEnd() {
        return "end".equals(id);
    }

    public String getId() {
        return id;
    }

    public List<Cave> getConnectedCaves() {
        return connectedCaves;
    }

    @Override
    public String toString() {
        return "Cave{" +
                "id='" + id + '\'' +
                ", connectedCaves=" + connectedCaves.stream().map(cave -> cave.id).collect(Collectors.toList()) +
                '}';
    }
}