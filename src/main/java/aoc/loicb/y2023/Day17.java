package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Day17 implements Day<List<List<Integer>>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<List<Integer>>> de = new DayExecutor<>(new InputToObjectList<List<Integer>>() {
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
        return findMinimumHeat(input, new CrucibleState(0, 0, Direction.Right, 0));
    }

    private Number findMinimumHeat(List<List<Integer>> input, Crucible startingCrucible) {
        long[][] heatmap = new long[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            heatmap[i] = new long[input.get(i).size()];
            Arrays.fill(heatmap[i], Long.MAX_VALUE);
        }
        heatmap[0][0] = 0;

        PriorityQueue<State> pqueue = new PriorityQueue<>();
        pqueue.add(new State(startingCrucible, 0));
        while (!pqueue.isEmpty()) {
            State currentState = pqueue.poll();
            Crucible cs = currentState.crucible();
            long currentHeat = heatmap[cs.position().x()][cs.position().y()];
            List<Crucible> nextSteps = cs.getNextStep();
            for (Crucible nextStep : nextSteps) {
                if (isInMap(nextStep, input)) {
                    long newHeat = currentHeat + input.get(nextStep.position().x()).get(nextStep.position().y());
                    if (newHeat < heatmap[nextStep.position().x()][nextStep.position().y()]) {
                        heatmap[nextStep.position().x()][nextStep.position().y()] = newHeat;
                        pqueue.add(new State(nextStep, newHeat));
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(heatmap));
        printheamap(heatmap);
        return heatmap[input.size() - 1][input.getFirst().size() - 1];
    }

    private void printheamap(long[][] heatmap) {
        for (int i = 0; i < heatmap.length; i++) {
            for (int j = 0; j < heatmap[i].length; j++) {
                if (heatmap[i][j] == Long.MAX_VALUE) {
                    System.out.print("|  -");
                } else if (heatmap[i][j] < 10) {
                    System.out.print("|  " + heatmap[i][j]);
                } else if (heatmap[i][j] < 100) {
                    System.out.print("| " + heatmap[i][j]);
                } else {
                    System.out.print("|" + heatmap[i][j]);
                }
            }
            System.out.println();
        }

    }

    private boolean isInMap(Crucible crucibleState, List<List<Integer>> map) {
        return crucibleState.position().x() >= 0
                && crucibleState.position().x() < map.size()
                && crucibleState.position().y() >= 0
                && crucibleState.position().y() < map.get(crucibleState.position().x()).size();
    }

    @Override
    public Number partTwo(List<List<Integer>> input) {
        return findMinimumHeat(input, new UltraCrucibleState(0, 0, Direction.Right, 0));
    }

    interface Crucible {
        Position position();

        Direction direction();

        int cpt();

        List<Crucible> getNextStep();

        boolean isEnoughCpt();
    }

    record State(Crucible crucible, long heat) implements Comparable<State> {
        @Override
        public int compareTo(State other) {
//            long ret = heat - other.heat();
//            if (ret == 0 && crucible.direction() == other.crucible.direction()) {
//                ret = crucible.cpt() - other.crucible.cpt();
//            }
//            if (ret == 0) {
//                ret = other.crucible.position().x() + other.crucible.position().y() - crucible.position().x()  - crucible.position().y();
//            }
//            return (int) ret;
//            return -1 * Long.compare(this.heat(), other.heat());
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

    record CrucibleState(Position position, Direction direction, int cpt) implements Crucible {
        CrucibleState(int x, int y, Direction direction, int cpt) {
            this(new Position(x, y), direction, cpt);
        }

        @Override
        public List<Crucible> getNextStep() {
            if (this.position().x() == 0 && this.position().y() == 0) return List.of(goStraight(this));
            if (this.cpt() == 3) return List.of(turnLeft(this), turnRight(this));
            return List.of(goStraight(this), turnLeft(this), turnRight(this));
        }

        @Override
        public boolean isEnoughCpt() {
            return true;
        }

        private Crucible goStraight(Crucible currentStep) {
            return move(currentStep.position(), currentStep.direction(), currentStep.cpt() + 1);
        }

        private Crucible turnLeft(Crucible currentStep) {
            return move(currentStep.position(), turnLeft(currentStep.direction()), 0);
        }

        private Crucible turnRight(Crucible currentStep) {
            return move(currentStep.position(), turnRight(currentStep.direction()), 0);
        }


        private CrucibleState move(Position position, Direction direction, int cpt) {
            return new CrucibleState(
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

    record UltraCrucibleState(Position position, Direction direction, int cpt) implements Crucible {
        UltraCrucibleState(int x, int y, Direction direction, int cpt) {
            this(new Position(x, y), direction, cpt);
        }

        @Override
        public List<Crucible> getNextStep() {
            if (this.cpt() < 4) return List.of(goStraight(this));
            if (this.cpt() < 10) return List.of(goStraight(this), turnLeft(this), turnRight(this));
            return List.of(turnLeft(this), turnRight(this));
        }

        @Override
        public boolean isEnoughCpt() {
            return this.cpt() >= 4;
        }

        private Crucible goStraight(Crucible currentStep) {
            return move(currentStep.position(), currentStep.direction(), currentStep.cpt() + 1);
        }

        private Crucible turnLeft(Crucible currentStep) {
            return move(currentStep.position(), turnLeft(currentStep.direction()), 0);
        }

        private Crucible turnRight(Crucible currentStep) {
            return move(currentStep.position(), turnRight(currentStep.direction()), 0);
        }


        private UltraCrucibleState move(Position position, Direction direction, int cpt) {
            return new UltraCrucibleState(
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

}
