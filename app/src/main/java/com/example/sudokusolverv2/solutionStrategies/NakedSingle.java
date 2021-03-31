package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.Solver;

import java.util.HashSet;

public class NakedSingle extends Solver {

    private Solver solver;

    public void enterNakedSingle() {
        int[] pos = getNakedSingleLocation();
        int row = pos[0];
        int column = pos[1];
        int solution = pos[2];
        solver.setSelectedRow(row + 1);
        solver.setSelectedColumn(column + 1);
        solver.setNumberPos(solution);
    }

    public int[] getNakedSingleLocation() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = solver.getCandidates(r, c);
                if (candidates != null && candidates.size() == 1) {
                    int[] pos = new int[3];
                    pos[0] = r;
                    pos[1] = c;
                    for (int i : candidates) {
                        pos[2] = i;
                    }
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
