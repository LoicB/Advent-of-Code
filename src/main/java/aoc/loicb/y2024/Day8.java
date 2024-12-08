package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day8 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day8());

    }

    @Override
    public Integer partOne(List<String> input) {
        return sortAntennasPerFrequency(findAntennas(input)).values()
                .stream()
                .map(antennas -> findAntinodes(input, antennas))
                .flatMap(List::stream)
                .distinct()
                .toList()
                .size();
    }

    protected List<Antinode> findAntinodes(List<String> input, List<Antenna> antennas) {
        var antinodes = new HashSet<Antinode>();
        for (int i = 0; i < antennas.size(); i++) {
            for (int j = 0; j < antennas.size(); j++) {
                if (i != j) {
                    antinodes.addAll(findAntinodes(antennas.get(i), antennas.get(j)));
                }
            }
        }
        return antinodes.stream().filter(antinode -> isInside(input, antinode)).toList();
    }

    private boolean isInside(List<String> input, Antinode antinode) {
        return antinode.x() >= 0 && antinode.x() < input.size() && antinode.y() >= 0 && antinode.y() < input.get(antinode.x()).length();
    }

    protected List<Antinode> findAntinodes(Antenna antenna1, Antenna antenna2) {
        return List.of(
                new Antinode(antenna1.x() - (antenna2.x() - antenna1.x()), antenna1.y() - (antenna2.y() - antenna1.y())),
                new Antinode(antenna2.x() + (antenna2.x() - antenna1.x()), antenna2.y() + (antenna2.y() - antenna1.y()))
        );
    }

    private Map<Character, List<Antenna>> sortAntennasPerFrequency(List<Antenna> antennas) {
        Map<Character, List<Antenna>> frequenciesToAntenna = new HashMap<>();
        antennas.forEach(antenna -> {
            if (!frequenciesToAntenna.containsKey(antenna.frequency())) {
                frequenciesToAntenna.put(antenna.frequency(), new ArrayList<>());
            }
            var list = frequenciesToAntenna.get(antenna.frequency());
            list.add(antenna);
            frequenciesToAntenna.put(antenna.frequency(), list);
        });
        return frequenciesToAntenna;
    }

    private List<Antenna> findAntennas(List<String> input) {
        List<Antenna> antennas = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) != '.') {
                    antennas.add(new Antenna(input.get(i).charAt(j), j, i));
                }
            }
        }
        return antennas;
    }


    @Override
    public Integer partTwo(List<String> input) {
        return sortAntennasPerFrequency(findAntennas(input)).values()
                .stream()
                .map(antennas -> findAntinodesAndTs(input, antennas))
                .flatMap(List::stream)
                .distinct()
                .toList()
                .size();
    }


    protected List<Antinode> findAntinodesAndTs(List<String> input, List<Antenna> antennas) {
        var antinodes = new HashSet<Antinode>();
        for (int i = 0; i < antennas.size(); i++) {
            for (int j = 0; j < antennas.size(); j++) {
                if (i != j) {
                    antinodes.addAll(findAdditionalAntinodes(input, antennas.get(i), antennas.get(j)));
                }
            }
        }
        return antinodes.stream().filter(antinode -> isInside(input, antinode)).toList();
    }


    protected List<Antinode> findAdditionalAntinodes(List<String> input, Antenna antenna1, Antenna antenna2) {
        var antinodes = new HashSet<>(findAntinodes(antenna1, antenna2));
        boolean keepGoing = true;
        while (keepGoing) {
            var list = new ArrayList<>(antinodes);
            int sizeBefore = antinodes.size();
            for (Antinode antinode : list) {
                antinodes.addAll(
                        findAdditionalAntinodes(antenna1, antinode)
                                .stream().filter(newAntinode -> isInside(input, newAntinode)).toList()
                );
                antinodes.addAll(
                        findAdditionalAntinodes(antenna2, antinode)
                                .stream().filter(newAntinode -> isInside(input, newAntinode)).toList()
                );
            }
            keepGoing = sizeBefore < antinodes.size();
        }
        return new ArrayList<>(antinodes);
    }

    protected List<Antinode> findAdditionalAntinodes(Antenna antenna, Antinode antinode) {
        return List.of(
                new Antinode(antenna.x() - (antinode.x() - antenna.x()), antenna.y() - (antinode.y() - antenna.y())),
                new Antinode(antinode.x() + (antinode.x() - antenna.x()), antinode.y() + (antinode.y() - antenna.y()))
        );
    }

    record Antenna(char frequency, int x, int y) {
    }

    record Antinode(int x, int y) {
    }
}
