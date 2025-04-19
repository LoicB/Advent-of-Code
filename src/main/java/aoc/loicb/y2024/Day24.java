package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.*;
import java.util.function.BiPredicate;

enum GateType {
    OR, AND, XOR
}

public class Day24 implements Day<MonitoringDevice, Object> {
    public static void main(String[] args) {
        DayExecutor<MonitoringDevice> de = new DayExecutor<>(new Day24InputTransformer());
        de.execute(new Day24());
    }

    @Override
    public Long partOne(MonitoringDevice input) {
        var wires = new HashMap<>(input.wires());
        var gates = input.gates();
        while (!isCompleted(wires, gates)) {
            applyGatesOperation(wires, gates);
        }
        return calculateOutput(wires);
    }


    long calculateOutput(Map<String, Boolean> wires) {
        return calculateValue(wires, 'z');
    }

    long calculateValue(Map<String, Boolean> wires, char c) {
        int index = 0;
        var wire = c + "00";
        long value = 0;
        while (wires.containsKey(wire)) {
            if (wires.get(wire)) value += Math.pow(2, index);
            index++;
            wire = getWireName(c, index);
        }
        return value;
    }

    private void applyGatesOperation(Map<String, Boolean> wires, List<Gate> gates) {
        gates.forEach(gate -> gate.operate(wires));
    }

    private boolean isCompleted(Map<String, Boolean> wires, List<Gate> gates) {
        return gates
                .stream()
                .filter(Gate::isTarget)
                .map(Gate::getOutput)
                .allMatch(wires::containsKey);
    }


    @Override
    public String partTwo(MonitoringDevice input) {
        List<String> swap = new ArrayList<>();
        List<Gate> gates = input.gates();
        for (int i = 1; i < 45; i++) {
            var incompatibleGate = findIncompatibleGates(i, input.gates());
            if (incompatibleGate.isPresent()) {
                Gate gate = incompatibleGate.get();
                int indexCurrentGate = gates.lastIndexOf(gate);
                var z = ("z" + "%02d").formatted(i);
                int indexOutputZ = gates.lastIndexOf(gates.stream().filter(gate1 -> gate1.getOutput().equals(z)).findFirst().orElseThrow());
                for (int j = 0; j < gates.size(); j++) {
                    if (findIncompatibleGates(i, swapGates(gates, j, indexCurrentGate)).isEmpty()) {
                        swap.add(gates.get(j).getOutput());
                        swap.add(gate.getOutput());
                    } else if (findIncompatibleGates(i, swapGates(gates, j, indexOutputZ)).isEmpty()) {
                        swap.add(gates.get(j).getOutput());
                        swap.add(z);
                    }
                }
            }
        }
        Collections.sort(swap);
        return String.join(",", swap);
    }

    protected Optional<Gate> findIncompatibleGates(int index, List<Gate> gates) {
        var startX = ("x" + "%02d").formatted(index);
        Optional<Gate> firstGate = gates.stream()
                .filter(gate -> gate.getGateType() == GateType.XOR)
                .filter(gate -> gate.getInput().contains(startX)).findFirst();
        var firstGateOutput = firstGate.orElseThrow().getOutput();
        var endZ = ("z" + "%02d").formatted(index);
        Optional<Gate> secondGate = gates.stream()
                .filter(gate -> gate.getGateType() == GateType.XOR)
                .filter(gate -> gate.getOutput().contains(endZ)
                        && gate.getInput().contains(firstGateOutput)).findFirst();
        if (secondGate.isEmpty()) return firstGate;
        Optional<Gate> thirdGate = gates.stream()
                .filter(gate -> gate.getGateType() == GateType.AND)
                .filter(gate -> gate.getInput().contains(secondGate.get().getInput1())
                        && gate.getInput().contains(secondGate.get().getInput2())).findFirst();
        if (thirdGate.isEmpty()) return secondGate;
        Optional<Gate> fourthGate = gates.stream()
                .filter(gate -> gate.getGateType() == GateType.AND)
                .filter(gate -> gate.getInput().contains(firstGate.get().getInput1())
                        && gate.getInput().contains(firstGate.get().getInput2())).findFirst();
        if (fourthGate.isEmpty()) return thirdGate;
        Optional<Gate> fiveGate = gates.stream()
                .filter(gate -> gate.getGateType() == GateType.OR)
                .filter(gate -> gate.getInput().contains(fourthGate.get().getOutput())
                        && gate.getInput().contains(thirdGate.get().getOutput())).findFirst();
        if (fiveGate.isEmpty()) return fourthGate;
        return Optional.empty();

    }

