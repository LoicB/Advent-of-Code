package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.IntStream;

public class Day17 implements Day<List<List<Integer>>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<List<Integer>>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public List<Integer> transformObject(String string) {
                return IntStream
                        .range(0, string.length())
                        .map(string::charAt)
                        .mapToObj(Character::getNumericValue)
                        .toList();
            }
        });
        de.execute(new Day17());
    }

    @Override
    public Number partOne(List<List<Integer>> input) {
        return findMinimumHeat(input, new SimpleMotionEngine(input), new Crucible(0, 0, Direction.Right, 0));
    }

    private Number findMinimumHeat(List<List<Integer>> input, StateMotionEngine stateMotionEngine, Crucible... startingCrucibles) {
        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        Set<Crucible> visited = new HashSet<>();
        Arrays.stream(startingCrucibles).map(s -> new State(s, 0, null)).forEach(priorityQueue::add);
        while (!priorityQueue.isEmpty()) {
            State currentState = priorityQueue.poll();
            if (!visited.contains(currentState.crucible())) {

                visited.add(currentState.crucible());
                List<State> nextSteps = stateMotionEngine.getNextStep(currentState);
                for (State nextStep : nextSteps) {
                    Crucible crucible = nextStep.crucible();
                    priorityQueue.add(nextStep);
                    if (crucible.position().x() == input.size() - 1
                            && crucible.position().y() == input.get(crucible.position().x()).size() - 1
                            && nextStep.crucible().cpt() >= stateMotionEngine.minimumMovements()) {
                        return nextStep.heat();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public Number partTwo(List<List<Integer>> input) {
        return findMinimumHeat(input, new UltraMotionEngine(input), new Crucible(0, 0, Direction.Right, 0), new Crucible(0, 0, Direction.Down, 0));
    }

    record Crucible(Position position, Direction direction, int cpt) {
        Crucible(int x, int y, Direction direction, int cpt) {
            this(new Position(x, y), direction, cpt);
        }
    }

    abstract static class StateMotionEngine {
        private final List<List<Integer>> heatmap;

        public StateMotionEngine(List<List<Integer>> heatmap) {
            this.heatmap = heatmap;
        }

        abstract List<Crucible> getNextStep(Crucible crucible);

        abstract int minimumMovements();
        public List<State> getNextStep(State state) {
            return getNextStep(state.crucible())
                    .stream()
                    .filter(crucible -> isInMap(crucible, heatmap))
                    .map(crucible -> getState(state, crucible)).toList();
        }
        private boolean isInMap(Crucible crucibleState, List<List<Integer>> map) {
            return crucibleState.position().x() >= 0
                    && crucibleState.position().x() < map.size()
                    && crucibleState.position().y() >= 0
                    && crucibleState.position().y() < map.get(crucibleState.position().x()).size();
        }


        private State getState(State state, Crucible crucible) {
            return new State(crucible, state.heat() + heatmap.get(crucible.position().x()).get(crucible.position().y()), state);
        }

        Crucible goStraight(Crucible currentStep) {
            return move(currentStep.position(), currentStep.direction(), currentStep.cpt() + 1);
        }

        Crucible turnLeft(Crucible currentStep) {
            return move(currentStep.position(), turnLeft(currentStep.direction()), 1);
        }

        Crucible turnRight(Crucible currentStep) {
            return move(currentStep.position(), turnRight(currentStep.direction()), 1);
        }


        private Crucible move(Position position, Direction direction, int cpt) {
            return new Crucible(
                    position.x() + direction.x(),
                    position.y() + direction.y(),
                    direction,
                    cpt);
        }


        private Direction turnLeft(Direction currentDirection) {
            return switch (currentDirection) {
                case Left -> Direction.Down;
                case Down -> Direction.Right;
                case Right -> Direction.Up;
                case Up -> Direction.Left;
            };
        }

        private Direction turnRight(Direction currentDirection) {
            return switch (currentDirection) {
                case Left -> Direction.Up;
                case Up -> Direction.Right;
                case Right -> Direction.Down;
                case Down -> Direction.Left;
            };
        }

    }

    static class SimpleMotionEngine extends StateMotionEngine {

        public SimpleMotionEngine(List<List<Integer>> heatmap) {
            super(heatmap);
        }

        @Override
        public List<Crucible> getNextStep(Crucible crucible) {
            if (crucible.position().x() == 0 && crucible.position().y() == 0) return List.of(goStraight(crucible));
            if (crucible.cpt() == 3) return List.of(turnLeft(crucible), turnRight(crucible));
            return List.of(goStraight(crucible), turnLeft(crucible), turnRight(crucible));
        }

        @Override
        int minimumMovements() {
            return 0;
        }


    }

    static class UltraMotionEngine extends StateMotionEngine {

        public UltraMotionEngine(List<List<Integer>> heatmap) {
            super(heatmap);
        }

        @Override
        public List<Crucible> getNextStep(Crucible crucible) {
            if (crucible.cpt() < 4) return List.of(goStraight(crucible));
            if (crucible.cpt() < 10) return List.of(goStraight(crucible), turnLeft(crucible), turnRight(crucible));
            return List.of(turnLeft(crucible), turnRight(crucible));
        }

        @Override
        int minimumMovements() {
            return 4;
        }

    }

    record State(Crucible crucible, long heat, State previous) implements Comparable<State> {
        @Override
        public int compareTo(State other) {
            if (this.heat() != other.heat()) {
                return Long.compare(this.heat(), other.heat());
            } else if (this.crucible().direction() == other.crucible().direction() && this.crucible().cpt() != other.crucible().cpt()) {
                return Integer.compare(this.crucible().cpt(), other.crucible().cpt());
            } else if (this.crucible().position().y() != other.crucible().position().y()) {
                return Integer.compare(this.crucible().position().y(), other.crucible().position().y());
            } else {
                return Integer.compare(this.crucible().position().x(), other.crucible().position().x());
            }
        }

    }

    record Position(int x, int y) {
    }
}