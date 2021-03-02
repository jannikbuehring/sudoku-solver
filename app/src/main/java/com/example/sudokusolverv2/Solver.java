package com.example.sudokusolverv2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Solver {

    int[][] board;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    public HashSet<int[][]> solutions = new HashSet<>();

    int selected_row;
    int selected_column;

    // Store blocks
    Set blockSet = new HashSet();

    Solver() {
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

    public int[][] getBoard() {
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex() {
        return this.emptyBoxIndex;
    }

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
<<<<<<< HEAD
    public int validateSudoku() {
=======
    public int validateSudoku(int[][] gameBoard) {
>>>>>>> 87181bc9ce12491a9cadfb7002e429d78c22dd32
        int row = -1;
        int col = -1;

        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
<<<<<<< HEAD
                if (this.board[r][c] == 0) {
=======
                if (gameBoard[r][c] == 0) {
>>>>>>> 87181bc9ce12491a9cadfb7002e429d78c22dd32
                    row = r;
                    col = c;
                    break;
                }
            }
        }

        // only passes if sudoku field is fully filled
        if (row == -1 || col == -1) {
            //save solution and try to find another one
<<<<<<< HEAD
            solutions.add(this.board);
=======
            solutions.add(gameBoard);
>>>>>>> 87181bc9ce12491a9cadfb7002e429d78c22dd32
            System.out.println(solutions);
            System.out.println(solutions.size());
            return 1;
        }

        for (int i=1; i<10; i++) {
<<<<<<< HEAD
            this.board[row][col] = i;

            if (check(row, col)) {
                if (validateSudoku() == 1 && solutions.size() > 1) {
                    return 2;
                }
                else if (validateSudoku() == 1) {
=======
            gameBoard[row][col] = i;

            if (check(row, col)) {
                if (validateSudoku(gameBoard) == 1 && solutions.size() > 1) {
                    return 2;
                }
                else if (validateSudoku(gameBoard) == 1) {
>>>>>>> 87181bc9ce12491a9cadfb7002e429d78c22dd32
                    return 1;
                }
            }

<<<<<<< HEAD
            this.board[row][col] = 0;
=======
            gameBoard[row][col] = 0;
>>>>>>> 87181bc9ce12491a9cadfb7002e429d78c22dd32
        }
        return 0;
    }

    public void resetBoard() {
        for(int r = 0; r<9; r++) {
            for(int c = 0; c<9; c++) {
                board[r][c] = 0;
            }
        }

        this.emptyBoxIndex = new ArrayList<>();
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
