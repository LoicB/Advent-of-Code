package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Stone {
    ore(new int[]{0, 0, 0, 1}), clay(new int[]{0, 0, 1, 0}), obsidian(new int[]{0, 1, 0, 0}), geode(new int[]{1, 0, 0, 0});

    private final int[] content;

    Stone(int[] content) {
        this.content = content;
    }

    public int[] content() {
        return content;
    }
}

public class Day19 implements Day<List<Blueprint>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<Blueprint>> de = new DayExecutor<>(new Day19Transformer());
        de.execute(new Day19());
    }

    @Override
    public Long partOne(List<Blueprint> input) {
        return IntStream.range(0, input.size()).mapToLong(index -> (index + 1) * largestNumberOfGeodes(input.get(index), 24)).sum();
    }


    long largestNumberOfGeodes(Blueprint blueprint, int time) {
        return bfs(blueprint, new State(new int[]{0, 0, 0, 0}, Stone.ore.content(), 0), time);
    }

    boolean canBuildRobot(Cost cost, State state) {
        return cost.ore() <= state.bucket()[3] && cost.clay() <= state.bucket()[2] && cost.obsidian() <= state.bucket()[1] && cost.geode() <= state.bucket()[0];
    }

    private int[] add(int[] bucket, int[] robots) {
        IntStream range = IntStream.range(0, Math.min(bucket.length, robots.length));
        IntStream stream3 = range.map(i -> bucket[i] + robots[i]);
        return stream3.toArray();
    }

    private int[] del(int[] bucket, int[] robots) {
        IntStream range = IntStream.range(0, Math.min(bucket.length, robots.length));
        IntStream stream3 = range.map(i -> bucket[i] - robots[i]);
        return stream3.toArray();
    }

    private int[] spend(State state, Cost cost) {
        return del(state.bucket(), new int[]{cost.geode(), cost.obsidian(), cost.clay(), cost.ore()});
    }


    private boolean isBuildUseful(int numberOfResourceRobots, int resource) {
        return numberOfResourceRobots < resource;
    }


    private boolean isBuildUseful(int numberOfResourceRobots, int... resources) {
        for (int resource : resources) {
            if (numberOfResourceRobots < resource) return true;
        }
        return false;
    }

    List<State> getPossibleStates(Blueprint blueprint, State currentState) {
        List<State> newStates = new ArrayList<>();
        State stateAfterCollect = new State(add(currentState.bucket(), currentState.robots()), currentState.robots(), currentState.minutesWithoutBuild() + 1);
        if (canBuildRobot(blueprint.geodeRobotCost(), currentState)) {
            State newState = new State(spend(stateAfterCollect, blueprint.geodeRobotCost()), add(stateAfterCollect.robots(), Stone.geode.content()), 0);
            newStates.add(newState);
        }
        if (canBuildRobot(blueprint.obsidianRobotCost(), currentState)
                && isBuildUseful(currentState.robots()[1], blueprint.geodeRobotCost().obsidian())) {
            State newState = new State(spend(stateAfterCollect, blueprint.obsidianRobotCost()), add(stateAfterCollect.robots(), Stone.obsidian.content()), 0);
            newStates.add(newState);
        }
        if (canBuildRobot(blueprint.clayRobotCost(), currentState)
                && isBuildUseful(currentState.robots()[2], blueprint.obsidianRobotCost().clay())) {
            State newState = new State(spend(stateAfterCollect, blueprint.clayRobotCost()), add(stateAfterCollect.robots(), Stone.clay.content()), 0);
            newStates.add(newState);
        }
        if (canBuildRobot(blueprint.oreRobotCost(), currentState)
                && isBuildUseful(currentState.robots()[3], blueprint.oreRobotCost().ore(), blueprint.clayRobotCost().ore(), blueprint.obsidianRobotCost().ore(), blueprint.geodeRobotCost().ore())) {
            State newState = new State(spend(stateAfterCollect, blueprint.oreRobotCost()), add(stateAfterCollect.robots(), Stone.ore.content()), 0);
            newStates.add(newState);
        }
        newStates.add(stateAfterCollect);
        return newStates;
    }

    private int bfs(Blueprint blueprint, State state, int totalTime) {
        List<Set<State>> states = new ArrayList<>();
        Set<State> history = new HashSet<>();
        states.add(0, Set.of(state));
        for (int time = 0; time < totalTime; time++) {
            Set<State> newStates = new HashSet<>();
            for (State currentState : states.get(time)) {
                var futureStates = getPossibleStates(blueprint, currentState);
                newStates.addAll(futureStates.stream()
                        .filter(state1 -> state1.minutesWithoutBuild() <= 4)
                        .filter(history::add)
                        .toList());
            }
            if (time % 5 == 0) {
                states.add(filterStates(newStates));
            } else {
                states.add(newStates);
            }

        }
        int max = 0;
        for (Set<State> stateSet : states) {
            var optionalInt = stateSet.stream().mapToInt(value -> value.bucket()[0]).max();
            if (optionalInt.isPresent()) max = Math.max(max, optionalInt.getAsInt());
        }
        return max;
    }

    Set<State> filterStates(Set<State> states) {
        OptionalInt geodeMax = states.stream().filter(state -> state.robots()[0] > 0).mapToInt(state -> state.robots()[0]).max();
        if (geodeMax.isPresent()) {
            return states.stream().filter(state -> state.robots()[0] >= geodeMax.getAsInt()).collect(Collectors.toSet());
        }
        return states;
    }


    @Override
    public Long partTwo(List<Blueprint> input) {
        long result = 1;
        for (int i = 0; i < Math.min(3, input.size()); i++) {
            long geodes = largestNumberOfGeodes(input.get(i), 32);
            result *= geodes;
        }
        return result;
    }
}

