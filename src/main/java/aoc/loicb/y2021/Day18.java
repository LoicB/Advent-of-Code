package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.*;
import java.util.stream.Collectors;

interface Node {
}

public class Day18 implements Day<String[], Integer> {

    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day18());
    }

    @Override
    public Integer partOne(String[] input) {
        SnailfishNumber result = finalSum(Arrays.stream(input).map(this::buildTree).collect(Collectors.toList()));
        return calculateMagnitude(result);
    }


    protected SnailfishNumber finalSum(List<SnailfishNumber> numbers) {
        return numbers.stream().reduce(this::addition).orElseThrow();
    }

    protected SnailfishNumber buildTree(String input) {
        int commasIndex = findCommas(input);
        String str1 = input.substring(1, commasIndex);
        String str2 = input.substring(commasIndex + 1, input.length() - 1);
        SnailfishNumber sf = new SnailfishNumber();
        sf.x = buildNodeTree(str1);
        sf.y = buildNodeTree(str2);
        return sf;
    }

    private Node buildNodeTree(String matcher) {
        if (matcher.indexOf(',') == -1) return new ValueNumber(Integer.parseInt(matcher));
        return buildTree(matcher);

    }

    private void reduce(SnailfishNumber number) {
        boolean exploded = explode(number);
        if (exploded) reduce(number);
        boolean split = split(number);
        if (split) reduce(number);
    }

    protected SnailfishNumber addition(SnailfishNumber number1, SnailfishNumber number2) {
        SnailfishNumber sum = new SnailfishNumber(number1, number2);
        reduce(sum);
        return sum;
    }

    protected boolean explode(SnailfishNumber number) {
        return explode(number, number, 0);
    }

    protected boolean explode(SnailfishNumber top, SnailfishNumber number, int level) {
        boolean found = false;
        if (level == 3) {
            if (number.x instanceof SnailfishNumber x) {
                found = true;
                explodeNumber(top, number, x);
            }
            if (!found && number.y instanceof SnailfishNumber y) {
                found = true;
                explodeNumber(top, number, y);
            }
        } else {
            if (number.x instanceof SnailfishNumber x) {
                found = explode(top, x, level + 1);
            }
            if (!found && number.y instanceof SnailfishNumber y) {
                found = explode(top, y, level + 1);
            }
        }
        return found;
    }

    protected void explodeNumber(SnailfishNumber top, SnailfishNumber parent, SnailfishNumber explodingNumber) {
        List<Node> adjacent = findAdjacent(top, explodingNumber);
        int index = adjacent.indexOf(explodingNumber);
        if (index > 0 && adjacent.get(index - 1) instanceof ValueNumber left)
            left.value += ((ValueNumber) explodingNumber.x).value;
        if (index + 1 < adjacent.size() && adjacent.get(index + 1) instanceof ValueNumber right)
            right.value += ((ValueNumber) explodingNumber.y).value;
        if (parent.x instanceof SnailfishNumber x && x.equals(explodingNumber)) parent.x = new ValueNumber(0);
        if (parent.y instanceof SnailfishNumber y && y.equals(explodingNumber)) parent.y = new ValueNumber(0);
    }


    private List<Node> findAdjacent(SnailfishNumber number, SnailfishNumber target) {
        List<Node> adjacent = new ArrayList<>();
        findAdjacent(number, target, adjacent);
        return adjacent;
    }

    private void findAdjacent(SnailfishNumber number, SnailfishNumber target, List<Node> adjacent) {
        if (number.equals(target)) {
            adjacent.add(number);
        } else {
            if (number.x instanceof SnailfishNumber x) {
                findAdjacent(x, target, adjacent);
            } else {
                adjacent.add(number.x);
            }
            if (number.y instanceof SnailfishNumber y) {
                findAdjacent(y, target, adjacent);
            } else {
                adjacent.add(number.y);
            }
        }
    }

    protected boolean split(SnailfishNumber number) {
        return split(number, number, 0);
    }

    private boolean split(SnailfishNumber top, SnailfishNumber number, int level) {
        boolean found = false;
        if (number.x instanceof ValueNumber vn && vn.value >= 10) {
            found = true;
            splitNumber(top, number, vn, level);
        } else if (number.x instanceof SnailfishNumber sn) {
            found = split(top, sn, level + 1);
        }
        if (found) return true;
        if (number.y instanceof ValueNumber vn && vn.value >= 10) {
            found = true;
            splitNumber(top, number, vn, level);
        } else if (number.y instanceof SnailfishNumber sn) {
            found = split(top, sn, level + 1);
        }
        return found;
    }


    protected void splitNumber(SnailfishNumber top, SnailfishNumber parent, ValueNumber valueNumber, int level) {
        SnailfishNumber newNumber = new SnailfishNumber(valueNumber.value / 2, valueNumber.value / 2 + valueNumber.value % 2);
        if (parent.x instanceof ValueNumber x && x.equals(valueNumber)) {
            parent.x = newNumber;
        } else if (parent.y instanceof ValueNumber y && y.equals(valueNumber)) {
            parent.y = newNumber;
        }
        if (level >= 3) explodeNumber(top, parent, newNumber);
    }


    protected Optional<ValueNumber> findValueToSplit(Node number) {
        Optional<ValueNumber> exploding = Optional.empty();
        if (number instanceof ValueNumber vn && vn.value >= 10) {
            exploding = Optional.of(vn);
        } else if (number instanceof SnailfishNumber sn) {
            exploding = findValueToSplit(sn.x);
            if (exploding.isEmpty()) exploding = findValueToSplit(sn.y);
        }
        return exploding;
    }


    private int findCommas(String input) {
        int cpt = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '[') cpt++;
            if (input.charAt(i) == ']') cpt--;
            if (input.charAt(i) == ',' && cpt == 1) return i;
        }
        return -1;
    }


    protected int calculateMagnitude(Node number) {
        if (number instanceof ValueNumber vn) return vn.value;
        if (number instanceof SnailfishNumber sn) {
            return 3 * calculateMagnitude(sn.x) + 2 * calculateMagnitude(sn.y);
        }
        return 0;
    }

    @Override
    public Integer partTwo(String[] input) {
        int max = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (i != j) max = Math.max(max, calculateMagnitude(addition(buildTree(input[i]), buildTree(input[j]))));
            }
        }
        return max;
    }
}

class SnailfishNumber implements Node {
    private final String id;
    Node x;
    Node y;

    public SnailfishNumber() {
        this.id = UUID.randomUUID().toString();
    }

    public SnailfishNumber(Node x, Node y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();

    }

    public SnailfishNumber(int x, int y) {
        this.x = new ValueNumber(x);
        this.y = new ValueNumber(y);
        this.id = UUID.randomUUID().toString();
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnailfishNumber that = (SnailfishNumber) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(x, that.x)) return false;
        return Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}

class ValueNumber implements Node {
    int value;

    ValueNumber(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueNumber that = (ValueNumber) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}