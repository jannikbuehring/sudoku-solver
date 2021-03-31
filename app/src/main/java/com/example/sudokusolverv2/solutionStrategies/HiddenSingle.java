package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.Solver;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenSingle {

    private Solver solver;

    public boolean EnterHiddenSingleRow(int row) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(9);
        for (int cap = 0; cap < 9; cap++) {
            list.add(new ArrayList<>());
        }
        for (int c = 0; c < 9; c++) {
            if (solver.board[row][c] == 0) {
                HashSet<Integer> candidates = solver.getCandidates(row, c);
                if (candidates != null) {
                    for (int i : candidates) {
                        list.get(i - 1).add(i);
                    }
                }
            }
        }
        int hiddenSingle = 0;
        for (int i = 0; i < 9; i++) {
            if (list.get(i).size() == 1) {
                hiddenSingle = list.get(i).get(0);
                break;
            }
        }
        if (hiddenSingle > 0) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[row][c] == 0) {
                    HashSet<Integer> candidates = solver.getCandidates(row, c);
                    if (candidates != null) {
                        for (int i : candidates) {
                            if (i == hiddenSingle) {
                                solver.board[row][c] = i;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean EnterHiddenSingleCol(int col) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(9);
        for (int cap = 0; cap < 9; cap++) {
            list.add(new ArrayList<>());
        }
        for (int r = 0; r < 9; r++) {
            if (solver.board[r][col] == 0) {
                HashSet<Integer> candidates = solver.getCandidates(r, col);
                if (candidates != null) {
                    for (int i : candidates) {
                        list.get(i - 1).add(i);
                    }
                }
            }
        }
        int hiddenSingle = 0;
        for (int i = 0; i < 9; i++) {
            if (list.get(i).size() == 1) {
                hiddenSingle = list.get(i).get(0);
                break;
            }
        }
        if (hiddenSingle > 0) {
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][col] == 0) {
                    HashSet<Integer> candidates = solver.getCandidates(r, col);
                    if (candidates != null) {
                        for (int i : candidates) {
                            if (i == hiddenSingle) {
                                solver.board[r][col] = i;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
