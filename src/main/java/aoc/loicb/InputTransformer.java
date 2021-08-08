package aoc.loicb;

@FunctionalInterface
public interface InputTransformer<I> {
    I transform(String rawInput);
}
