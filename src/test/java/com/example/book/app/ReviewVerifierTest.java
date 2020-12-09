package com.example.book.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewVerifierTest {


    @Test
    void shouldFailWhenReviewContainsSwearWord() {
        //given
        String review= "This book is shit ";
        ReviewVerifier reviewVerifier= new ReviewVerifier();
        boolean result =reviewVerifier.doesMeetQualityStandards(review);
        Assertions.assertFalse(result);
        //then
    }

    @Test
    void shouldReturnWhenReviewDoesntContainsSwearWord() {
        String review= "This book is very good I can strongly recommended it to everyone ";
        ReviewVerifier reviewVerifier= new ReviewVerifier();
        boolean result =reviewVerifier.doesMeetQualityStandards(review);

        Assertions.assertTrue(result);
        //given
        //when
        //then
    }
}