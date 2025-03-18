package com.caelcs.nativeTest.account;

import com.tngtech.jgiven.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingThen extends Stage<GreetingThen> {
    private String response;

    public GreetingThen the_response_should_be(String expected) {
        assertEquals(expected, response);
        return self();
    }

    public GreetingThen setResponse(String response) {
        this.response = response;
        return self();
    }
}
