package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 implements Day<List<String>, Object> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day23());

    }

    @Override
    public Long partOne(List<String> connections) {
        return findTripartite(indexingConnections(connections))
                .stream()
                .filter(this::isLanWithT)
                .count();
    }

    private boolean isLanWithT(ComputerLan lan) {
        return lan.computers().stream().anyMatch(s -> s.charAt(0) == 't');
    }

    private List<ComputerLan> findTripartite(Map<String, List<String>> indexes) {
        return indexes.keySet().stream().map(s -> findTripartite(s, indexes)).flatMap(List::stream)
                .distinct()
                .toList();
    }

    private List<ComputerLan> findTripartite(String computer1, Map<String, List<String>> indexes) {
        return indexes
                .getOrDefault(computer1, new ArrayList<>())
                .stream()
                .map(computer2 -> findTripartite(computer1, computer2, indexes))
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    private List<ComputerLan> findTripartite(String computer1, String computer2, Map<String, List<String>> indexes) {
        return indexes
                .getOrDefault(computer2, new ArrayList<>())
                .stream()
                .map(computer3 -> findTripartite(computer1, computer2, computer3, indexes))
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    private List<ComputerLan> findTripartite(String computer1, String computer2, String computer3, Map<String, List<String>> indexes) {
        List<ComputerLan> groups = new ArrayList<>();
        if (indexes.containsKey(computer3)) {
            if (indexes.get(computer3).contains(computer1)) {
                var computers = new ArrayList<>(List.of(computer1, computer2, computer3));
                Collections.sort(computers);
                groups.add(new ComputerLan(computers));
            }
        }
        return groups;
    }


    private Map<String, List<String>> indexingConnections(List<String> connections) {
        Map<String, List<String>> index = new HashMap<>();
        connections.stream().map(this::parseConnection).forEach(stringStringPair -> addConnectionToMap(index, stringStringPair));
        return index;
    }

    private void addConnectionToMap(Map<String, List<String>> index, Pair<String, String> connection) {
        addConnectionToMap(index, connection.a, connection.b);
        addConnectionToMap(index, connection.b, connection.a);
    }

    private void addConnectionToMap(Map<String, List<String>> index, String computer1, String computer2) {
        var targets = index.getOrDefault(computer1, new ArrayList<>());
        targets.add(computer2);
        index.put(computer1, targets);
    }

    private Pair<String, String> parseConnection(String connection) {
        return new Pair<>(connection.substring(0, 2), connection.substring(3));
    }


    @Override
    public String partTwo(List<String> connections) {
        var indexes = indexingConnections(connections);
        var output = bronKerbosch(new ArrayList<>(),
                new ArrayList<>(indexes.keySet()),
                new ArrayList<>(),
                indexingConnections(connections))
                .stream()
                .max(Comparator.comparingInt(value -> value.computers().size()))
                .orElse(new ComputerLan(List.of())).computers();
        Collections.sort(output);
        return output.toString();
    }

    private List<ComputerLan> bronKerbosch(List<String> current,
                                           List<String> candidates,
                                           List<String> history,
                                           Map<String, List<String>> indexes) {
        List<ComputerLan> parties = new ArrayList<>();
        if (candidates.isEmpty() && history.isEmpty()) {
            parties.add(new ComputerLan(current));
        } else {
            var copyCandidates = new ArrayList<>(candidates);
            for (String computer : copyCandidates) {
                var newCurrent = new ArrayList<>(current);
                newCurrent.add(computer);
                var neighbors = indexes.getOrDefault(computer, List.of());
                parties.addAll(bronKerbosch(newCurrent,
                        candidates.stream().filter(neighbors::contains).collect(Collectors.toList()),
                        history.stream().filter(neighbors::contains).collect(Collectors.toList()),
                        indexes));
                candidates.remove(computer);
                history.add(computer);
            }
        }
        return parties;
    }

    record ComputerLan(List<String> computers) {

        @Override
        public String toString() {
            return String.join(",", computers);
        }
    }
}
