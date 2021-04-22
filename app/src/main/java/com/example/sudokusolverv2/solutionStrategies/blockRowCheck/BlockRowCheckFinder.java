package com.example.sudokusolverv2.solutionStrategies.blockRowCheck;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;


import java.util.ArrayList;
import java.util.HashSet;

public class BlockRowCheckFinder {

    private Solver solver;

    private boolean checkIfBlockRowCheckCanRemoveCandidatesFromRow(BlockRowCheck blockRowCheck) {
        for (int c = 0; c < 9; c++) {

            // skip the actual block row check fields
            if (c == blockRowCheck.candidate1.column || c == blockRowCheck.candidate2.column
                    || c == blockRowCheck.candidate3.column) {
                continue;
            }
            if (solver.calculatedCandidates.get(blockRowCheck.candidate1.row * 9 + c)
                    .contains(blockRowCheck.candidate1.value)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfBlockRowCheckCanRemoveCandidatesFromColumn(BlockRowCheck blockRowCheck) {

        for (int r = 0; r < 9; r++) {
            // skip the actual block row check fields
            if (r == blockRowCheck.candidate1.row || r == blockRowCheck.candidate2.row
                    || r == blockRowCheck.candidate3.row) {
                continue;
            }
            if (solver.calculatedCandidates.get(r * 9 + blockRowCheck.candidate1.column)
                    .contains(blockRowCheck.candidate1.value)) {
                return true;
            }
        }
        return false;
    }

    public void applyBlockRowCheckToRow() {
        BlockRowCheck blockRowCheck = getBlockRowCheckInRow();
        if (blockRowCheck == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {

            // skip the actual block row check fields
            if (c == blockRowCheck.candidate1.column || c == blockRowCheck.candidate2.column
                    || c == blockRowCheck.candidate3.column) {
                continue;
            }
            solver.calculatedCandidates.get(blockRowCheck.candidate1.row * 9 + c)
                    .remove(blockRowCheck.candidate1.value);
            solver.personalCandidates.get(blockRowCheck.candidate1.row * 9 + c)
                    .remove(blockRowCheck.candidate1.value);
        }
    }

    public void applyBlockRowCheckToColumn() {
        BlockRowCheck blockRowCheck = getBlockRowCheckInColumn();
        if (blockRowCheck == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {

            // skip the actual block row check fields
            if (r == blockRowCheck.candidate1.row || r == blockRowCheck.candidate2.row
                    || r == blockRowCheck.candidate3.row) {
                continue;
            }
            solver.calculatedCandidates.get(r * 9 + blockRowCheck.candidate1.column)
                    .remove(blockRowCheck.candidate1.value);
            solver.personalCandidates.get(r * 9 + blockRowCheck.candidate1.column)
                    .remove(blockRowCheck.candidate1.value);
        }
    }

    public BlockRowCheck getBlockRowCheckInRow() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> blockSet = new HashSet<>();
                HashSet<Integer> blockSetRemoved = new HashSet<>();
                for (int r = row; r < row + 3; r++) {
                    HashSet<Integer> rowSet = new HashSet<>();
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                            if (candidates != null) {
                                rowSet.addAll(candidates);
                            }
                        }
                    }
                    for (int i : rowSet) {
                        if (blockSet.contains(i) || blockSetRemoved.contains(i)) {
                            blockSet.remove(i);
                            blockSetRemoved.add(i);
                        } else {
                            blockSet.add(i);
                        }
                    }
                }
                if (blockSet.size() > 0) {
                    // check which block the number came from
                    for (int result : blockSet) {
                        ArrayList<Candidate> occurrences = new ArrayList<>();
                        for (int r = row; r < row + 3; r++) {
                            for (int c = col; c < col + 3; c++) {
                                if (solver.calculatedCandidates.get(r * 9 + c).contains(result)) {
                                    occurrences.add(new Candidate(r, c, result));
                                }
                            }
                        }
                        if (occurrences.size() == 1) {
                            Candidate candidate = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromRow(blockRowCheck)) {
                                return blockRowCheck;
                            }
                        } else if (occurrences.size() == 2) {
                            Candidate candidate1 = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            Candidate candidate2 = new Candidate(occurrences.get(1).row,
                                    occurrences.get(1).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate1, candidate2);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromRow(blockRowCheck)) {
                                return blockRowCheck;
                            }
                        } else if (occurrences.size() == 3) {
                            Candidate candidate1 = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            Candidate candidate2 = new Candidate(occurrences.get(1).row,
                                    occurrences.get(1).column, result);
                            Candidate candidate3 = new Candidate(occurrences.get(2).row,
                                    occurrences.get(2).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate1,
                                    candidate2, candidate3);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromRow(blockRowCheck)) {
                                return blockRowCheck;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public BlockRowCheck getBlockRowCheckInColumn() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> blockSet = new HashSet<>();
                HashSet<Integer> blockSetRemoved = new HashSet<>();
                for (int c = col; c < col + 3; c++) {
                    HashSet<Integer> columnSet = new HashSet<>();
                    for (int r = row; r < row + 3; r++) {
                        if (solver.board[r][c] == 0) {
                            HashSet<Integer> candidates = solver.calculatedCandidates.get(r * 9 + c);
                            if (candidates != null) {
                                columnSet.addAll(candidates);
                            }
                        }
                    }
                    for (int i : columnSet) {
                        if (blockSet.contains(i) || blockSetRemoved.contains(i)) {
                            blockSet.remove(i);
                            blockSetRemoved.add(i);
                        } else {
                            blockSet.add(i);
                        }
                    }
                }
                if (blockSet.size() > 0) {
                    // check which block the number came from
                    for (int result : blockSet) {
                        ArrayList<Candidate> occurrences = new ArrayList<>();
                        for (int c = col; c < col + 3; c++) {
                            for (int r = row; r < row + 3; r++) {
                                if (solver.calculatedCandidates.get(r * 9 + c).contains(result)) {
                                    occurrences.add(new Candidate(r, c, result));
                                }
                            }
                        }
                        if (occurrences.size() == 1) {
                            Candidate candidate = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromColumn(blockRowCheck)) {
                                return blockRowCheck;
                            }
                        } else if (occurrences.size() == 2) {
                            Candidate candidate1 = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            Candidate candidate2 = new Candidate(occurrences.get(1).row,
                                    occurrences.get(1).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate1, candidate2);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromColumn(blockRowCheck)) {
                                return blockRowCheck;
                            }
                        } else if (occurrences.size() == 3) {
                            Candidate candidate1 = new Candidate(occurrences.get(0).row,
                                    occurrences.get(0).column, result);
                            Candidate candidate2 = new Candidate(occurrences.get(1).row,
                                    occurrences.get(1).column, result);
                            Candidate candidate3 = new Candidate(occurrences.get(2).row,
                                    occurrences.get(2).column, result);
                            BlockRowCheck blockRowCheck = new BlockRowCheck(candidate1,
                                    candidate2, candidate3);
                            // check if it can remove something from block
                            if (checkIfBlockRowCheckCanRemoveCandidatesFromColumn(blockRowCheck)) {
                                return blockRowCheck;
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
