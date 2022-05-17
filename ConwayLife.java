import java.util.Arrays;

public class ConwayLife {

    public static void main(String[] args) {
        int[][] gen = {{1,1,0},{0,1,1},{0,0,0}};
        System.out.println(Arrays.deepToString(getGeneration(gen, 3)));
    }

    public static int[][] getGeneration(int[][] cells, int generations) {
        int[][] universe = expandOnAllSides(cells);
        universe = expandOnAllSides(universe);

        for (int g = 1; g <= generations; g++) {
            boolean shouldExpandUp = false;
            boolean shouldExpandDown = false;
            boolean shouldExpandRight = false;
            boolean shouldExpandLeft = false;
            int[][] nextGenUniverse = new int[universe.length][universe[0].length];

            for (int i = 1; i < universe.length - 1; i++) {
                for (int j = 1; j < universe[0].length - 1; j++) {

                    int numOfLiveNeighbours = 0;

                    for (int p = -1; p <= 1; p++) {
                        for (int q = -1; q <= 1; q++) {
                            if (universe[i + p][j + q] == 1 && !(p == 0 && q == 0)) {
                                numOfLiveNeighbours++;
                            }
                        }
                    }

                    if (universe[i][j] == 0 && numOfLiveNeighbours == 3) {
                        nextGenUniverse[i][j] = 1;
                        if (i == 1) {
                            shouldExpandUp = true;
                        } else if (i == universe.length - 2) {
                            shouldExpandDown = true;
                        }
                        if (j == 1) {
                            shouldExpandLeft = true;
                        } else if (j == universe[0].length - 2) {
                            shouldExpandRight = true;
                        }
                    } else if (universe[i][j] == 1 && (numOfLiveNeighbours == 2 || numOfLiveNeighbours == 3)) {
                        nextGenUniverse[i][j] = 1;
                    }
                }
            }
            universe = nextGenUniverse;
            if (shouldExpandUp) {
                universe = expandUp(universe);
            }
            if (shouldExpandDown) {
                universe = expandDown(universe);
            }
            if (shouldExpandLeft) {
                universe = expandLeft(universe);
            }
            if (shouldExpandRight) {
                universe = expandRight(universe);
            }
        }
        return cropUniverse(universe);
    }

    public static int[][] cropUniverse(int[][] universe) {
        // num. of lines to crop from each side:
        int cropTop = scanTop(universe);
        if (cropTop == universe.length) {
            return new int[0][0];
        }
        int cropBottom = scanBottom(universe);
        int cropRight = scanRight(universe);
        int cropLeft = scanLeft(universe);

        int[][] cropped = new int[universe.length - cropTop - cropBottom][universe[0].length - cropRight - cropLeft];

        for (int i = 0; i < cropped.length; i++) {
            System.arraycopy(universe[i + cropTop], cropLeft, cropped[i], 0,
                    universe[0].length - cropRight - cropLeft);
        }
        return cropped;
    }

    public static int scanTop(int[][] universe) {
        int linesToCrop = 0;

        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe[0].length; j++) {
                if (universe[i][j] != 0) {
                    return linesToCrop;
                }
            }
            linesToCrop++;
        }
        return linesToCrop;
    }

    public static int scanBottom(int[][] universe) {
        int linesToCrop = 0;

        for (int i = universe.length - 1; i >= 0; i--) {
            for (int j = 0; j < universe[0].length; j++) {
                if (universe[i][j] != 0) {
                    return linesToCrop;
                }
            }
            linesToCrop++;
        }
        return linesToCrop;
    }

    public static int scanRight(int[][] universe) {
        int linesToCrop = 0;

        for (int j = universe[0].length - 1; j >= 0; j--) {
            for (int i = 0; i < universe.length; i++) {
                if (universe[i][j] != 0) {
                    return linesToCrop;
                }
            }
            linesToCrop++;
        }
        return linesToCrop;
    }

    public static int scanLeft(int[][] universe) {
        int linesToCrop = 0;

        for (int j = 0; j < universe[0].length; j++) {
            for (int i = 0; i < universe.length; i++) {
                if (universe[i][j] != 0) {
                    return linesToCrop;
                }
            }
            linesToCrop++;
        }
        return linesToCrop;
    }


    public static int[][] expandUp(int[][] universe) {
        int[][] expanded = new int[universe.length + 1][universe[0].length];

        for (int i = 0; i < universe.length; i++) {
            System.arraycopy(universe[i], 0, expanded[i+1], 0, universe[0].length);
        }
        return expanded;
    }

    public static int[][] expandDown(int[][] universe) {
        int[][] expanded = new int[universe.length + 1][universe[0].length];

        for (int i = 0; i < universe.length; i++) {
            System.arraycopy(universe[i], 0, expanded[i], 0, universe[0].length);
        }
        return expanded;
    }

    public static int[][] expandRight(int[][] universe) {
        int[][] expanded = new int[universe.length][universe[0].length + 1];

        for (int i = 0; i < universe.length; i++) {
            System.arraycopy(universe[i], 0, expanded[i], 0, universe[0].length);
        }
        return expanded;
    }

    public static int[][] expandLeft(int[][] universe) {
        int[][] expanded = new int[universe.length][universe[0].length + 1];

        for (int i = 0; i < universe.length; i++) {
            System.arraycopy(universe[i], 0, expanded[i], 1, universe[0].length);
        }
        return expanded;
    }

    public static int[][] expandOnAllSides(int[][] universe) {
        int[][] expanded;
        expanded = expandRight(universe);
        expanded = expandLeft(expanded);
        expanded = expandUp(expanded);
        expanded = expandDown(expanded);

        return expanded;
    }
}
