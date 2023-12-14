package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day14());

    }

    @Override
    public Number partOne(List<String> platform) {
        return calculateTotalLoad(tilt(platform));
    }

    List<String> tilt(List<String> platform) {
        List<char[]> transformedPlatform = transformPlatform(platform);
        tiltNorth(transformedPlatform);
        return transformBack(transformedPlatform);
    }

    private void tiltNorth(List<char[]> platform) {
        for (int i = 1; i < platform.size(); i++) {
            for (int j = 0; j < platform.get(i).length; j++) {
                if (isRoundedRocks(platform.get(i)[j])) {
                    int rockNewIndex = i;
                    while (rockNewIndex >= 1 && isEmpty(platform.get(rockNewIndex - 1)[j])) {
                        rockNewIndex--;
                    }
                    platform.get(i)[j] = '.';
                    platform.get(rockNewIndex)[j] = 'O';
                }
            }
        }
    }

    private void tiltSouth(List<char[]> platform) {
        for (int i = platform.size() - 1; i >= 0; i--) {
            for (int j = 0; j < platform.get(i).length; j++) {
                if (isRoundedRocks(platform.get(i)[j])) {
                    int rockNewIndex = i;
                    while (rockNewIndex < platform.size() - 1 && isEmpty(platform.get(rockNewIndex + 1)[j])) {
                        rockNewIndex++;
                    }
                    platform.get(i)[j] = '.';
                    platform.get(rockNewIndex)[j] = 'O';
                }
            }
        }
    }

    private void tiltWest(List<char[]> platform) {
        for (char[] chars : platform) {
            for (int j = 0; j < chars.length; j++) {
                if (isRoundedRocks(chars[j])) {
                    int rockNewIndex = j;
                    while (rockNewIndex >= 1 && isEmpty(chars[rockNewIndex - 1])) {
                        rockNewIndex--;
                    }
                    chars[j] = '.';
                    chars[rockNewIndex] = 'O';
                }
            }
        }
    }

    private void tiltEast(List<char[]> platform) {
        for (char[] chars : platform) {
            for (int j = chars.length - 1; j >= 0; j--) {
                if (isRoundedRocks(chars[j])) {
                    int rockNewIndex = j;
                    while (rockNewIndex < chars.length - 1 && isEmpty(chars[rockNewIndex + 1])) {
                        rockNewIndex++;
                    }
                    chars[j] = '.';
                    chars[rockNewIndex] = 'O';
                }
            }
        }
    }

    private List<char[]> transformPlatform(List<String> platform) {
        return platform
                .stream()
                .map(String::toCharArray)
                .toList();
    }

    private List<String> transformBack(List<char[]> transformedPlatform) {
        return transformedPlatform
                .stream()
                .map(String::new)
                .collect(Collectors.toList());
    }


    long calculateTotalLoad(List<String> platform) {
        return IntStream.range(0, platform.size())
                .mapToLong(operand -> (platform.size() - operand) * calculateTotalLoad(platform.get(operand)))
                .sum();
    }

    long calculateTotalLoad(String line) {
        return IntStream.range(0, line.length())
                .map(line::charAt)
                .filter(value -> isRoundedRocks((char) value))
                .count();
    }

    private boolean isRoundedRocks(char c) {
        return c == 'O';
    }

    private boolean isEmpty(char c) {
        return c == '.';
    }

    @Override
    public Number partTwo(List<String> platform) {
        return calculateTotalLoad(tiltCycle(platform));
    }


    List<String> tiltCycle(List<String> platform) {
        Set<List<String>> cycleDetector = new HashSet<>();
        List<List<String>> history = new ArrayList<>();
        int index = 0;
        boolean cycle = false;
        List<String> currentPlatform = platform;
        while (index < 1000000000 && !cycle) {
            currentPlatform = tiltCycleOnce(currentPlatform);
            cycle = !cycleDetector.add(currentPlatform);
            if (!cycle) {
                history.add(currentPlatform);
                index++;
            }
        }
        int historyIndex = history.indexOf(currentPlatform);
        return history.get((1000000000 - 1 - index) % (index - historyIndex) + historyIndex);
    }

    List<String> tiltCycleOnce(List<String> platform) {
        List<char[]> transformedPlatform = transformPlatform(platform);
        tiltNorth(transformedPlatform);
        tiltWest(transformedPlatform);
        tiltSouth(transformedPlatform);
        tiltEast(transformedPlatform);
        return transformBack(transformedPlatform);
    }


}
