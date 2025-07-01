package org.example.tests;

// File: src/TestEntry.java

public class TestEntry {
    public String category;
    public String label;
    public double score;
    public String reason;
    public Double compression_ratio;

    public TestEntry(String category, String label, double score, String reason) {
        this(category, label, score, reason, null);
    }

    public TestEntry(String category, String label, double score, String reason, Double compression_ratio) {
        this.category = category;
        this.label = label;
        this.score = score;
        this.reason = reason;
        this.compression_ratio = compression_ratio;
    }
}
