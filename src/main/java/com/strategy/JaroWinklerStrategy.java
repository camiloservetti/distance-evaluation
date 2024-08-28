package com.strategy;

import com.enums.ScoreType;
import org.apache.commons.text.similarity.JaroWinklerDistance;


public class JaroWinklerStrategy implements ISimilarityStrategy {
    public static final String STRATEGY_NAME = "Jaro-Winkler"; // Constant defined
    public static final double HIGH_THRESHOLD = 0.9; // Constant defined
    public static final double MEDIUM_THRESHOLD = 0.85;
    public static final double LOW_THRESHOLD = 0.75;

    private final JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();

    @Override
    public double computeSimilarity(String s1, String s2) {
        return (1 - jaroWinkler.apply(s1, s2));
    }

    @Override
    public String categorizeScore(double score) {
        if (score >= HIGH_THRESHOLD) {
            return ScoreType.HIGH.name();
        } else if (score >= MEDIUM_THRESHOLD) {
            return ScoreType.MEDIUM.name();
        } else if (score >= LOW_THRESHOLD) {
            return ScoreType.LOW.name();
        } else {
            throw new UnsupportedOperationException("UnsupportedThreshold");
        }
    }
}
