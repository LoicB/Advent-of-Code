package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.Collectors;

public class Day16 implements Day<List<String>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day16());

    }
    @Override
    public Integer partOne(List<String> map) {
        var from = findStartingPosition(map);
        var scoreMap = initScoreMap(map);
        scoreMap[from.y()][from.x()] = 0;
        navigate(map, '>', from.x(), from.y(), scoreMap);
        var to = findEndingPosition(map);
        printScores(scoreMap);
        return navigate(map);
//        var from = findStartingPosition(map);
//        var scoreMap = initScoreMap(map);
//        scoreMap[from.y()][from.x()] = 0;
//        navigate(map, '>', from.x(), from.y(), scoreMap);
//        var to = findEndingPosition(map);
//        return scoreMap[to.y()][to.x()];
    }

    private int navigate(List<String> map) {
        var fromPos = findStartingPosition(map);
        var from = new ReindeerStatus('>', fromPos.x(), fromPos.y());
        Map<ReindeerStatus, Integer> positionToScore = new HashMap<>();
        LinkedList<ReindeerStatus> queue = new LinkedList<>();
        queue.add(from);
        positionToScore.put(from, 0);
        int result = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            int nextX = moveForwardX(current.direction(), current.x());
            int nextY = moveForwardY(current.direction(), current.y());
            if (map.get(nextY).charAt(nextX) == 'E') result = Math.min(positionToScore.get(current) + 1, result);
            // return positionToScore.get(current) + 1;
            var newPosition = new ReindeerStatus(current.direction(), nextX, nextY);

            if (!isWall(map, nextX, nextY) && positionToScore.getOrDefault(newPosition, Integer.MAX_VALUE) > positionToScore.get(current) + 1) {
                positionToScore.put(newPosition, positionToScore.get(current) + 1);
                queue.add(newPosition);
            }
            char rotateDirect = rotateDirect(current.direction());
            var newPositionDirect = new ReindeerStatus(rotateDirect, current.x(), current.y());
            if (positionToScore.getOrDefault(newPositionDirect, Integer.MAX_VALUE) > positionToScore.get(current) + 1000) {
                positionToScore.put(newPositionDirect, positionToScore.get(current) + 1000);
                queue.add(newPositionDirect);
            }
            char rotateClockwise = rotateClockwise(current.direction());
            var newPositionClockwise = new ReindeerStatus(rotateClockwise, current.x(), current.y());
            if (positionToScore.getOrDefault(newPositionClockwise, Integer.MAX_VALUE) > positionToScore.get(current) + 1000) {
                positionToScore.put(newPositionClockwise, positionToScore.get(current) + 1000);
                queue.add(newPositionClockwise);
            }

        }
        return result;

    }
    private void navigate(List<String> map, char direction, int x, int y, int[][] scoreMap) {
//        if (map.get(y).charAt(x) == 'E') return 1;
        // going forward
        int nextX = moveForwardX(direction, x);
        int nextY = moveForwardY(direction, y);
        if (!isWall(map, nextX, nextY) && scoreMap[nextY][nextX] >= scoreMap[y][x] + 1) {
            scoreMap[nextY][nextX] = scoreMap[y][x] + 1;
            navigate(map, direction, nextX, nextY, scoreMap);
        }
        char rotateDirect = rotateDirect(direction);
        int directX = moveForwardX(rotateDirect, x);
        int directY = moveForwardY(rotateDirect, y);
        if (!isWall(map, directX, directY) && scoreMap[directY][directX] >= scoreMap[y][x] + 1001) {
            scoreMap[directY][directX] = scoreMap[y][x] + 1001;
            navigate(map, rotateDirect, directX, directY, scoreMap);
        }
        char rotateClockwise = rotateClockwise(direction);
        int clockwiseX = moveForwardX(rotateClockwise, x);
        int clockwiseY = moveForwardY(rotateClockwise, y);
        if (!isWall(map, clockwiseX, clockwiseY) && scoreMap[clockwiseY][clockwiseX] >= scoreMap[y][x] + 1001) {
            scoreMap[clockwiseY][clockwiseX] = scoreMap[y][x] + 1001;
            navigate(map, rotateClockwise, clockwiseX, clockwiseY, scoreMap);
        }
//        printScores(scoreMap);
//        return 0;
    }

    private void printScores(int[][] scoreMap) {
        for (int i = 0; i < scoreMap.length; i++) {
            for (int j = 0; j < scoreMap[i].length; j++) {
                if (scoreMap[i][j] == Integer.MAX_VALUE) {
                    System.out.print("|..|");
                } else {
                    if (scoreMap[i][j] < 10) {
                        System.out.print("    " + scoreMap[i][j]);
                    } else if (scoreMap[i][j] < 100) {
                        System.out.print("   " + scoreMap[i][j]);
                    } else if (scoreMap[i][j] < 1000) {
                        System.out.print("  " + scoreMap[i][j]);
                    } else if (scoreMap[i][j] < 10000) {
                        System.out.print(" " + scoreMap[i][j]);
                    } else {
                        System.out.print("" + scoreMap[i][j]);
                    }

                }
            }
            System.out.println();
        }
    }
    private Position findStartingPosition(List<String> map) {
        return findCharPosition(map, 'S');

    }

    private Position findEndingPosition(List<String> map) {
        return findCharPosition(map, 'E');
    }

    private Position findCharPosition(List<String> map, char c) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (map.get(i).charAt(j) == c) return new Position(j, i);
            }
        }
        throw new RuntimeException(c + " not found");

    }

    private int[][] initScoreMap(List<String> map) {
        int[][] scoreMap = new int[map.size()][];
        for (int i = 0; i < map.size(); i++) {
            scoreMap[i] = new int[map.get(i).length()];
            for (int j = 0; j < map.get(i).length(); j++) {
                scoreMap[i][j] = Integer.MAX_VALUE;
            }
        }
        return scoreMap;
    }

    private int[][] initStepsMap(List<String> map) {
        int[][] scoreMap = new int[map.size()][];
        for (int i = 0; i < map.size(); i++) {
            scoreMap[i] = new int[map.get(i).length()];
            for (int j = 0; j < map.get(i).length(); j++) {
                scoreMap[i][j] = Integer.MAX_VALUE;
            }
        }
        return scoreMap;
    }


    private int moveForwardX(char direction, int fomX) {
        return switch (direction) {
            case '<' -> fomX - 1;
            case '>' -> fomX + 1;
            default -> fomX;
        };
    }

    private int moveForwardY(char direction, int fomY) {
        return switch (direction) {
            case '^' -> fomY - 1;
            case 'v' -> fomY + 1;
            default -> fomY;
        };
    }
    private char rotateDirect(char direction) {
        return switch (direction) {
            case '^' -> '<';
            case '>' -> '^';
            case 'v' -> '>';
            case '<' -> 'v';
            default -> throw new RuntimeException("Invalid direction");
        };
    }
    private char rotateClockwise(char direction) {
        return switch (direction) {
            case '^' -> '>';
            case '>' -> 'v';
            case 'v' -> '<';
            case '<' -> '^';
            default -> throw new RuntimeException("Invalid direction");
        };
    }

    private boolean isWall(List<String> map, int x, int y) {
        return map.get(y).charAt(x) == '#';
    }

    @Override
    public Integer partTwo(List<String> map) {
        return navigate2(map);
    }

    private Optional<ReindeerSuperStatus> moveForward(List<String> map, ReindeerSuperStatus current) {
        int nextX = moveForwardX(current.direction(), current.x());
        int nextY = moveForwardY(current.direction(), current.y());
        if (!isWall(map, nextX, nextY))
            return Optional.of(new ReindeerSuperStatus(current.direction(), current.score() + 1, nextX, nextY));
        return Optional.empty();
    }

    private List<ReindeerSuperStatus> getNext(List<String> map, ReindeerSuperStatus current) {
        List<ReindeerSuperStatus> result = new ArrayList<>();
        moveForward(map, current).ifPresent(result::add);
        char rotateClockwise = rotateDirect(current.direction());
        var newStatusClockwise = new ReindeerSuperStatus(rotateClockwise, current.score() + 1000, current.x(), current.y());
        moveForward(map, newStatusClockwise).ifPresent(result::add);
        char rotateDirect = rotateClockwise(current.direction());
        var newStatusDirect = new ReindeerSuperStatus(rotateDirect, current.score() + 1000, current.x(), current.y());
        moveForward(map, newStatusDirect).ifPresent(result::add);

        return result;
    }


    private int navigate2(List<String> map) {
        int max = navigate(map);
        System.out.println(max);
        LinkedList<ReindeerSuperStatus> queue = new LinkedList<>();
        Set<ReindeerSuperStatus> visited = new HashSet<>();
//        Map<Position, ReindeerSuperStatus> visited = new HashMap<>();
        Map<ReindeerSuperStatus, List<ReindeerSuperStatus>> parent = new HashMap<>();
        Map<Position, List<Position>> posParent = new HashMap<>();
        Map<Position, Integer> positionToScore = new HashMap<>();
        var fromPos = findStartingPosition(map);
        var from = new ReindeerSuperStatus('>', 0, fromPos.x(), fromPos.y());
        visited.add(from);

        positionToScore.put(fromPos, 0);
        var to = findEndingPosition(map);
        queue.add(from);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.position().equals(to)) {
                break;
            }
            if (current.x() == to.x() && Math.abs(current.y() - to.y()) == 1
                    || current.y() == to.y() && Math.abs(current.x() - to.x()) == 1) {
                System.out.println(current);
            }
            var next = getNext(map, current).stream().filter(reindeerSuperStatus -> reindeerSuperStatus.score() <= max).toList();
