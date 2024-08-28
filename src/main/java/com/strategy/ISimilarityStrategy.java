package com.strategy;

public interface ISimilarityStrategy {
    double computeSimilarity(String s1, String s2);
    String categorizeScore(double score);
}
