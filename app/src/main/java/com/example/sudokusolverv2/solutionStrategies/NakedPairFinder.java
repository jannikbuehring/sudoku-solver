package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;
import com.example.sudokusolverv2.Solver;

import java.util.ArrayList;

public class NakedPairFinder {

    private Solver solver;

    private boolean checkIfNakedPairCanRemoveCandidatesFromRow(NakedPair nakedPair) {
        for(int c = 0; c < 9; c++) {
            if(c == nakedPair.field1.column || c == nakedPair.field2.column) {
                continue;
            }
            for (int i : nakedPair.field1.candidateSet) {
                if(solver.calculatedCandidates.get(nakedPair.field1.row * 9 + c).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfNakedPairCanRemoveCandidatesFromColumn(NakedPair nakedPair) {
        for(int r = 0; r < 9; r++) {
            if(r == nakedPair.field1.row || r == nakedPair.field2.row) {
                continue;
            }
            for (int i : nakedPair.field1.candidateSet) {
                if(solver.calculatedCandidates.get(r * 9 + nakedPair.field1.column).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void applyNakedPairToRow() {
        NakedPair nakedPair = getNakedPairInRow();
        if(nakedPair == null) {
            return;
        }

        if(nakedPair.field1.row == nakedPair.field2.row) {
            for(int c = 0; c < 9; c++) {
                // skip the actual naked pair
                if(c == nakedPair.field1.column || c == nakedPair.field2.column) {
                    continue;
                }
                solver.calculatedCandidates.get(nakedPair.field1.row * 9 + c)
                        .removeAll(nakedPair.field1.candidateSet);
            }
        }
    }

    public void applyNakedPairToColumn() {
        NakedPair nakedPair = getNakedPairInColumn();
        if(nakedPair == null) {
            return;
        }
        if(nakedPair.field1.column == nakedPair.field2.column) {
            for(int r = 0; r < 9; r++) {
                // skip the actual naked pair
                if(r == nakedPair.field1.row || r == nakedPair.field2.row) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + nakedPair.field1.column)
                        .removeAll(nakedPair.field1.candidateSet);
            }
        }
    }

    public NakedPair getNakedPairInRow() {
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
                            NakedPair nakedPair = new NakedPair(candidates, candidates2);
                            if(checkIfNakedPairCanRemoveCandidatesFromRow(nakedPair)) {
                                return nakedPair;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedPair getNakedPairInColumn() {
        for (int c = 0; c < 9; c++) {
            ArrayList<FieldCandidates> completeRow = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
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
                            NakedPair nakedPair = new NakedPair(candidates, candidates2);
                            if(checkIfNakedPairCanRemoveCandidatesFromColumn(nakedPair)) {
                                return nakedPair;
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
