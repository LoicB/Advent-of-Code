package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
import java.util.stream.Collectors;

public class Day19 implements Day<List<Scanner>, Integer> {

    public static void main(String[] args) {
        var de = new DayExecutor<>(rawInput -> transformInput(rawInput.split("\\R")));
        de.execute(new Day19());
    }

    protected static List<Scanner> transformInput(String[] input) {
        Iterator<String> iterable = Arrays.stream(input).iterator();
        List<Scanner> scanners = new ArrayList<>();
        while (iterable.hasNext()) {
            String line = iterable.next();
            String title = line;
            List<Beacon> beacons = new ArrayList<>();
            while (iterable.hasNext() && !(line = iterable.next()).isEmpty()) {
                beacons.add(new Beacon(createVector(line)));
            }
            scanners.add(new Scanner(title, beacons));
        }
        return scanners;
    }

    protected static int[] createVector(String input) {
        return Arrays
                .stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Override
    public Integer partOne(List<Scanner> scanners) {
        return countBeacons(calibrateScanners(scanners));
    }

    @Override
    public Integer partTwo(List<Scanner> scanners) {
        return getBiggestManhattanDistance(calculateOrigins(scanners));
    }

    private int getBiggestManhattanDistance(int[][] origins) {
        return Arrays.stream(origins).mapToInt(scanner -> getBiggestManhattanDistanceFromScanner(scanner, origins)).max().orElse(0);
    }

    private int getBiggestManhattanDistanceFromScanner(int[] scanner1, int[][] origins) {
        return Arrays.stream(origins).mapToInt(scanner -> getManhattanDistance(scanner1, scanner)).max().orElse(0);
    }

    protected int getManhattanDistance(int[] scanner1, int[] scanner2) {
        int distance = Math.abs(scanner1[0] - scanner2[0]);
        distance += Math.abs(scanner1[1] - scanner2[1]);
        if (scanner1.length == 2) return distance;
        return distance + Math.abs(scanner1[2] - scanner2[2]);
    }

    private List<Scanner> calibrateScanners(List<Scanner> scanners) {
        int[][] origins = calculateOrigins(scanners);
        return calibrateScanners(scanners, origins);
    }

    private List<Scanner> calibrateScanners(List<Scanner> scanners, int[][] origins) {
        List<Scanner> calibratedScanners = new ArrayList<>();
        calibratedScanners.add(scanners.get(0));
        for (int i = 1; i < origins.length; i++) {
            Scanner scannerToCalibrate = scanners.get(i);
            int finalI = i;
            Scanner newScanner = new Scanner(scannerToCalibrate.title(),
                    scannerToCalibrate
                            .beacons()
                            .stream()
                            .map(beacon -> new Beacon(updateBeaconCoordinates(beacon, origins[finalI])))
                            .collect(Collectors.toList()));
            calibratedScanners.add(newScanner);
        }
        return calibratedScanners;
    }

    private int[][] calculateOrigins(List<Scanner> scanners) {
        int[][] origins = new int[scanners.size()][];
        origins[0] = new int[]{0, 0, 0};
        var indexes = new LinkedList<Integer>();
        indexes.add(0);
        Map<Scanner, List<List<Double>>> distancesBuffer = new HashMap<>();
        int scannerWithOriginCount = 1;
        while (!indexes.isEmpty() && scannerWithOriginCount < scanners.size()) {
            int index = indexes.poll();
            for (int i = 0; i < scanners.size(); i++) {
                if (origins[i] == null) {
                    Optional<int[]> position = getScannerPosition(scanners.get(index), origins[index], scanners.get(i), distancesBuffer);
                    if (position.isPresent()) {
                        origins[i] = position.get();
                        indexes.add(i);
                        scannerWithOriginCount++;
                    }
                }
            }
        }
        return origins;
    }

    protected int countBeacons(List<Scanner> calibratedScanners) {
        return mergeScanners(calibratedScanners).size();
    }

    protected Set<Beacon> mergeScanners(List<Scanner> calibratedScanners) {
        Set<Beacon> beacons = new HashSet<>();
        for (Scanner scanner : calibratedScanners) {
            beacons.addAll(scanner.beacons());
        }
        return beacons;

    }

    protected Optional<int[]> getScannerPosition(Scanner scannerReference, int[] referencePoint, Scanner scannerToCalibrate, Map<Scanner, List<List<Double>>> distancesBuffer) {
        Optional<int[]> optionalPosition = getScannerPosition(scannerReference, scannerToCalibrate, distancesBuffer);
        return optionalPosition.map(position -> change(position, referencePoint));
    }

    protected int[] change(int[] reference, int[] target) {
        int[] origin = new int[reference.length];
        origin[0] = target[0] + reference[0];
        origin[1] = target[1] + reference[1];
        if (origin.length > 2) origin[2] = reference[2] + target[2];
        return origin;
    }

    protected Optional<int[]> getScannerPosition(Scanner scannerReference, Scanner scannerToCalibrate, Map<Scanner, List<List<Double>>> distancesBuffer) {
        List<List<Double>> distancesReference = getDistanceOrCalculate(scannerReference, distancesBuffer);
        List<List<Double>> distancesCalibrate = getDistanceOrCalculate(scannerToCalibrate, distancesBuffer);

        Map<Integer, Integer> indexToIndex = new HashMap<>();
        for (int i = 0; i < distancesReference.size(); i++) {
            int finalI = i;
            findMatchingBeacons(distancesReference.get(i), distancesCalibrate).ifPresent(integer -> indexToIndex.put(finalI, integer));
        }
        if (indexToIndex.size() == 0) return Optional.empty();
        var indexIterator = indexToIndex.keySet().iterator();
        int index1 = indexIterator.next();
        int index2 = indexIterator.next();
        Beacon reference1 = scannerReference.beacons().get(index1);
        Beacon target1 = scannerToCalibrate.beacons().get(indexToIndex.get(index1));
        Beacon reference2 = scannerReference.beacons().get(index2);
        Beacon target2 = scannerToCalibrate.beacons().get(indexToIndex.get(index2));
        while (!areBeaconsAligned(reference1, target1, reference2, target2)) {
            scannerToCalibrate.rotate();
        }
        return Optional.of(calculateOrigin(reference1, target1));
    }

    private List<List<Double>> getDistanceOrCalculate(Scanner scanner, Map<Scanner, List<List<Double>>> distancesBuffer) {
        if (!distancesBuffer.containsKey(scanner)) distancesBuffer.put(scanner, calculateDistancesInScanner(scanner));
        return distancesBuffer.get(scanner);
    }

    private boolean areBeaconsAligned(Beacon reference1, Beacon target1, Beacon reference2, Beacon target2) {
        return Arrays.equals(calculateOrigin(reference1, target1), calculateOrigin(reference2, target2));
    }

    protected int[] calculateOrigin(Beacon reference, Beacon target) {
        int[] origin = new int[reference.getData().length];
        origin[0] = reference.getData()[0] - target.getData()[0];
        origin[1] = reference.getData()[1] - target.getData()[1];
        if (origin.length > 2) origin[2] = reference.getData()[2] - target.getData()[2];
        return origin;
    }

    protected int[] updateBeaconCoordinates(Beacon beacon, int[] origin) {
        int[] newCoordinates = new int[beacon.getData().length];
        newCoordinates[0] = beacon.getData()[0] + origin[0];
        newCoordinates[1] = beacon.getData()[1] + origin[1];
        if (newCoordinates.length > 2) newCoordinates[2] = beacon.getData()[2] + origin[2];
        return newCoordinates;
    }

    protected Optional<Integer> findMatchingBeacons(List<Double> beaconDistances, List<List<Double>> distances2) {
        for (int i = 0; i < distances2.size(); i++) {
            int count = countMatches(beaconDistances, distances2.get(i));
            if (count >= 2) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    protected int countMatches(List<Double> distances1, List<Double> distances2) {
        int count = 0;
        for (Double distance1 : distances1) {
            for (Double distance2 : distances2) {
                if (distance2 != 0 && Objects.equals(distance1, distance2)) {
                    count++;
                }
            }
        }
        return count;
    }

    protected List<List<Double>> calculateDistancesInScanner(Scanner scanner) {
        return scanner
                .beacons()
                .stream()
                .map(beacon -> calculateDistances(scanner.beacons(), beacon))
                .collect(Collectors.toList());
    }

    protected List<Double> calculateDistances(List<Beacon> beacons, Beacon beacon) {
        return beacons.stream().map(beacon1 -> calculateDistance(beacon1, beacon))
                .collect(Collectors.toList());
    }

    protected double calculateDistance(Beacon beacon1, Beacon beacon2) {
        double x = Math.pow(beacon2.getData()[0] - beacon1.getData()[0], 2);
        double y = Math.pow(beacon2.getData()[1] - beacon1.getData()[1], 2);
        if (beacon1.getData().length == 2) return x + y;
        return x + y + Math.pow(beacon2.getData()[2] - beacon1.getData()[2], 2);
    }
}


class Beacon {
    private final int[] data;
    private int rotation;

    Beacon(int... data) {
        this(data, 0);
    }

    private Beacon(int[] data, int rotation) {
        this.data = data;
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return Arrays.toString(getData());
    }

    void rotate() {
        this.rotation++;
    }

    int[] getData() {
        if (data.length == 2) {
            return switch (rotation) {
                case 0 -> data;
                case 1 -> new int[]{-data[0], -data[1]};
                case 2 -> new int[]{-data[0], data[1]};
                case 3 -> new int[]{data[0], data[1]};
                default -> throw new IllegalStateException("Invalid rotation");
            };
        } else {
            return switch (rotation) {
                case 0 -> data;
                case 1 -> new int[]{-data[0], -data[1], data[2]};
                case 2 -> new int[]{-data[0], data[1], -data[2]};
                case 3 -> new int[]{data[0], -data[1], -data[2]};
                case 4 -> new int[]{-data[0], data[2], data[1]};
                case 5 -> new int[]{data[0], -data[2], data[1]};
                case 6 -> new int[]{data[0], data[2], -data[1]};
                case 7 -> new int[]{-data[0], -data[2], -data[1]};
                case 8 -> new int[]{-data[1], data[0], data[2]};
                case 9 -> new int[]{data[1], -data[0], data[2]};
                case 10 -> new int[]{data[1], data[0], -data[2]};
                case 11 -> new int[]{-data[1], -data[0], -data[2]};
                case 12 -> new int[]{data[1], data[2], data[0]};
                case 13 -> new int[]{-data[1], -data[2], data[0]};
                case 14 -> new int[]{-data[1], data[2], data[0]};
                case 15 -> new int[]{data[1], -data[2], -data[0]};
                case 16 -> new int[]{data[2], data[0], data[1]};
                case 17 -> new int[]{-data[2], -data[0], data[1]};
                case 18 -> new int[]{-data[2], data[0], -data[1]};
                case 19 -> new int[]{data[2], -data[0], -data[1]};
                case 20 -> new int[]{-data[2], data[1], data[0]};
                case 21 -> new int[]{data[2], -data[1], data[0]};
                case 22 -> new int[]{data[2], data[1], -data[0]};
                case 23 -> new int[]{-data[2], -data[1], -data[0]};
                case 24 -> new int[]{-data[2], -data[1], data[0]};
                case 25 -> new int[]{-data[1], data[2], -data[0]};
                default -> throw new IllegalStateException("Invalid rotation");
            };
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beacon beacon = (Beacon) o;
        return Arrays.equals(data, beacon.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}

record Scanner(String title, List<Beacon> beacons) {
    void rotate() {
        beacons.forEach(Beacon::rotate);
    }
}