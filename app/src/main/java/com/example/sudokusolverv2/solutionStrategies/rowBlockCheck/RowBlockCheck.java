package com.example.sudokusolverv2.solutionStrategies.rowBlockCheck;

import com.example.sudokusolverv2.candidateSystem.Candidate;

import java.util.HashSet;

public class RowBlockCheck {

    public Candidate candidate1;
    public Candidate candidate2 = new Candidate(-1, -1, -1);;
    public Candidate candidate3 = new Candidate(-1, -1, -1);;

    public RowBlockCheck(Candidate candidate1) {
        this.candidate1 = candidate1;
    }

    public RowBlockCheck(Candidate candidate1, Candidate candidate2) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
    }

    public RowBlockCheck(Candidate candidate1, Candidate candidate2, Candidate candidate3) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.candidate3 = candidate3;
    }

}
