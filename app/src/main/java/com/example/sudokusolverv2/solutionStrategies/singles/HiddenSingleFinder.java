package com.example.sudokusolverv2.solutionStrategies.singles;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.solutionStrategies.singles.HiddenSingle;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenSingleFinder {

    private Solver solver;

    public void enterHiddenSingle() {
        HiddenSingle hiddenSingle = getHiddenSingle();
        if (hiddenSingle != null) {
            int row = hiddenSingle.row;
            int column = hiddenSingle.col;
            int solution = hiddenSingle.value;
            solver.setSelectedRow(row + 1);
            solver.setSelectedColumn(column + 1);
            solver.setNumberPos(solution);
            solver.updateCandidates();
        }
    }

    public HiddenSingle getHiddenSingle() {
        HiddenSingle hiddenSingle;

        // Check for hidden singles in rows first
        for (int r = 0; r < 9; r++) {
            if (getHiddenSingleInRow(r) != null) {
                hiddenSingle = getHiddenSingleInRow(r);
                return hiddenSingle;
            }
        }

        // Check for hidden singles in columns
        for (int c = 0; c < 9; c++) {
            if (getHiddenSingleInCol(c) != null) {
                hiddenSingle = getHiddenSingleInCol(c);
                return hiddenSingle;
            }
        }

        // Check for hidden singles in blocks
        if (getHiddenSingleBlockLocation() != null) {
            hiddenSingle = getHiddenSingleBlockLocation();
            return hiddenSingle;
        }

        return null;
    }

    public HiddenSingle getHiddenSingleInRow() {
        HiddenSingle hiddenSingle;
        for (int r = 0; r < 9; r++) {
            if (getHiddenSingleInRow(r) != null) {
                hiddenSingle = getHiddenSingleInRow(r);
                return hiddenSingle;
            }
        }
        return null;
    }

    // Return the location (row, column) of a hidden single in the row
    public HiddenSingle getHiddenSingleInRow(int row) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(9);
        for (int cap = 0; cap < 9; cap++) {
            list.add(new ArrayList<>());
        }
        for (int c = 0; c < 9; c++) {
            if (solver.board[row][c] == 0) {
                HashSet<Integer> candidates = solver.calculatedCandidates.get(row * 9 + c);
                if (candidates != null) {
                    for (int i : candidates) {
                        list.get(i - 1).add(i);
                    }
                }
            }
        }
        int hiddenSingleValue = 0;
        for (int i = 0; i < 9; i++) {
            if (list.get(i).size() == 1) {
                hiddenSingleValue = list.get(i).get(0);
                break;
            }
        }
        if (hiddenSingleValue > 0) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[row][c] == 0) {
                    HashSet<Integer> candidates = solver.calculatedCandidates.get(row * 9 + c);
                    if (candidates != null) {
                        for (int i : candidates) {
                            if (i == hiddenSingleValue) {
                                HiddenSingle hiddenSingle = new HiddenSingle(row, c, hiddenSingleValue);
                                return hiddenSingle;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public HiddenSingle getHiddenSingleInCol() {
        HiddenSingle hiddenSingle;
        for (int c = 0; c < 9; c++) {
            if (getHiddenSingleInCol(c) != null) {
                hiddenSingle = getHiddenSingleInCol(c);
                return hiddenSingle;
            }
        }
        return null;
    }

    // Return the location (row, column) of a hidden single in the column
    public HiddenSingle getHiddenSingleInCol(int col) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(9);
        for (int cap = 0; cap < 9; cap++) {
            list.add(new ArrayList<>());
        }
        for (int r = 0; r < 9; r++) {
            if (solver.board[r][col] == 0) {
                HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + col);
                if (candidates != null) {
                    for (int i : candidates) {
                        list.get(i - 1).add(i);
                    }
                }
            }
        }
        int hiddenSingleValue = 0;
        for (int i = 0; i < 9; i++) {
            if (list.get(i).size() == 1) {
                hiddenSingleValue = list.get(i).get(0);
                break;
            }
        }
        if (hiddenSingleValue > 0) {
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][col] == 0) {
                    HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + col);
                    if (candidates != null) {
                        for (int i : candidates) {
                            if (i == hiddenSingleValue) {
                                HiddenSingle hiddenSingle = new HiddenSingle(r, col, hiddenSingleValue);
                                return hiddenSingle;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // Return the location (row, column) of a hidden single in a block
    public HiddenSingle getHiddenSingleBlockLocation() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {

                ArrayList<ArrayList<Integer>> list = new ArrayList<>(9);
                for (int cap = 0; cap < 9; cap++) {
                    list.add(new ArrayList<>());
                }

                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                            if (candidates != null) {
                                for (int i : candidates) {
                                    list.get(i - 1).add(i);
                                }
                            }
                        }
                    }
                }

                int hiddenSingleValue = 0;
                for (int i = 0; i < 9; i++) {
                    if (list.get(i).size() == 1) {
                        hiddenSingleValue = list.get(i).get(0);
                        break;
                    }
                }
                if (hiddenSingleValue > 0) {
                    for (int r = row; r < row + 3; r++) {
                        for (int c = col; c < col + 3; c++) {
                            if (solver.board[r][c] == 0) {
                                HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                                if (candidates != null) {
                                    for (int i : candidates) {
                                        if (i == hiddenSingleValue) {
                                            HiddenSingle hiddenSingle = new HiddenSingle(r, col, hiddenSingleValue);
                                            return hiddenSingle;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
