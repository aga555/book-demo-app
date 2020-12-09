package com.example.book.app;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.annotation.ElementType.PARAMETER;

public class RandomReviewParameterResolverExtension implements ParameterResolver {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(PARAMETER)
    public @interface RandomReview {

    }

    private static final ArrayList<String> badReviews = new ArrayList<String>() {
        {
            add("This book was shit I dont like it");
            add("I was reading the book an I think the book is okay. I have read better books an I think I know what is good");
            add("Good book with good agende and good example. I can recommend for everyone.");
            add("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum");
            add("This book was great I  like it");
        }
    };

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomReview.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return badReviews.get(ThreadLocalRandom.current().nextInt(0, badReviews.size()));
    }
}
