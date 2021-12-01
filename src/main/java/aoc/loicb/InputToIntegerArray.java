package aoc.loicb;

import java.util.Arrays;

public class InputToIntegerArray implements InputTransformer<Integer[]> {
    @Override
    public Integer[] transform(String rawInput) {
        String[] arr = rawInput.split("\\r?\\n");
        return Arrays.stream(arr).map(Integer::parseInt).toArray(Integer[]::new);
    }
}
