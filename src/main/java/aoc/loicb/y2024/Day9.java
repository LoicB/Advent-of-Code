package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Block {
    Optional<Long> getId();

    int inputIndex();
}

public class Day9 implements Day<List<Integer>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Integer>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split("")).map(Integer::parseInt).collect(Collectors.toList()));
        de.execute(new Day9());
    }

    @Override
    public Long partOne(List<Integer> input) {
        var files = buildFilesBlock(input);
        moveFileBlocks(files);
        return calculateChecksum(files);
    }

    Block[] buildFilesBlock(List<Integer> input) {
        int index = 0;
        int id = 0;
        boolean isFreeSpace = false;
        List<Block> files = new ArrayList<>();
        while (index < input.size()) {
            for (int i = 0; i < input.get(index); i++) {
                files.add(createBlock(isFreeSpace, id, index));
            }
            id += isFreeSpace ? 0 : 1;
            isFreeSpace = !isFreeSpace;
            index++;
        }
        Block[] result = new Block[files.size()];
        files.toArray(result);
        return result;
    }

    private Block createBlock(boolean isFreeSpace, int id, int index) {
        if (isFreeSpace) return new EmptyBlock(index);
        return new FileBlock(id, index);
    }

    void moveFileBlocks(Block[] files) {
        int firstEmpty = getFirstEmpty(files);
        int lastFull = getLastFull(files);
        while (firstEmpty < lastFull) {
            Block block = files[firstEmpty];
            files[firstEmpty] = files[lastFull];
            files[lastFull] = block;
            firstEmpty = getFirstEmpty(files, firstEmpty);
            lastFull = getLastFull(files, lastFull);
        }
    }

    private int getFirstEmpty(Block[] files) {
        return getFirstEmpty(files, 0);
    }

    private int getFirstEmpty(Block[] files, int from) {
        for (int i = from; i < files.length; i++) {
            if (files[i].getId().isEmpty()) return i;
        }
        return -1;
    }

    private int getLastFull(Block[] files) {
        return getLastFull(files, files.length - 1);
    }

    private int getLastFull(Block[] files, int from) {
        for (int i = from; i >= 0; i--) {
            if (files[i].getId().isPresent()) return i;
        }
        return -1;
    }

    private long calculateChecksum(Block[] files) {
        long result = 0;
        int index = 0;
        while (files[index].getId().isPresent()) {
            result += index * ((FileBlock) files[index]).id();
            index++;
        }
        return result;

    }

    @Override
    public Long partTwo(List<Integer> input) {
        var files = buildFilesBlock(input);
        moveFileBlocksPartTwo(files, input.stream().mapToInt(value -> value).toArray());
        return calculateChecksumPatTwo(files);
    }

    private long calculateChecksumPatTwo(Block[] files) {
        long result = 0;
        for (int i = 0; i < files.length; i++) {
            result += i * files[i].getId().orElse(0L);
        }
        return result;

    }


    void moveFileBlocksPartTwo(Block[] files, int[] input) {
        int left = 1;
        int right = input.length - 1;
        while (right > 0 && right > left) {
            int tmpLeft = left;
            while (tmpLeft < input.length && input[tmpLeft] < input[right]) {
                tmpLeft += 2;
            }
            if (tmpLeft < input.length && tmpLeft < right) {
                int firstEmpty = getBlockIndex(files, tmpLeft);
                int lastFull = getBlockIndex(files, right);
                for (int i = 0; i < input[right]; i++) {
                    Block block = files[firstEmpty + i];
                    files[firstEmpty + i] = files[lastFull + i];
                    files[lastFull + i] = block;
                }
                input[tmpLeft] -= input[right];
                input[right] = 0;
            }
            right -= 2;
            while (input[left] == 0) left += 2;
        }
    }


    private int getBlockIndex(Block[] files, int inputIndex) {
        int blockIndex = -1;
        for (int i = 0; i < files.length && blockIndex == -1; i++) {
            if (files[i].inputIndex() == inputIndex) {
                blockIndex = i;
            }
        }
        return blockIndex;
    }

    record EmptyBlock(int inputIndex) implements Block {
        @Override
        public Optional<Long> getId() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    record FileBlock(long id, int inputIndex) implements Block {
        @Override
        public Optional<Long> getId() {
            return Optional.of(id);
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }
}