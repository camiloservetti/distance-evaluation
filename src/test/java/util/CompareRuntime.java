package util;

import com.dto.ContactDto;
import com.dto.ScoreResultDto;
import com.service.ContactMatcher;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareRuntime {

    public static void main (String[] array) {
        List<ContactDto> contactDtos = new ArrayList<>();
        contactDtos.add(new ContactDto("1", "John", "Doe", "john.doe@example.com", "12345", "123 Main St"));
        contactDtos.add(new ContactDto("2", "Johnny", "Do", "johnny.d@example.com", "12345", "123 Main St"));

        ISimilarityStrategy strategy = new JaroWinklerStrategy();
        ContactMatcher matcher = new ContactMatcher(strategy);

        Map<String, Double> weights = new HashMap<>();
        weights.put("firstName", 1.0);
        weights.put("lastName", 1.0);
        weights.put("email", 1.0);
        weights.put("address", 1.0);
        weights.put("zipCode", 1.0);

        Map<String, ScoreResultDto> result = matcher.findPotentialMatches(contactDtos, weights);

        for(Map.Entry<String, ScoreResultDto> entry : result.entrySet()){
            System.out.println("Contacts: " + entry.getKey() + ", Score: " + entry.getValue().getScore() + ", Category: " + entry.getValue().getType());
        }
    }
}
