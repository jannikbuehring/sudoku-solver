package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenPairFinder {

    private Solver solver;

    private boolean checkIfHiddenPairCanRemoveCandidatesFromRow(HiddenPair hiddenPair) {
        for (int c = 0; c < 9; c++) {
            // skip the actual hidden pair
            if (c == hiddenPair.field1.column || c == hiddenPair.field2.column) {
                continue;
            }
            // if other candidates of the row contain a number of the hidden pair, return true
            for (Candidate candidate : hiddenPair.pairCandidates) {
                if (solver.calculatedCandidates.get(hiddenPair.field1.row * 9 + c).contains(candidate.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void applyHiddenPairToRow() {
        HiddenPair hiddenPair = getHiddenPairInRow();
        if (hiddenPair == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            // remove other candidates of the hidden pair
            if (c == hiddenPair.field1.column || c == hiddenPair.field2.column) {
                solver.calculatedCandidates.get(hiddenPair.field1.row * 9 + c)
                        .removeAll(hiddenPair.pairCandidates);
            }
        }
    }

    public HiddenPair getHiddenPairInRow() {
        for (int r = 0; r < 9; r++) {
            ArrayList<ArrayList<Candidate>> list = new ArrayList<>(9);
            for (int cap = 0; cap < 9; cap++) {
                list.add(new ArrayList<>());
            }
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidates = new
                            FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    if (candidates.candidateSet != null) {
                        for (int value : candidates.candidateSet) {
                            Candidate candidate = new Candidate(r, c, value);
                            list.get(value - 1).add(candidate);
                        }
                    }
                }
            }
            ArrayList<Candidate> potentialPair = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (list.get(i).size() == 2) {
                    potentialPair.addAll(list.get(i));
                }
            }
            if (potentialPair.size() >= 2) {
                for (Candidate candidate : potentialPair) {
                    for (Candidate candidate2 : potentialPair) {
                        if (candidate.compareTo(candidate2) == 1) {
                            HashSet<Integer> values = new HashSet<>();
                            values.add(candidate.value);
                            values.add(candidate2.value);
                            FieldCandidates pair = new FieldCandidates(candidate.row,
                                    candidate.column, values);
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
