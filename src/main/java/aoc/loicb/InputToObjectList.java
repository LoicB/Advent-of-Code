package aoc.loicb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InputToObjectList<T> implements InputTransformer<List<T>> {
    @Override
    public List<T> transform(String rawInput) {
        String[] arr = rawInput.split("\\r?\\n");
        return Arrays.stream(arr).map(this::transformObject).collect(Collectors.toList());
    }

    public abstract T transformObject(String string);


}
