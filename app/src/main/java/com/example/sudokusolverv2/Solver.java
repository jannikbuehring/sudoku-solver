package com.example.sudokusolverv2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Solver {

    int[][] board;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

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

    // Check if a section is valid
    private boolean isValid(int i, int j) {
        // Loop the column
        for(int c = i; c < i+3; c++) {
            // Loop the row
            for(int r = j; r < j+3; r++) {
                // False if already in the set
                if(blockSet.contains(this.board[r][c]))
                    return false;
                // If still in a section, add it
                if(board[c][r] != 0)
                    blockSet.add(this.board[r][c]);
            }
        }

        // Is valid!
        return true;
    }

    public boolean checkRuleBreaks(SudokuBoard display) {

        // Row and Column variables to check against
        Set rowSet = new HashSet<>();
        Set colSet = new HashSet<>();

        // Loop through the column
        for(int c = 0; c<9; c++){
            // Loop through the row
            for(int r = 0; r<9; r++){
                // False if exists in column
                if(colSet.contains(this.board[r][c]))
                    return false;
                // False if exists in row
                if(rowSet.contains(this.board[r][c]))
                    return false;

                // Add to column and row
                if(this.board[r][c] != 0) colSet.add(this.board[r][c]);
                if(this.board[r][c] != 0) rowSet.add(this.board[r][c]);
            }
            // Clear this run at the end
            rowSet.clear();
            colSet.clear();
        }
        System.out.println("row and cols clear");

        // While we loop the board
        int i = 0, j = 0;
        while(i < 9 && j < 9) {
            while(i < 9) {
                // False if not valid
                if(!isValid(i, j))
                    return false;
                // Increment one section
                i += 3;
                // Clear the board
                blockSet.clear();
            }
            // Next column/row
            i = 0;
            j += 3;
        }

        // Is valid!
        return true;
    }

    /*
        // row checker
        for(int row = 0; row < 9; row++) {
            for (int col = 0; col < 8; col++) {
                for (int col2 = col + 1; col2 < 9; col2++) {
                    if (this.board[row][col] == this.board[row][col2] && this.board[row][col] != 0) {
                        return false;
                    }
                }
            }
        }

        // column checker
        for(int col = 0; col < 9; col++) {
            for (int row = 0; row < 8; row++) {
                for (int row2 = row + 1; row2 < 9; row2++) {
                    if (this.board[row][col] == this.board[row2][col] && this.board[row][col] != 0) {
                        return false;
                    }
                }
            }
        }

        // grid checker
        for(int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                // row, col is start of the 3 by 3 grid
                for (int pos = 0; pos < 8; pos++) {
                    for (int pos2 = pos + 1; pos2 < 9; pos2++) {
                        if (this.board[row + pos % 3][col + pos / 3] ==
                                this.board[row + pos2 % 3][col + pos2 / 3]
                                && this.board[row][col] != 0) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;


    }*/

    public boolean validate(SudokuBoard display) {
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

        if (row == -1 || col == -1){
            return true;
        }

        for (int i=1; i<10; i++) {
            this.board[row][col] = i;

            if (check(row, col)) {
                if (validate(display)) {
                    return true;
                }
            }

            this.board[row][col] = 0;
        }

        return false;
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
