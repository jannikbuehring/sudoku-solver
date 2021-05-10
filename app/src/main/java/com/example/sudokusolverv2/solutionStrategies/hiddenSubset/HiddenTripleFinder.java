package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.ArrayUtils;
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

    private boolean checkHiddenTripleValidity(HiddenTriple hiddenTriple, ArrayList<FieldCandidates> completeUnit) {
        int unitOccurrences = 0;
        int hiddenTripleOccurrences = 0;
        for (int numberToCheck : hiddenTriple.candidates) {
            if (hiddenTriple.field1.candidateSet.contains(numberToCheck)) {
                hiddenTripleOccurrences++;
            }
            if (hiddenTriple.field2.candidateSet.contains(numberToCheck)) {
                hiddenTripleOccurrences++;
            }
            if (hiddenTriple.field3.candidateSet.contains(numberToCheck)) {
                hiddenTripleOccurrences++;
            }

            for (FieldCandidates candidates : completeUnit) {
                if (candidates.candidateSet.contains(numberToCheck)) {
                    unitOccurrences++;
                }
            }

            if (unitOccurrences != hiddenTripleOccurrences) {
                return false;
            }
        }

        return true;
    }

    private ArrayList<HiddenTriple> getPossibleHiddenTriples(FieldCandidates candidates,
                                                             FieldCandidates candidates2,
                                                             FieldCandidates candidates3) {
        ArrayList<HiddenTriple> hiddenTriples = new ArrayList<>();
        Permutation permutation = new Permutation();
        permutation.getCombination(candidates.candidateSet, 2);
        permutation.getCombination(candidates.candidateSet, 3);
        Permutation permutation2 = new Permutation();
        permutation2.getCombination(candidates2.candidateSet, 2);
        permutation2.getCombination(candidates2.candidateSet, 3);
        Permutation permutation3 = new Permutation();
        permutation3.getCombination(candidates3.candidateSet, 2);
        permutation3.getCombination(candidates3.candidateSet, 3);
        for (HashSet<Integer> subSet : permutation.combinations) {
            for (HashSet<Integer> subSet2 : permutation2.combinations) {
                for (HashSet<Integer> subSet3 : permutation3.combinations) {
                    FieldCandidates candidatesCopy = new FieldCandidates(candidates.row,
                            candidates.column, subSet);
                    FieldCandidates candidatesCopy2 = new FieldCandidates(candidates2.row,
                            candidates2.column, subSet2);
                    FieldCandidates candidatesCopy3 = new FieldCandidates(candidates3.row,
                            candidates3.column, subSet3);
                    HashSet<Integer> result = new HashSet<>(candidatesCopy.candidateSet);
                    result.addAll(candidatesCopy2.candidateSet);
                    result.addAll(candidatesCopy3.candidateSet);
                    if (result.size() == 3) {
                        HiddenTriple hiddenTriple = new HiddenTriple(candidatesCopy, candidatesCopy2,
                                candidatesCopy3, result);
                        hiddenTriples.add(hiddenTriple);
                    }
                }
            }
        }


        return hiddenTriples;
    }

    // https://www.youtube.com/watch?v=mwoy-1B4qYw
    // TODO: Fix bug in hidden triple selection (check for valid triple part)
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

            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeRow) {
                if (candidates.candidateSet.size() == 0) {
                    found.add(candidates);
                }
            }
            completeRow.removeAll(found);

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

                            ArrayList<HiddenTriple> hiddenTriples = getPossibleHiddenTriples
                                    (candidates, candidates2, candidates3);

                            for (HiddenTriple hiddenTriple : hiddenTriples) {
                                if (checkIfHiddenTripleCanRemoveCandidatesFromBlock(hiddenTriple)
                                        && checkHiddenTripleValidity(hiddenTriple, completeRow)) {
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

            ArrayList<FieldCandidates> found = new ArrayList<>();
            for (FieldCandidates candidates : completeColumn) {
                if (candidates.candidateSet.size() == 0) {
                    found.add(candidates);
                }
            }
            completeColumn.removeAll(found);

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

                            ArrayList<HiddenTriple> hiddenTriples = getPossibleHiddenTriples
                                    (candidates, candidates2, candidates3);

                            for (HiddenTriple hiddenTriple : hiddenTriples) {
                                if (checkIfHiddenTripleCanRemoveCandidatesFromBlock(hiddenTriple)
                                        && checkHiddenTripleValidity(hiddenTriple, completeColumn)) {
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

                ArrayList<FieldCandidates> found = new ArrayList<>();
                for (FieldCandidates candidates : completeBlock) {
                    if (candidates.candidateSet.size() == 0) {
                        found.add(candidates);
                    }
                }
                completeBlock.removeAll(found);

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

                                ArrayList<HiddenTriple> hiddenTriples = getPossibleHiddenTriples
                                        (candidates, candidates2, candidates3);

                                for (HiddenTriple hiddenTriple : hiddenTriples) {
                                    if (checkIfHiddenTripleCanRemoveCandidatesFromBlock(hiddenTriple)
                                            && checkHiddenTripleValidity(hiddenTriple, completeBlock)) {
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

// Java program to print all combination of size
// r in an array of size n
class Permutation {

    ArrayList<HashSet<Integer>> combinations = new ArrayList<>();

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    private void combinationUtil(int arr[], int n, int r,
                                 int index, int data[], int i) {
        // Current combination is ready to be printed,
        // print it
        if (index == r) {
            HashSet<Integer> set = new HashSet<>();
            for (int j = 0; j < r; j++) {
                set.add(data[j]);
            }
            combinations.add(set);
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
            return;

        // current is included, put next at next
        // location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index + 1,
                data, i + 1);

        // current is excluded, replace it with
        // next (Note that i+1 is passed, but
        // index is not changed)
        combinationUtil(arr, n, r, index, data, i + 1);
    }

    // The main function that prints all combinations
    // of size r in arr[] of size n. This function
    // mainly uses combinationUtil()
    public void getCombination(HashSet<Integer> canidateSet, int r) {
        if (canidateSet.size() <= 2) {
            combinations.add(canidateSet);
            return;
        }
        // A temporary array to store all combination
        // one by one
        int data[] = new int[r];
        int arr[] = canidateSet.stream().mapToInt(Number::intValue).toArray();
        int n = canidateSet.size();
        // Print all combination using temprary
        // array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0);
    }
}
