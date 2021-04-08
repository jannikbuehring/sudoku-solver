package com.example.sudokusolverv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sudokusolverv2.BlockLocation;
import com.example.sudokusolverv2.R;
import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.SudokuBoard;
import com.example.sudokusolverv2.solutionStrategies.HiddenSingle;
import com.example.sudokusolverv2.solutionStrategies.NakedPair;
import com.example.sudokusolverv2.solutionStrategies.NakedPairFinder;
import com.example.sudokusolverv2.solutionStrategies.NakedSingle;
import com.example.sudokusolverv2.solutionStrategies.NakedSingleFinder;

import java.util.Timer;
import java.util.TimerTask;

public class SolverActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;
    private final NakedSingleFinder nakedSingleFinder = new NakedSingleFinder();
    private final HiddenSingle hiddenSingle = new HiddenSingle();
    private final NakedPairFinder nakedPairFinder = new NakedPairFinder();

    private boolean editCandidatesButtonSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);
        Bundle b = getIntent().getExtras();
        int[][] board = (int[][]) b.getSerializable("Board");
        gameBoard = findViewById(R.id.SudokuSolvingBoard);
        gameBoardSolver = gameBoard.getSolver();
        gameBoardSolver.board = board;
        gameBoard.invalidate();
        gameBoardSolver.fixNumbers();
        gameBoardSolver.calculateInitialCandidates();
        nakedSingleFinder.setSolver(gameBoardSolver);
        hiddenSingle.setSolver(gameBoardSolver);
        nakedPairFinder.setSolver(gameBoardSolver);
    }

    public void BTNOnePress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(1);
        } else {
            gameBoardSolver.setNumberPos(1);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNTwoPress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(2);
        } else {
            gameBoardSolver.setNumberPos(2);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNThreePress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(3);
        } else {
            gameBoardSolver.setNumberPos(3);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNFourPress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(4);
        } else {
            gameBoardSolver.setNumberPos(4);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNFivePress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(5);
        } else {
            gameBoardSolver.setNumberPos(5);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNSixPress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(6);
        } else {
            gameBoardSolver.setNumberPos(6);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNSevenPress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(7);
        } else {
            gameBoardSolver.setNumberPos(7);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNEightPress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(8);
        } else {
            gameBoardSolver.setNumberPos(8);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void BTNNinePress(View view) {
        if (this.editCandidatesButtonSelected) {
            gameBoardSolver.setCandidatePos(9);
        } else {
            gameBoardSolver.setNumberPos(9);
            gameBoardSolver.updateCandidates();
        }
        gameBoard.invalidate();
    }

    public void editCandidatesButtonPress(View view) {
        Button editCandidatesButton = findViewById(R.id.editCandidatesButton);
        if (editCandidatesButton.isSelected()) {
            editCandidatesButton.setSelected(false);

            this.editCandidatesButtonSelected = false;
        } else {
            editCandidatesButton.setSelected(true);

            this.editCandidatesButtonSelected = true;
        }

    }

    public void tipButtonPress(View view) {
        if (nakedSingleFinder.getNakedSingle() != null) {
            NakedSingle nakedSingle = nakedSingleFinder.getNakedSingle();
            highlightBlock(nakedSingle.row, nakedSingle.col);
            showTooltip("Probiere es hier mit der Naked Single Strategie", 5000);
        } else if (hiddenSingle.getHiddenSingleRowLocation() != null) {
            int[] pos;
            pos = hiddenSingle.getHiddenSingleRowLocation();
            int row = pos[0];
            highlightRow(row);
            showTooltip("In dieser Reihe befindet sich ein Hidden Single",
                    5000);
        } else if (hiddenSingle.getHiddenSingleColLocation() != null) {
            int[] pos;
            pos = hiddenSingle.getHiddenSingleColLocation();
            int col = pos[1];
            highlightColumn(col);
            showTooltip("In dieser Spalte befindet sich ein Hidden Single", 5000);
        } else if (hiddenSingle.getHiddenSingleBlockLocation() != null) {
            int[] pos;
            pos = hiddenSingle.getHiddenSingleBlockLocation();
            int row = pos[0];
            int col = pos[1];
            highlightBlock(row, col);
            showTooltip("In diesem Block befindet sich ein Hidden Single",
                    5000);
        } else if (nakedPairFinder.getNakedPairInRow() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInRow();
            highlightRow(nakedPair.field1.row);
            showTooltip("In dieser Reihe befindet sich ein Naked Pair", 5000);
        } else if (nakedPairFinder.getNakedPairInColumn() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInColumn();
            highlightColumn(nakedPair.field1.column);
            showTooltip("In dieser Spalte befindet sich ein Naked Pair", 5000);
        } else if (nakedPairFinder.getNakedPairInBlock() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInBlock();
            highlightBlock(nakedPair.field1.row, nakedPair.field1.column);
            showTooltip("In diesem Block befindet sich ein Naked Pair", 5000);
        } else {
            gameBoard.setTipLocationBlock(null);
            gameBoard.invalidate();

            showTooltip("Kein Tipp verfügbar", 3000);
        }
    }

    public void showAllCandidatesButtonPress(View view) {
        Button showAllCandidatesButton = findViewById(R.id.showAllCandidatesButton);
        if (showAllCandidatesButton.isSelected()) {
            showAllCandidatesButton.setSelected(false);
            gameBoard.setShowAllCandidates(false);
        } else {
            showAllCandidatesButton.setSelected(true);
            gameBoard.setShowAllCandidates(true);
        }
    }

    public void solutionButtonPress(View view) {
        if (nakedSingleFinder.getNakedSingle() != null) {
            nakedSingleFinder.enterNakedSingle();
            showTooltip("Auf dieses Feld konnte die Naked Single Strategie angewendet werden", 5000);
        } else if (hiddenSingle.getHiddenSingleLocation() != null) {
            hiddenSingle.enterHiddenSingle();
            showTooltip("Auf dieses Feld konnte die Hidden Single Strategie angewendet werden", 5000);
        } else if (nakedPairFinder.getNakedPairInRow() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInRow();
            highlightRow(nakedPair.field1.row);
            nakedPairFinder.applyNakedPairToRow();
            showTooltip("Mit dem Naked Pair konnten in dieser Reihe Kandidaten entfernt werden", 5000);
        } else if (nakedPairFinder.getNakedPairInColumn() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInColumn();
            highlightColumn(nakedPair.field1.column);
            nakedPairFinder.applyNakedPairToColumn();
            showTooltip("Mit dem Naked Pair konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (nakedPairFinder.getNakedPairInBlock() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInBlock();
            highlightBlock(nakedPair.field1.row, nakedPair.field1.column);
            nakedPairFinder.applyNakedPairToBlock();
            showTooltip("Mit dem Naked Pair konnten in diesem Block Kandidaten entfernt werden", 5000);
        }

        else{
            showTooltip("Aktuell keine Lösungsstrategie verfügbar", 5000);
        }
    }

    private void showTooltip(String message, int duration) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    private void highlightRow(int row) {
        gameBoard.setTipLocationRow(row);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationRow(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }

    private void highlightColumn(int column) {
        gameBoard.setTipLocationCol(column);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationCol(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }

    private void highlightBlock(int row, int column) {
        BlockLocation tipLocationBlock = gameBoardSolver.calculateTipLocationBlock(row, column);
        gameBoard.setTipLocationBlock(tipLocationBlock);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationBlock(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }
}