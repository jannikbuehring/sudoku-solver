package com.example.sudokusolverv2.solutionStrategies.nakedSubset;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;
import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedPair;

import java.util.ArrayList;

public class NakedPairFinder {

    private Solver solver;

    private boolean checkIfNakedPairCanRemoveCandidatesFromRow(NakedPair nakedPair) {
        for (int c = 0; c < 9; c++) {
            // skip the actual naked pair
            if (c == nakedPair.field1.column || c == nakedPair.field2.column) {
                continue;
            }
            // if other candidates of the row contain a number of the naked pair, return true
            for (int i : nakedPair.field1.candidateSet) {
                if (solver.calculatedCandidates.get(nakedPair.field1.row * 9 + c).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfNakedPairCanRemoveCandidatesFromColumn(NakedPair nakedPair) {
        for (int r = 0; r < 9; r++) {
            // skip the actual naked pair
            if (r == nakedPair.field1.row || r == nakedPair.field2.row) {
                continue;
            }
            // if other candidates of the column contain a number of the naked pair, return true
            for (int i : nakedPair.field1.candidateSet) {
                if (solver.calculatedCandidates.get(r * 9 + nakedPair.field1.column).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfNakedPairCanRemoveCandidatesFromBlock(NakedPair nakedPair) {
        int boxRow = nakedPair.field1.row / 3;
        int boxCol = nakedPair.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked pair
                if (r == nakedPair.field1.row && c == nakedPair.field1.column
                        || r == nakedPair.field2.row && c == nakedPair.field2.column) {
                    continue;
                }
                for (int i : nakedPair.field1.candidateSet) {
                    if (solver.calculatedCandidates.get(r * 9 + c).contains(i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void applyNakedPairToRow() {
        NakedPair nakedPair = getNakedPairInRow();
        if (nakedPair == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            // skip the actual naked pair
            if (c == nakedPair.field1.column || c == nakedPair.field2.column) {
                continue;
            }
            solver.calculatedCandidates.get(nakedPair.field1.row * 9 + c)
                    .removeAll(nakedPair.field1.candidateSet);
        }
    }

    public void applyNakedPairToColumn() {
        NakedPair nakedPair = getNakedPairInColumn();
        if (nakedPair == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            // skip the actual naked pair
            if (r == nakedPair.field1.row || r == nakedPair.field2.row) {
                continue;
            }
            solver.calculatedCandidates.get(r * 9 + nakedPair.field1.column)
                    .removeAll(nakedPair.field1.candidateSet);
        }
    }

    public void applyNakedPairToBlock() {
        NakedPair nakedPair = getNakedPairInBlock();
        if (nakedPair == null) {
            return;
        }
        int boxRow = nakedPair.field1.row / 3;
        int boxCol = nakedPair.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked pair
                if (r == nakedPair.field1.row && c == nakedPair.field1.column
                        || r == nakedPair.field2.row && c == nakedPair.field2.column) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + c).removeAll(nakedPair.field1.candidateSet);
            }
        }
    }

    public NakedPair getNakedPairInRow() {
        for (int r = 0; r < 9; r++) {
            ArrayList<FieldCandidates> completeRow = new ArrayList<>();
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    completeRow.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeRow) {
                for (FieldCandidates candidates2 : completeRow) {
                    if (candidates == candidates2) {
                        continue;
                    }
                    if (candidates.candidateSet.size() == 2 && candidates2.candidateSet.size() == 2) {
                        if (candidates.candidateSet.equals(candidates2.candidateSet)) {
                            NakedPair nakedPair = new NakedPair(candidates, candidates2);
                            if (checkIfNakedPairCanRemoveCandidatesFromRow(nakedPair)) {
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
            ArrayList<FieldCandidates> completeCol = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    completeCol.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeCol) {
                for (FieldCandidates candidates2 : completeCol) {
                    if (candidates == candidates2) {
                        continue;
                    }
                    if (candidates.candidateSet.size() == 2 && candidates2.candidateSet.size() == 2) {
                        if (candidates.candidateSet.equals(candidates2.candidateSet)) {
                            NakedPair nakedPair = new NakedPair(candidates, candidates2);
                            if (checkIfNakedPairCanRemoveCandidatesFromColumn(nakedPair)) {
                                return nakedPair;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedPair getNakedPairInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                ArrayList<FieldCandidates> completeBlock = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            FieldCandidates candidateSet = new
                                    FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                            completeBlock.add(candidateSet);
                        }
                    }
                }
                for (FieldCandidates candidates : completeBlock) {
                    for (FieldCandidates candidates2 : completeBlock) {
                        if (candidates == candidates2) {
                            continue;
                        }
                        if (candidates.candidateSet.size() == 2
                                && candidates2.candidateSet.size() == 2) {
                            if (candidates.candidateSet.equals(candidates2.candidateSet)) {
                                NakedPair nakedPair = new NakedPair(candidates, candidates2);
                                if (checkIfNakedPairCanRemoveCandidatesFromBlock(nakedPair)) {
                                    return nakedPair;
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
