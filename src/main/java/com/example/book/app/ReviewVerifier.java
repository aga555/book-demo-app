package com.example.book.app;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ReviewVerifier {
    private boolean doesNotContainsSwearWords(String review) {
        return !review.contains("shit");
    }

    public boolean doesMeetQualityStandards(String review) {
        if (review.contains("Lorem ipsum")) {
            return false;
        }

        String[] words = review.split(" ");
        if (Arrays.stream(words).filter(s -> s.equalsIgnoreCase("i")).count() >= 5) {
            return false;
        }
        if (Arrays.stream(words).filter(s -> s.equalsIgnoreCase("good")).count() >= 3) {
            return false;
        }
        if (words.length <= 10) {
            return false;
        }
        return doesNotContainsSwearWords(review);

    }
}