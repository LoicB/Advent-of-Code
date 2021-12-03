package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class Day3 implements Day<String[], Integer> {
    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day3());
    }

    @Override
    public Integer partOne(String[] binaries) {
        return gammaRate(binaries) * epsilonRate(binaries);
    }

    protected int gammaRate(String[] binaries) {
        return binaryRate(binaries, this::hasMoreOneBit);
    }

    protected int epsilonRate(String[] binaries) {
        return binaryRate(binaries, this::hasMoreZeroBit);
    }

    protected int binaryRate(String[] binaries, BiPredicate<Integer, Integer> bitSelector) {
        StringBuilder gammaRateBuilder = new StringBuilder();
        for (int i = 0; i < binaries[0].length(); i++) {
            gammaRateBuilder.append(bitCounter(binaries, i, bitSelector));
        }
        return Integer.parseInt(gammaRateBuilder.toString(), 2);
    }

    private char bitCounter(String[] binaries, int index, BiPredicate<Integer, Integer> bitSelector) {
        int cptZeros = 0;
        int cptOnes = 0;
        for (String binary : binaries) {
            if (binary.charAt(index) == '0') {
                cptZeros++;
            } else {
                cptOnes++;
            }
        }
        return bitSelector.test(cptZeros, cptOnes) ? '1' : '0';
    }

    private boolean hasMoreOneBit(int cptZeros, int cptOnes) {
        return cptOnes >= cptZeros;
    }

    private boolean hasMoreZeroBit(int cptZeros, int cptOnes) {
        return cptOnes < cptZeros;
    }

    @Override
    public Integer partTwo(String[] binaries) {
        return oxygenGeneratorRating(binaries) * co2ScrubberRating(binaries);
    }

    protected int oxygenGeneratorRating(String[] binaries) {
        return ratingCalculator(binaries, this::hasMoreOneBit);
    }

    protected int co2ScrubberRating(String[] binaries) {
        return ratingCalculator(binaries, this::hasMoreZeroBit);
    }

    protected int ratingCalculator(String[] binaries, BiPredicate<Integer, Integer> bitSelector) {
        int index = 0;
        String[] filteredBinaries = binaries;
        while (filteredBinaries.length > 1) {
            filteredBinaries = filterBinaries(filteredBinaries, index % binaries[0].length(), bitSelector);
            index++;
        }
        return Integer.parseInt(filteredBinaries[0], 2);
    }

    private String[] filterBinaries(String[] binaries, int index, BiPredicate<Integer, Integer> bitSelector) {
        char targetChat = bitCounter(binaries, index, bitSelector);
        return Arrays.stream(binaries).filter(s -> s.charAt(index) == targetChat).toArray(String[]::new);
    }

}
