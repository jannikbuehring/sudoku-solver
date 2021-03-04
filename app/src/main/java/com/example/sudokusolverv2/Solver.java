package com.example.sudokusolverv2;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Solver implements Serializable {

    int[][] board;
    int maxRecursion = 0;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    public HashSet<int[][]> solutions = new HashSet<>();

    int selected_row;
    int selected_column;

    // Store blocks
    Set blockSet = new HashSet();

    public Solver() {
        selected_row = -1;
        selected_column = -1;

        board = new int[9][9];

        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = 0;
            }
        }

        emptyBoxIndex = new ArrayList<>();
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
    public boolean checkNumberinPosition(int row, int column, int number) {
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
        if(emptyBoxIndex.size() > 64) {
            return false;
        }
        return true;
    }

    public boolean preCheckMoreThan2NumbersNotOccuring() {

        HashSet<Integer> occuringNumbers = new HashSet<>();

        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(this.board[r][c] != 0) {
                    occuringNumbers.add(this.board[r][c]);
                }
            }
        }
        if(occuringNumbers.size() < 8) {
            return false;
        }
        else {
            return true;
        }
    }

    // Function to check if a given row is valid
    public boolean validateRow(int row){
        int temp[] = this.board[row];
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

    public ArrayList<ArrayList<Integer>> updateCandidates() {
        ArrayList<ArrayList<Integer>> candidates = new ArrayList<>();
        //TODO: add proper data structure for candidates (something like list[][] of int[]s)

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.board[r][c] == 0) {
                    for (int i = 1; i < 10; i++) {
                        if (checkNumberinPosition(r, c, i)) {
                            // Add to list of possible candidates for row and col
                            //candidates[r][c].add(i);
                            //System.out.println(candidates[r][c]);
                        }
                    }
                }
            }
        }
        return candidates;
    }


    public void resetBoard() {
        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = 0;
            }
        }

        this.emptyBoxIndex = new ArrayList<>();
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

    public void setSelectedRow(int row) {
        selected_row = row;
    }

    public void setSelectedColumn(int column) {
        selected_column = column;
    }
}
