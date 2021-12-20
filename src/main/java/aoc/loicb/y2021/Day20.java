package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.HashMap;
import java.util.Map;

public class Day20 implements Day<aoc.loicb.y2021.Day20Input, Integer> {
    private final static int DIFFERENCE = 200;

    public static void main(String[] args) {
        DayExecutor<aoc.loicb.y2021.Day20Input> de = new DayExecutor<>(new aoc.loicb.y2021.InputToImage());
        de.execute(new aoc.loicb.y2021.Day20());
    }

    @Override
    public Integer partOne(aoc.loicb.y2021.Day20Input input) {
        return solve(input, 2);
    }


    private int solve(aoc.loicb.y2021.Day20Input input, int numberOfRound) {
        Map<Point, Character> resultImage = input.image();
        for (int i = 0; i < numberOfRound; i++) {
            resultImage = enhanceImage(input.imageEnhancementAlgorithm(), resultImage);
            int minX = resultImage.keySet().stream().mapToInt(Point::x).min().orElseThrow();
            int maxX = resultImage.keySet().stream().mapToInt(Point::x).max().orElseThrow();
            int minY = resultImage.keySet().stream().mapToInt(Point::y).min().orElseThrow();
            int maxY = resultImage.keySet().stream().mapToInt(Point::y).max().orElseThrow();
            for (int k = minX; k <= maxX; k++) {
                resultImage.put(new Point(k, minY), resultImage.get(new Point(k, minY + 1)));
            }
            for (int k = minY; k <= maxY; k++) {
                resultImage.put(new Point(maxX, k), resultImage.get(new Point(maxX - 1, k)));
            }

        }
        int count = 0;
        int difference = DIFFERENCE;
        for (int i = -difference; i < difference; i++) {
            for (int j = -difference; j < difference; j++) {
                if (getPixelAt(resultImage, new Point(i, j)) == '#') count++;
            }
        }
        return count;
    }

    protected Map<Point, Character> enhanceImage(char[] imageEnhancementAlgorithm, Map<Point, Character> image) {
        Map<Point, Character> newImage = new HashMap<>();
        int difference = DIFFERENCE;
        for (int i = -difference; i < difference; i++) {
            for (int j = -difference; j < difference; j++) {
                Point point = new Point(i, j);
                newImage.put(point, imageEnhancementAlgorithm[pixelValue(getNeighbourPixels(image, point))]);
            }
        }
        return newImage;
    }

    protected char[] getNeighbourPixels(Map<Point, Character> image, Point point) {
        char[] pixels = new char[9];
        int x = point.x();
        int y = point.y();
        pixels[0] = getPixelAt(image, new Point(x - 1, y - 1));
        pixels[1] = getPixelAt(image, new Point(x - 1, y));
        pixels[2] = getPixelAt(image, new Point(x - 1, y + 1));
        pixels[3] = getPixelAt(image, new Point(x, y - 1));
        pixels[4] = getPixelAt(image, new Point(x, y));
        pixels[5] = getPixelAt(image, new Point(x, y + 1));
        pixels[6] = getPixelAt(image, new Point(x + 1, y - 1));
        pixels[7] = getPixelAt(image, new Point(x + 1, y));
        pixels[8] = getPixelAt(image, new Point(x + 1, y + 1));
        return pixels;
    }

    protected char getPixelAt(Map<Point, Character> image, Point point) {
        if (image.containsKey(point)) return image.get(point);
        return '.';
    }

    protected int pixelValue(char[] neighbourPixels) {
        int value = 0;
        for (int i = 0; i < 9; i++) {
            if (neighbourPixels[neighbourPixels.length - i - 1] == '#') {
                value += Math.pow(2, i);
            }
        }
        return value;
    }

    @Override
    public Integer partTwo(aoc.loicb.y2021.Day20Input input) {
        return solve(input, 50);
    }
}

record Day20Input(char[] imageEnhancementAlgorithm, Map<Point, Character> image) {
}

class InputToImage implements InputTransformer<aoc.loicb.y2021.Day20Input> {

    @Override
    public aoc.loicb.y2021.Day20Input transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        Map<Point, Character> image = new HashMap<>();
        char[] imageEnhancementAlgorithm = inputLines[0].toCharArray();
        for (int i = 2; i < inputLines.length; i++) {
            for (int j = 0; j < inputLines[i].length(); j++) {
                image.put(new Point(i - 2, j), inputLines[i].charAt(j));
            }
        }
        return new aoc.loicb.y2021.Day20Input(imageEnhancementAlgorithm, image);
    }

}