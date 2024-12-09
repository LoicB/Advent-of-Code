package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 implements Day<List<Integer>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Integer>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split("")).map(Integer::parseInt).collect(Collectors.toList()));
        de.execute(new Day9());
    }

    @Override
    public Long partOne(List<Integer> input) {
        long time1 = System.currentTimeMillis();
        var files = buildFilesBlock(input);
        long time2 = System.currentTimeMillis();
//        System.out.println(printFiles(files));
        moveFileBlocks2(files);
        long time3 = System.currentTimeMillis();
        System.out.println((time2 - time1));
        System.out.println((time3 - time2));
        return calculateChecksum(files);
    }

    protected Block[] buildFilesBlock(List<Integer> input) {
        int index = 0;
        int id = 0;
        boolean isFreeSpace = false;
        List<Block> files = new ArrayList<>();
        while (index < input.size()) {
            for (int i = 0; i < input.get(index); i++) {
                files.add(createBlock(isFreeSpace, id));
            }
            id += isFreeSpace ? 0 : 1;
            isFreeSpace = !isFreeSpace;
            index++;
        }
        Block[] result = new Block[files.size()];
        files.toArray(result);
        return result;
    }

    private Block createBlock(boolean isFreeSpace, int id) {
        if (isFreeSpace) return new EmptyBlock();
        return new FileBlock(id);
    }

    protected Character[] buildFiles(List<Integer> input) {
        int index = 0;
        int id = 0;
        boolean isFreeSpace = false;
        List<Character> files = new ArrayList<>();
        while (index < input.size()) {
            if (isFreeSpace) {
                for (int i = 0; i < input.get(index); i++) {
                    files.add('.');
                }
            } else {
                for (int i = 0; i < input.get(index); i++) {
                    files.add(Character.forDigit(id, 10));
                }
                id++;
            }
            isFreeSpace = !isFreeSpace;
            index++;
        }
        Character[] result = new Character[files.size()];
        files.toArray(result);
        return result;
    }

    protected String printFiles(Character[] files) {
        StringBuilder sb = new StringBuilder();
        for (Character file : files) {
            sb.append(file);
        }
        return sb.toString();
    }

    protected void moveFileBlocks2(Block[] files) {
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

    protected void moveFileBlocks(Character[] files) {
        int firstEmpty = getFirstEmpty(files);
        int lastFull = getLastFull(files);
        while (firstEmpty < lastFull) {
            char c = files[firstEmpty];
            files[firstEmpty] = files[lastFull];
            files[lastFull] = c;
            firstEmpty = getFirstEmpty(files);
            lastFull = getLastFull(files);
        }

    }

    private boolean hasFreeSpace(Character[] files) {
        return getFirstEmpty(files) > getLastFull(files);
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

    private int getFirstEmpty(Character[] files) {
        for (int i = 0; i < files.length; i++) {
            if (files[i] == '.') return i;
        }
        return -1;
    }

    private int getLastFull(Character[] files) {
        for (int i = files.length - 1; i >= 0; i--) {
            if (files[i] != '.') return i;
        }
        return -1;
    }

    private long calculateChecksum(Block[] files) {
        long time1 = System.currentTimeMillis();
        long result = 0;
        int index = 0;
        while (files[index].getId().isPresent()) {
//            result += index * files[index].getId().orElse(0L);
            result += index * ((FileBlock) files[index]).id();
            index++;
        }
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
        return result;

    }

    private BigInteger calculateChecksum(Character[] files) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i <= getLastFull(files); i++) {
            result = result.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(files[i] - '0')));
//            result += (long) i * (files[i] - '0');
        }
        return result;

    }


    @Override
    public Integer partTwo(List<Integer> input) {
        return null;
    }

    interface Block {
        Optional<Long> getId();
    }

    record Disk(Block[] blocks) {

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Block block : blocks) {
                sb.append(block.toString());
            }
            return sb.toString();
        }
    }

    record EmptyBlock() implements Block {

        @Override
        public Optional<Long> getId() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    record FileBlock(long id) implements Block {

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