record State(int[] bucket, int[] robots, int minutesWithoutBuild) {
    public State(int[] bucket, int[] robots) {
        this(bucket, robots, 0);
    }

    @Override
    public String toString() {
        return "State{" +
                "bucket=" + Arrays.toString(bucket) +
                ", robots=" + Arrays.toString(robots) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (!Arrays.equals(bucket, state.bucket)) return false;
        return Arrays.equals(robots, state.robots);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(bucket);
        result = 31 * result + Arrays.hashCode(robots);
        return result;
    }
}

record Cost(int ore, int clay, int obsidian, int geode) {
}

record Blueprint(Cost oreRobotCost, Cost clayRobotCost, Cost obsidianRobotCost, Cost geodeRobotCost) {
}

class Day19Transformer extends InputToObjectList<Blueprint> {
    private final Pattern orePattern = Pattern.compile("(\\d+)\\s*ore");
    private final Pattern clayPattern = Pattern.compile("(\\d+)\\s*clay");
    private final Pattern obsidianPattern = Pattern.compile("(\\d+)\\s*obsidian");
    private final Pattern geodePattern = Pattern.compile("(\\d+)\\s*geode");

    @Override
    public Blueprint transformObject(String string) {
        String[] strings = string.split("Each");
        return new Blueprint(createCost(strings[1]), createCost(strings[2]), createCost(strings[3]), createCost(strings[4]));
    }

    Cost createCost(String string) {
        return new Cost(getOreCost(string), getClayCost(string), getObsidianCost(string), getGeodeCost(string));
    }

    int getOreCost(String string) {
        return findCost(orePattern, string);
    }

    int getClayCost(String string) {
        return findCost(clayPattern, string);
    }

    int getObsidianCost(String string) {
        return findCost(obsidianPattern, string);
    }

    int getGeodeCost(String string) {
        return findCost(geodePattern, string);
    }

    private int findCost(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) return Integer.parseInt(matcher.group(1));
        return 0;

    }
}
