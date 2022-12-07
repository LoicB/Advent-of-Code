package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

interface File {
    String name();

}

public class Day7 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day7());
    }

    @Override
    public Integer partOne(List<String> input) {
        Folder root = new Folder("/", new ArrayList<>(), Optional.empty());
        parseCommand(input, 1, root);
        Map<String, Integer> folderSizes = new HashMap<>();
        calculateSize(root, folderSizes);
        return folderSizes.values().stream().filter(integer -> integer <= 100000).mapToInt(i -> i).sum();
    }

    private int calculateSize(File file, Map<String, Integer> folderSizes) {
        if (file instanceof DataFile dataFile) {
            return dataFile.Size();
        }
        if (file instanceof Folder folder) {
            int size = folder.content().stream().mapToInt(file1 -> calculateSize(file1, folderSizes)).sum();
            folderSizes.put(folder.name(), size);
            return size;
        }
        return 0;
    }

    private void parseCommand(List<String> input, int index, Folder currentDirectory) {
        String[] command = getCommand(input, index);
        if (isCommand(command)) {
            if (isChangeDirectory(command)) {
                if (command[2].equals("..")) {
                    parseCommand(input, index + 1, currentDirectory.parent().orElseThrow());
                } else {
                    parseCommand(input, index + 1, (Folder) currentDirectory.content().stream().filter(file -> file.name().equals(command[2])).findFirst().orElseThrow());
                }
            } else if (isList(command)) {
                parseCommand(input, index + 1, currentDirectory);
            }

        } else {
            if (isFile(command)) {
                currentDirectory.content().add(new DataFile(command[1], Integer.parseInt(command[0]), Optional.of(currentDirectory)));
            } else {
                currentDirectory.content().add(new Folder(command[1], new ArrayList<>(), Optional.of(currentDirectory)));
            }
            if (index + 1 < input.size()) parseCommand(input, index + 1, currentDirectory);
        }
    }


    private String[] getCommand(List<String> input, int index) {
        return input.get(index).split(" ");
    }

    private boolean isCommand(String[] command) {
        return command[0].equals("$");
    }

    private boolean isChangeDirectory(String[] command) {
        return command[1].equals("cd");
    }

    private boolean isList(String[] command) {
        return command[1].equals("ls");
    }

    private boolean isDirectory(String[] command) {
        return command[0].equals("dir");
    }

    private boolean isFile(String[] command) {
        return !isCommand(command) && !isDirectory(command);
    }

    @Override
    public Integer partTwo(List<String> input) {
        Folder root = new Folder("/", new ArrayList<>(), Optional.empty());
        parseCommand(input, 1, root);
        Map<String, Integer> folderSizes = new HashMap<>();
        calculateSize(root, folderSizes);
        int size = folderSizes.get("/");
        int freeSpace = 70000000 - size;
        return folderSizes.values().stream().filter(integer -> freeSpace + integer >= 30000000).mapToInt(value -> value).min().orElseThrow();
    }
}

record Folder(String name, List<File> content, Optional<Folder> parent) implements File {

}

record DataFile(String name, int Size, Optional<Folder> parent) implements File {

}