import com.dto.ContactDto;
import com.dto.ScoreResultDto;
import com.service.ContactMatcher;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;
import com.util.CSVReaderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PairEvaluationRuntime {

    public static void main(String[] array) {
        String inputFilePath = "src/main/resources/input.csv"; // Ruta al archivo CSV
        List<ContactDto> contactDtos = CSVReaderUtil.readContactsFromCSV(inputFilePath);

        // Define weights for attributes
        Map<String, Double> weights = new HashMap<>();
        weights.put("firstName", 1.0);
        weights.put("lastName", 1.0);
        weights.put("email", 1.0);
        weights.put("address", 1.0);
        weights.put("zipCode", 1.0);

        // Use Jaro-Winkler strategy
        ISimilarityStrategy jwStrategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(jwStrategy);

        ScoreResultDto r = matcher.evaluate(contactDtos, weights, 530, 842);
    }
}
