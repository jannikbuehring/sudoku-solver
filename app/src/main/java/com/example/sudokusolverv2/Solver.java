package com.example.sudokusolverv2;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Solver implements Serializable {

    public int[][] board;
    public boolean[][] fixed;

    ArrayList<HashSet<Integer>> rowCandidates = new ArrayList<>();
    ArrayList<HashSet<Integer>> colCandidates = new ArrayList<>();
    ArrayList<HashSet<Integer>> subsquareCandidates = new ArrayList<>();

    public ArrayList<HashSet<Integer>> calculatedCandidates = new ArrayList<>(81);
    public ArrayList<HashSet<Integer>> personalCandidates = new ArrayList<>(81);

    private int selectedRow;
    private int selectedColumn;

    public Solver() {
        selectedRow = -1;
        selectedColumn = -1;

        board = new int[9][9];
        fixed = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = 0;
                fixed[r][c] = false;
            }
        }

        for (int i = 0; i < 81; i++) {
            HashSet<Integer> emptyPersonalCandidateSet = new HashSet<>();
            HashSet<Integer> emptyCalculatedCandidateSet = new HashSet<>();
            personalCandidates.add(emptyPersonalCandidateSet);
            calculatedCandidates.add(emptyCalculatedCandidateSet);
        }
    }

    public Solver(int[][] sudokuBoard) {
        selectedRow = -1;
        selectedColumn = -1;

        board = new int[9][9];
        fixed = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = sudokuBoard[r][c];
                fixed[r][c] = false;
            }
        }
        board = sudokuBoard;
    }

    //------------------------------------BOARD MANIPULATION----------------------------------------

    // Reset to empty board
    public void resetBoard() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = 0;
            }
        }
    }

    // Once the board is validated, fix the given numbers so they cant be changed
    public void fixNumbers() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != 0) {
                    fixed[r][c] = true;
                }
            }
        }
    }

    // Set a number on the board
    public void setNumberPos(int number) {
        if (this.selectedRow != -1 && this.selectedColumn != -1) {
            if (this.board[this.selectedRow - 1][this.selectedColumn - 1] == number) {
                this.board[this.selectedRow - 1][this.selectedColumn - 1] = 0;
            } else {
                this.board[this.selectedRow - 1][this.selectedColumn - 1] = number;
            }
        }
    }

    // Set a candidate on the board
    public void setCandidatePos(int number) {
        if (this.selectedRow != -1 && this.selectedColumn != -1) {
            if (this.board[this.selectedRow - 1][this.selectedColumn - 1] == 0) {
                // add number to list of personal candidates
                if (personalCandidates.get(((this.selectedRow - 1) * 9) + this.selectedColumn - 1)
                        .contains(number)) {
                    personalCandidates.get(((this.selectedRow - 1) * 9) + this.selectedColumn - 1)
                            .remove(number);
                } else {
                    personalCandidates.get(((this.selectedRow - 1) * 9) + this.selectedColumn - 1)
                            .add(number);
                }
            }
        }
    }

    //----------------------------------CANDIDATE SYSTEM--------------------------------------------

    public void updateCandidates() {
        updateCalculatedCandidates();
        updatePersonalCandidates();

        // if a number got removed, recalculate candidates for complete candidate list
        if (this.board[this.selectedRow - 1][this.selectedColumn - 1] == 0) {
            calculateInitialCandidates();
        }
    }

    public HashSet<Integer> getRowCandidates(int row) {
        this.rowCandidates = updateRowCandidates();
        return this.rowCandidates.get(row);
    }

    public HashSet<Integer> getColCandidates(int col) {
        this.colCandidates = updateColCandidates();
        return this.colCandidates.get(col);
    }

    public HashSet<Integer> getSubsquareCandidates(int row, int col) {
        this.subsquareCandidates = updateSubsquareCandidates();
        int boxRow = row / 3;
        int boxCol = col / 3;
        return this.subsquareCandidates.get(boxCol + boxRow * 3);
    }

    public HashSet<Integer> getCandidates(int row, int col) {
        // return null (no candidates) if there is a number on the board
        if (this.board[row][col] != 0) {
            return null;
        }
        HashSet<Integer> rowCandidates = getRowCandidates(row);
        HashSet<Integer> colCandidates = getColCandidates(col);
        HashSet<Integer> subsquareCandidates = getSubsquareCandidates(row, col);

        // only return numbers that are contained in all three sets
        HashSet<Integer> candidateSet = new HashSet<>(rowCandidates);
        candidateSet.retainAll(colCandidates);
        candidateSet.retainAll(subsquareCandidates);
        return candidateSet;
    }

    public ArrayList<HashSet<Integer>> updateRowCandidates() {
        this.rowCandidates.clear();
        for (int row = 0; row < 9; row++) {
            HashSet<Integer> rowSet = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                rowSet.add(i);
            }
            for (int c = 0; c < 9; c++) {
                if (this.board[row][c] != 0) {
                    rowSet.remove(this.board[row][c]);
                }
            }
            this.rowCandidates.add(rowSet);
        }
        return this.rowCandidates;
    }

    public ArrayList<HashSet<Integer>> updateColCandidates() {
        this.colCandidates.clear();
        for (int col = 0; col < 9; col++) {
            HashSet<Integer> colSet = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                colSet.add(i);
            }
            for (int r = 0; r < 9; r++) {
                if (this.board[r][col] != 0) {
                    colSet.remove(this.board[r][col]);
                }
            }
            this.colCandidates.add(colSet);
        }
        return this.colCandidates;
    }

    public ArrayList<HashSet<Integer>> updateSubsquareCandidates() {
        this.subsquareCandidates.clear();
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> subsquareSet = new HashSet<>();
                for (int i = 1; i < 10; i++) {
                    subsquareSet.add(i);
                }
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (this.board[r][c] != 0) {
                            subsquareSet.remove(this.board[r][c]);
                        }
                    }
                }
                this.subsquareCandidates.add(subsquareSet);
            }
        }
        return this.subsquareCandidates;
    }

    public void calculateInitialCandidates() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = getCandidates(r, c);
                if (candidates != null) {
                    for (int candidate : candidates) {
                        calculatedCandidates.get(r * 9 + c).add(candidate);
                    }
                }
            }
        }
    }

    public void updateCalculatedCandidates() {
        int row = this.selectedRow - 1;
        int col = this.selectedColumn - 1;

        // clear candidates of selected field
        calculatedCandidates.get(row * 9 + col).clear();

        int number = this.board[row][col];

        // remove from row
        for (int c = 0; c < 9; c++) {
            calculatedCandidates.get(row * 9 + c).remove(number);
        }

        // remove from col
        for (int r = 0; r < 9; r++) {
            calculatedCandidates.get(r * 9 + col).remove(number);
        }

        // remove from block
        int boxRow = row / 3;
        int boxCol = col / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                calculatedCandidates.get(r * 9 + c).remove(number);
            }
        }
    }

    // Update the list of user added candidates when a new number gets added
    // If a number of the personal candidate set is no longer in the set of all candidates, remove it
    public void updatePersonalCandidates() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                HashSet<Integer> calculatedCandidateSet = (HashSet<Integer>) calculatedCandidates
                        .get(r * 9 + c).clone();
                HashSet<Integer> personalCandidateSet = (HashSet<Integer>) personalCandidates
                        .get(r * 9 + c).clone();
                for (int candidate : personalCandidateSet) {
                    if (calculatedCandidateSet == null) {
                        personalCandidates.get(r * 9 + c).remove(candidate);
                    } else if (!calculatedCandidateSet.contains(candidate)) {
                        personalCandidates.get(r * 9 + c).remove(candidate);
                    }
                }
            }
        }
    }

    public int countCandidateOccurrencesInRow(int row, int candidate) {
        int occurrences = 0;
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> candidates = calculatedCandidates.get(row * 9 + c);
            if(candidates.contains(candidate)) {
                occurrences++;
            }
        }
        return occurrences;
    }

    public ArrayList<FieldCandidates> findCandidateOccurrencesInRow(int row, int candidate) {
        ArrayList<FieldCandidates> occurrencesList = new ArrayList<>();
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> candidates = calculatedCandidates.get(row * 9 + c);
            if (candidates.contains(candidate)) {
                occurrencesList.add(new FieldCandidates(row, c, candidates));
            }
        }
        return occurrencesList;
    }

    public int countCandidateOccurrencesInColumn(int column, int candidate) {
        int occurrences = 0;
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> candidates = calculatedCandidates.get(r * 9 + column);
            if(candidates.contains(candidate)) {
                occurrences++;
            }
        }
        return occurrences;
    }

    public ArrayList<FieldCandidates> findCandidateOccurrencesInColumn(int column, int candidate) {
        ArrayList<FieldCandidates> occurrencesList = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> candidates = calculatedCandidates.get(r * 9 + column);
            if (candidates.contains(candidate)) {
                occurrencesList.add(new FieldCandidates(r, column, candidates));
            }
        }
        return occurrencesList;
    }

    //-------------------------------------TIP FUNCTIONALITY----------------------------------------

    /* NOT IN USE ATM
    public String calculateTipLocationBlocks(int row, int column) {
        Random random = new Random();
        int x = random.nextInt(2);
        if (row < 3 && column < 3) {
            if (x == 0) {
                return "Top horizontal";
            } else {
                return "Left vertical";
            }
        } else if (row >= 3 && row < 6 && column < 3) {
            if (x == 0) {
                return "Middle horizontal";
            } else {
                return "Left vertical";
            }
        } else if (row >= 6 && column < 3) {
            if (x == 0) {
                return "Bottom horizontal";
            } else {
                return "Left vertical";
            }
        } else if (row < 3 && column < 6) {
            if (x == 0) {
                return "Top horizontal";
            } else {
                return "Middle vertical";
            }
        } else if (row >= 6 && column < 6) {
            if (x == 0) {
                return "Bottom horizontal";
            } else {
                return "Middle vertical";
            }
        } else if (row < 3) {
            if (x == 0) {
                return "Top horizontal";
            } else {
                return "Right vertical";
            }
        } else if (row < 6 && column >= 6) {
            if (x == 0) {
                return "Middle horizontal";
            } else {
                return "Right vertical";
            }
        } else if (row >= 6) {
            if (x == 0) {
                return "Bottom horizontal";
            } else {
                return "Right vertical";
            }
        } else {
            if (x == 0) {
                return "Middle horizontal";
            } else {
                return "Middle vertical";
            }
        }
    }

     */

    public BlockLocation calculateTipLocationBlock(int row, int column) {
        if (row < 3 && column < 3) {
            return BlockLocation.TOP_LEFT;
        } else if (row >= 3 && row < 6 && column < 3) {
            return BlockLocation.LEFT;
        } else if (row >= 6 && column < 3) {
            return BlockLocation.BOTTOM_LEFT;
        } else if (row < 3 && column < 6) {
            return BlockLocation.TOP;
        } else if (row >= 6 && column < 6) {
            return BlockLocation.BOTTOM;
        } else if (row < 3) {
            return BlockLocation.TOP_RIGHT;
        } else if (row < 6 && column >= 6) {
            return BlockLocation.RIGHT;
        } else if (row >= 6) {
            return BlockLocation.BOTTOM_RIGHT;
        } else {
            return BlockLocation.CENTER;
        }
    }

    //--------------------------------GETTERS AND SETTERS-------------------------------------------

    public int[][] getBoard() {
        return this.board;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    public ArrayList<HashSet<Integer>> getPersonalCandidates() {
        return this.personalCandidates;
    }

    public void setSelectedRow(int row) {
        selectedRow = row;
    }

    public void setSelectedColumn(int column) {
        selectedColumn = column;
    }
}
