package com.service;

import com.dto.ContactDto;
import com.dto.ScoreResultDto;
import com.strategy.ISimilarityStrategy;
import com.strategy.JaroWinklerStrategy;
import com.strategy.LevenshteinStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ContactMatcher with strategy pattern for similarity calculation
public class ContactMatcher {
    private ISimilarityStrategy similarityStrategy;

    public ContactMatcher(ISimilarityStrategy strategy) {
        this.similarityStrategy = strategy;
    }

    public void setSimilarityStrategy(ISimilarityStrategy strategy) {
        this.similarityStrategy = strategy;
    }

    public ScoreResultDto evaluate(List<ContactDto> contactDtos, Map<String, Double> weights, int i, int j) {
        ContactDto c1 = contactDtos.get(i-1);
        ContactDto c2 = contactDtos.get(j-1);
        double score = calculateMatchScore(c1, c2, weights);
        String category = similarityStrategy.categorizeScore(score);
        return new ScoreResultDto(c1.getContactId(), c2.getContactId(), score, category);
    }

    // Method to compare contact list "contactDtos" one by one.
    public Map<String, ScoreResultDto> findPotentialMatches(List<ContactDto> contactDtos, Map<String, Double> weights) {
        Map<String, ScoreResultDto> matches = new HashMap<>();

        for (int i = 0; i < contactDtos.size(); i++) {
            for (int j = i + 1; j < contactDtos.size(); j++) {
                ContactDto c1 = contactDtos.get(i);
                ContactDto c2 = contactDtos.get(j);

                double score = calculateMatchScore(c1, c2, weights);
                if (score >= getLowThreshold()) { // Similarity threshold to consider a match.
                    String key = c1.getContactId() + "-" + c2.getContactId();
                    String category = similarityStrategy.categorizeScore(score);
                    ScoreResultDto result = new ScoreResultDto(c1.getContactId(), c2.getContactId(), score, category);
                    matches.put(key, result);
                }
            }
        }
        return matches;
    }

    // Method for comparing two contacts field by field, the degree of similarity is calculated by adding the score of
    // each field and then dividing by the total number of fields to obtain the average.
    // The expected result is a number in the range [0..1] where 1 means the highest degree of similarity.
    public double calculateMatchScore(ContactDto c1, ContactDto c2, Map<String, Double> weights) {
        double weightFistName = weights.getOrDefault("fistName", 1.0);
        double weightLastName = weights.getOrDefault("lastName", 1.0);
        double weightEmail = weights.getOrDefault("email", 1.0);
        double weightAddress = weights.getOrDefault("address", 1.0);
        double weightZipCode = weights.getOrDefault("zipCode", 1.0);
        double totalWeight = 0;

        double firstNameScore = 0;
        if (!c1.getFirstName().isEmpty() && !c2.getFirstName().isEmpty()) {
            firstNameScore = weightFistName * similarityStrategy.computeSimilarity(c1.getFirstName(), c2.getFirstName());
            totalWeight += weightFistName;
        }

        double lastNameScore = 0;
        if (!c1.getLastName().isEmpty() && !c2.getLastName().isEmpty()) {
            lastNameScore = weightLastName * similarityStrategy.computeSimilarity(c1.getLastName(), c2.getLastName());
            totalWeight += weightLastName;
        }

        double emailScore = 0;
        if (!c1.getEmail().isEmpty() && !c2.getEmail().isEmpty()) {
            emailScore = weightEmail * similarityStrategy.computeSimilarity(c1.getEmail(), c2.getEmail());
            totalWeight += weightEmail;
        }

        double addressScore = 0;
        if (!c1.getAddress().isEmpty() && !c2.getAddress().isEmpty()) {
            addressScore = weightAddress * similarityStrategy.computeSimilarity(c1.getAddress(), c2.getAddress());
            totalWeight += weightAddress;
        }

        double zipCodeScore = 0;
        if (!c1.getZipCode().isEmpty() && !c2.getZipCode().isEmpty()) {
            zipCodeScore = weightZipCode * similarityStrategy.computeSimilarity(c1.getZipCode(), c2.getZipCode());
            totalWeight += weightZipCode;
        }

        double score = (firstNameScore + lastNameScore + emailScore + addressScore + zipCodeScore) / totalWeight;

        // For testing purposes only, this code will be removed.
        boolean log = false;
        if (log) {
            System.out.println(c1.getContactId() + "-" + c2.getContactId());
            System.out.println("firstNameScore: " + firstNameScore + " " + c1.getFirstName() + " " + c2.getFirstName());
            System.out.println("lastNameScore: " + lastNameScore + " " + c1.getLastName() + " " + c2.getLastName());
            System.out.println("emailSocore: " + emailScore + " " + c1.getEmail() + " " + c2.getEmail());
            System.out.println("addressScore: " + addressScore + " " + c1.getAddress() + " " + c2.getAddress());
            System.out.println("zipCodeScore: " + zipCodeScore + " " + c1.getZipCode() + " " + c2.getZipCode());
            System.out.println("totalWeight: " + totalWeight);
            System.out.println(score);
        }
        return score;
    }

    // Method to obtain the lower limit for considering two contacts to be similar.
    public double getLowThreshold() {
        if (similarityStrategy instanceof JaroWinklerStrategy) {
            return JaroWinklerStrategy.LOW_THRESHOLD;
        } else if (similarityStrategy instanceof LevenshteinStrategy) {
            return LevenshteinStrategy.LOW_THRESHOLD;
        } else {
            throw new UnsupportedOperationException("Unknown threshold.");
        }
    }
}