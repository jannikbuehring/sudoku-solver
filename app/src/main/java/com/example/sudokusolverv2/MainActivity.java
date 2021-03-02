package com.example.sudokusolverv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    private Button validateBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();

        validateBTN = findViewById(R.id.validateButton);
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

    public void reset(View view) {
        gameBoardSolver.resetBoard();
        gameBoard.invalidate();
    }

    public void validate(View view) {

        if(!gameBoardSolver.validateBoard()) {
            // TODO: Add error message
            System.out.println("Rule violations found");
            return;
        }

        //SolveBoardThread solveBoardThread = new SolveBoardThread();

        //new Thread(solveBoardThread).start();

        if(gameBoardSolver.validateSudoku(gameBoardSolver.board) == 2) {
            System.out.println("Too many solutions");
            //TODO: Check why this affects givenBoardState
            //Maybe add board parameter to function
            return;
        }
        else if (gameBoardSolver.validateSudoku(gameBoardSolver.board) == 0){
            System.out.println("No solution found!");
            return;
        }
        else {
            System.out.println("Sudoku validated");

            // Starting the next activity and giving over the game state
            Intent intent = new Intent(MainActivity.this, SolverActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Board", gameBoardSolver.board);
            intent.putExtras(b);
            MainActivity.this.startActivity(intent);
        }


    }
    /*
    class SolveBoardThread implements Runnable {
        @Override
        public void run() {
            gameBoardSolver.validateSudoku(gameBoard, 0);
        }
    }

     */
}