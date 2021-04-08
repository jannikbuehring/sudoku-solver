package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.FieldCandidates;
import com.example.sudokusolverv2.Solver;

import java.util.ArrayList;
import java.util.HashSet;

public class NakedPair {

    private Solver solver;

    public boolean applyNakedPairToCandidates() {
        FieldCandidates[] nakedPair = getNakedPair();
        if(nakedPair == null) {
            return false;
        }
        if(nakedPair[0].row == nakedPair[1].row) {
            boolean removedSomething = false;
            for(int c = 0; c < 9; c++) {
                if(c == nakedPair[0].column || c == nakedPair[1].column) {
                    continue;
                }
                if(solver.calculatedCandidates.get(nakedPair[0].row * 9 + c)
                        .removeAll(nakedPair[0].candidateSet)) {
                    removedSomething = true;
                }
            }
            if(removedSomething) {
                return true;
            }
        }
        return false;
    }

    public FieldCandidates[] getNakedPair() {
        FieldCandidates[] nakedPair;
        if (getNakedPairInRow() != null) {
            nakedPair = getNakedPairInRow();
            return nakedPair;
        }


        return null;
    }

    public FieldCandidates[] getNakedPairInRow() {
        for (int r = 0; r < 9; r++) {
            ArrayList<FieldCandidates> completeRow = new ArrayList<>();
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates(solver.calculatedCandidates.get(r * 9 + c));
                    candidateSet.row = r;
                    candidateSet.column = c;
                    completeRow.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeRow) {
                for (FieldCandidates candidates2 : completeRow) {
                    if (candidates == candidates2) {
                        continue;
                    }
                    if(candidates.candidateSet.size() == 2 && candidates2.candidateSet.size() == 2) {
                        if(candidates.candidateSet.equals(candidates2.candidateSet)) {
                            FieldCandidates[] nakedPair = new FieldCandidates[2];
                            nakedPair[0] = candidates;
                            nakedPair[1] = candidates2;
                            return nakedPair;
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
