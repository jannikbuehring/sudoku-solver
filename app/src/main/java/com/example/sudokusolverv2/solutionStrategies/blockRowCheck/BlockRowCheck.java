package com.example.sudokusolverv2.solutionStrategies.blockRowCheck;

import com.example.sudokusolverv2.candidateSystem.Candidate;

public class BlockRowCheck {

    public Candidate candidate1;
    public Candidate candidate2 = new Candidate(-1, -1, -1);
    public Candidate candidate3 = new Candidate(-1, -1, -1);

    public BlockRowCheck(Candidate candidate1) {
        this.candidate1 = candidate1;
    }

    public BlockRowCheck(Candidate candidate1, Candidate candidate2) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
    }

    public BlockRowCheck(Candidate candidate1, Candidate candidate2, Candidate candidate3) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.candidate3 = candidate3;
    }
}
