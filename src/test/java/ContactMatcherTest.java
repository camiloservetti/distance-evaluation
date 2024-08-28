import com.dto.ContactDto;
import com.service.ContactMatcher;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;
import org.junit.jupiter.api.Test; // Para JUnit 5

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ContactMatcherTest {

    @Test
    public void testComputeSimilarityHigh() {
        // Setup
        ContactDto c1 = new ContactDto("1", "John", "Doe", "john.doe@example.com", "12345", "123 Main St");
        ContactDto c2 = new ContactDto("2", "Johnny", "Do", "johnny.d@example.com", "12345", "123 Main St");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score > 0.95, "The similarity score should be greater than 0.8 for similar contacts.");
    }

    @Test
    public void testComputeSimilarityHighBorder() {
        // Setup, score should be 0.91 HIGH > 0.9
        ContactDto c1 = new ContactDto("194", "Odette", "Richard", "cum@icloud.ca", "52558", "Ap #390-9494 Consectetuer Rd.");
        ContactDto c2 = new ContactDto("694", "Odette", "R", "um@icloud.ca", "52558", "Ap #390-9494 Consectetuer Rd.");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score > 0.9, "The similarity score should be greater than 0.8 for similar contacts.");
    }

    @Test
    public void testComputeSimilarityMedium() {
        // Setup, score should be 0.85 > s < 0.9. Possible same person with different domain name.
        ContactDto c1 = new ContactDto("266", "Ruby", "Mcclain", "lacus.vestibulum@protonmail.com", "48612", "Ap #219-7768 Phasellus Ave");
        ContactDto c2 = new ContactDto("766", "R", "M", "lacus.vestibulum@att.com", "48612", "Ap #219-7768 Phasellus Ave");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score > 0.85 && score < 0.9, "The similarity score should be greater than 0.8 for similar contacts.");
    }

    @Test
    public void testComputeSimilarityLow() {
        // Setup: Case when attributes has same mail domain.
        ContactDto c1 = new ContactDto("858", "H", "W", "fusce.feugiat.lorem@att.net", "58665", "440-6269 Donec Street");
        ContactDto c2 = new ContactDto("949", "H", "W", "", "", "Proin road");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score > 0.75 && score < 0.8, "The similarity score should be greater than 0.75");
    }

    @Test
    public void testComputeSimilarityLowBorder() {
        // Setup: Case when attributes has same mail domain.
        ContactDto c1 = new ContactDto("353", "Lenore", "Santana", "ligula.aenean.euismod@yahoo.couk", "44665", "P.O. Box 686, 6716 Nunc Street");
        ContactDto c2 = new ContactDto("853", "L", "S", "ligula.aenean.euismod@yahoo.couk", "44665", "Non Avenue");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score > 0.75 && score < 0.8, "The similarity score should be greater than 0.75");
    }


    @Test
    public void testComputeSimilarityEmptyFields() {
        // Setup: Case when attributes are empty. If attributes are empty, they should be ignored. This is a negative case.
        ContactDto c1 = new ContactDto("910", "K", "W", "", "", "Consequat road");
        ContactDto c2 = new ContactDto("929", "M", "W", "", "", "Non Avenue");
        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);
        Map<String, Double> weights = new HashMap<>();

        // Execution
        double score = matcher.calculateMatchScore(c1, c2, weights);

        // Verification
        assertTrue(score < 0.6, "The similarity score should be less than 0.8.");
    }



}
