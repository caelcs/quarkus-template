package com.caelcs.auth;

import com.caelcs.TestContainerLifecycleManager;
import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class AuthAccessTokenUtil {

    public static final String CLIENT_ID = "quarkus-template-app";
    public static final String CLIENT_SECRET = "YSFwvyazqPmLukTvwBWa0ZhlhtP3T031";

    public static final String REALM = "quarkus-template";

    public static final String USER_USERNAME = "alice";
    public static final String USER_PASSWORD = "alice";

    public static final String ADMIN_USERNAME = "john";
    public static final String ADMIN_PASSWORD = "jhon";

    public static final String SUPPORT_USERNAME = "linda";
    public static final String SUPPORT_PASSWORD = "linda";

    public static final String REPORT_USERNAME = "lucas";
    public static final String REPORT_PASSWORD = "lucas";

    public static final String ALL_USERNAME = "pinza";
    public static final String ALL_PASSWORD = "pinza";


    public static String getAccessToken(String username, String password) {
        String authServerUrl = TestContainerLifecycleManager.keycloakContainer.getAuthServerUrl();
        System.out.println("✅ authurl: " + authServerUrl);
        return given()
                .relaxedHTTPSValidation()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET)
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, REALM))
                .then()
                .statusCode(200)
                .extract().path("access_token");
    }

    public static String getInvalidAccessToken() {
        return "3P3RViqprRZEPGl1jf28rcGbmKe5KQ!E!Wp9a1h9/Pj50aAunPVWCJYBQSsaJB-sh=ThvTG-JT=-hrdJcNxAqHE3hQgs!QpiLM2?PsQYQpIM8pxUVpRT183?6RQdSn0MzbJ?IEeBF2QoQTNfSgztk2TR=TnsrSX1auK9jLQCGpPETwUIvE5-lw?SiMd-a6fZOa5PxVbbAOOnoun8BHlzv-BYGdJslTswdYCFH0jYJJO9taqEcNMZ4c13R6uRNP!q";
    }

    public static String getExpiredAccessToken() {
        return "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI4ZXNDWk1iRG5fMlRLNVdpLXJJcmN2WU5leENzRktZVFV1SGxJcnZ1dXVnIn0.eyJleHAiOjE3NDM2MzcyNTAsImlhdCI6MTc0MzYzNjk1MCwianRpIjoiYWY3ZWYzMTEtZTQ0ZC00ZTU1LWE4OTItYzAzZThlNzllYzgyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDozMzAxNy9yZWFsbXMvcXVhcmt1cy10ZW1wbGF0ZSIsInN1YiI6IjdhYzFiOGIwLTVlZTUtNGU0ZC1iNWEyLTk3NDhjN2Q3YWIxNSIsInR5cCI6IkJlYXJlciIsImF6cCI6InF1YXJrdXMtdGVtcGxhdGUtYXBwIiwic2lkIjoiYTgzYmY4ZDMtYWEwNi00N2M4LTlkZmItMDA1NjM0MDcxZDZiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLXF1YXJrdXMtdGVtcGxhdGUiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJhbGljZSBhbGljZSIsInByZWZlcnJlZF91c2VybmFtZSI6ImFsaWNlIiwiZ2l2ZW5fbmFtZSI6ImFsaWNlIiwiZmFtaWx5X25hbWUiOiJhbGljZSIsImVtYWlsIjoiYWxpY2VAYWxpY2UuY29tIn0.T8rWyYl_NPzx4PeC4p4-rS1006zhUct7d5YIYlLeV5RFXq3Fi9d6t25E5wHcq6OJyNXpXcstxWvdqx7X4mauwC4NLvDQYwuU1SGSAlCdNzD85BzHjbf7pCFo6QRyo-pDIi5X-NCqR8ps2b0iTVOR2Z4FS9lDcH91WA0rC2qmfFiW1_c9uwtA-SCOQ_iOzOd4juJWuw5p0IKnHlfOJI1xokb96quAxe-L-i9MOTpmPZ7F9mTVU6wTSu87RCCd6AVjD-e_7VUINERjdKDZDQRJjv4_hkz9CNY0TEGLJyCGyXoCtvnyli_z2kUSWAYn8txoR--QUDtrXfh5IizOvuLycQ";
    }

}
