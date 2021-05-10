package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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

    private Integer checkForOneRemainingCandidate(ArrayList<FieldCandidates> completeUnit) {

        for (FieldCandidates candidates : completeUnit) {
            if (candidates.candidateSet.size() == 1) {
                Iterator<Integer> iterator = candidates.candidateSet.iterator();
                return iterator.next();
            }
        }

        return null;
    }

    private boolean checkHiddenQuadValidity(HiddenQuad hiddenQuad, ArrayList<FieldCandidates> completeUnit) {
        int unitOccurrences = 0;
        int hiddenQuadOccurrences = 0;
        for (int numberToCheck : hiddenQuad.candidates) {
            if (hiddenQuad.field1.candidateSet.contains(numberToCheck)) {
                hiddenQuadOccurrences++;
            }
            if (hiddenQuad.field2.candidateSet.contains(numberToCheck)) {
                hiddenQuadOccurrences++;
            }
            if (hiddenQuad.field3.candidateSet.contains(numberToCheck)) {
                hiddenQuadOccurrences++;
            }
            if (hiddenQuad.field4.candidateSet.contains(numberToCheck)) {
                hiddenQuadOccurrences++;
            }

            for (FieldCandidates candidates : completeUnit) {
                if (candidates.candidateSet.contains(numberToCheck)) {
                    unitOccurrences++;
                }
            }

            if (unitOccurrences != hiddenQuadOccurrences) {
                return false;
            }
        }

        return true;
    }

    private ArrayList<HiddenQuad> getPossibleHiddenQuads(FieldCandidates candidates,
                                                           FieldCandidates candidates2,
                                                           FieldCandidates candidates3,
                                                           FieldCandidates candidates4) {
        ArrayList<HiddenQuad> hiddenQuads = new ArrayList<>();
        Permutation permutation = new Permutation();
        permutation.getCombination(candidates.candidateSet, 2);
        permutation.getCombination(candidates.candidateSet, 3);
        permutation.getCombination(candidates.candidateSet, 4);
        Permutation permutation2 = new Permutation();
        permutation2.getCombination(candidates2.candidateSet, 2);
        permutation2.getCombination(candidates2.candidateSet, 3);
        permutation2.getCombination(candidates2.candidateSet, 4);
        Permutation permutation3 = new Permutation();
        permutation3.getCombination(candidates3.candidateSet, 2);
        permutation3.getCombination(candidates3.candidateSet, 3);
        permutation3.getCombination(candidates3.candidateSet, 4);
        Permutation permutation4 = new Permutation();
        permutation4.getCombination(candidates4.candidateSet, 2);
        permutation4.getCombination(candidates4.candidateSet, 3);
        permutation4.getCombination(candidates4.candidateSet, 4);
        for (HashSet<Integer> subSet : permutation.combinations) {
            for (HashSet<Integer> subSet2 : permutation2.combinations) {
                for (HashSet<Integer> subSet3 : permutation3.combinations) {
                    for (HashSet<Integer> subSet4 : permutation4.combinations) {
                        FieldCandidates candidatesCopy = new FieldCandidates(candidates.row,
                                candidates.column, subSet);
                        FieldCandidates candidatesCopy2 = new FieldCandidates(candidates2.row,
                                candidates2.column, subSet2);
                        FieldCandidates candidatesCopy3 = new FieldCandidates(candidates3.row,
                                candidates3.column, subSet3);
                        FieldCandidates candidatesCopy4 = new FieldCandidates(candidates4.row,
                                candidates4.column, subSet4);
                        HashSet<Integer> result = new HashSet<>(candidatesCopy.candidateSet);
                        result.addAll(candidatesCopy2.candidateSet);
                        result.addAll(candidatesCopy3.candidateSet);
                        result.addAll(candidatesCopy4.candidateSet);
                        if (result.size() == 4) {
                            HiddenQuad hiddenQuad = new HiddenQuad(candidatesCopy, candidatesCopy2,
                                    candidatesCopy3, candidatesCopy4, result);
                            hiddenQuads.add(hiddenQuad);
                        }
                    }
                }
            }
        }


        return hiddenQuads;
    }

    public HiddenQuad getHiddenQuadInRow() {
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

            while (checkForOneRemainingCandidate(completeRow) != null) {
                int remainingNumber = checkForOneRemainingCandidate(completeRow);
                for (FieldCandidates candidates : completeRow) {
                    candidates.candidateSet.remove(remainingNumber);
                }
            }

            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeRow) {
                if (candidates.candidateSet.size() == 0) {
                    found.add(candidates);
                }
            }
            completeRow.removeAll(found);

            // if only three cells are left for consideration, it is not enough for a valid quad
            if (completeRow.size() < 4) {
                continue;
            } else {
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

                                ArrayList<HiddenQuad> hiddenQuads = getPossibleHiddenQuads
                                        (candidates, candidates2, candidates3, candidates4);

                                for (HiddenQuad hiddenQuad : hiddenQuads) {
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)
                                            && checkHiddenQuadValidity(hiddenQuad, completeRow)) {
                                        return hiddenQuad;
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

    public HiddenQuad getHiddenQuadInColumn() {
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

            while (checkForOneRemainingCandidate(completeColumn) != null) {
                int remainingNumber = checkForOneRemainingCandidate(completeColumn);
                for (FieldCandidates candidates : completeColumn) {
                    candidates.candidateSet.remove(remainingNumber);
                }
            }

            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeColumn) {
                if (candidates.candidateSet.size() == 0) {
                    found.add(candidates);
                }
            }
            completeColumn.removeAll(found);

            // if only three cells are left for consideration, it is not enough for a valid quad
            if (completeColumn.size() < 4) {
                continue;
            } else {
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

                                ArrayList<HiddenQuad> hiddenQuads = getPossibleHiddenQuads
                                        (candidates, candidates2, candidates3, candidates4);

                                for (HiddenQuad hiddenQuad : hiddenQuads) {
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)
                                            && checkHiddenQuadValidity(hiddenQuad, completeColumn)) {
                                        return hiddenQuad;
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

    public HiddenQuad getHiddenQuadInBlock() {
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

                while (checkForOneRemainingCandidate(completeBlock) != null) {
                    int remainingNumber = checkForOneRemainingCandidate(completeBlock);
                    for (FieldCandidates candidates : completeBlock) {
                        candidates.candidateSet.remove(remainingNumber);
                    }
                }

                ArrayList<FieldCandidates> found = new ArrayList<>();
                for (FieldCandidates candidates : completeBlock) {
                    if (candidates.candidateSet.size() == 0) {
                        found.add(candidates);
                    }
                }
                completeBlock.removeAll(found);

                // if only three cells are left for consideration, it is not enough for a valid quad
                if (completeBlock.size() < 4) {
                    continue;
                } else {
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

                                    ArrayList<HiddenQuad> hiddenQuads = getPossibleHiddenQuads
                                            (candidates, candidates2, candidates3, candidates4);

                                    for (HiddenQuad hiddenQuad : hiddenQuads) {
                                        if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)
                                                && checkHiddenQuadValidity(hiddenQuad, completeBlock)) {
                                            return hiddenQuad;
                                        }
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