//            System.out.println(current);
//            System.out.println(next);
//            System.out.println("!!!!!!!!!!");
            for (ReindeerSuperStatus node : next) {
                if (!visited.contains(node)
//                        || node.score() < visited.get(node.position()).score()
                ) {
                    visited.add(node);
                    queue.add(node);
//                    var listNext = parent.getOrDefault(node, new ArrayList<>());
                    List<ReindeerSuperStatus> listNext = new ArrayList<>();
                    listNext.add(current);
                    parent.put(node, listNext);
                    List<Position> listNext2 = new ArrayList<>();
                    listNext2.add(current.position());
                    posParent.put(node.position, listNext2);
                } else
//                    if (node.score() == visited.get(node.position()).score() /*|| node.direction() != visited.get(node.position()).direction()*/)
                {
                    var listNext = parent.getOrDefault(node, new ArrayList<>());
                    listNext.add(current);
                    parent.put(node, listNext);
                    List<Position> listNext2 = posParent.getOrDefault(node.position(), new ArrayList<>());
                    listNext2.add(current.position());
                    posParent.put(node.position, listNext2);
                }
            }
        }
//        System.out.println(to);
//        System.out.println(posParent);
        System.out.println(posParent.get(to));
//        System.out.println(posParent.get(new Position(15, 2)));
//        System.out.println(posParent.get(new Position(15, 3)));
//        System.out.println(posParent.get(new Position(15, 4)));
//        System.out.println(posParent.get(new Position(15, 5)));
//        System.out.println(posParent.get(new Position(15, 6)));
//        System.out.println(posParent.get(new Position(15, 7)));
//        System.out.println(visited.get(to));
        var all = parent.keySet().stream().filter(reindeerStatus -> map.get(reindeerStatus.y()).charAt(reindeerStatus.x()) == 'E').toList();
        System.out.println(all);
        var pos = all
                .stream()
                .map(reindeerSuperStatus -> getSteps(parent, reindeerSuperStatus))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        printAll(map, pos);
        return (int) all
                .stream()
                .map(reindeerSuperStatus -> getSteps(parent, reindeerSuperStatus))
                .flatMap(Set::stream)
                .distinct()
                .count();
