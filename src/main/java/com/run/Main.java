package com.run;

import com.dto.ContactDto;
import com.dto.ScoreResultDto;
import com.strategy.LevenshteinStrategy;
import com.util.CSVReaderUtil;
import com.service.ContactMatcher;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;
import com.util.CSVWriteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        String inputFilePath = "src/main/resources/input.csv"; // Input CSV file
        String outputFilePath = "src/main/resources/output.csv"; // Output CSV file
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

        Map<String, ScoreResultDto> matches = matcher.findPotentialMatches(contactDtos, weights);
        // Iterate "Potential Matches (Jaro-Winkler) results:");
        for (Map.Entry<String, ScoreResultDto> entry : matches.entrySet()) {
            System.out.println("Contacts: " + entry.getKey() + ", Score: " + entry.getValue().getScore() + ", Category: " + entry.getValue().getType());
        }

        // Write map to output CSV file.
        CSVWriteUtil.writeMapToCSV(matches, outputFilePath);

    }
}