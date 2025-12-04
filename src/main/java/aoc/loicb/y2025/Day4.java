package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;

public class Day4 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day4());
    }

    @Override
    public Integer partOne(List<String> diagram) {
        int count = 0;
        for (int y = 0; y < diagram.size(); y++) {
            for (int x = 0; x < diagram.get(y).length(); x++) {
                count += isAccessibleByForklift(diagram, x, y) ? 1 : 0;
            }
        }
        return count;
    }

    boolean isRoll(List<String> diagram, int x, int y) {
        return diagram.get(y).charAt(x) == '@';
    }

    boolean isAccessibleByForklift(List<String> diagram, int x, int y) {
        if (!isRoll(diagram, x, y)) return false;
        int count = 0;
        int fromI = Math.max(y - 1, 0);
        int fromJ = Math.max(x - 1, 0);
        int toI = Math.min(y + 1, diagram.size() - 1);
        int toJ = Math.min(x + 1, diagram.get(y).length() - 1);
        for (int i = fromI; i <= toI; i++) {
            for (int j = fromJ; j <= toJ; j++) {
                if (i != y || j != x) {
                    count += isRoll(diagram, j, i) ? 1 : 0;
                }
            }
        }
        return count < 4;
    }


    @Override
    public Number partTwo(List<String> diagram) {
        List<String> currentDiagram = diagram;
        int count = 0;
        boolean keepGoing = true;
        while (keepGoing) {
            keepGoing = false;
            List<String> newDiagram = new ArrayList<>(currentDiagram.size());
            for (int y = 0; y < currentDiagram.size(); y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < currentDiagram.get(y).length(); x++) {
                    if (!isRoll(currentDiagram, x, y)) {
                        sb.append('.');
                    } else if (isAccessibleByForklift(currentDiagram, x, y)) {
                        sb.append('.');
                        keepGoing = true;
                        count++;
                    } else {
                        sb.append('@');
                    }
                }
                newDiagram.add(sb.toString());
            }
            currentDiagram = newDiagram;
        }
        return count;
    }
}
