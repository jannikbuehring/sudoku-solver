package com.example.sudokusolverv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SolverActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    private boolean editCandidatesButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);

        Bundle b = getIntent().getExtras();
        int[][] board = (int[][])b.getSerializable("Board");
        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
        gameBoardSolver.board = board;
        gameBoard.invalidate();

        //TODO: Make given numbers unchangeable
        //TODO: Change color of newly added numbers
    }

    public void BTNOnePress(View view) {
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }

    public void BTNTwoPress(View view) {
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }

    public void BTNThreePress(View view) {
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }

    public void BTNFourPress(View view) {
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }

    public void BTNFivePress(View view) {
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }

    public void BTNSixPress(View view) {
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }

    public void BTNSevenPress(View view) {
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }

    public void BTNEightPress(View view) {
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }

    public void BTNNinePress(View view) {
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }

    public void backButtonPress(View view) {

    }

    public void forwardButtonPress(View view) {

    }

    public void editCandidatesButtonPress(View view) {
        if(!editCandidatesButtonPressed) {
            // change color
            editCandidatesButtonPressed = true;
        }
        else {
            // change color
            editCandidatesButtonPressed = false;
        }
    }

    public void tipButtonPress(View view) {
        if(gameBoardSolver.NakedSingleTip() != null) {
            int[] pos = gameBoardSolver.NakedSingleTip();
            //TODO: Function to give a tip based on position
        }
    }

    public void solutionButtonPress(View view) {
        if(gameBoardSolver.NakedSingle()) {
            return;
        }
    }
}