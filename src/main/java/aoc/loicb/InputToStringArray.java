package aoc.loicb;

public class InputToStringArray implements InputTransformer<String[]> {
    @Override
    public String[] transform(String rawInput) {
        return rawInput.split("\\r?\\n");
    }
}
