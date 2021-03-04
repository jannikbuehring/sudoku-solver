package com.example.sudokusolverv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

public class MainActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();

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

        Solver gameBoardSolverUnsolved;
        gameBoardSolverUnsolved = (Solver) SerializationUtils.clone(gameBoardSolver);

        if(!gameBoardSolver.validateBoard()) {
            Context context = getApplicationContext();
            CharSequence text = "Rule violations found!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return;
        }
        else if(!gameBoardSolver.preCheckLessThan17Cells()) {
            Context context = getApplicationContext();
            CharSequence text = "Too many solutions!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return;
        }
        else if(!gameBoardSolver.preCheckMoreThan2NumbersNotOccuring()) {
            Context context = getApplicationContext();
            CharSequence text = "Too many solutions!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return;
        }

        //SolveBoardThread solveBoardThread = new SolveBoardThread();

        //new Thread(solveBoardThread).start();
        gameBoardSolver.maxRecursion = 0;

        if(gameBoardSolver.checkIfSudokuHasTooManySolutions(gameBoardSolverUnsolved.board)) {
            Context context = getApplicationContext();
            CharSequence text = "Too many solutions!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return;
        }

        gameBoardSolver.maxRecursion = 0;

        if (!gameBoardSolver.checkIfSudokuHasOneSolution()){
            Context context = getApplicationContext();
            CharSequence text = "No solution found!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
        else {
            // Starting the next activity and giving over the game state
            Intent intent = new Intent(MainActivity.this, SolverActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Board", gameBoardSolverUnsolved.board);
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