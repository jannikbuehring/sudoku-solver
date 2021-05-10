package com.example.sudokusolverv2.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sudokusolverv2.BlockLocation;
import com.example.sudokusolverv2.R;
import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.SudokuBoard;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.ThirdEye;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.ThirdEyeFinder;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.XWing;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.XWingFinder;
import com.example.sudokusolverv2.solutionStrategies.blockRowCheck.BlockRowCheck;
import com.example.sudokusolverv2.solutionStrategies.blockRowCheck.BlockRowCheckFinder;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenPair;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenPairFinder;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuad;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuadFinder;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenTriple;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenTripleFinder;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedQuad;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedQuadFinder;
import com.example.sudokusolverv2.solutionStrategies.rowBlockCheck.RowBlockCheck;
import com.example.sudokusolverv2.solutionStrategies.rowBlockCheck.RowBlockCheckFinder;
import com.example.sudokusolverv2.solutionStrategies.singles.HiddenSingle;
import com.example.sudokusolverv2.solutionStrategies.singles.HiddenSingleFinder;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedPair;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedPairFinder;
import com.example.sudokusolverv2.solutionStrategies.singles.NakedSingle;
import com.example.sudokusolverv2.solutionStrategies.singles.NakedSingleFinder;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedTriple;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedTripleFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class SolverActivity extends AppCompatActivity {

    // TODO: implement solved board detection

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;
    Runnable savedToolTip;
    Runnable savedHighlight;
    private final NakedSingleFinder nakedSingleFinder = new NakedSingleFinder();
    private final HiddenSingleFinder hiddenSingleFinder = new HiddenSingleFinder();
    private final NakedPairFinder nakedPairFinder = new NakedPairFinder();
    private final NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
    private final NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
    private final HiddenPairFinder hiddenPairFinder = new HiddenPairFinder();
    private final HiddenTripleFinder hiddenTripleFinder = new HiddenTripleFinder();
    private final HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
    private final RowBlockCheckFinder rowBlockCheckFinder = new RowBlockCheckFinder();
    private final BlockRowCheckFinder blockRowCheckFinder = new BlockRowCheckFinder();
    private final XWingFinder xWingFinder = new XWingFinder();
    private final ThirdEyeFinder thirdEyeFinder = new ThirdEyeFinder();

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
        hiddenSingleFinder.setSolver(gameBoardSolver);
        nakedPairFinder.setSolver(gameBoardSolver);
        nakedTripleFinder.setSolver(gameBoardSolver);
        nakedQuadFinder.setSolver(gameBoardSolver);
        hiddenPairFinder.setSolver(gameBoardSolver);
        hiddenTripleFinder.setSolver(gameBoardSolver);
        hiddenQuadFinder.setSolver(gameBoardSolver);
        rowBlockCheckFinder.setSolver(gameBoardSolver);
        blockRowCheckFinder.setSolver(gameBoardSolver);
        xWingFinder.setSolver(gameBoardSolver);
        thirdEyeFinder.setSolver(gameBoardSolver);
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
        } else if (hiddenSingleFinder.getHiddenSingleInRow() != null) {
            HiddenSingle hiddenSingle = hiddenSingleFinder.getHiddenSingleInRow();
            highlightRow(new ArrayList<>(Arrays.asList(hiddenSingle.row)));
            showTooltip("In dieser Reihe befindet sich ein Hidden Single", 5000);
        } else if (hiddenSingleFinder.getHiddenSingleInCol() != null) {
            HiddenSingle hiddenSingle = hiddenSingleFinder.getHiddenSingleInCol();
            highlightColumn(new ArrayList<>(Arrays.asList(hiddenSingle.col)));
            showTooltip("In dieser Spalte befindet sich ein Hidden Single", 5000);
        } else if (hiddenSingleFinder.getHiddenSingleBlockLocation() != null) {
            HiddenSingle hiddenSingle = hiddenSingleFinder.getHiddenSingleBlockLocation();
            highlightBlock(hiddenSingle.row, hiddenSingle.col);
            showTooltip("In diesem Block befindet sich ein Hidden Single", 5000);
        } else if (nakedPairFinder.getNakedPairInRow() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInRow();
            highlightRow(new ArrayList<>(Arrays.asList(nakedPair.field1.row)));
            showTooltip("In dieser Reihe befindet sich ein Naked Pair", 5000);
        } else if (nakedPairFinder.getNakedPairInColumn() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(nakedPair.field1.column)));
            showTooltip("In dieser Spalte befindet sich ein Naked Pair", 5000);
        } else if (nakedPairFinder.getNakedPairInBlock() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInBlock();
            highlightBlock(nakedPair.field1.row, nakedPair.field1.column);
            showTooltip("In diesem Block befindet sich ein Naked Pair", 5000);
        } else if (nakedTripleFinder.getNakedTripleInRow() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInRow();
            highlightRow(new ArrayList<>(Arrays.asList(nakedTriple.field1.row)));
            showTooltip("In diese Reihe befindet sich ein Naked Triple", 5000);
        } else if (nakedTripleFinder.getNakedTripleInColumn() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(nakedTriple.field1.column)));
            showTooltip("In diese Spalte befindet sich ein Naked Triple", 5000);
        } else if (nakedTripleFinder.getNakedTripleInBlock() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInBlock();
            highlightBlock(nakedTriple.field1.row, nakedTriple.field1.column);
            showTooltip("In diesem Block befindet sich ein Naked Triple", 5000);
        } else if (nakedQuadFinder.getNakedQuadInRow() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInRow();
            highlightRow(new ArrayList<>(Arrays.asList(nakedQuad.field1.row)));
            showTooltip("In dieser Reihe befindet sich ein Naked Quad", 5000);
        } else if (nakedQuadFinder.getNakedQuadInColumn() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(nakedQuad.field1.column)));
            showTooltip("In dieser Spalte befindet sich ein Naked Quad", 5000);
        } else if (nakedQuadFinder.getNakedQuadInBlock() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInBlock();
            highlightBlock(nakedQuad.field1.row, nakedQuad.field1.column);
            showTooltip("In diesem Block befindet sich ein Naked Quad", 5000);
        } else if (hiddenPairFinder.getHiddenPairInRow() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInRow();
            highlightRow(new ArrayList<>(Arrays.asList(hiddenPair.field1.row)));
            showTooltip("In dieser Zeile befindet sich ein Hidden Pair", 5000);
        } else if (hiddenPairFinder.getHiddenPairInColumn() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(hiddenPair.field1.column)));
            showTooltip("In dieser Spalte befindet sich ein Hidden Pair", 5000);
        } else if (hiddenPairFinder.getHiddenPairInBlock() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInBlock();
            highlightBlock(hiddenPair.field1.row, hiddenPair.field1.column);
            showTooltip("In diesem Block befindet sich ein Hidden Pair", 5000);
        } /*else if (hiddenTripleFinder.getHiddenTripleInRow() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInRow();
            highlightRow(new ArrayList<>(Arrays.asList(hiddenTriple.field1.row)));
            showTooltip("In dieser Zeile befindet sich ein Hidden Triple", 5000);
        } else if (hiddenTripleFinder.getHiddenTripleInColumn() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(hiddenTriple.field1.column)));
            showTooltip("In dieser Spalte befindet sich ein Hidden Triple", 5000);
        } else if (hiddenTripleFinder.getHiddenTripleInBlock() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInBlock();
            highlightBlock(hiddenTriple.field1.row, hiddenTriple.field1.column);
            showTooltip("In diesem Block befindet sich ein Hidden Triple", 5000);
        } /*else if (hiddenQuadFinder.getHiddenQuadInRow() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInRow();
            highlightRow(new ArrayList<>(Arrays.asList(hiddenQuad.field1.row)));
            showTooltip("In dieser Zeile befindet sich ein Hidden Quad", 5000);
        } else if (hiddenQuadFinder.getHiddenQuadInColumn() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(hiddenQuad.field1.column)));
            showTooltip("In dieser Spalte befindet sich ein Hidden Quad", 5000);
        } else if (hiddenQuadFinder.getHiddenQuadInBlock() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInBlock();
            highlightBlock(hiddenQuad.field1.row, hiddenQuad.field1.column);
            showTooltip("In diesem Block befindet sich ein Hidden Quad", 5000);
        } */else if (rowBlockCheckFinder.getRowBlockCheckInRow() != null) {
            RowBlockCheck rowBlockCheck = rowBlockCheckFinder.getRowBlockCheckInRow();
            highlightRow(new ArrayList<>(Arrays.asList(rowBlockCheck.candidate1.row)));
            showTooltip("In dieser Zeile kann ein Reihe-Block-Check angewendet werden", 5000);
        } else if (rowBlockCheckFinder.getRowBlockCheckInColumn() != null) {
            RowBlockCheck rowBlockCheck = rowBlockCheckFinder.getRowBlockCheckInColumn();
            highlightColumn(new ArrayList<>(Arrays.asList(rowBlockCheck.candidate1.column)));
            showTooltip("In dieser Spalte kann ein Reihe-Block-Check angewendet werden", 5000);
        } else if (blockRowCheckFinder.getBlockRowCheckInRow() != null) {
            BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInRow();
            highlightBlock(blockRowCheck.candidate1.row, blockRowCheck.candidate1.column);
            showTooltip("In diesem Block kann ein Block-Reihe-Check auf eine Reihe angewendet werden", 5000);
        } else if (blockRowCheckFinder.getBlockRowCheckInColumn() != null) {
            BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInColumn();
            highlightBlock(blockRowCheck.candidate1.row, blockRowCheck.candidate1.column);
            showTooltip("In diesem Block kann ein Block-Reihe-Check auf eine Spalte angewendet werden", 5000);
        } else if (xWingFinder.getXWingInRow() != null) {
            XWing xWing = xWingFinder.getXWingInRow();
            highlightColumn(new ArrayList<>(Arrays.asList(xWing.pair1.field1.column, xWing.pair1.field2.column)));
            showTooltip("In diesen Spalten kann die X-Wing Strategie angewendet werden", 5000);
        } else if (xWingFinder.getXWingInColumn() != null) {
            XWing xWing = xWingFinder.getXWingInColumn();
            highlightRow(new ArrayList<>(Arrays.asList(xWing.pair1.field1.row, xWing.pair1.field2.row)));
            showTooltip("In diesen Zeilen kann die X-Wing Strategie angewendet werden", 5000);
        } else if (thirdEyeFinder.getThirdEye() != null) {
            ThirdEye thirdEye = thirdEyeFinder.getThirdEye();
            highlightBlock(thirdEye.thirdEye.row, thirdEye.thirdEye.column);
            showTooltip("In diesem Block kann die Drittes Auge Strategie angewendet werden", 5000);
        }

        else {
            gameBoard.setTipLocationBlocks(null);
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
            showSolutionTooltip("Auf dieses Feld konnte die Naked Single Strategie angewendet werden", 5000);
        } else if (hiddenSingleFinder.getHiddenSingle() != null) {
            hiddenSingleFinder.enterHiddenSingle();
            showSolutionTooltip("Auf dieses Feld konnte die Hidden Single Strategie angewendet werden", 5000);
        } else if (nakedPairFinder.getNakedPairInRow() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(nakedPair.field1.row)));
            nakedPairFinder.applyNakedPairToRow();
            showSolutionTooltip("Mit dem Naked Pair konnten in dieser Reihe Kandidaten entfernt werden", 5000);
        } else if (nakedPairFinder.getNakedPairInColumn() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(nakedPair.field1.column)));
            nakedPairFinder.applyNakedPairToColumn();
            showSolutionTooltip("Mit dem Naked Pair konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (nakedPairFinder.getNakedPairInBlock() != null) {
            NakedPair nakedPair = nakedPairFinder.getNakedPairInBlock();
            highlightBlockSolution(nakedPair.field1.row, nakedPair.field1.column);
            nakedPairFinder.applyNakedPairToBlock();
            showSolutionTooltip("Mit dem Naked Pair konnten in diesem Block Kandidaten entfernt werden", 5000);
        } else if (nakedTripleFinder.getNakedTripleInRow() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(nakedTriple.field1.row)));
            nakedTripleFinder.applyNakedTripleToRow();
            showSolutionTooltip("Mit dem Naked Triple konnten in dieser Reihe Kandidaten entfernt werden", 5000);
        } else if (nakedTripleFinder.getNakedTripleInColumn() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInColumn();
           highlightColumnSolution(new ArrayList<>(Arrays.asList(nakedTriple.field1.column)));
            nakedTripleFinder.applyNakedTripleToColumn();
            showSolutionTooltip("Mit dem Naked Triple konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (nakedTripleFinder.getNakedTripleInBlock() != null) {
            NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInBlock();
            highlightBlockSolution(nakedTriple.field1.row, nakedTriple.field1.column);
            nakedTripleFinder.applyNakedTripleToBlock();
            showSolutionTooltip("Mit dem Naked Triple konnten in dieser Reihe Kandidaten entfernt werden", 5000);
        } else if (nakedQuadFinder.getNakedQuadInRow() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(nakedQuad.field1.row)));
            nakedQuadFinder.applyNakedQuadToRow();
            showSolutionTooltip("Mit dem Naked Quad konnten in dieser Reihe Kandidaten entfernt werden", 5000);
        } else if (nakedQuadFinder.getNakedQuadInColumn() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(nakedQuad.field1.column)));
            nakedQuadFinder.getNakedQuadInColumn();
            showSolutionTooltip("Mit dem Naked Quad konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (nakedQuadFinder.getNakedQuadInBlock() != null) {
            NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInBlock();
            highlightBlockSolution(nakedQuad.field1.row, nakedQuad.field1.column);
            nakedQuadFinder.applyNakedQuadToBlock();
            showSolutionTooltip("Mit dem Naked Quad konnten in diesem Block Kandidaten entfernt werden", 5000);
        } else if (hiddenPairFinder.getHiddenPairInRow() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(hiddenPair.field1.row)));
            hiddenPairFinder.applyHiddenPairToRow();
            showSolutionTooltip("Mit dem Hidden Pair konnten in dieser Zeile Kandidaten entfernt werden", 5000);
        } else if (hiddenPairFinder.getHiddenPairInColumn() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(hiddenPair.field1.column)));
            hiddenPairFinder.applyHiddenPairToColumn();
            showSolutionTooltip("Mit dem Hidden Pair konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (hiddenPairFinder.getHiddenPairInBlock() != null) {
            HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInBlock();
            highlightBlockSolution(hiddenPair.field1.row, hiddenPair.field1.column);
            hiddenPairFinder.applyHiddenPairToBlock();
            showSolutionTooltip("Mit dem Hidden Pair konnten in diesem Block Kandidaten entfernt werden", 5000);
        } /*else if (hiddenTripleFinder.getHiddenTripleInRow() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(hiddenTriple.field1.row)));
            hiddenTripleFinder.applyHiddenTripleToRow();
            showSolutionTooltip("Mit dem Hidden Triple konnten in dieser Zeile Kandidaten entfernt werden", 5000);
        } else if (hiddenTripleFinder.getHiddenTripleInColumn() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(hiddenTriple.field1.column)));
            hiddenTripleFinder.applyHiddenTripleToColumn();
            showSolutionTooltip("Mit dem Hidden Triple konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (hiddenTripleFinder.getHiddenTripleInBlock() != null) {
            HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInBlock();
            highlightBlockSolution(hiddenTriple.field1.row, hiddenTriple.field1.column);
            hiddenTripleFinder.applyHiddenTripleToBlock();
            showSolutionTooltip("Mit dem Hidden Triple konnten in diesem Block Kandidaten entfernt werden", 5000);
        } else if (hiddenQuadFinder.getHiddenQuadInRow() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(hiddenQuad.field1.row)));
            hiddenQuadFinder.applyHiddenQuadToRow();
            showSolutionTooltip("Mit dem Hidden Quad konnten in dieser Zeile Kandidaten entfernt werden", 5000);
        } else if (hiddenQuadFinder.getHiddenQuadInColumn() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(hiddenQuad.field1.column)));
            hiddenQuadFinder.applyHiddenQuadToColumn();
            showSolutionTooltip("Mit dem Hidden Quad konnten in dieser Spalte Kandidaten entfernt werden", 5000);
        } else if (hiddenQuadFinder.getHiddenQuadInBlock() != null) {
            HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInBlock();
            highlightBlockSolution(hiddenQuad.field1.row, hiddenQuad.field1.column);
            hiddenQuadFinder.applyHiddenQuadToBlock();
            showSolutionTooltip("Mit dem Hidden Quad konnten in diesem Block Kandidaten entfernt werden", 5000);
        } */else if (rowBlockCheckFinder.getRowBlockCheckInRow() != null) {
            RowBlockCheck rowBlockCheck = rowBlockCheckFinder.getRowBlockCheckInRow();
            highlightBlockSolution(rowBlockCheck.candidate1.row, rowBlockCheck.candidate1.column);
            rowBlockCheckFinder.applyRowBlockCheckToBlock();
            showSolutionTooltip("Mit dem Reihe-Block-Check konnten in diesem Block Kandidaten entfernt werden", 5000);
        } else if (rowBlockCheckFinder.getRowBlockCheckInColumn() != null) {
            RowBlockCheck rowBlockCheck = rowBlockCheckFinder.getRowBlockCheckInColumn();
            highlightBlockSolution(rowBlockCheck.candidate1.row, rowBlockCheck.candidate1.column);
            rowBlockCheckFinder.applyColumnBlockCheckToBlock();
            showSolutionTooltip("Mit dem Reihe-Block-Check konnten in diesem Block Kandidaten entfernt werden", 5000);
        } else if (blockRowCheckFinder.getBlockRowCheckInRow() != null) {
            BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInRow();
            highlightRowSolution(new ArrayList<>(Arrays.asList(blockRowCheck.candidate1.row)));
            blockRowCheckFinder.applyBlockRowCheckToRow();
            showSolutionTooltip("Mit dem Block-Reihe-Check konnten in dieser Zeile Kandidaten entfernt werden", 5000);
        } else if (blockRowCheckFinder.getBlockRowCheckInColumn() != null) {
            BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInColumn();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(blockRowCheck.candidate1.column)));
            blockRowCheckFinder.applyBlockRowCheckToColumn();
            showSolutionTooltip("Mit dem Block-Reihe-Check konnten in dieser Zeile Kandidaten entfernt werden", 5000);
        } else if (xWingFinder.getXWingInRow() != null) {
            XWing xWing = xWingFinder.getXWingInRow();
            highlightColumnSolution(new ArrayList<>(Arrays.asList(xWing.pair1.field1.column, xWing.pair1.field2.column)));
            xWingFinder.applyXWingToColumn();
            showSolutionTooltip("In diesen Spalten konnten mit dem X-Wing Kandidaten entfernt werden", 5000);
        } else if (xWingFinder.getXWingInColumn() != null) {
            XWing xWing = xWingFinder.getXWingInColumn();
            highlightRowSolution(new ArrayList<>(Arrays.asList(xWing.pair1.field1.row, xWing.pair1.field2.row)));
            xWingFinder.applyXWingToRow();
            showSolutionTooltip("In diesen Zeilen konnten mit dem X-Wing Kandidaten entfernt werden", 5000);
        } else if (thirdEyeFinder.getThirdEye() != null) {
            ThirdEye thirdEye = thirdEyeFinder.getThirdEye();
            highlightBlockSolution(thirdEye.thirdEye.row, thirdEye.thirdEye.column);
            thirdEyeFinder.applyThirdEye();
            showSolutionTooltip("In diesem Block konnten mit der Drittes Auge Strategie Kandidaten entfernt werden", 5000);
        }

        else{
            showTooltip("Aktuell keine Lösungsstrategie verfügbar", 5000);
        }
    }

    public void lastSolutionButtonPress(View view) {
        if (savedToolTip != null && savedHighlight != null) {
            savedToolTip.run();
            savedHighlight.run();
        }
        else {
            showTooltip("Keine letzte Lösung verfügbar", 5000);
        }
    }

    private void showTooltip(String message, int duration) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    private void showSolutionTooltip(String message, int duration) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
        savedToolTip = () -> showTooltip(message, duration);
    }

    private void highlightRow(ArrayList<Integer> rows) {
        gameBoard.setTipLocationRows(rows);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationRows(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }

    // highlighted solution gets saved
    private void highlightRowSolution(ArrayList<Integer> rows) {
        gameBoard.setTipLocationRows(rows);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationRows(null);
                gameBoard.invalidate();
            }
        }, 3000);
        savedHighlight = () -> highlightRow(rows);
    }

    private void highlightColumn(ArrayList<Integer> columns) {
        gameBoard.setTipLocationCols(columns);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationCols(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }

    // highlighted solution gets saved
    private void highlightColumnSolution(ArrayList<Integer> columns) {
        gameBoard.setTipLocationCols(columns);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationCols(null);
                gameBoard.invalidate();
            }
        }, 3000);
        savedHighlight = () -> highlightColumn(columns);
    }

    private void highlightBlock(int row, int column) {
        BlockLocation tipLocationBlock = gameBoardSolver.calculateTipLocationBlock(row, column);
        gameBoard.setTipLocationBlocks(tipLocationBlock);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationBlocks(null);
                gameBoard.invalidate();
            }
        }, 3000);
    }

    // highlighted solution gets saved
    private void highlightBlockSolution(int row, int column) {
        BlockLocation tipLocationBlock = gameBoardSolver.calculateTipLocationBlock(row, column);
        gameBoard.setTipLocationBlocks(tipLocationBlock);
        gameBoard.invalidate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.setTipLocationBlocks(null);
                gameBoard.invalidate();
            }
        }, 3000);
        savedHighlight = () -> highlightBlock(row, column);
    }
}