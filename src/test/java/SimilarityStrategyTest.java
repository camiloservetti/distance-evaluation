import com.enums.ScoreType;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimilarityStrategyTest {

    @Test
    public void testCategorizeScoreLow() {
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        assertEquals(strategy.categorizeScore(0.76), ScoreType.LOW.name());
    }

    @Test
    public void testCategorizeScoreMedium() {
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        assertEquals(strategy.categorizeScore(0.86), ScoreType.MEDIUM.name());
    }

    @Test
    public void testCategorizeScoreHigh() {
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        assertEquals(strategy.categorizeScore(0.97), ScoreType.HIGH.name());
    }

    @Test
    public void testCategorizedScoreException() {
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            // Code that should throw UnsupportedOperationException
            strategy.categorizeScore(0.6);
        });
    }
}