    protected List<Gate> swapGates(List<Gate> gates, int swap1, int swap2) {
        var gate1 = gates.get(swap1).swapWith(gates.get(swap2));
        var gate2 = gates.get(swap2).swapWith(gates.get(swap1));
        var swappedGates = new ArrayList<>(gates);
        swappedGates.set(swap1, gate1);
        swappedGates.set(swap2, gate2);
        return swappedGates;
    }

    private String getWireName(char c, int index) {
        return c + (index < 10 ? "0" : "") + index;
    }

}

record MonitoringDevice(Map<String, Boolean> wires, List<Gate> gates) {
}

class Gate {
    private final String input1;
    private final String input2;
    private final String output;
    private final GateType gateType;
    private final BiPredicate<Boolean, Boolean> gateOperation;

    private Gate(String input1, String input2, String output, BiPredicate<Boolean, Boolean> gateOperation, GateType gateType) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.gateOperation = gateOperation;
        this.gateType = gateType;
    }

    static Gate AndGate(String input1, String input2, String output) {
        return new Gate(input1, input2, output, (inputA, inputB) -> inputA && inputB, GateType.AND);
    }

    static Gate OrGate(String input1, String input2, String output) {
        return new Gate(input1, input2, output, (inputA, inputB) -> inputA || inputB, GateType.OR);
    }

    static Gate XorGate(String input1, String input2, String output) {
        return new Gate(input1, input2, output, (inputA, inputB) -> inputA ^ inputB, GateType.XOR);
    }

    Gate swapWith(Gate gate) {
        return new Gate(this.input1, this.input2, gate.output, this.gateOperation, this.gateType);
    }

    void operate(Map<String, Boolean> wires) {
        if (wires.containsKey(output)) return;
        if (!wires.containsKey(input1)
                || !wires.containsKey(input2)) return;
        wires.put(output, this.gateOperation.test(wires.get(input1), wires.get(input2)));
    }

    String getInput1() {
        return input1;
    }

    String getInput2() {
        return input2;
    }

    List<String> getInput() {
        return List.of(input1, input2);
    }

    String getOutput() {
        return output;
    }

    GateType getGateType() {
        return gateType;
    }

    boolean isTarget() {
        return output.startsWith("z");
    }


    @Override
    public String toString() {
        return "Gate{" +
                "input1='" + input1 + '\'' +
                ", input2='" + input2 + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}


class Day24InputTransformer implements InputTransformer<MonitoringDevice> {
    @Override
    public MonitoringDevice transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        int index = 0;
        Map<String, Boolean> wires = new HashMap<>();
        while (!inputLines[index].isEmpty()) {
            String[] wireData = inputLines[index].split(": ");
            wires.put(wireData[0], wireData[1].equals("1"));
            index++;
        }
        index++;
        List<Gate> gates = new ArrayList<>();
        while (index < inputLines.length) {
            String[] gateData = inputLines[index].split(" ");
            switch (gateData[1]) {
                case "AND" -> gates.add(Gate.AndGate(gateData[0], gateData[2], gateData[4]));
                case "OR" -> gates.add(Gate.OrGate(gateData[0], gateData[2], gateData[4]));
                case "XOR" -> gates.add(Gate.XorGate(gateData[0], gateData[2], gateData[4]));
            }
            index++;
        }
        return new MonitoringDevice(wires, gates);
    }
}
