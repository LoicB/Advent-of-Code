package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

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
        Map<String, Cave> idToCave = new HashMap<>();
        for (Connection connection : connections) {
            Cave cave1 = getCave(connection.from(), idToCave);
            Cave cave2 = getCave(connection.to(), idToCave);
            if (canMove(cave1, cave2)) cave1.addConnectedCave(cave2);
            if (canMove(cave2, cave1)) cave2.addConnectedCave(cave1);
        }
        return idToCave.get("start");
    }

    private boolean canMove(Cave from, Cave to) {
        return !to.isStart() && !from.isEnd();
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
            if (cave.isBig() || !traversedCaves.contains(cave.getId()) || cavesAllowance-- > 0) {
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
}