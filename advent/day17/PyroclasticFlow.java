package advent.day17;

import java.util.ArrayList;
import java.util.List;

import advent.common.util.InputReader;

public class PyroclasticFlow {

    private static class Rock {

        private final boolean[][] shape;

        public Rock(List<String> inputs) {
            shape = new boolean[inputs.size()][inputs.get(0).length()];
            for (int y = 0; y < inputs.size(); y++) {
                for (int x = 0; x < inputs.get(y).length(); x++) {
                    if (inputs.get(y).charAt(x) == '#') {
                        shape[inputs.size() - y - 1][x] = true;
                    }
                }
            }
        }

        public boolean[][] getShape() {
            return shape;
        };

        public int getNumRows() {
            return shape.length;
        }

        public int getNumCols() {
            return shape[0].length;
        }
    }

    private static class Board {
        private boolean[][] cells;

        private final String jets;

        private int jetCount = 0;

        private int towerHeight = 0;

        private static final int NUM_COLS = 7;

        public Board(String jets, int maxNumRocks) {
            cells = new boolean[5 * maxNumRocks + 5][NUM_COLS];
            this.jets = jets;
        }

        private boolean hasCollisons(final Rock rock, int testX, int testY) {
            for (int row=0; row<rock.getNumRows(); row++) {
                for (int col=0; col<rock.getNumCols(); col++) {
                    if (testX+col >= NUM_COLS || rock.getShape()[row][col] && cells[testY+row][testX+col]) {
                        return true;
                    }
                }
            }

            return false;
        }

        private void solidifyRock(final Rock rock, int x, int y) {
            for (int row=0; row<rock.getNumRows(); row++) {
                for (int col=0; col<rock.getNumCols(); col++) {
                    if (rock.getShape()[row][col]) {
                        cells[y+row][x+col] = true;
                        towerHeight = Math.max(towerHeight, y + row + 1);
                    }
                }
            }
        }

        public int dropRock(final Rock rock) {
            // (x,y) is position of lower left corner of rock's shape (0,0)
            int x = 2;
            int y = towerHeight + 3;
            int origTowerHeight = towerHeight;

            boolean falling = true;
            while (falling) {
                // JET
                int testX = jets.charAt(jetCount % jets.length()) == '<' ? Math.max(0, x - 1) : Math.min(NUM_COLS - 1, x + 1);
                if (!hasCollisons(rock, testX, y)) {
                    x = testX;
                }
                jetCount++;

                // DROP
                int testY = y - 1;
                if (y == 0 || hasCollisons(rock, x, testY)) {
                    falling = false;
                } else {
                    y = testY;
                }
            }

            solidifyRock(rock, x, y);

            return towerHeight - origTowerHeight;
        }

        public int getTowerHeight() {
            return towerHeight;
        }

        public void printTower() {
            for (int row = towerHeight + 3; row >= 0; row --) {
                for (int col = 0; col < NUM_COLS; col++) {
                    System.out.print(cells[row][col] ? '#' : '.');
                }
                System.out.println();
            }
            System.out.println("~~~~~~~");
        }
    }

    private static int getRepeatingPattern(List<Integer> sequence, List<Integer> repeatingPattern, int minPatternSize) {
        repeatingPattern.clear();

        for (int testLength = minPatternSize; testLength < sequence.size() / 3 - 3; testLength++) {
            for (int offset = 0; offset < sequence.size() / 3 - 3; offset++) {
                for (int cursor = 0; cursor <= testLength; cursor++) {
                    if (!sequence.get(cursor + offset).equals(sequence.get(cursor + offset + testLength))) {
                        break;
                    }
                    if (cursor == testLength) {
                        repeatingPattern.addAll(sequence.subList(offset, offset + testLength));
                        return offset;
                    }
                }
            }
        }
        
        return -1;
    }

    public static void main(String[] args) {
    
        List<Rock> rockRotation = new ArrayList<>();

        List<String> shapeInput = InputReader.readLinesFromInput("advent/day17/shapes.txt");
        List<String> currentShapeInputs = new ArrayList<>();

        for (String line : shapeInput) {
            if (line.isEmpty()) {
                rockRotation.add(new Rock(currentShapeInputs));
                currentShapeInputs.clear();
            } else {
                currentShapeInputs.add(line);
            }
        }

        String jetInput = InputReader.readLinesFromInput("advent/day17/jets.txt").get(0);

        List<Integer> heightDeltas = new ArrayList<>();
        List<Integer> repeatingPattern = new ArrayList<>();

        Board board = new Board(jetInput, 10000);

        int rockNum = 0;
        int patternStartIndex = -1;

        while (true) {
            heightDeltas.add(board.dropRock(rockRotation.get(rockNum % rockRotation.size())));
            patternStartIndex = getRepeatingPattern(heightDeltas, repeatingPattern, jetInput.length());
            if (patternStartIndex != -1) {
                break;
            }

            rockNum++;
        }

        long totalRocksDropped = 1000000000000L;
        long height = 0;

        // Get height from initial non-repeating portion of tower        
        height += heightDeltas.subList(0, patternStartIndex).stream().mapToLong(Long::valueOf).sum();

        // Add to the height for each complete repetition of the pattern
        long patternHeight = repeatingPattern.stream().mapToLong(Long::valueOf).sum();
        long numPatternReps = (totalRocksDropped - patternStartIndex) / repeatingPattern.size();
        height += numPatternReps * patternHeight;

        // Add to the height for the incomplete remainder of the pattern
        int numRemainderRocks = (int) ((totalRocksDropped - patternStartIndex) % repeatingPattern.size());
        height += repeatingPattern.subList(0, numRemainderRocks).stream().mapToLong(Long::valueOf).sum();

        System.out.println(height);
    }
}