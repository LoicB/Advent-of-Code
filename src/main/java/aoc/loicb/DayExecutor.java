package aoc.loicb;

import com.google.common.base.Stopwatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record DayExecutor<I>(InputTransformer<I> transformer) {

    public void execute(Day<I, ?> day) {
        String rawInput = readFileQuietly(getInputFile(day.getClass()));
        execute(day, transformer.transform(rawInput));
    }

    public void execute(Day<I, ?> day, I input) {
        Stopwatch timerPart1 = Stopwatch.createStarted();
        Object resultPart1 = day.partOne(input);
        Stopwatch endPart1 = timerPart1.stop();
        Stopwatch timerPart2 = Stopwatch.createStarted();
        Object resultPart2 = day.partTwo(input);
        Stopwatch endPart2 = timerPart2.stop();
        System.out.println("result part one: " + resultPart1);
        System.out.println("result part two: " + resultPart2);
        System.out.printf("| [%s](./src/main/java/%s) | %s | %s |",
                day.getClass().getSimpleName().replace("Day", "Day "),
                day.getClass().getCanonicalName().replaceAll("\\.", "/"),
                endPart1,
                endPart2);
    }

    private Path getInputFile(Class<?> classObject) {
        return Paths.get("./src/main/resources/" + classObject.getPackage().getName(), classObject.getSimpleName() + ".txt");
    }

    private String readFileQuietly(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
