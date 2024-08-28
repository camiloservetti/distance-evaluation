package com.strategy;
import com.enums.ScoreType;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinStrategy implements  ISimilarityStrategy{
    public static final String STRATEGY_NAME = "Levenshtein"; // Constant defined
    public static final double HIGH_THRESHOLD = 0.8 ; // Constant defined
    public static final double MEDIUM_THRESHOLD = 0.7 ;
    public static final double LOW_THRESHOLD = 0.6 ;
    private final LevenshteinDistance levenshtein = new LevenshteinDistance();

    @Override
    public double computeSimilarity(String s1, String s2) {
        // Normalize Levenshtein distance to a similarity score between 0 and 1
        int maxLength = Math.max(s1.length(), s2.length());
        int distance = levenshtein.apply(s1, s2);
        return 1.0 - ((double) distance / maxLength);
    }

    @Override
    public String categorizeScore(double score) {
        if (score >= HIGH_THRESHOLD) {
            return ScoreType.HIGH.name();
        } else if (score >= MEDIUM_THRESHOLD) {
            return ScoreType.MEDIUM.name();
        } else {
            return ScoreType.LOW.name();
        }
    }
}
