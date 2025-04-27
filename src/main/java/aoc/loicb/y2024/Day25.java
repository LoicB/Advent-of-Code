package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;

public class Day25 implements Day<List<List<String>>, Object> {
    public static void main(String[] args) {

        DayExecutor<List<List<String>>> de = new DayExecutor<>(rawInput -> {
            List<List<String>> result = new ArrayList<>();
            List<String> current = new ArrayList<>();
            for (String line : rawInput.split("\\r?\\n")) {
                if (line.isEmpty()) {
                    result.add(current);
                    current = new ArrayList<>();
                } else {
                    current.add(line);
                }
            }
            result.add(current);
            return result;
        });
        de.execute(new Day25());

    }

    @Override
    public Long partOne(List<List<String>> input) {
        var keys = input.stream().filter(this::isKey).toList();
        var locks = input.stream().filter(this::isLock).toList();
        return locks.stream().mapToLong(lock -> countFittingKeys(lock, keys)).sum();
    }

    boolean isKey(List<String> schema) {
        return schema.get(0).charAt(0) == '.';

    }

    boolean isLock(List<String> schema) {
        return schema.get(0).charAt(0) == '#';

    }

    boolean areSchemasFitting(List<String> schema1, List<String> schema2) {
        if (schema1.size() != schema2.size()) return false;
        for (int i = 0; i < schema1.size(); i++) {
            if (schema1.get(i).length() != schema2.get(i).length()) return false;
            for (int j = 0; j < schema1.get(i).length(); j++) {
                if (schema1.get(i).charAt(j) == '#' && schema2.get(i).charAt(j) == '#') return false;
            }
        }
        return true;
    }

    private long countFittingKeys(List<String> lock, List<List<String>> keys) {
        return keys.stream().filter(key -> areSchemasFitting(key, lock)).count();
    }

    @Override
    public String partTwo(List<List<String>> input) {
        return "Ho ho ho";
    }
}
