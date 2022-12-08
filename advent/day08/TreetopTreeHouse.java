package advent.day08;

import java.util.List;

import advent.common.util.InputReader;

public class TreetopTreeHouse {

    private static boolean[][] getVizMap(int[][] trees) {
        boolean[][] vizMap = new boolean[trees.length][trees[0].length];

        for (int i=0; i<trees.length; i++) {
            int tallest = -1;
            for (int j=0; j<trees[i].length; j++) {
                if (trees[i][j] > tallest) {
                    vizMap[i][j] = true;
                    tallest = trees[i][j];
                }
            }

            tallest = -1;
            for (int j=trees[i].length-1; j>=0; j--) {
                if (trees[i][j] > tallest) {
                    vizMap[i][j] = true;
                    tallest = trees[i][j];
                } 
            }
        }

        for (int j=0; j<trees[0].length; j++) {
            int tallest = -1;
            for (int i=0; i<trees.length; i++) {
                if (trees[i][j] > tallest) {
                    vizMap[i][j] = true;
                    tallest = trees[i][j];
                }
            }

            tallest = -1;
            for (int i=trees.length-1; i>=0; i--) {
                if (trees[i][j] > tallest) {
                    vizMap[i][j] = true;
                    tallest = trees[i][j];
                } 
            }
        }

        return vizMap;
    }

    private static int getNumVisibleTrees(boolean[][] vizMap) {
        int count = 0;
        for (boolean[] row : vizMap) {
            for (boolean isViz : row) {
                if (isViz) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int[][] getScenicScores(int[][] trees) {
        int[][] scores = new int[trees.length][trees[0].length];

        for (int i=0; i<trees.length; i++) {
            for (int j=0; j<trees[i].length; j++) {
                int cursor;
                int myHeight = trees[i][j];
                int viewDistance = 0;
                scores[i][j] = 1;

                // LEFT
                for (cursor = j-1; cursor>=0; cursor--) {
                    viewDistance++;
                    if (trees[i][cursor] >= myHeight) {
                        break;
                    }
                }
                scores[i][j] *= viewDistance;

                // RIGHT
                viewDistance = 0;
                for (cursor = j+1; cursor<trees[i].length; cursor++) {
                    viewDistance++;
                    if (trees[i][cursor] >= myHeight) {
                        break;
                    }
                }
                scores[i][j] *= viewDistance;

                // UP
                viewDistance = 0;
                for (cursor = i-1; cursor>=0; cursor--) {
                    viewDistance++;
                    if (trees[cursor][j] >= myHeight) {
                        break;
                    }
                }
                scores[i][j] *= viewDistance;

                // DOWN
                cursor = i+1;
                viewDistance = 0;
                for (cursor = i+1; cursor<trees.length; cursor++) {
                    viewDistance++;
                    if (trees[cursor][j] >= myHeight) {
                        break;
                    }
                }
                scores[i][j] *= viewDistance;
            }
        }

        return scores;
    }

    private static int getMaxScenicScore(int[][] scores) {
        int maxScore = 0;
        for (int i=0; i<scores.length; i++) {
            for (int j=0; j<scores[i].length; j++) {
                maxScore = Math.max(maxScore, scores[i][j]);
            }
        }
        return maxScore;
    }

    public static void main(String[] args) {
        List<String> input = InputReader.readLinesFromInput("advent/day08/input.txt");
        int[][] trees = new int[input.size()][input.get(0).length()];

        int x = 0;
        for (String line : input) {
            int y = 0;
            for (char c : line.toCharArray()) {
                trees[x][y] = Integer.parseInt(""+c);
                y++;
            }
            x++;
        }

        int[][] scores = getScenicScores(trees);
        
        /*for (int i=0; i<scores.length; i++) {
            for (int j=0; j<scores[i].length; j++) {
                System.out.print(scores[i][j] + " ");
            }
            System.out.println();
        }*/

        System.out.println(getMaxScenicScore(scores));
    }
}