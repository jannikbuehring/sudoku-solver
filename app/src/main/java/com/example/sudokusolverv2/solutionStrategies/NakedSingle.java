package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.Solver;

import java.util.HashSet;

public class NakedSingle extends Solver {

    private Solver solver;

    public boolean EnterNakedSingle() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = solver.getCandidates(r, c);
                if (candidates != null && candidates.size() == 1) {
                    for (int i : candidates) {
                        solver.board[r][c] = i;
                    }
                    solver.setSelectedRow(r + 1);
                    solver.setSelectedColumn(c + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public int[] GetNakedSingleLocation() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = solver.getCandidates(r, c);
                if (candidates != null && candidates.size() == 1) {
                    int[] pos = new int[2];
                    pos[0] = r;
                    pos[1] = c;
                    return pos;
                }
            }
        }
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
