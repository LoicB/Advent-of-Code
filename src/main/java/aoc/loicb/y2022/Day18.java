package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day18 implements Day<List<LavaDroplet>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<LavaDroplet>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public LavaDroplet transformObject(String string) {
                String[] input = string.split(",");
                return new LavaDroplet(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            }
        });
        de.execute(new Day18());
    }


    @Override
    public Integer partOne(List<LavaDroplet> droplets) {
        int sides = droplets.size() * 6;
        for (int i = 0; i < droplets.size(); i++) {
            for (int j = i + 1; j < droplets.size(); j++) {
                sides -= commonSidesCount(droplets.get(i), droplets.get(j));
            }
        }
        return sides;
    }

    protected int commonSidesCount(LavaDroplet droplet1, LavaDroplet droplet2) {
        int commonValues = 0;
        if (droplet1.x() == droplet2.x()) commonValues++;
        if (droplet1.y() == droplet2.y()) commonValues++;
        if (droplet1.z() == droplet2.z()) commonValues++;
        return commonValues == 2 && Math.abs((droplet1.x() - droplet2.x()) + (droplet1.y() - droplet2.y()) + (droplet1.z() - droplet2.z())) == 1 ? 2 : 0;
    }

    @Override
    public Integer partTwo(List<LavaDroplet> droplets) {
        int maxX = droplets.stream().mapToInt(LavaDroplet::x).max().orElseThrow();
        int maxY = droplets.stream().mapToInt(LavaDroplet::y).max().orElseThrow();
        int maxZ = droplets.stream().mapToInt(LavaDroplet::z).max().orElseThrow();

        Set<LavaDroplet> history = new HashSet<>(droplets);
        LinkedList<LavaDroplet> queue = new LinkedList<>();
        queue.add(new LavaDroplet(0, 0, 0));
        boolean[][][] air = new boolean[maxX + 2][maxY + 2][maxZ + 2];

        droplets.forEach(droplet -> air[droplet.x()][droplet.y()][droplet.z()] = true);
        while (!queue.isEmpty()) {
            LavaDroplet current = queue.poll();
            if (history.add(current)) {
                air[current.x()][current.y()][current.z()] = true;
                List<LavaDroplet> adjacent = getAdjacentCubes(current).stream()
                        .filter(cube -> !history.contains(cube))
                        .filter(cube -> cube.x() >= 0 && cube.x() <= maxX + 1)
                        .filter(cube -> cube.y() >= 0 && cube.y() <= maxY + 1)
                        .filter(cube -> cube.z() >= 0 && cube.z() <= maxZ + 1)
                        .toList();
                queue.addAll(adjacent);
            }
        }

        List<LavaDroplet> untouched = new ArrayList<>();
        for (int i = 0; i < air.length; i++) {
            for (int j = 0; j < air[i].length; j++) {
                for (int k = 0; k < air[i][j].length; k++) {
                    if (!air[i][j][k]) untouched.add(new LavaDroplet(i, j, k));
                }
            }
        }
        untouched.addAll(droplets);
        return partOne(untouched);
    }

    List<LavaDroplet> getAdjacentCubes(LavaDroplet droplet) {
        return List.of(
                new LavaDroplet(droplet.x() + 1, droplet.y(), droplet.z()),
                new LavaDroplet(droplet.x() - 1, droplet.y(), droplet.z()),
                new LavaDroplet(droplet.x(), droplet.y() + 1, droplet.z()),
                new LavaDroplet(droplet.x(), droplet.y() - 1, droplet.z()),
                new LavaDroplet(droplet.x(), droplet.y(), droplet.z() + 1),
                new LavaDroplet(droplet.x(), droplet.y(), droplet.z() - 1)
        );
    }

}


record LavaDroplet(int x, int y, int z) {
}
