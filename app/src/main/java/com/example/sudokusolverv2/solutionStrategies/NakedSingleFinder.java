package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.Solver;

import java.util.HashSet;

public class NakedSingleFinder {

    private Solver solver;

    public void enterNakedSingle() {
        NakedSingle nakedSingle = getNakedSingle();
        int row = nakedSingle.row;
        int column = nakedSingle.col;
        int solution = nakedSingle.value;
        solver.setSelectedRow(row + 1);
        solver.setSelectedColumn(column + 1);
        solver.setNumberPos(solution);
        solver.updateCandidates();
    }

    public NakedSingle getNakedSingle() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                if (candidates != null && candidates.size() == 1) {
                    int value = -1;
                    for (int i : candidates) {
                        value = i;
                    }
                    NakedSingle nakedSingle = new NakedSingle(r, c, value);
                    return nakedSingle;
                }
            }
        }
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
