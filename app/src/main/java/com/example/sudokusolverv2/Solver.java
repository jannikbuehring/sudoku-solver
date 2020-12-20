package com.example.sudokusolverv2;

import java.util.ArrayList;

public class Solver {

    int[][] board;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    int selected_row;
    int selected_column;

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

    private void getEmptyBoxIndexes() {
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
