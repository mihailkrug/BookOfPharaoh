package com.bookofpharaoh;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MatrixBookOfPharaoh {

    public static final int FREE = 0;

    public static final int N = 4;

    private int[][] numbers;

    private Random rnd;

    private List<SpotSlot> freeSpotSlots;

    private Set<SpotSlot> joinSpotSlots;

    private SpotSlot newSpotSlot;

    public MatrixBookOfPharaoh() {
        rnd = new Random();
        numbers = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                numbers[i][j] = FREE;
            }
        }

        freeSpotSlots = new ArrayList<SpotSlot>();
        joinSpotSlots = new HashSet<SpotSlot>();

        newSpotSlot = new SpotSlot(0, 0);


        for (int i = 0; i < 5; ++i) {
            initSp(2);
        }
    }




    public boolean newGenInit() {
        int v = rnd.nextInt(100);
        if (0 <= v && v <= 79) {
            initSp(2);
            return true;
        } else if (80 <= v && v <= 95) {
            initSp(4);
            return true;
        }
        return false;
    }

    public MatrixBookOfPharaoh(MatrixBookOfPharaoh copy) {
        rnd = copy.rnd;
        numbers = new int[N][N];
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                numbers[r][c] = copy.numbers[r][c];
            }
        }
        freeSpotSlots = new ArrayList<SpotSlot>();
        for (SpotSlot spotSlot : copy.freeSpotSlots) {
            freeSpotSlots.add(spotSlot);
        }

        joinSpotSlots = new HashSet<SpotSlot>();
        for (SpotSlot spotSlot : copy.joinSpotSlots) {
            joinSpotSlots.add(spotSlot);
        }

        newSpotSlot = new SpotSlot(copy.newSpotSlot.r, copy.newSpotSlot.c);
    }

    public int getSpot(int r, int c) {
        return numbers[r][c];
    }

    private void initSp(int n) {
        collectEmptySpots();
        if (!freeSpotSlots.isEmpty()) {
            int i = rnd.nextInt(freeSpotSlots.size());
            newSpotSlot = freeSpotSlots.get(i);
            numbers[newSpotSlot.r][newSpotSlot.c] = n;
        }
    }
    public boolean isStuck() {
        //Log.e("2048", "Hello");
        MatrixBookOfPharaoh copy = new MatrixBookOfPharaoh(this);
        int score = 0;
        for (Direction d : Direction.values()) {
            score += copy.swipe(d);
        }
        copy.collectEmptySpots();
        //Log.e("2048", "Hello end()");
        return score == 0 && copy.freeSpotSlots.isEmpty();
    }

    private void collectEmptySpots() {
        freeSpotSlots.clear();
        for (int x = 0; x < N; ++x) {
            for (int y = 0; y < N; ++y) {
                if (numbers[x][y] == FREE) {
                    freeSpotSlots.add(new SpotSlot(x, y));
                }
            }
        }
    }



    public int joinLeft(int row) {
        int idx = 0;
        int score = 0;
        boolean joined = false;
        for (int i = 0; i < N; ++i) {
            if (numbers[row][i] != FREE) {

                int farthest = -1;
                for (int j = i + 1; j < N; ++j) {
                    if (numbers[row][j] != FREE) {
                        farthest = j;
                        break;
                    }
                }

                joined = false;
                if (farthest != -1) {

                    if (numbers[row][i] == numbers[row][farthest]) {
                        numbers[row][i] += numbers[row][farthest];
                        score += numbers[row][i];
                        numbers[row][farthest] = FREE;
                        joined = true;
                    }
                }
                numbers[row][idx] = numbers[row][i];
                if (joined) {
                    joinSpotSlots.add(new SpotSlot(row, idx));
                }
                if (idx != i) {
                    numbers[row][i] = FREE;
                }
                idx++;
            }
        }
        return score;
    }

    public int joinRight(int row) {
        int idx = N - 1;
        int score = 0;
        boolean joined = false;
        for (int i = N - 1; i >= 0; --i) {
            if (numbers[row][i] != FREE) {

                int farthest = -1;
                for (int j = i - 1; j >= 0; --j) {
                    if (numbers[row][j] != FREE) {
                        farthest = j;
                        break;
                    }
                }

                joined = false;
                if (farthest != -1) {

                    if (numbers[row][i] == numbers[row][farthest]) {
                        numbers[row][i] += numbers[row][farthest];
                        score += numbers[row][i];
                        numbers[row][farthest] = FREE;
                        joined = true;
                    }
                }
                numbers[row][idx] = numbers[row][i];
                if (joined) {
                    joinSpotSlots.add(new SpotSlot(row, idx));
                }
                if (idx != i) {
                    numbers[row][i] = FREE;
                }
                idx--;
            }
        }
        return score;
    }

    public int joinUp(int column) {
        int idx = 0;
        int score = 0;
        boolean joined = false;
        for (int i = 0; i < N; ++i) {
            if (numbers[i][column] != FREE) {

                int farthest = -1;
                for (int j = i + 1; j < N; ++j) {
                    if (numbers[j][column] != FREE) {
                        farthest = j;
                        break;
                    }
                }
                joined = false;
                if (farthest != -1) {

                    if (numbers[i][column] == numbers[farthest][column]) {
                        numbers[i][column] += numbers[farthest][column];
                        score = numbers[i][column];
                        numbers[farthest][column] = FREE;
                        joined = true;
                    }
                }
                numbers[idx][column] = numbers[i][column];
                if (joined) {
                    joinSpotSlots.add(new SpotSlot(idx, column));
                }
                if (idx != i) {
                    numbers[i][column] = FREE;
                }
                idx++;
            }
        }
        return score;
    }

    public int mergeDown(int column) {
        int idx = N - 1;
        int score = 0;
        boolean joined;
        for (int i = N - 1; i >= 0; --i) {
            if (numbers[i][column] != FREE) {

                int farthest = -1;
                for (int j = i - 1; j >= 0; --j) {
                    if (numbers[j][column] != FREE) {
                        farthest = j;
                        break;
                    }
                }

                joined = false;
                if (farthest != -1) {

                    if (numbers[i][column] == numbers[farthest][column]) {
                        numbers[i][column] += numbers[farthest][column];
                        score = numbers[i][column];
                        numbers[farthest][column] = FREE;
                        joined = true;
                    }
                }
                numbers[idx][column] = numbers[i][column];
                if (joined) {
                    joinSpotSlots.add(new SpotSlot(idx, column));
                }
                if (idx != i) {
                    numbers[i][column] = FREE;
                }
                idx--;
            }
        }
        return score;
    }


    public boolean isMergeSpot(int r, int c) {
        return joinSpotSlots.contains(new SpotSlot(r, c));
    }




    public int swipe(Direction dir) {
        int totalScore = 0;
        joinSpotSlots.clear();
        if (dir == Direction.DOWN || dir == Direction.UP) {
            for (int i = 0; i < N; ++i) {
                totalScore += (dir == Direction.DOWN) ? mergeDown(i) : joinUp(i);
            }
        } else {
            for (int i = 0; i < N; ++i) {
                totalScore += (dir == Direction.LEFT) ? joinLeft(i) : joinRight(i);
            }
        }
        return totalScore;
    }

    public static class SpotSlot {
        public final int r;
        public final int c;

        public SpotSlot(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SpotSlot)) return false;

            SpotSlot spotSlot = (SpotSlot) o;

            if (c != spotSlot.c) return false;
            if (r != spotSlot.r) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + c;
            return result;
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                builder.append("|");
                builder.append(numbers[r][c]);
                builder.append("|");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
