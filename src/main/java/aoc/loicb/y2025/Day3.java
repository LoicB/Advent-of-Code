package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day3 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day3());
    }

    @Override
    public Long partOne(List<String> banks) {
        return banks.stream().mapToLong(this::maximumJoltage).sum();
    }

    long maximumJoltage(String bank) {
        return maximumJoltageWitnBatteries(bank, 2);
    }

    @Override
    public Long partTwo(List<String> banks) {
        return banks.stream().mapToLong(this::maximumJoltageTwelveBatteries).sum();
    }


    long maximumJoltageTwelveBatteries(String bank) {
        return maximumJoltageWitnBatteries(bank, 12);
    }


    long maximumJoltageWitnBatteries(String bank, int numberOfBatteries) {
        int batteriesLeft = numberOfBatteries;
        int maxIndex = 0;
        long joltage = 0;
        while (batteriesLeft > 0) {
            char max = bank.charAt(maxIndex);
            for (int i = maxIndex; i <= bank.length() - batteriesLeft; i++) {
                if (bank.charAt(i) > max) {
                    max = bank.charAt(i);
                    maxIndex = i;
                }
            }
            maxIndex++;
            batteriesLeft--;
            joltage = 10 * joltage + (max - '0');
        }
        return joltage;
    }
}
