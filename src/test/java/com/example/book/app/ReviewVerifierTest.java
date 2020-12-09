package com.example.book.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Assertions.assertFalse(result,"ReviewVerifier did not detected swear word ");

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
        Assertions.assertFalse(result);

        //given
        //when
        //then
    }
}