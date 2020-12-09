package com.example.book.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ReviewVerifierTest {

    private ReviewVerifier reviewVerifier;

    @BeforeEach
    void setUp() {
        reviewVerifier = new ReviewVerifier();
    }

    @Test
    void shouldFailWhenReviewContainsSwearWord() {
        String review = "This book is shit ";
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        assertFalse(result, "ReviewVerifier did not detected swear word ");

    }

    @Test
    void shouldReturnWhenReviewDoesntContainsSwearWord() {
        String review = "This book is very good I can strongly recommended it to everyone ";
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        Assertions.assertTrue(result);
    }

    @Test
    void shouldFaildWhenReviewContainsLoremIpsum() {
        String review = "lorem ipsum  lorem ipsum lorem ipsum lorem ipsum ";
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        assertFalse(result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/badReview.csv")
    void shouldFailedWhenReviewIsBadQuality(String review) {
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        assertFalse(result, "ReviewVerifier did not detected bad review");

    }
}