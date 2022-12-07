package advent.day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.common.util.InputReader;

public class NoSpaceLeft {

    private abstract static class File {
        protected String name;
        
        private Directory parent;

        public abstract long getSize();

        public Directory getParent() {
            return parent;
        }

        public String getName() {
            return name;
        }
    }

    private static class LeafFile extends File {

        private final long size;

        public LeafFile(String name, long size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public long getSize() {
            return size;
        }
    }

    private static class Directory extends File {
        
        private List<File> children = new ArrayList<>();
        
        public Directory(String name) {
            this.name = name;
        }

        @Override
        public long getSize() {
            return children.stream().mapToLong(File::getSize).sum();
        }

        public void addChild(File file) {
            children.add(file);
            file.parent = this;
        }

        public Directory getChild(String name) {
            return (Directory)children.stream().filter(file -> file.getName().equals(name) && file instanceof Directory).findAny().orElse(null);
        }

        public List<File> getChildren() {
            return children;
        }
    }

    private static final String PROMPT = "$";
    private static final String LIST = "ls";
    private static final String CHANGE_DIR = "cd";
    private static final String PARENT_DIR = "..";
    private static final String ROOT_DIR = "/";
    private static final String DIRECTORY = "dir";

    private static final long MAX_DELETE_SIZE = 100000l;

    private static final long DISK_SIZE = 70000000l;
    private static final long UPDATE_SIZE = 30000000l;

    private static long getDeletableSize(File file) {
        long size = 0l;
        if (!(file instanceof Directory)) {
            return size;
        }

        Directory pwd = (Directory)file;
        long mySize = pwd.getSize();
        if (mySize < MAX_DELETE_SIZE) {
            size += mySize;
        }

        for (File childFile : pwd.getChildren()) {
            size += getDeletableSize(childFile);
        }

        return size;
    }

    private static long getBestDeletionSize(Directory dir, long requiredSpace) {
        long bestSize = dir.getSize();
        
        if (bestSize < requiredSpace) {
            return Long.MAX_VALUE;
        }

        for (File childFile : dir.getChildren()) {
            if (childFile instanceof Directory) {
                Directory childDir = (Directory)childFile;
                long bestChildSize = getBestDeletionSize(childDir, requiredSpace);
                if (bestChildSize < bestSize) {
                    bestSize = bestChildSize;
                }
            }
        }

        return bestSize;
    }

    public static void main(String[] args) {
        
        Directory root = new Directory("/");
        Directory pwd = root;

        List<String> inputs = InputReader.readLinesFromInput("advent/day07/input.txt");
        int index = 0;

        while (index < inputs.size()) {
            String[] tokens = inputs.get(index).split(" ");
            if (!tokens[0].equals(PROMPT)) {
                System.exit(1);
            }

            if (tokens[1].equals(CHANGE_DIR)) {
                switch(tokens[2]) {
                    case PARENT_DIR:
                        pwd = pwd.getParent();
                        break;
                    case ROOT_DIR:
                        pwd = root;
                        break;
                    default:
                        pwd = pwd.getChild(tokens[2]);
                        break;
                }
                index++;
            } else if (tokens[1].equals(LIST)) {
                index++;
                while (index < inputs.size() && !PROMPT.equals(inputs.get(index).substring(0, 1))) {
                    tokens = inputs.get(index).split(" ");
                    File child;
                    if (tokens[0].equals(DIRECTORY)) {
                        child = new Directory(tokens[1]);
                    } else {
                        child = new LeafFile(tokens[1], Long.parseLong(tokens[0]));
                    }
                    pwd.addChild(child);
                    index++;
                }
            }
        }

        //System.out.println(getDeletableSize(root));

        long freeSpace = DISK_SIZE - root.getSize();
        long requiredSpace = UPDATE_SIZE - freeSpace;

        System.out.println(getBestDeletionSize(root, requiredSpace));
    }
}