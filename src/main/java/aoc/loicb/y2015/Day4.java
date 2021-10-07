package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

public class Day4 implements Day<String, Integer> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day4());
    }

    @Override
    public Integer partOne(String input) {
        return day4(input, this::isMd5HashValid);
    }

    @Override
    public Integer partTwo(String input) {
        return day4(input, this::isMd5HashValidPart2);
    }

    private int day4(String input, Predicate<byte[]> validator) {
        MessageDigest md = getMessageDigest();
        int number = 1;
        md.update((input + number).getBytes());
        while (!validator.test(md.digest())) {
            number++;
            md.update((input + number).getBytes());
        }
        return number;
    }


    protected boolean isMd5HashValid(byte[] digest) {
        if (digest[0] != 0) return false;
        if (digest[1] != 0) return false;
        return digest[2] < 16 && digest[2] >= 0;
    }

    protected boolean isMd5HashValidPart2(byte[] digest) {
        if (digest[0] != 0) return false;
        if (digest[1] != 0) return false;
        return digest[2] == 0;
    }

    private MessageDigest getMessageDigest() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return messageDigest;
    }
}
