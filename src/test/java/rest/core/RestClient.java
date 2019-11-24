package rest.core;


import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.http.ContentType.JSON;

public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static boolean automaticRedirects = false;


    public static RequestSpecification getRequest(String path) {
        reset();
        return given().relaxedHTTPSValidation().contentType(JSON).baseUri(path);
    }

    /**
     * Sends the current request using PUT
     *
     * @param request the current request
     * @return the response received
     */
    public static Response put(Request request) {
        LOGGER.info("Sending PUT request {}", request);
        ExtractableResponse response = given()
                .redirects().follow(automaticRedirects)
                .contentType(request.getContentType())
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .pathParams(request.getPathParams())
                .body(request.getBody())
                .log().all()
                .put(request.getVersion() + request.getPath())
                .then()
                .log().all()
                .extract();
        Response resp = new Response(response.statusCode(), response.body().asString(), response.headers());
        LOGGER.info("Received response {}", resp);
        return resp;
    }

    /**
     * Sends the current request using GET
     *
     * @param request the current request
     * @return the response received
     */
    public static Response get(Request request) {
        LOGGER.info("Sending GET request {}", request);
        ExtractableResponse response = given()
                .redirects().follow(automaticRedirects)
                .contentType(request.getContentType())
                .urlEncodingEnabled(true)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .pathParams(request.getPathParams())
                .log().all()
                .get(request.getVersion() + request.getPath())
                .then()
                .log().all()
                .extract();
        Response resp = new Response(response.statusCode(), response.body().asString(), response.headers());
        LOGGER.info("Received response {}", resp);
        return resp;
    }

    /**
     * Sends the current request using POST
     * @param request the current request
     * @return the response received
     */
    public static Response post(Request request) {
        LOGGER.info("Sending POST request {}", request);
        ExtractableResponse response;
        RequestSpecification rs = given()
                .redirects().follow(automaticRedirects)
                .contentType(request.getContentType())
                .headers(request.getHeaders());

        if (!request.getFormParams().isEmpty()) {
            //use form params and no body;
            rs.formParams(request.getFormParams());
        } else if (!request.getParams().isEmpty()) {
            //encode the params map and send it in the request body
            rs.body(JsonUtils.mapToUrlEncodedString(request.getParams()));
        } else {
            // send just the body, usually used when the content-type is application/json
            rs.body(request.getBody());
        }

        rs
                .queryParams(request.getQueryParams())
                .pathParams(request.getPathParams())
                .log().all();

        if(request.getContentType().isEmpty()){
            response = rs
                    .post(request.getVersion() + request.getPath()).prettyPeek()
                    .then()
                    .log().all()
                    .extract();
        }
        else{
            response = rs
                    .post(request.getVersion() + request.getPath())
                    .then()
                    .log().all()
                    .extract();
        }

        Response resp = new Response(response.statusCode(), response.body().asString(), response.headers());
        LOGGER.info("Received response {}", resp);
        return resp;
    }

    /**
     * Sends the current request using DELETE
     * @param request the current request
     * @return the response received
     */
    public static Response delete(Request request) {
        LOGGER.info("Sending DELETE request {}", request);
        ExtractableResponse response = given()
                .redirects().follow(automaticRedirects)
                .contentType(request.getContentType())
                .headers(request.getHeaders())
                .log().all()
                .delete(request.getVersion() + request.getPath())
                .then()
                .log().all()
                .extract();
        Response resp = new Response(response.statusCode(), response.body().asString(), response.headers());
        LOGGER.info("Received response {}", resp);
        return resp;
    }

    /**
     * Sends the current request using HEAD
     * @param request the current request
     * @return the response received
     */
    public static Response head(Request request) {
        LOGGER.info("Sending HEAD request {}", request);
        ExtractableResponse response = given()
                .redirects().follow(automaticRedirects)
                .contentType(request.getContentType())
                .headers(request.getHeaders())
                .log().all()
                .head(request.getVersion() + request.getPath())
                .then()
                .log().all()
                .extract();
        Response resp = new Response(response.statusCode(), response.body().asString(), response.headers());
        LOGGER.info("Received response {}", resp);
        return resp;
    }

    /**
     * Sets the default encoding used in all the requests
     *
     * @param charset the encoding
     */
    public static void setDefaultEncoding(String charset) {
        LOGGER.info("Setting default encoding to {}", charset);
        RestAssured.config = config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(charset).
                appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }
}
