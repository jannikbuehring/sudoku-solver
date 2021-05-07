package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HiddenTripleFinder implements Serializable {

    private Solver solver;

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromRow(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromColumn(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenTriple.field1.row || r == hiddenTriple.field2.row
                    || r == hiddenTriple.field3.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenTriple.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromBlock(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        int boxRow = hiddenTriple.field1.row / 3;
        int boxCol = hiddenTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden triple
                if (r == hiddenTriple.field1.row && c == hiddenTriple.field1.column
                        || r == hiddenTriple.field2.row && c == hiddenTriple.field2.column
                        || r == hiddenTriple.field3.row && c == hiddenTriple.field3.column) {
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

    public void applyHiddenTripleToRow() {
        HiddenTriple hiddenTriple = getHiddenTripleInRow();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenTripleToColumn() {
        HiddenTriple hiddenTriple = getHiddenTripleInColumn();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenTriple.field1.row || r == hiddenTriple.field2.row
                    || r == hiddenTriple.field3.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(r * 9 + hiddenTriple.field1.column)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(r * 9 + hiddenTriple.field1.column)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenTripleToBlock() {
        HiddenTriple hiddenTriple = getHiddenTripleInBlock();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        int boxRow = hiddenTriple.field1.row / 3;
        int boxCol = hiddenTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden triple
                if (r == hiddenTriple.field1.row && c == hiddenTriple.field1.column
                        || r == hiddenTriple.field2.row && c == hiddenTriple.field2.column
                        || r == hiddenTriple.field3.row && c == hiddenTriple.field3.column) {
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

    // https://www.youtube.com/watch?v=mwoy-1B4qYw
    public HiddenTriple getHiddenTripleInRow() {

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

            // if a number occurs more than three times, it cannot be part of a hidden triple
            for (int i = 1; i < 10; i++) {
                ArrayList<Integer> occurrences = new ArrayList<>();
                for (FieldCandidates candidates : completeRow) {
                    if (candidates.candidateSet.contains(i)) {
                        occurrences.add(i);
                    }
                }
                if (occurrences.size() > 3) {
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

            // if only two cells are left for consideration, it is not enough for a valid triple
            if (completeRow.size() < 3) {
                continue;
            } else {
                // check for a valid triple
                for (FieldCandidates candidates : completeRow) {
                    for (FieldCandidates candidates2 : completeRow) {
                        for (FieldCandidates candidates3 : completeRow) {
                            // making sure we are not comparing the same fields
                            if (candidates == candidates2 || candidates == candidates3
                                    || candidates2 == candidates3) {
                                continue;
                            }
                            // if the size of the union set of all three sets is three, it should be
                            // a valid triple
                            HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                            result.addAll(candidates2.candidateSet);
                            result.addAll(candidates3.candidateSet);
                            if (result.size() == 3 && candidates.candidateSet.size() >= 2
                                    && candidates2.candidateSet.size() >= 2
                                    && candidates3.candidateSet.size() >= 2) {
                                HiddenTriple hiddenTriple = new HiddenTriple(candidates, candidates2,
                                        candidates3, result);
                                if (checkIfHiddenTripleCanRemoveCandidatesFromRow(hiddenTriple)) {
                                    return hiddenTriple;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public HiddenTriple getHiddenTripleInColumn() {
        for (int c = 0; c < 9; c++) {

            // get a list of all current candidates for this column
            ArrayList<FieldCandidates> completeColumn = new ArrayList<>();
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    HashSet<Integer> candidates = SerializationUtils.clone(solver.calculatedCandidates.get(r * 9 + c));
                    FieldCandidates candidateSet = new FieldCandidates(r, c, candidates);
                    completeColumn.add(candidateSet);
                }
            }

            // if a number occurs more than three times, it cannot be part of a hidden triple
            for (int i = 1; i < 10; i++) {
                ArrayList<Integer> occurrences = new ArrayList<>();
                for (FieldCandidates candidates : completeColumn) {
                    if (candidates.candidateSet.contains(i)) {
                        occurrences.add(i);
                    }
                }
                if (occurrences.size() > 3) {
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

            // if only two cells are left for consideration, it is not enough for a valid triple
            if (completeColumn.size() < 3) {
                continue;
            } else {
                // check for a valid triple
                for (FieldCandidates candidates : completeColumn) {
                    for (FieldCandidates candidates2 : completeColumn) {
                        for (FieldCandidates candidates3 : completeColumn) {
                            // making sure we are not comparing the same fields
                            if (candidates == candidates2 || candidates == candidates3
                                    || candidates2 == candidates3) {
                                continue;
                            }
                            // if the size of the union set of all three sets is three, it should be
                            // a valid triple
                            HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                            result.addAll(candidates2.candidateSet);
                            result.addAll(candidates3.candidateSet);
                            if (result.size() == 3 && candidates.candidateSet.size() >= 2
                                    && candidates2.candidateSet.size() >= 2
                                    && candidates3.candidateSet.size() >= 2) {
                                HiddenTriple hiddenTriple = new HiddenTriple(candidates, candidates2,
                                        candidates3, result);
                                if (checkIfHiddenTripleCanRemoveCandidatesFromColumn(hiddenTriple)) {
                                    return hiddenTriple;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public HiddenTriple getHiddenTripleInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                // get a list of all current candidates for this block
                ArrayList<FieldCandidates> completeBlock = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            HashSet<Integer> candidates = SerializationUtils.clone(solver.calculatedCandidates.get(r * 9 + c));
                            FieldCandidates candidateSet = new FieldCandidates(r, c, candidates);
                            completeBlock.add(candidateSet);
                        }
                    }
                }

                // if a number occurs more than three times, it cannot be part of a hidden triple
                for (int i = 1; i < 10; i++) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (FieldCandidates candidates : completeBlock) {
                        if (candidates.candidateSet.contains(i)) {
                            occurrences.add(i);
                        }
                    }
                    if (occurrences.size() > 3) {
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

                // if only two cells are left for consideration, it is not enough for a valid triple
                if (completeBlock.size() < 3) {
                    continue;
                } else {
                    // check for a valid triple
                    for (FieldCandidates candidates : completeBlock) {
                        for (FieldCandidates candidates2 : completeBlock) {
                            for (FieldCandidates candidates3 : completeBlock) {
                                // making sure we are not comparing the same fields
                                if (candidates == candidates2 || candidates == candidates3
                                        || candidates2 == candidates3) {
                                    continue;
                                }
                                // if the size of the union set of all three sets is three, it should be
                                // a valid triple
                                HashSet<Integer> result = new HashSet<>(candidates.candidateSet);
                                result.addAll(candidates2.candidateSet);
                                result.addAll(candidates3.candidateSet);
                                if (result.size() == 3 && candidates.candidateSet.size() >= 2
                                        && candidates2.candidateSet.size() >= 2
                                        && candidates3.candidateSet.size() >= 2) {
                                    HiddenTriple hiddenTriple = new HiddenTriple(candidates, candidates2,
                                            candidates3, result);
                                    if (checkIfHiddenTripleCanRemoveCandidatesFromBlock(hiddenTriple)) {
                                        return hiddenTriple;
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
