package org.example.tests;

import java.util.List;

public class TestResult {
    public String student_id;
    public boolean config_valid;
    public TestEntry[] test_results;
    public double final_score;

    public TestResult(String student_id, boolean config_valid, List<TestEntry> test_results, double final_score) {
        this.student_id = student_id;
        this.config_valid = config_valid;
        this.test_results = test_results.toArray(new TestEntry[0]);
        this.final_score = final_score;
    }
}