//        System.out.println(countSteps(parent, all.get(0)));
//        var pos = getSteps(posParent, to);
//        printAll(map, pos);
//        return countSteps(posParent, to);
    }

    void printAll(List<String> map, Set<Position> positions) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (positions.contains(new Position(j, i))) {
                    System.out.print('O');
                } else {
                    System.out.print(map.get(i).charAt(j));
                }
            }
            System.out.println();
        }
    }

    private int navigateOld(List<String> map) {
        var fromPos = findStartingPosition(map);
        var from = new ReindeerStatus('>', fromPos.x(), fromPos.y());
        Map<ReindeerStatus, Integer> positionToScore = new HashMap<>();
        Map<ReindeerStatus, List<ReindeerStatus>> next = new HashMap<>();
        Map<Position, Set<Position>> nextPosition = new HashMap<>();
        LinkedList<ReindeerStatus> queue = new LinkedList<>();
        queue.add(from);
        positionToScore.put(from, 0);
        int result = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            int nextX = moveForwardX(current.direction(), current.x());
            int nextY = moveForwardY(current.direction(), current.y());
            if (map.get(nextY).charAt(nextX) == 'E') {
                result = Math.min(positionToScore.get(current) + 1, result);
//                positionToScore.put(current, result);
            }
            // return positionToScore.get(current) + 1;
            var newPosition = new ReindeerStatus(current.direction(), nextX, nextY);

            if (!isWall(map, nextX, nextY) && positionToScore.getOrDefault(newPosition, Integer.MAX_VALUE) >= positionToScore.get(current) + 1) {
                positionToScore.put(newPosition, positionToScore.get(current) + 1);
                queue.add(newPosition);
//                Set<Position> nextPositions = new HashSet<>();
                var nextPositions = nextPosition.getOrDefault(current.position(), new HashSet<>());
                nextPositions.add(newPosition.position());
                nextPosition.put(current.position(), nextPositions);
                var listNext = next.getOrDefault(current, new ArrayList<>());
                listNext.add(newPosition);
                next.put(current, listNext);
            } else if (!isWall(map, nextX, nextY) && positionToScore.getOrDefault(newPosition, Integer.MAX_VALUE) == positionToScore.get(current) + 1) {
                var nextPositions = nextPosition.getOrDefault(current.position(), new HashSet<>());
                nextPositions.add(newPosition.position());
                nextPosition.put(current.position(), nextPositions);
            }
            if (map.get(nextY).charAt(nextX) != 'E') {
                char rotateDirect = rotateDirect(current.direction());
                var newPositionDirect = new ReindeerStatus(rotateDirect, current.x(), current.y());
                if (positionToScore.getOrDefault(newPositionDirect, Integer.MAX_VALUE) >= positionToScore.get(current) + 1000) {
                    positionToScore.put(newPositionDirect, positionToScore.get(current) + 1000);
                    queue.add(newPositionDirect);
                    var listNext = next.getOrDefault(current, new ArrayList<>());
                    listNext.add(newPositionDirect);
                    next.put(current, listNext);
                } /*else if (positionToScore.getOrDefault(newPositionDirect, Integer.MAX_VALUE) == positionToScore.get(current) + 1000) {
                var listNext = next.getOrDefault(current, new ArrayList<>());
                listNext.add(newPositionDirect);
                next.put(current, listNext);
            }*/
                char rotateClockwise = rotateClockwise(current.direction());
                var newPositionClockwise = new ReindeerStatus(rotateClockwise, current.x(), current.y());
                if (positionToScore.getOrDefault(newPositionClockwise, Integer.MAX_VALUE) >= positionToScore.get(current) + 1000) {
                    positionToScore.put(newPositionClockwise, positionToScore.get(current) + 1000);
                    queue.add(newPositionClockwise);
                    var listNext = next.getOrDefault(current, new ArrayList<>());
                    listNext.add(newPositionClockwise);
                    next.put(current, listNext);
                } /* else if (positionToScore.getOrDefault(newPositionClockwise, Integer.MAX_VALUE) == positionToScore.get(current) + 1000) {
                var listNext = next.getOrDefault(current, new ArrayList<>());
                listNext.add(newPositionClockwise);
                next.put(current, listNext);
            } */

            }
        }
        System.out.println("GO GO GO");
        System.out.println(nextPosition.get(from.position()));
        System.out.println(next.entrySet().stream().filter(reindeerStatusListEntry -> reindeerStatusListEntry.getValue().size() > 1).toList());
        Map<Position, Set<Position>> reversedPositions = new HashMap<>();
        for (Map.Entry<Position, Set<Position>> entry : nextPosition.entrySet()) {
            entry.getValue().forEach(s -> {
                var listNext = reversedPositions.getOrDefault(s, new HashSet<>());
                listNext.add(entry.getKey());
                reversedPositions.put(s, listNext);
            });
        }
        var ePos = reversedPositions
                .keySet()
                .stream()
                .filter(postion -> map.get(postion.y()).charAt(postion.x()) == 'E')
                .toList();
        System.out.println(ePos);
        System.out.println(nextPosition.get(fromPos));
        System.out.println(nextPosition.get(new Position(1, 12)));
        System.out.println(nextPosition.get(new Position(1, 11)));
