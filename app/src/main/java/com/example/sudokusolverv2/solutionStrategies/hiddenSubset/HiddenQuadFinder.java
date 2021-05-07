package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenQuadFinder {

    private Solver solver;

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromRow(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the quad triple
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden quad, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromColumn(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden quad
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden quad, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromBlock(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden quad
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    for (Integer candidate : otherCandidates) {
                        if (solver.calculatedCandidates.get(r * 9 + c).contains(candidate)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void applyHiddenQuadToRow() {
        HiddenQuad hiddenQuad = getHiddenQuadInRow();
        if (hiddenQuad == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden quad
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden quad, remove them
                solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenQuadToColumn() {
        HiddenQuad hiddenQuad = getHiddenQuadInColumn();
        if (hiddenQuad == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden quad
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden quad, remove them
                solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenQuadToBlock() {
        HiddenQuad hiddenQuad = getHiddenQuadInBlock();
        if (hiddenQuad == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden quad
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    solver.calculatedCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
                    solver.personalCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
                }
            }
        }
    }

    // TODO: this does not find all possible hidden quads yet
    public HiddenQuad getHiddenQuadInRow() {
        /*
        for (int r = 0; r < 9; r++) {

            // get a list of all current candidates for this row
            ArrayList<FieldCandidates> completeRow = new ArrayList<>();
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    // we need to make a deep copy of the candidates because we want to edit it
                    HashSet<Integer> candidates = SerializationUtils.clone(solver.calculatedCandidates.get(r * 9 + c));
                    FieldCandidates candidateSet = new FieldCandidates(r, c, candidates);
                    completeRow.add(candidateSet);
                }
            }

            // if a number occurs more than four times, it cannot be part of a hidden quad
            for (int i = 1; i < 10; i++) {
                ArrayList<Integer> occurrences = new ArrayList<>();
                for (FieldCandidates candidates : completeRow) {
                    if (candidates.candidateSet.contains(i)) {
                        occurrences.add(i);
                    }
                }
                if (occurrences.size() > 4) {
                    for (FieldCandidates candidates : completeRow) {
                        candidates.candidateSet.remove(i);
                    }
                }
            }

            // if there is only one candidate left, it cannot be part of a hidden quad
            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeRow) {
                if (candidates.candidateSet.size() <= 1) {
                    found.add(candidates);
                }
            }
            completeRow.removeAll(found);

            // if only three cells are left for consideration, it is not enough for a valid quad
            if (completeRow.size() < 4) {
                continue;
            } else {
                // if a number occurs once in the remaining set, it cannot be part of a valid quad
                for (int i = 1; i < 10; i++) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (FieldCandidates candidates : completeRow) {
                        if (candidates.candidateSet.contains(i)) {
                            occurrences.add(i);
                        }
                    }
                    if (occurrences.size() == 1) {
                        for (FieldCandidates candidates : completeRow) {
                            candidates.candidateSet.remove(i);
                        }
                    }
                }

                // check for a valid quad
                for (FieldCandidates candidates : completeRow) {
                    for (FieldCandidates candidates2 : completeRow) {
                        for (FieldCandidates candidates3 : completeRow) {
                            for (FieldCandidates candidates4 : completeRow) {
                                // making sure we are not comparing the same fields
                                if (candidates == candidates2 || candidates == candidates3
                                        || candidates2 == candidates3 || candidates == candidates4
                                        || candidates2 == candidates4 || candidates3 == candidates4) {
                                    continue;
                                }
                                // if the size of the union set of all four sets is four, it should be
                                // a valid quad
                                HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                                result.addAll(candidates2.candidateSet);
                                result.addAll(candidates3.candidateSet);
                                result.addAll(candidates4.candidateSet);
                                if (result.size() == 4 && candidates.candidateSet.size() >= 2
                                        && candidates2.candidateSet.size() >= 2
                                        && candidates3.candidateSet.size() >= 2) {
                                    HiddenQuad hiddenQuad = new HiddenQuad(candidates, candidates2,
                                            candidates3, candidates4, result);
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)) {
                                        return hiddenQuad;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return null;
    }

    public HiddenQuad getHiddenQuadInColumn() {
        /*
        for (int c = 0; c < 9; c++) {

            // get a list of all current candidates for this row
            ArrayList<FieldCandidates> completeColumn = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    // we need to make a deep copy of the candidates because we want to edit it
                    HashSet<Integer> candidates = SerializationUtils.clone(solver.calculatedCandidates.get(r * 9 + c));
                    FieldCandidates candidateSet = new FieldCandidates(r, c, candidates);
                    completeColumn.add(candidateSet);
                }
            }

            // if a number occurs more than four times, it cannot be part of a hidden quad
            for (int i = 1; i < 10; i++) {
                ArrayList<Integer> occurrences = new ArrayList<>();
                for (FieldCandidates candidates : completeColumn) {
                    if (candidates.candidateSet.contains(i)) {
                        occurrences.add(i);
                    }
                }
                if (occurrences.size() > 4) {
                    for (FieldCandidates candidates : completeColumn) {
                        candidates.candidateSet.remove(i);
                    }
                }
            }

            // if there is only one candidate left, it cannot be part of a hidden quad
            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeColumn) {
                if (candidates.candidateSet.size() <= 1) {
                    found.add(candidates);
                }
            }
            completeColumn.removeAll(found);

            // if only three cells are left for consideration, it is not enough for a valid quad
            if (completeColumn.size() < 4) {
                continue;
            } else {
                // if a number occurs once in the remaining set, it cannot be part of a valid quad
                for (int i = 1; i < 10; i++) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (FieldCandidates candidates : completeColumn) {
                        if (candidates.candidateSet.contains(i)) {
                            occurrences.add(i);
                        }
                    }
                    if (occurrences.size() == 1) {
                        for (FieldCandidates candidates : completeColumn) {
                            candidates.candidateSet.remove(i);
                        }
                    }
                }

                // check for a valid quad
                for (FieldCandidates candidates : completeColumn) {
                    for (FieldCandidates candidates2 : completeColumn) {
                        for (FieldCandidates candidates3 : completeColumn) {
                            for (FieldCandidates candidates4 : completeColumn) {
                                // making sure we are not comparing the same fields
                                if (candidates == candidates2 || candidates == candidates3
                                        || candidates2 == candidates3 || candidates == candidates4
                                        || candidates2 == candidates4 || candidates3 == candidates4) {
                                    continue;
                                }
                                // if the size of the union set of all four sets is four, it should be
                                // a valid quad
                                HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                                result.addAll(candidates2.candidateSet);
                                result.addAll(candidates3.candidateSet);
                                result.addAll(candidates4.candidateSet);
                                if (result.size() == 4 && candidates.candidateSet.size() >= 2
                                        && candidates2.candidateSet.size() >= 2
                                        && candidates3.candidateSet.size() >= 2) {
                                    HiddenQuad hiddenQuad = new HiddenQuad(candidates, candidates2,
                                            candidates3, candidates4, result);
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromColumn(hiddenQuad)) {
                                        return hiddenQuad;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return null;
    }

    public HiddenQuad getHiddenQuadInBlock() {
        /*
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                // get a list of all current candidates for this row
                ArrayList<FieldCandidates> completeBlock = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            // we need to make a deep copy of the candidates because we want to edit it
                            HashSet<Integer> candidates = SerializationUtils.clone(solver.calculatedCandidates.get(r * 9 + c));
                            FieldCandidates candidateSet = new FieldCandidates(r, c, candidates);
                            completeBlock.add(candidateSet);
                        }
                    }
                }

                // if a number occurs more than four times, it cannot be part of a hidden quad
                for (int i = 1; i < 10; i++) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (FieldCandidates candidates : completeBlock) {
                        if (candidates.candidateSet.contains(i)) {
                            occurrences.add(i);
                        }
                    }
                    if (occurrences.size() > 4) {
                        for (FieldCandidates candidates : completeBlock) {
                            candidates.candidateSet.remove(i);
                        }
                    }
                }

                // if there is only one candidate left, it cannot be part of a hidden quad
                ArrayList<FieldCandidates> found = new ArrayList<>();
                for (FieldCandidates candidates : completeBlock) {
                    if (candidates.candidateSet.size() <= 1) {
                        found.add(candidates);
                    }
                }
                completeBlock.removeAll(found);

                // if only three cells are left for consideration, it is not enough for a valid quad
                if (completeBlock.size() < 4) {
                    continue;
                } else {
                    // if a number occurs once in the remaining set, it cannot be part of a valid quad
                    for (int i = 1; i < 10; i++) {
                        ArrayList<Integer> occurrences = new ArrayList<>();
                        for (FieldCandidates candidates : completeBlock) {
                            if (candidates.candidateSet.contains(i)) {
                                occurrences.add(i);
                            }
                        }
                        if (occurrences.size() == 1) {
                            for (FieldCandidates candidates : completeBlock) {
                                candidates.candidateSet.remove(i);
                            }
                        }
                    }

                    // check for a valid quad
                    for (FieldCandidates candidates : completeBlock) {
                        for (FieldCandidates candidates2 : completeBlock) {
                            for (FieldCandidates candidates3 : completeBlock) {
                                for (FieldCandidates candidates4 : completeBlock) {
                                    // making sure we are not comparing the same fields
                                    if (candidates == candidates2 || candidates == candidates3
                                            || candidates2 == candidates3 || candidates == candidates4
                                            || candidates2 == candidates4 || candidates3 == candidates4) {
                                        continue;
                                    }
                                    // if the size of the union set of all four sets is four, it should be
                                    // a valid quad
                                    HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                                    result.addAll(candidates2.candidateSet);
                                    result.addAll(candidates3.candidateSet);
                                    result.addAll(candidates4.candidateSet);
                                    if (result.size() == 4 && candidates.candidateSet.size() >= 2
                                            && candidates2.candidateSet.size() >= 2
                                            && candidates3.candidateSet.size() >= 2) {
                                        HiddenQuad hiddenQuad = new HiddenQuad(candidates, candidates2,
                                                candidates3, candidates4, result);
                                        if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)) {
                                            return hiddenQuad;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
