package aoc.loicb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InputToObjectList<T> implements InputTransformer<List<T>> {
    private final String regex;

    public InputToObjectList() {
        this("\\r?\\n");
    }

    public InputToObjectList(String regex) {
        this.regex = regex;
    }

    @Override
    public List<T> transform(String rawInput) {
        String[] arr = rawInput.split(regex);
        return Arrays.stream(arr).map(this::transformObject).collect(Collectors.toList());
    }

    public abstract T transformObject(String string);


}
