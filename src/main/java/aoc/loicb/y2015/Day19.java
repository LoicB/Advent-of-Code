package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import com.github.javaparser.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 implements Day<Pair<Map<String, List<String>>, String>, Number> {
    public static void main(String[] args) {

        DayExecutor<Pair<Map<String, List<String>>, String>> de = new DayExecutor<>(rawInput -> {
            var splitInput = rawInput.split("\\r?\\n");
            Map<String, List<String>> replacements = new HashMap<>();
            for (int i = 0; i < splitInput.length - 2; i++) {
                String[] replacementPart = splitInput[i].split(" => ");
                if (!replacements.containsKey(replacementPart[0])) {
                    replacements.put(replacementPart[0], new ArrayList<>());
                }
                var list = replacements.get(replacementPart[0]);
                list.add(replacementPart[1]);
                replacements.put(replacementPart[0], list);
            }
            String molecules = splitInput[splitInput.length - 1];
            return new Pair<>(replacements, molecules);
        });
        de.execute(new Day19());

    }

    // 638 too high
    @Override
    public Long partOne(Pair<Map<String, List<String>>, String> input) {
        String molecule = input.b;
        return input.a.entrySet()
                .stream()
                .map(stringStringEntry -> applyReplacement(molecule, stringStringEntry))
                .flatMap(List::stream)
                .distinct()
                .count();
    }


    private List<String> applyReplacement(String molecule, Map.Entry<String, List<String>> replacements) {
        List<String> result = new ArrayList<>();
        int index = molecule.indexOf(replacements.getKey());
        while (index != -1) {
            result.addAll(replace(molecule, index, replacements.getKey().length(), replacements.getValue()));
            index = molecule.indexOf(replacements.getKey(), index + 1);
        }
        return result;
    }

    private List<String> replace(String molecule, int index, int size, List<String> newValues) {
        return newValues.stream().map(s -> replace(molecule, index, size, s)).toList();
    }

    private String replace(String molecule, int index, int size, String newValue) {
        return (new StringBuilder(molecule)).replace(index, index + size, newValue).toString();
    }

    @Override
    public Integer partTwo(Pair<Map<String, List<String>>, String> input) {
        Map<String, List<String>> reversedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : input.a.entrySet()) {
            entry.getValue().forEach(s -> reversedMap.put(s, List.of(entry.getKey())));
        }
        List<String> keys = new ArrayList<>(reversedMap.keySet());
        keys.sort((o1, o2) -> o2.length() - o1.length());

        int count = 0;
        String molecule = input.b;
        while (!"e".equals(molecule)) {
            String currentMolecule = molecule;
            String key = keys.stream().filter(currentMolecule::contains).findFirst().orElse("");

            int index = molecule.indexOf(key);
            if (index != -1) {
                String value = reversedMap.get(key).get(0);
                molecule = currentMolecule.substring(0, index)
                        + value
                        + currentMolecule.substring(index + key.length());
                count++;
            }

        }
        return count;
    }
}
