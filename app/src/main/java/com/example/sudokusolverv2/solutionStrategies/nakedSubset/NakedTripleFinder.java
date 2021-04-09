package com.example.sudokusolverv2.solutionStrategies.nakedSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;
import java.util.HashSet;

public class NakedTripleFinder {

    private Solver solver;

    private boolean checkIfNakedTripleCanRemoveCandidatesFromRow(NakedTriple nakedTriple) {
        for (int c = 0; c < 9; c++) {
            // skip the actual naked triple
            if (c == nakedTriple.field1.column || c == nakedTriple.field2.column
                    || c == nakedTriple.field3.column) {
                continue;
            }
            // if other candidates of the row contain a number of the naked triple, return true
            for (int i : nakedTriple.candidateUnion) {
                if (solver.calculatedCandidates.get(nakedTriple.field1.row * 9 + c).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfNakedTripleCanRemoveCandidatesFromColumn(NakedTriple nakedTriple) {
        for (int r = 0; r < 9; r++) {
            // skip the actual naked triple
            if (r == nakedTriple.field1.row || r == nakedTriple.field2.row
                    || r == nakedTriple.field3.row) {
                continue;
            }
            // if other candidates of the row contain a number of the naked triple, return true
            for (int i : nakedTriple.candidateUnion) {
                if (solver.calculatedCandidates.get(r * 9 + nakedTriple.field1.column).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfNakedTripleCanRemoveCandidatesFromBlock(NakedTriple nakedTriple) {
        int boxRow = nakedTriple.field1.row / 3;
        int boxCol = nakedTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked triple
                if (r == nakedTriple.field1.row && c == nakedTriple.field1.column
                        || r == nakedTriple.field2.row && c == nakedTriple.field2.column
                        || r == nakedTriple.field3.row && c == nakedTriple.field3.column) {
                    continue;
                }
                // if other candidates of the row contain a number of the naked triple, return true
                for (int i : nakedTriple.candidateUnion) {
                    if (solver.calculatedCandidates.get(r * 9 + c).contains(i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void applyNakedTripleToRow() {
        NakedTriple nakedTriple = getNakedTripleInRow();
        if (nakedTriple == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            //skip the actual naked triple
            if (c == nakedTriple.field1.column || c == nakedTriple.field2.column
                    || c == nakedTriple.field3.column) {
                continue;
            }
            solver.calculatedCandidates.get(nakedTriple.field1.row * 9 + c)
                    .removeAll(nakedTriple.candidateUnion);
        }
    }

    public void applyNakedTripleToColumn() {
        NakedTriple nakedTriple = getNakedTripleInColumn();
        if (nakedTriple == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            //skip the actual naked triple
            if (r == nakedTriple.field1.row || r == nakedTriple.field2.row
                    || r == nakedTriple.field3.row) {
                continue;
            }
            solver.calculatedCandidates.get(r * 9 + nakedTriple.field1.column)
                    .removeAll(nakedTriple.candidateUnion);
        }
    }

    public void applyNakedTripleToBlock() {
        NakedTriple nakedTriple = getNakedTripleInBlock();
        if (nakedTriple == null) {
            return;
        }
        int boxRow = nakedTriple.field1.row / 3;
        int boxCol = nakedTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked triple
                if (r == nakedTriple.field1.row && c == nakedTriple.field1.column
                        || r == nakedTriple.field2.row && c == nakedTriple.field2.column
                        || r == nakedTriple.field3.row && c == nakedTriple.field3.column) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + c).removeAll(nakedTriple.candidateUnion);
            }
        }
    }

    public NakedTriple getNakedTripleInRow() {
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
                    for (FieldCandidates candidates3 : completeRow) {
                        // making sure we are not comparing the same fields
                        if (candidates == candidates2 || candidates == candidates3
                                || candidates2 == candidates3) {
                            continue;
                        }
                        // if the size of the union set of all three sets is three, it should be
                        // a naked triple
                        HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                        result.addAll(candidates2.candidateSet);
                        result.addAll(candidates3.candidateSet);
                        if (result.size() == 3) {
                            NakedTriple nakedTriple = new NakedTriple(candidates, candidates2,
                                    candidates3, result);
                            if (checkIfNakedTripleCanRemoveCandidatesFromRow(nakedTriple)) {
                                return nakedTriple;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedTriple getNakedTripleInColumn() {
        for (int c = 0; c < 9; c++) {
            ArrayList<FieldCandidates> completeColumn = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    completeColumn.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeColumn) {
                for (FieldCandidates candidates2 : completeColumn) {
                    for (FieldCandidates candidates3 : completeColumn) {
                        // making sure we are not comparing the same fields
                        if (candidates == candidates2 || candidates == candidates3
                                || candidates2 == candidates3) {
                            continue;
                        }
                        // if the size of the union set of all three sets is three, it should be
                        // a naked triple
                        HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                        result.addAll(candidates2.candidateSet);
                        result.addAll(candidates3.candidateSet);
                        if (result.size() == 3) {
                            NakedTriple nakedTriple = new NakedTriple(candidates, candidates2,
                                    candidates3, result);
                            if (checkIfNakedTripleCanRemoveCandidatesFromColumn(nakedTriple)) {
                                return nakedTriple;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedTriple getNakedTripleInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                ArrayList<FieldCandidates> completeBlock = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            FieldCandidates candidateSet = new FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                            completeBlock.add(candidateSet);
                        }
                    }
                }
                for (FieldCandidates candidates : completeBlock) {
                    for (FieldCandidates candidates2 : completeBlock) {
                        for (FieldCandidates candidates3 : completeBlock) {
                            // making sure we are not comparing the same fields
                            if (candidates == candidates2 || candidates == candidates3
                                    || candidates2 == candidates3) {
                                continue;
                            }
                            // if the size of the union set of all three sets is three, it should be
                            // a naked triple
                            HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                            result.addAll(candidates2.candidateSet);
                            result.addAll(candidates3.candidateSet);
                            if (result.size() == 3) {
                                NakedTriple nakedTriple = new NakedTriple(candidates, candidates2,
                                        candidates3, result);
                                if (checkIfNakedTripleCanRemoveCandidatesFromBlock(nakedTriple)) {
                                    return nakedTriple;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

        public void setSolver (Solver solver){
            this.solver = solver;
        }
    }
