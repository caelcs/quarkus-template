package com.caelcs.nativeTest.helloworld;

import com.tngtech.jgiven.Stage;

class GreetingWhen extends Stage<GreetingWhen> {
    private String response;

    GreetingWhen I_request_the_greeting() {
        response = "Hello world";
        return self();
    }

    String response() {
        return response;
    }
}
