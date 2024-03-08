package com.boot.ch04.exception;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class UrlNotAccessibleFailureAnalyzer
        extends AbstractFailureAnalyzer<UrlNotAccessibleException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, UrlNotAccessibleException cause) {
        return new FailureAnalysis("Unable to access the URL" + cause
                , "Validate the URL and ensure it's is accessible", cause);
    }
}
