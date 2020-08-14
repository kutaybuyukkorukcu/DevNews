package com.scalx.devnews.exception;

public class RecommendationHttpException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public RecommendationHttpException() {
        super();
    }

    public RecommendationHttpException(String message) {
        super(message);
    }

    public RecommendationHttpException(Throwable e) {
        super(e);
    }

    public RecommendationHttpException(String message, Throwable e) {
        super(message, e);
    }
}