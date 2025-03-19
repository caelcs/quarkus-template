package com.caelcs.nativeTest.helloworld;

import com.tngtech.jgiven.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingThen extends Stage<GreetingThen> {
    private String response;

    GreetingThen the_response_should_be(String expected) {
        assertEquals(expected, response);
        return self();
    }

    GreetingThen setResponse(String response) {
        this.response = response;
        return self();
    }
}
