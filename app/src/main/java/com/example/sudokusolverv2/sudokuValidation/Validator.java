package com.example.sudokusolverv2.sudokuValidation;

import com.example.sudokusolverv2.Solver;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Validator {

    private Solver solver;

    private int recursionSteps = 0;
    ArrayList<ArrayList<Object>> emptyBoxIndex;
    private final HashSet<int[][]> solutions = new HashSet<>();

    public Validator() {
        emptyBoxIndex = new ArrayList<>();
    }

    // Function to check if a given row is valid
    public boolean validateRow(int row) {
        int[] rowValues = solver.board[row];
        HashSet<Integer> rowSet = new HashSet<Integer>();
        for (int value : rowValues) {

            //Checking for repeated values.
            if (value != 0) {
                if (!rowSet.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to check if a given column is valid
    public boolean validateCol(int col) {
        Set<Integer> colSet = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {

            // Checking for repeated values.
            if (solver.board[i][col] != 0) {
                if (!colSet.add(solver.board[i][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to check if all the subsquares are valid
    public boolean validateSubsquares() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                Set<Integer> set = new HashSet<Integer>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {

                        // Checking for repeated values.
                        if (solver.board[r][c] != 0) {
                            if (!set.add(solver.board[r][c])) {
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
    public boolean validateBoard() {

        // Check the rows and columns
        for (int i = 0; i < 9; i++) {
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

    // check if given board has less than 17 given cells (uniqueness violated)
    public boolean preCheckLessThan17Cells() {
        emptyBoxIndex.clear();
        getEmptyBoxIndexes();
        System.out.println(emptyBoxIndex.size());
        return emptyBoxIndex.size() <= 64;
    }

    public boolean preCheckMoreThan2NumbersNotOccurring() {

        HashSet<Integer> occurringNumbers = new HashSet<>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] != 0) {
                    occurringNumbers.add(solver.board[r][c]);
                }
            }
        }
        return occurringNumbers.size() >= 8;
    }

    // Zeile und Spalte angeben, überprüfen ob Eintrag dort valide ist
    private boolean check(int row, int column) {
        if (solver.board[row][column] > 0) {
            for (int i = 0; i < 9; i++) {
                if (solver.board[i][column] == solver.board[row][column] && row != i) {
                    return false;
                }
                if (solver.board[row][i] == solver.board[row][column] && column != i) {
                    return false;
                }
            }

            int boxRow = row / 3;
            int boxColumn = column / 3;

            for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
                for (int c = boxColumn * 3; c < boxColumn * 3 + 3; c++) {
                    if (solver.board[r][c] == solver.board[row][column] && row != r && column != c) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* NOT IN USE ATM
    // give in a number and a position and check if that number is valid in this position
    public boolean checkNumberInPosition(int row, int column, int number) {
        if (solver.board[row][column] == 0) {
            for (int i = 0; i < 9; i++) {

                //check row
                if (solver.board[row][i] == number) {
                    return false;
                }

                //check column
                if (solver.board[i][column] == number) {
                    return false;
                }
            }

            int boxRow = row / 3;
            int boxColumn = column / 3;

            for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
                for (int c = boxColumn * 3; c < boxColumn * 3 + 3; c++) {
                    if (solver.board[r][c] == number) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

     */

    //Function to validate Sudoku (check if there is one/more than one solution)
    public boolean checkIfSudokuHasTooManySolutions(int[][] unsolvedBoard) {

        recursionSteps++;
        if (recursionSteps > 10000) {
            return false;
        }

        int row = -1;
        int col = -1;

        outerLoop:
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    row = r;
                    col = c;
                    break outerLoop;
                }
            }
        }

        // only passes if sudoku field is fully filled
        if (row == -1 || col == -1) {

            // only add if board is valid
            if (validateBoard()) {
                // and if it has not been added yet
                // save solution and try to find another one
                if (solutions.add(solver.board)) {
                    // reset board state
                    int[][] newBoard;
                    newBoard = SerializationUtils.clone(unsolvedBoard);
                    solver.board = newBoard;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }


        // assign the board position all possible values and check
        for (int i = 1; i < 10; i++) {
            solver.board[row][col] = i;

            // check if i in position is valid
            if (check(row, col)) {

                if (checkIfSudokuHasTooManySolutions(unsolvedBoard) && solutions.size() > 1) {
                    return true;
                }
            }
            solver.board[row][col] = 0;
        }
        return false;
    }

    public boolean checkIfSudokuHasOneSolution() {
        recursionSteps++;
        if (recursionSteps > 1000000) {
            return false;
        }

        int row = -1;
        int col = -1;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
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
        for (int i = 1; i < 10; i++) {
            solver.board[row][col] = i;

            // check if i in position is valid
            if (check(row, col)) {

                if (checkIfSudokuHasOneSolution()) {
                    return true;
                }
            }
            solver.board[row][col] = 0;
        }
        // no solution
        return false;
    }

    public void getEmptyBoxIndexes() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(c);
                }
            }
        }
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }

    public void setRecursionSteps(int steps) {
        recursionSteps = steps;
    }
}
