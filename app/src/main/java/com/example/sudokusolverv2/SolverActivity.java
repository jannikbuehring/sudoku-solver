package com.example.sudokusolverv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SolverActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    private boolean editCandidatesButtonSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);
        Bundle b = getIntent().getExtras();
        int[][] board = (int[][])b.getSerializable("Board");
        gameBoard = findViewById(R.id.SudokuSolvingBoard);
        gameBoardSolver = gameBoard.getSolver();
        gameBoardSolver.board = board;
        gameBoard.invalidate();
        gameBoard.fixNumbers();
    }

    public void BTNOnePress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(1);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(1);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNTwoPress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(2);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(2);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNThreePress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(3);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(3);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNFourPress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(4);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(4);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNFivePress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(5);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(5);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNSixPress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(6);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(6);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNSevenPress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(7);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(7);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNEightPress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(8);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(8);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void BTNNinePress(View view) {
        if(this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(9);
            gameBoard.invalidate();
        }
        else {
            gameBoardSolver.setNumberPos(9);
            gameBoardSolver.updatePersonalCandidates();
            gameBoard.invalidate();
        }
    }

    public void backButtonPress(View view) {

    }

    public void forwardButtonPress(View view) {

    }

    public void editCandidatesButtonPress(View view) {
        Button editCandidatesButton = findViewById(R.id.editCandidatesButton);
        if(editCandidatesButton.isSelected()) {
            editCandidatesButton.setSelected(false);

            this.editCandidatesButtonSelected = false;
        }
        else {
            editCandidatesButton.setSelected(true);

            this.editCandidatesButtonSelected = true;
        }

    }

    public void tipButtonPress(View view) {
        if(gameBoardSolver.NakedSingleTip() != null) {
            int[] pos = gameBoardSolver.NakedSingleTip();
            //TODO: Function to give a tip based on position
        }
    }

    public void showAllCandidatesButtonPress(View view) {
        Button showAllCandidatesButton = findViewById(R.id.showAllCandidatesButton);
        if (showAllCandidatesButton.isSelected()) {
            showAllCandidatesButton.setSelected(false);
            gameBoard.setShowAllCandidates(false);
        }
        else {
            showAllCandidatesButton.setSelected(true);
            gameBoard.setShowAllCandidates(true);
        }
    }

    public void solutionButtonPress(View view) {
        if(gameBoardSolver.NakedSingle()) {
            return;
        }
    }
}