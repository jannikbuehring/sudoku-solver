package com.example.sudokusolverv2.solutionStrategies.nakedSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;
import java.util.HashSet;

public class NakedQuadFinder {

    private Solver solver;

    private boolean checkIfNakedQuadCanRemoveCandidatesFromRow(NakedQuad nakedQuad) {
        for (int c = 0; c < 9; c++) {
            // skip the actual naked quad
            if (c == nakedQuad.field1.column || c == nakedQuad.field2.column
                    || c == nakedQuad.field3.column || c == nakedQuad.field4.column) {
                continue;
            }
            // if other candidates of the row contain a number of the naked quad, return true
            for (int i : nakedQuad.candidateUnion) {
                if (solver.calculatedCandidates.get(nakedQuad.field1.row * 9 + c).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfNakedQuadCanRemoveCandidatesFromColumn(NakedQuad nakedQuad) {
        for (int r = 0; r < 9; r++) {
            // skip the actual naked quad
            if (r == nakedQuad.field1.row || r == nakedQuad.field2.row
                    || r == nakedQuad.field3.row || r == nakedQuad.field4.row) {
                continue;
            }
            // if other candidates of the row contain a number of the naked quad, return true
            for (int i : nakedQuad.candidateUnion) {
                if (solver.calculatedCandidates.get(r * 9 + nakedQuad.field1.column).contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfNakedQuadCanRemoveCandidatesFromBlock(NakedQuad nakedQuad) {
        int boxRow = nakedQuad.field1.row / 3;
        int boxCol = nakedQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked quad
                if (r == nakedQuad.field1.row && c == nakedQuad.field1.column
                        || r == nakedQuad.field2.row && c == nakedQuad.field2.column
                        || r == nakedQuad.field3.row && c == nakedQuad.field3.column
                        || r == nakedQuad.field4.row && c == nakedQuad.field4.column) {
                    continue;
                }
                // if other candidates of the row contain a number of the naked triple, return true
                for (int i : nakedQuad.candidateUnion) {
                    if (solver.calculatedCandidates.get(r * 9 + c).contains(i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void applyNakedQuadToRow() {
        NakedQuad nakedQuad = getNakedQuadInRow();
        if (nakedQuad == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            //skip the actual naked triple
            if (c == nakedQuad.field1.column || c == nakedQuad.field2.column
                    || c == nakedQuad.field3.column || c == nakedQuad.field4.column) {
                continue;
            }
            solver.calculatedCandidates.get(nakedQuad.field1.row * 9 + c)
                    .removeAll(nakedQuad.candidateUnion);
            solver.personalCandidates.get(nakedQuad.field1.row * 9 + c)
                    .removeAll(nakedQuad.candidateUnion);
        }
    }

    public void applyNakedQuadToColumn() {
        NakedQuad nakedQuad = getNakedQuadInColumn();
        if (nakedQuad == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            //skip the actual naked triple
            if (r == nakedQuad.field1.row || r == nakedQuad.field2.row
                    || r == nakedQuad.field3.row || r == nakedQuad.field4.row) {
                continue;
            }
            solver.calculatedCandidates.get(r * 9 + nakedQuad.field1.column)
                    .removeAll(nakedQuad.candidateUnion);
            solver.personalCandidates.get(r * 9 + nakedQuad.field1.column)
                    .removeAll(nakedQuad.candidateUnion);
        }
    }

    public void applyNakedQuadToBlock() {
        NakedQuad nakedQuad = getNakedQuadInBlock();
        if (nakedQuad == null) {
            return;
        }
        int boxRow = nakedQuad.field1.row / 3;
        int boxCol = nakedQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual naked quad
                if (r == nakedQuad.field1.row && c == nakedQuad.field1.column
                        || r == nakedQuad.field2.row && c == nakedQuad.field2.column
                        || r == nakedQuad.field3.row && c == nakedQuad.field3.column
                        || r == nakedQuad.field4.row && c == nakedQuad.field4.column) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + c).removeAll(nakedQuad.candidateUnion);
                solver.personalCandidates.get(r * 9 + c).removeAll(nakedQuad.candidateUnion);
            }
        }
    }

    public NakedQuad getNakedQuadInRow() {
        for (int r = 0; r < 9; r++) {
            ArrayList<FieldCandidates> completeRow = new ArrayList<>();
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates
                            (r, c, solver.calculatedCandidates.get(r * 9 + c));
                    completeRow.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeRow) {
                for (FieldCandidates candidates2 : completeRow) {
                    for (FieldCandidates candidates3 : completeRow) {
                        for (FieldCandidates candidates4 : completeRow) {
                            // making sure we are not comparing the same fields
                            if (candidates == candidates2 || candidates == candidates3
                                    || candidates == candidates4 || candidates2 == candidates4
                                    || candidates2 == candidates3 || candidates3 == candidates4) {
                                continue;
                            }
                            // if the size of the union set of all four sets is four, it should be
                            // a naked quad
                            HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                            result.addAll(candidates2.candidateSet);
                            result.addAll(candidates3.candidateSet);
                            result.addAll(candidates4.candidateSet);
                            if (result.size() == 4) {
                                NakedQuad nakedQuad = new NakedQuad(candidates, candidates2,
                                        candidates3, candidates4, result);
                                if (checkIfNakedQuadCanRemoveCandidatesFromRow(nakedQuad)) {
                                    return nakedQuad;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedQuad getNakedQuadInColumn() {
        for (int c = 0; c < 9; c++) {
            ArrayList<FieldCandidates> completeColumn = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidateSet = new FieldCandidates
                            (r, c, solver.calculatedCandidates.get(r * 9 + c));
                    completeColumn.add(candidateSet);
                }
            }
            for (FieldCandidates candidates : completeColumn) {
                for (FieldCandidates candidates2 : completeColumn) {
                    for (FieldCandidates candidates3 : completeColumn) {
                        for (FieldCandidates candidates4 : completeColumn) {
                            // making sure we are not comparing the same fields
                            if (candidates == candidates2 || candidates == candidates3
                                    || candidates == candidates4 || candidates2 == candidates4
                                    || candidates2 == candidates3 || candidates3 == candidates4) {
                                continue;
                            }
                            // if the size of the union set of all four sets is four, it should be
                            // a naked quad
                            HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                            result.addAll(candidates2.candidateSet);
                            result.addAll(candidates3.candidateSet);
                            result.addAll(candidates4.candidateSet);
                            if (result.size() == 4) {
                                NakedQuad nakedQuad = new NakedQuad(candidates, candidates2,
                                        candidates3, candidates4, result);
                                if (checkIfNakedQuadCanRemoveCandidatesFromColumn(nakedQuad)) {
                                    return nakedQuad;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public NakedQuad getNakedQuadInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                ArrayList<FieldCandidates> completeBlock = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            FieldCandidates candidateSet = new FieldCandidates
                                    (r, c, solver.calculatedCandidates.get(r * 9 + c));
                            completeBlock.add(candidateSet);
                        }
                    }
                }
                for (FieldCandidates candidates : completeBlock) {
                    for (FieldCandidates candidates2 : completeBlock) {
                        for (FieldCandidates candidates3 : completeBlock) {
                            for (FieldCandidates candidates4 : completeBlock) {
                                // making sure we are not comparing the same fields
                                if (candidates == candidates2 || candidates == candidates3
                                        || candidates == candidates4 || candidates2 == candidates4
                                        || candidates2 == candidates3 || candidates3 == candidates4) {
                                    continue;
                                }
                                // if the size of the union set of all four sets is four, it should be
                                // a naked quad
                                HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                                result.addAll(candidates2.candidateSet);
                                result.addAll(candidates3.candidateSet);
                                result.addAll(candidates4.candidateSet);
                                if (result.size() == 4) {
                                    NakedQuad nakedQuad = new NakedQuad(candidates, candidates2,
                                            candidates3, candidates4, result);
                                    if (checkIfNakedQuadCanRemoveCandidatesFromBlock(nakedQuad)) {
                                        return nakedQuad;
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
