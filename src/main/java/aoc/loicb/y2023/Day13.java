package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day13 implements Day<List<List<String>>, Number> {
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
        de.execute(new Day13());

    }

    @Override
    public Number partOne(List<List<String>> input) {
        return input
                .stream()
                .mapToLong(this::calculateReflexionScore)
                .sum();
    }

    long calculateReflexionScore(List<String> pattern) {
        return mirroringColumnIndex(pattern) + 100L * mirroringRowIndex(pattern);
    }

    int mirroringColumnIndex(List<String> pattern) {
        return mirroringRowIndex(flipPattern(pattern));
    }

    List<String> flipPattern(List<String> pattern) {
        List<String> flippedPattern = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (String string : pattern) {
                sb.append(string.charAt(i));
            }
            flippedPattern.add(sb.toString());
        }
        return flippedPattern;
    }


    int mirroringRowIndex(List<String> pattern) {
        int index = 0;
        for (int i = 0; i < pattern.size() - 1; i++) {
            if (isMirroringAt(pattern, i)) index = i + 1;
        }
        return index;
    }

    private boolean isMirroringAt(List<String> pattern, int index) {
        for (int i = index + 1; i < pattern.size() && index - (i - index) + 1 >= 0; i++) {
            String val1 = pattern.get(i);
            String val2 = pattern.get(index - (i - index) + 1);
            if (!val1.equals(val2)) return false;
        }
        return true;
    }


    @Override
    public Number partTwo(List<List<String>> input) {
        return input
                .stream()
                .mapToLong(this::calculateRepairedReflexionScore)
                .sum();
    }

    long calculateRepairedReflexionScore(List<String> pattern) {
        long horizontallyRepaired = repairing(pattern);
        if (horizontallyRepaired > 0) return 100L * horizontallyRepaired;
        return repairing(flipPattern(pattern));

    }


    int repairing(List<String> pattern) {
        boolean fixSomething = false;
        for (int i = 0; i < pattern.size() && !fixSomething; i++) {
            Optional<Repair> potentialRepair = needRepair(pattern, i);
            if (!fixSomething && potentialRepair.isPresent()) {
                return i + 1;
            }
        }
        return 0;
    }


    Optional<Repair> needRepair(List<String> pattern, int index) {
        int cptRepair = 0;
        Optional<Repair> potentialRepair = Optional.empty();
        for (int i = index + 1; i < pattern.size() && index - (i - index) + 1 >= 0; i++) {
            String val1 = pattern.get(i);
            String val2 = pattern.get(index - (i - index) + 1);
            if (val2.equals(val1)) {
                continue;
            }
            Optional<Integer> potentialRepairRow = findPotentialRepair(val1, val2);
            if (potentialRepairRow.isPresent()) {
                cptRepair++;
                potentialRepair = Optional.of(new Repair(i, potentialRepairRow.get()));
            } else {
                return Optional.empty();
            }

        }
        return cptRepair == 1 ? potentialRepair : Optional.empty();
    }

    private Optional<Integer> findPotentialRepair(String line1, String line2) {
        int cpt = 0;
        int index = 0;
        for (int i = 0; i < line1.length(); i++) {
            if (line1.charAt(i) != line2.charAt(i)) {
                cpt++;
                index = i;
            }
        }
        return cpt == 1 ? Optional.of(index) : Optional.empty();

    }

    record Repair(int line, int row) {
    }
}
