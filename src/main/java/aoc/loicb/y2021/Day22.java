package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 implements Day<String[], Number> {
    private static final Pattern p = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day22());
    }

    @Override
    public Long partOne(String[] rebootSteps) {
        Region region = new Region(new Range(-50, 50), new Range(-50, 50), new Range(-50, 50));
        List<RebootStep> steps = buildRebootSteps(rebootSteps)
                .stream()
                .filter(resetStep -> isRebootInRegion(resetStep, region))
                .toList();
        return count(steps);
    }

    @Override
    public Long partTwo(String[] rebootSteps) {
        return count(buildRebootSteps(rebootSteps));
    }

    protected List<RebootStep> buildRebootSteps(String[] rebootSteps) {
        return Arrays.stream(rebootSteps).map(this::buildRebootSteps).toList();
    }

    protected RebootStep buildRebootSteps(String rebootStep) {
        Matcher m = p.matcher(rebootStep);
        if (m.find()) {
            return new RebootStep("on".equals(m.group(1)),
                    new Range(m.group(2), m.group(3)),
                    new Range(m.group(4), m.group(5)),
                    new Range(m.group(6), m.group(7)));
        }
        return null;
    }

    protected boolean isRebootInRegion(RebootStep rebootStep, Region region) {
        return isRangeInside(rebootStep.region().rangeX(), region.rangeX())
                && isRangeInside(rebootStep.region().rangeY(), region.rangeY())
                && isRangeInside(rebootStep.region().rangeZ(), region.rangeZ());
    }

    private boolean isRangeInside(Range range, Range otherRage) {
        if (range.from() < otherRage.from()) return false;
        return range.to() <= otherRage.to();
    }

    protected long count(List<RebootStep> steps) {
        List<RebootStep> counted = new ArrayList<>();
        for (RebootStep step : steps) {
            List<RebootStep> currentSteps = new ArrayList<>();
            if (step.on()) currentSteps.add(step);
            for (RebootStep past : counted) {
                getIntersection(step, past).ifPresent(currentSteps::add);
            }
            counted.addAll(currentSteps);
        }
        return counted.stream().mapToLong(value -> (value.on() ? 1 : -1) * countNumberOfCubes(value.region())).sum();
    }

    private Optional<RebootStep> getIntersection(RebootStep step1, RebootStep step2) {
        Region region1 = step1.region();
        Region region2 = step2.region();
        if (!hasIntersection(region1, region2)) return Optional.empty();
        return Optional.of(new RebootStep(!(step2.on()), new Range(Math.max(region1.rangeX().from(), region2.rangeX().from()), Math.min(region1.rangeX().to(), region2.rangeX().to())),
                new Range(Math.max(region1.rangeY().from(), region2.rangeY().from()), Math.min(region1.rangeY().to(), region2.rangeY().to())),
                new Range(Math.max(region1.rangeZ().from(), region2.rangeZ().from()), Math.min(region1.rangeZ().to(), region2.rangeZ().to()))));
    }

    protected boolean hasIntersection(Region region1, Region region2) {
        return (Math.min(region2.rangeX().from(), region2.rangeX().to()) <= Math.max(region1.rangeX().from(), region1.rangeX().to())) &&
                (Math.min(region1.rangeX().from(), region1.rangeX().to()) <= Math.max(region2.rangeX().from(), region2.rangeX().to())) &&
                (Math.min(region2.rangeY().from(), region2.rangeY().to()) <= Math.max(region1.rangeY().from(), region1.rangeY().to())) &&
                (Math.min(region1.rangeY().from(), region1.rangeY().to()) <= Math.max(region2.rangeY().from(), region2.rangeY().to())) &&
                (Math.min(region2.rangeZ().from(), region2.rangeZ().to()) <= Math.max(region1.rangeZ().from(), region1.rangeZ().to())) &&
                (Math.min(region1.rangeZ().from(), region1.rangeZ().to()) <= Math.max(region2.rangeZ().from(), region2.rangeZ().to()));
    }

    protected long countNumberOfCubes(Region region) {
        return countNumberOfCubes(region.rangeX())
                * countNumberOfCubes(region.rangeY())
                * countNumberOfCubes(region.rangeZ());
    }

    protected long countNumberOfCubes(Range range) {
        return range.to() + 1 - range.from();
    }

}


record Region(Range rangeX, Range rangeY, Range rangeZ) {
}

record RebootStep(boolean on, Region region) {
    RebootStep(boolean on, Range rangeX, Range rangeY, Range rangeZ) {
        this(on, new Region(rangeX, rangeY, rangeZ));
    }
}

record Range(long from, long to) {
    public Range(String from, String to) {
        this(Integer.parseInt(from), Integer.parseInt(to));
    }

}