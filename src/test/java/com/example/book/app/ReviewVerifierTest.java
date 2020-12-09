package com.example.book.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(RandomReviewParameterResolverExtension.class)
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


    @RepeatedTest(5)
    void shouldFailWhenRandomQualityIsBad(@RandomReviewParameterResolverExtension.RandomReview String review) {
        System.out.println(review);
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        System.out.print(result);
        assertFalse(result, "ReviewVerifier did not detected  random bad review");
    }

    @Test
    void shouldPassWhenReviewIsGood() {
        String review="Yaa Gyasi. Five Days. by Wes Moore and Erica L. Mexican Gothic. by Silvia Moreno-Garcia. Eat a Peach. by David Chang and Gabe Ulla. The Searcher. by Tana French. Hidden Valley Road. by Robert Kolker.";
        boolean result = reviewVerifier.doesMeetQualityStandards(review);
        assertTrue(result,"ReviewVerifier detected  random bad review");
    }
}