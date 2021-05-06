package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;

public class HiddenQuadFinder {

    private Solver solver;

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromRow(HiddenQuad hiddenQuad) {
        return false;
    }

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromColumn(HiddenQuad hiddenQuad) {
        return false;
    }

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromBlock(HiddenQuad hiddenQuad) {
        return false;
    }

    public void applyHiddenQuadToRow() {

    }

    public void applyHiddenQuadToColumn() {

    }

    public void applyHiddenQuadToBlock() {

    }

    public HiddenQuad getHiddenQuadInRow() {
        return null;
    }

    public HiddenQuad getHiddenQuadInColumn() {
        return null;
    }

    public HiddenQuad getHiddenQuadInBlock() {
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
