package com.example.sudokusolverv2;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Solver implements Serializable {

    int[][] board;
    boolean[][] fixed;
    int maxRecursion = 0;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    ArrayList<HashSet<Integer>> rowCandidates = new ArrayList<>();
    ArrayList<HashSet<Integer>> colCandidates = new ArrayList<>();
    ArrayList<HashSet<Integer>> subsquareCandidates = new ArrayList<>();

    ArrayList<HashSet<Integer>> personalCandidates = new ArrayList<>(81);

    public HashSet<int[][]> solutions = new HashSet<>();

    int selected_row;
    int selected_column;

    public Solver() {
        selected_row = -1;
        selected_column = -1;

        board = new int[9][9];
        fixed = new boolean[9][9];

        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = 0;
                fixed[r][c] = false;
            }
        }

        emptyBoxIndex = new ArrayList<>();

        for (int i = 0; i < 81; i++) {
            HashSet<Integer> emptySet = new HashSet<>();
            personalCandidates.add(emptySet);
        }
    }

    public Solver(int [][] sudokuBoard) {
        selected_row = -1;
        selected_column = -1;

        board = new int[9][9];
        fixed = new boolean[9][9];

        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = sudokuBoard[r][c];
                fixed[r][c] = false;
            }
        }
        board = sudokuBoard;
    }

    public void getEmptyBoxIndexes() {
        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                if(this.board[r][c] == 0) {
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(c);
                }
            }
        }
    }

    public ArrayList<HashSet<Integer>> updateRowCandidates() {
        this.rowCandidates.clear();
        for (int row = 0; row < 9; row++) {
            HashSet<Integer> rowSet = new HashSet<>();
            for(int i = 1; i < 10; i++) {
                rowSet.add(i);
            }
            for (int c = 0; c < 9; c++) {
                if(this.board[row][c] != 0) {
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
            for(int i = 1; i < 10; i++) {
                colSet.add(i);
            }
            for (int r = 0; r < 9; r++) {
                if(this.board[r][col] != 0) {
                    colSet.remove(this.board[r][col]);
                }
            }
            this.colCandidates.add(colSet);
        }
        return this.colCandidates;
    }

    public ArrayList<HashSet<Integer>> updateSubsquareCandidates() {
        this.subsquareCandidates.clear();
        for (int row = 0 ; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> subsquareSet = new HashSet<>();
                for(int i = 1; i < 10; i++) {
                    subsquareSet.add(i);
                }
                for(int r = row; r < row+3; r++) {
                    for(int c= col; c < col+3; c++) {
                        if (this.board[r][c] != 0){
                            subsquareSet.remove(this.board[r][c]);
                        }
                    }
                }
                this.subsquareCandidates.add(subsquareSet);
            }
        }
        return this.subsquareCandidates;
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
        int boxRow = row/3;
        int boxCol = col/3;
        return this.subsquareCandidates.get(boxCol + boxRow * 3);
    }

    public HashSet<Integer> getCandidates(int row, int col) {
        // return null (no candidates) if there is a number on the board
        if(this.board[row][col] != 0) {
            return null;
        }
        HashSet<Integer> rowSet = getRowCandidates(row);
        HashSet<Integer> colSet = getColCandidates(col);
        HashSet<Integer> subsquareSet = getSubsquareCandidates(row, col);

        // only return numbers that are contained in all three sets
        HashSet<Integer> candidateSet = new HashSet<>(rowSet);
        candidateSet.retainAll(colSet);
        candidateSet.retainAll(subsquareSet);
        return candidateSet;
    }

    // Set a number on the board
    public void setNumberPos(int number) {
        if(this.selected_row != -1 && this.selected_column != -1) {
            if(this.board[this.selected_row-1][this.selected_column-1] == number) {
                this.board[this.selected_row-1][this.selected_column-1] = 0;
            }
            else{
                this.board[this.selected_row-1][this.selected_column-1] = number;
            }
        }
    }

    // Set a candidate on the board
    public void setCandidatePos(int number) {
        if(this.selected_row != -1 && this.selected_column != -1) {
            if(this.board[this.selected_row-1][this.selected_column-1] == 0) {
                // add number to list of personal candidates
                if(personalCandidates.get(((this.selected_row - 1) * 9) + this.selected_column - 1).contains(number)) {
                    personalCandidates.get(((this.selected_row - 1) * 9) + this.selected_column - 1).remove(number);
                }
                else {
                    personalCandidates.get(((this.selected_row - 1) * 9) + this.selected_column - 1).add(number);
                }
            }
        }
    }

    // Update the list of user added candidates when a new number gets added
    // If a number of the personal candidate set is no longer in the set of all candidates, remove it
    public void updatePersonalCandidates() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                HashSet<Integer> allCandidates = getCandidates(r,c);
                HashSet<Integer> personalCandidateSet = (HashSet<Integer>) personalCandidates.get(r * 9 + c).clone();
                for (int candidate : personalCandidateSet) {
                    if(!allCandidates.contains(candidate)) {
                        personalCandidates.get(r * 9 + c).remove(candidate);
                    }
                }
            }
        }
    }

    // Zeile und Spalte angeben, überprüfen ob Eintrag dort valide ist
    private boolean check(int row, int column) {
        if(this.board[row][column] > 0) {
            for(int i = 0; i<9; i++) {
                if(this.board[i][column] == this.board[row][column] && row != i) {
                    return false;
                }
                if(this.board[row][i] == this.board[row][column] && column != i) {
                    return false;
                }
            }

            int boxRow = row/3;
            int boxColumn = column/3;

            for(int r=boxRow*3; r<boxRow*3 + 3; r++) {
                for(int c=boxColumn*3; c<boxColumn*3 + 3; c++) {
                    if(this.board[r][c] == this.board[row][column] && row != r && column != c) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // give in a number and a position and check if that number is valid in this position
    public boolean checkNumberInPosition(int row, int column, int number) {
        if(this.board[row][column] == 0) {
            for(int i = 0; i<9; i++) {

                //check row
                if(this.board[row][i] == number) {
                    return false;
                }

                //check column
                if(this.board[i][column] == number) {
                    return false;
                }
            }

            int boxRow = row/3;
            int boxColumn = column/3;

            for(int r=boxRow*3; r<boxRow*3 + 3; r++) {
                for(int c=boxColumn*3; c<boxColumn*3 + 3; c++) {
                    if(this.board[r][c] == number) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    // check if given board has less than 17 given cells (uniqueness violated)
    public boolean preCheckLessThan17Cells() {
        emptyBoxIndex.clear();
        getEmptyBoxIndexes();
        System.out.println(emptyBoxIndex.size());
        return emptyBoxIndex.size() <= 64;
    }

    public boolean preCheckMoreThan2NumbersNotOccurring() {

        HashSet<Integer> occurringNumbers = new HashSet<>();

        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(this.board[r][c] != 0) {
                    occurringNumbers.add(this.board[r][c]);
                }
            }
        }
        return occurringNumbers.size() >= 8;
    }

    // Function to check if a given row is valid
    public boolean validateRow(int row){
        int[] temp = this.board[row];
        Set<Integer>set = new HashSet<Integer>();
        for (int value : temp) {

            //Checking for repeated values.
            if (value != 0){
                if (!set.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to check if a given column is valid
    public boolean validateCol(int col){
        Set<Integer>set = new HashSet<Integer>();
        for (int i =0 ; i< 9; i++) {

            // Checking for repeated values.
            if (this.board[i][col] != 0){
                if (!set.add(this.board[i][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to check if all the subsquares are valid
    public boolean validateSubsquares(){
        for (int row = 0 ; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                Set<Integer>set = new HashSet<Integer>();
                for(int r = row; r < row+3; r++) {
                    for(int c= col; c < col+3; c++) {

                        // Checking for repeated values.
                        if (this.board[r][c] != 0){
                            if (!set.add(this.board[r][c])) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    //Function to check if the board valid (check for rule violations)
    public boolean validateBoard(){

        // Check the rows and columns
        for (int i =0 ; i< 9; i++) {
            if (!validateRow(i)) {
                return false;
            }
            if (!validateCol(i)) {
                return false;
            }
        }

        // Check the subsquares
        return validateSubsquares();
    }

    //Function to validate Sudoku (check if there is one/more than one solution)
    public boolean checkIfSudokuHasTooManySolutions(int[][] unsolvedBoard) {

        maxRecursion++;
        if(maxRecursion > 10000) {
            return false;
        }

        int row = -1;
        int col = -1;

        outerLoop:
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                if (this.board[r][c] == 0) {
                    row = r;
                    col = c;
                    break outerLoop;
                }
            }
        }

        // only passes if sudoku field is fully filled
        if (row == -1 || col == -1) {

            // only add if board is valid
            if(validateBoard()) {
                // and if it has not been added yet
                // save solution and try to find another one
                if(solutions.add(this.board)) {
                    // reset board state
                    int[][] newBoard;
                    newBoard = SerializationUtils.clone(unsolvedBoard);
                    this.board = newBoard;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }


        // assign the board position all possible values and check
        for (int i=1; i<10; i++) {
            this.board[row][col] = i;

            // check if i in position is valid
            if (check(row, col)) {

                if(checkIfSudokuHasTooManySolutions(unsolvedBoard) && solutions.size() > 1) {
                    return true;
                }
            }
            this.board[row][col] = 0;
        }
        return false;
    }

    public boolean checkIfSudokuHasOneSolution() {
        maxRecursion++;
        if (maxRecursion > 1000000) {
            return false;
        }

        int row = -1;
        int col = -1;

        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                if (this.board[r][c] == 0) {
                    row = r;
                    col = c;
                    break;
                }
            }
        }

        // only passes if sudoku field is fully filled
        if (row == -1 || col == -1) {
            return true;
        }


        // assign the board position all possible values and check
        for (int i=1; i<10; i++) {
            this.board[row][col] = i;

            // check if i in position is valid
            if (check(row, col)) {

                if(checkIfSudokuHasOneSolution()) {
                    return true;
                }
            }
            this.board[row][col] = 0;
        }
        // no solution
        return false;
    }

    public boolean NakedSingle() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = getCandidates(r,c);
                if(candidates != null && candidates.size() == 1) {
                    for(int i : candidates) {
                        this.board[r][c] = i;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[] NakedSingleTip() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                HashSet<Integer> candidates = getCandidates(r,c);
                if(candidates != null && candidates.size() == 1) {
                    int[] pos = new int[2];
                    pos[0] = r;
                    pos[1] = c;
                    return pos;
                }
            }
        }
        return null;
    }

    public void resetBoard() {
        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = 0;
            }
        }

        this.emptyBoxIndex = new ArrayList<>();
    }

    public String calculateTipLocation(int row, int column) {
        Random random = new Random();
        int x = random.nextInt(2);
        if(row < 3 && column < 3) {
            if(x == 0) {
                return "Top horizontal";
            }
            else {
                return "Left vertical";
            }
        }
        else if(row >= 3 && row < 6 && column < 3) {
            if(x == 0) {
                return "Middle horizontal";
            }
            else {
                return "Left vertical";
            }
        }
        else if(row >= 6 && column < 3) {
            if(x == 0) {
                return "Bottom horizontal";
            }
            else {
                return "Left vertical";
            }
        }
        else if(row < 3 && column < 6) {
            if(x == 0) {
                return "Top horizontal";
            }
            else {
                return "Middle vertical";
            }
        }
        else if(row >= 6 && column < 6) {
            if(x == 0) {
                return "Bottom horizontal";
            }
            else {
                return "Middle vertical";
            }
        }
        else if(row < 3) {
            if(x == 0) {
                return "Top horizontal";
            }
            else {
                return "Right vertical";
            }
        }
        else if(row < 6 && column >= 6) {
            if(x == 0) {
                return "Middle horizontal";
            }
            else {
                return "Right vertical";
            }
        }
        else if(row >= 6) {
            if(x == 0) {
                return "Bottom horizontal";
            }
            else {
                return "Right vertical";
            }
        }
        else {
            if(x == 0) {
                return "Middle horizontal";
            }
            else {
                return "Middle vertical";
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex() {
        return this.emptyBoxIndex;
    }

    public int getSelectedRow() {
        return selected_row;
    }

    public int getSelectedColumn() {
        return selected_column;
    }

    public ArrayList<HashSet<Integer>> getPersonalCandidates() {
        return this.personalCandidates;
    }

    public void setSelectedRow(int row) {
        selected_row = row;
    }

    public void setSelectedColumn(int column) {
        selected_column = column;
    }
}
