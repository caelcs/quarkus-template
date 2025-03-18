package com.caelcs.nativeTest.account;

import com.tngtech.jgiven.Stage;

public class GreetingWhen extends Stage<GreetingWhen> {
    private String response;

    public GreetingWhen I_request_the_greeting() {
        response = "Hello world";
        return self();
    }

    public String response() {
        return response;
    }
}
