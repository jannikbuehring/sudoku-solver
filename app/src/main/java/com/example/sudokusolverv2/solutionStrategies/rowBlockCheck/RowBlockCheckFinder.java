package com.example.sudokusolverv2.solutionStrategies.rowBlockCheck;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;

import java.util.ArrayList;
import java.util.HashSet;

public class RowBlockCheckFinder {

    private Solver solver;

    private boolean checkIfRowBlockCheckCanRemoveCandidatesFromBlock(RowBlockCheck rowBlockCheck) {
        int boxRow = rowBlockCheck.candidate1.row / 3;
        int boxCol = rowBlockCheck.candidate1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual row block check fields
                if (r == rowBlockCheck.candidate1.row && c == rowBlockCheck.candidate1.column ||
                        r == rowBlockCheck.candidate2.row && c == rowBlockCheck.candidate2.column ||
                        r == rowBlockCheck.candidate3.row && c == rowBlockCheck.candidate3.column) {
                    continue;
                }
                if (solver.calculatedCandidates.get(r * 9 + c)
                        .contains(rowBlockCheck.candidate1.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void applyRowBlockCheckToBlock() {
        RowBlockCheck rowBlockCheck = getRowBlockCheckInRow();
        if (rowBlockCheck == null) {
            return;
        }
        int boxRow = rowBlockCheck.candidate1.row / 3;
        int boxCol = rowBlockCheck.candidate1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual row block check fields
                if (r == rowBlockCheck.candidate1.row && c == rowBlockCheck.candidate1.column ||
                        r == rowBlockCheck.candidate2.row && c == rowBlockCheck.candidate2.column ||
                        r == rowBlockCheck.candidate3.row && c == rowBlockCheck.candidate3.column) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + c).remove(rowBlockCheck.candidate1.value);
            }
        }
    }

    public void applyColumnBlockCheckToBlock() {
        RowBlockCheck rowBlockCheck = getRowBlockCheckInColumn();
        if (rowBlockCheck == null) {
            return;
        }
        int boxRow = rowBlockCheck.candidate1.row / 3;
        int boxCol = rowBlockCheck.candidate1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // skip the actual row block check fields
                if (r == rowBlockCheck.candidate1.row && c == rowBlockCheck.candidate1.column ||
                        r == rowBlockCheck.candidate2.row && c == rowBlockCheck.candidate2.column ||
                        r == rowBlockCheck.candidate3.row && c == rowBlockCheck.candidate3.column) {
                    continue;
                }
                solver.calculatedCandidates.get(r * 9 + c).remove(rowBlockCheck.candidate1.value);
            }
        }
    }

    public RowBlockCheck getRowBlockCheckInRow() {
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> rowSet = new HashSet<>();
            HashSet<Integer> rowSetRemoved = new HashSet<>();
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> blockSet = new HashSet<>();
                for (int c = col; c < col + 3; c++) {
                    if (solver.board[r][c] == 0) {
                        HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                        if (candidates != null) {
                            blockSet.addAll(candidates);
                        }
                    }
                }
                for (int i : blockSet) {
                    if (rowSet.contains(i) || rowSetRemoved.contains(i)) {
                        rowSet.remove(i);
                        rowSetRemoved.add(i);
                    } else {
                        rowSet.add(i);
                    }
                }
            }
            if (rowSet.size() > 0) {
                // check which block the number came from
                for (int result : rowSet) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (int c = 0; c < 9; c++) {
                        if (solver.calculatedCandidates.get(r * 9 + c).contains(result)) {
                            occurrences.add(c);
                        }
                    }
                    if (occurrences.size() == 1) {
                        Candidate candidate = new Candidate(r, occurrences.get(0), result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
                        }
                    } else if (occurrences.size() == 2) {
                        Candidate candidate1 = new Candidate(r, occurrences.get(0), result);
                        Candidate candidate2 = new Candidate(r, occurrences.get(1), result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate1, candidate2);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
                        }
                    } else if (occurrences.size() == 3) {
                        Candidate candidate1 = new Candidate(r, occurrences.get(0), result);
                        Candidate candidate2 = new Candidate(r, occurrences.get(1), result);
                        Candidate candidate3 = new Candidate(r, occurrences.get(2), result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate1, candidate2, candidate3);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
                        }
                    }
                }
            }

        }
        return null;
    }

    public RowBlockCheck getRowBlockCheckInColumn() {
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> colSet = new HashSet<>();
            HashSet<Integer> colSetRemoved = new HashSet<>();
            for (int row = 0; row < 9; row = row + 3) {
                HashSet<Integer> blockSet = new HashSet<>();
                for (int r = row; r < row + 3; r++) {
                    if (solver.board[r][c] == 0) {
                        HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                        if (candidates != null) {
                            blockSet.addAll(candidates);
                        }
                    }
                }
                for (int i : blockSet) {
                    if (colSet.contains(i) || colSetRemoved.contains(i)) {
                        colSet.remove(i);
                        colSetRemoved.add(i);
                    } else {
                        colSet.add(i);
                    }
                }
            }
            if (colSet.size() > 0) {
                // check which block the number came from
                for (int result : colSet) {
                    ArrayList<Integer> occurrences = new ArrayList<>();
                    for (int r = 0; r < 9; r++) {
                        if (solver.calculatedCandidates.get(r * 9 + c).contains(result)) {
                            occurrences.add(r);
                        }
                    }
                    if (occurrences.size() == 1) {
                        Candidate candidate = new Candidate(occurrences.get(0), c, result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
                        }
                    } else if (occurrences.size() == 2) {
                        Candidate candidate1 = new Candidate(occurrences.get(0), c, result);
                        Candidate candidate2 = new Candidate(occurrences.get(1), c, result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate1, candidate2);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
                        }
                    } else if (occurrences.size() == 3) {
                        Candidate candidate1 = new Candidate(occurrences.get(0), c, result);
                        Candidate candidate2 = new Candidate(occurrences.get(1), c, result);
                        Candidate candidate3 = new Candidate(occurrences.get(2), c, result);
                        RowBlockCheck rowBlockCheck = new RowBlockCheck(candidate1, candidate2, candidate3);
                        // check if it can remove something from block
                        if (checkIfRowBlockCheckCanRemoveCandidatesFromBlock(rowBlockCheck)) {
                            return rowBlockCheck;
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