//        countSteps(reversedPositions, ePos.get(0));

        Map<ReindeerStatus, Set<ReindeerStatus>> reversedMap = new HashMap<>();
        for (Map.Entry<ReindeerStatus, List<ReindeerStatus>> entry : next.entrySet()) {
            entry.getValue().forEach(s -> {
                var listNext = reversedMap.getOrDefault(s.position(), new HashSet<>());
                listNext.add(entry.getKey());
                reversedMap.put(s, listNext);
            });
        }
        var all = reversedMap.keySet().stream().filter(reindeerStatus -> map.get(reindeerStatus.y()).charAt(reindeerStatus.x()) == 'E').toList();
        System.out.println(all);
//        System.out.println(result);
        System.out.println(all.stream().map(reindeerStatus -> positionToScore.get(reindeerStatus)).toList());
        int finalResult = result;
        var e = reversedMap
                .keySet()
                .stream()
                .filter(reindeerStatus -> map.get(reindeerStatus.y()).charAt(reindeerStatus.x()) == 'E')
                .filter(reindeerStatus -> positionToScore.get(reindeerStatus) == finalResult)
                .toList();
//        System.out.println("?"+e);
//        System.out.println(e.stream().map(reindeerStatus -> countSteps(reversedMap, reindeerStatus)).toList());
//        System.out.println("?????");
        return countSteps(reversedMap, e.get(0));

    }

    private Set<Position> getSteps(Map<Position, List<Position>> map, Position finalPlace) {
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(finalPlace);
        Set<Position> history = new HashSet<>();
        Set<Position> positions = new HashSet<>();
//        System.out.println(finalPlace);
        while (!queue.isEmpty()) {
            var current = queue.poll();
//            System.out.println(current);
//            System.out.println(map.get(current));
            var pos = new Position(current.x(), current.y());
            positions.add(pos);
            if (history.add(current) && map.containsKey(current)) queue.addAll(map.get(current));
        }
        return positions;
    }

    private Set<Position> getSteps(Map<ReindeerSuperStatus, List<ReindeerSuperStatus>> map, ReindeerSuperStatus finalPlace) {
        LinkedList<ReindeerSuperStatus> queue = new LinkedList<>();
        queue.add(finalPlace);
        Set<ReindeerSuperStatus> history = new HashSet<>();
        Set<Position> positions = new HashSet<>();
//        System.out.println(finalPlace);
        while (!queue.isEmpty()) {
            var current = queue.poll();
//            System.out.println(current);
//            System.out.println(map.get(current));
            var pos = new Position(current.x(), current.y());
            positions.add(pos);
            if (history.add(current) && map.containsKey(current)) queue.addAll(map.get(current));
        }
        return positions;
    }

    private int countSteps(Map<Position, List<Position>> map, Position finalPlace) {
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(finalPlace);
        Set<Position> history = new HashSet<>();
        Set<Position> positions = new HashSet<>();
//        System.out.println(finalPlace);
        while (!queue.isEmpty()) {
            var current = queue.poll();
//            System.out.println(current);
//            System.out.println(map.get(current));
            var pos = new Position(current.x(), current.y());
            positions.add(pos);
            if (history.add(current) && map.containsKey(current)) queue.addAll(map.get(current));
        }
        return positions.size();
    }

    private int countSteps(Map<ReindeerStatus, Set<ReindeerStatus>> map, ReindeerStatus finalPlace) {
        LinkedList<ReindeerStatus> queue = new LinkedList<>();
        queue.add(finalPlace);
        Set<ReindeerStatus> history = new HashSet<>();
        Set<Position> positions = new HashSet<>();
//        System.out.println(finalPlace);
        while (!queue.isEmpty()) {
            var current = queue.poll();
//            System.out.println(current);
//            System.out.println(map.get(current));
            var pos = new Position(current.x(), current.y());
            positions.add(pos);
            if (history.add(current) && map.containsKey(current)) queue.addAll(map.get(current));
        }
        return positions.size();
    }

    private int countSteps(Map<ReindeerSuperStatus, List<ReindeerSuperStatus>> map, ReindeerSuperStatus finalPlace) {
        LinkedList<ReindeerSuperStatus> queue = new LinkedList<>();
        queue.add(finalPlace);
        Set<ReindeerSuperStatus> history = new HashSet<>();
        Set<Position> positions = new HashSet<>();
//        System.out.println(finalPlace);
        while (!queue.isEmpty()) {
            var current = queue.poll();
//            System.out.println(current);
//            System.out.println(map.get(current));
            var pos = new Position(current.x(), current.y());
            positions.add(pos);
            if (history.add(current) && map.containsKey(current)) queue.addAll(map.get(current));
        }
        return positions.size();
    }


    record ReindeerSuperStatus(char direction, int score, Position position) {
        ReindeerSuperStatus(char direction, int score, int x, int y) {
            this(direction, score, new Position(x, y));
        }

        int x() {
            return position().x();
        }

        int y() {
            return position().y();
        }
    }

    record ReindeerStatus(char direction, Position position) {
        ReindeerStatus(char direction, int x, int y) {
            this(direction, new Position(x, y));
        }

        int x() {
            return position().x();
        }

        int y() {
            return position().y();
        }
    }
    record Position(int x, int y) {
    }
}
