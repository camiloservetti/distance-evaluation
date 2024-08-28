package com.dto;

public class ScoreResultDto {
    private String sourceContactId;
    private String matchContactId;
    private double score;
    private String type;

    public ScoreResultDto(String sourceContactId, String matchContactId, double score, String type) {
        this.sourceContactId = sourceContactId;
        this.matchContactId = matchContactId;
        this.score = score;
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceContactId() {
        return sourceContactId;
    }

    public void setSourceContactId(String sourceContactId) {
        this.sourceContactId = sourceContactId;
    }

    public String getMatchContactId() {
        return matchContactId;
    }

    public void setMatchContactId(String matchContactId) {
        this.matchContactId = matchContactId;
    }
}
