package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 implements Day<String, Long> {
    private final static Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day5());

    }

    @Override
    public Long partOne(String input) {
        Almanac almanac = buildAlmanac(input);
        return almanac.seeds().stream().mapToLong(seed -> findSeedLocation(seed, almanac.maps())).min().orElse(0);
    }

    Long findSeedLocation(long seed, List<List<List<Long>>> maps) {
        long id = seed;
        for (List<List<Long>> map : maps) {
            id = getMappedValue(id, map);
        }

        return id;
    }

    private long getMappedValue(long id, List<List<Long>> map) {
        for (List<Long> longs : map) {
            if (isInRange(id, longs)) {
                return id - longs.get(1) + longs.get(0);
            }
        }
        return id;
    }

    private boolean isInRange(long id, List<Long> mapUnit) {
        return (id >= mapUnit.get(1) && id < mapUnit.get(1) + mapUnit.get(2));
    }


    Almanac buildAlmanac(String input) {
        String[] arr = input.split("\\r?\\n");
        List<Long> seeds = getNumbers(arr[0]);
        List<List<List<Long>>> maps = getNotIndexedMaps(arr);
        return new Almanac(seeds, maps);
    }


    Map<Long, Long> indexMap(List<List<Long>> rawMap) {
        Map<Long, Long> map = new HashMap<>();
        for (List<Long> longs : rawMap) {
            for (int j = 0; j < longs.get(2); j++) {
                map.put(longs.get(1) + j, longs.get(0) + j);
            }
        }
        return map;
    }

    private List<List<List<Long>>> getNotIndexedMaps(String[] inputArr) {
        List<List<List<Long>>> maps = new ArrayList<>();
        int index = 3;
        while (index < inputArr.length) {
            List<List<Long>> map = getNotIndexedMap(inputArr, index);
            maps.add(map);
            index += map.size() + 2;
        }
        return maps;
    }

    private List<List<Long>> getNotIndexedMap(String[] inputArr, int index) {
        List<List<Long>> map = new ArrayList<>();
        int tmpIndex = index;
        while (tmpIndex < inputArr.length && !inputArr[tmpIndex].trim().isEmpty()) {
            map.add(getNumbers(inputArr[tmpIndex]));
            tmpIndex++;
        }
        return map;
    }

    private List<Long> getNumbers(String playedRepresentation) {
        Matcher m = DIGIT_PATTERN.matcher(playedRepresentation);
        List<Long> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(Long.parseLong(m.group()));
        }
        return numbers;
    }

    @Override
    public Long partTwo(String input) {
        Almanac almanac = buildAlmanac(input);
        long min = Long.MAX_VALUE;
        for (int i = 0; i < almanac.seeds().size(); i += 2) {
            long location = findMinimum(almanac, almanac.seeds().get(i), almanac.seeds().get(i) + almanac.seeds().get(i + 1));
            min = Math.min(min, location);
        }
        return min;

    }

    private long findMinimum(Almanac almanac, long from, long to) {
        long middle = from + (to - from) / 2;
        long fromLocation = findSeedLocation(from, almanac.maps());
        if (from == to) return fromLocation;
        long toLocation = findSeedLocation(to, almanac.maps());
        if (toLocation - fromLocation == to - from) {
            return Math.min(fromLocation, toLocation);
        } else {
            return Math.min(findMinimum(almanac, from, middle), findMinimum(almanac, middle + 1, to));
        }
    }

    record Almanac(List<Long> seeds, List<List<List<Long>>> maps) {
    }
}

