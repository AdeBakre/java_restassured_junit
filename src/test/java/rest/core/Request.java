package rest.core;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private String path;
    private Map<String, Object> params;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, String> formParams;
    private Map<String, String> pathParams;
    private String body;
    private String contentType;
    private String version;
    private String verb;

    public Request() {
        this.path = "";
        this.body = "";
        this.version = "";
        this.verb = "";
        this.params = new HashMap();
        this.headers = new HashMap();
        this.queryParams = new HashMap();
        this.formParams = new HashMap();
        this.pathParams = new HashMap();
    }

    public Request(String path, Map<String, Object> params, Map<String, String> headers, Map<String, String> queryParams, Map<String, String> formParams, Map<String, String> pathParams, String body, String contentType, String version, String verb) {
        this.path = path;
        this.params = params;
        this.headers = headers;
        this.queryParams = queryParams;
        this.formParams = formParams;
        this.pathParams = pathParams;
        this.body = body;
        this.contentType = contentType;
        this.version = version;
        this.verb = verb;
    }

    public Request(Request requestParam) {
        this.path = requestParam.path;
        this.params = new HashMap(requestParam.params);
        this.headers = new HashMap(requestParam.headers);
        this.queryParams = new HashMap(requestParam.queryParams);
        this.formParams = new HashMap(requestParam.formParams);
        this.pathParams = new HashMap(requestParam.pathParams);
        this.body = requestParam.body;
        this.contentType = requestParam.contentType;
        this.version = requestParam.version;
        this.verb = requestParam.verb;
    }

    public static Request generate(String body, String path) {
        Request request = new Request();
        request.getHeaders().put("X-Client-Id", "rms-ui");
        request.setContentType("application/json");
        request.setBody(body);
        request.setPath(path);
        return request;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addAuthorization(String authString) {
        this.headers.put("Authorization", authString);
    }

    public void deleteAuthorization() {
        this.headers.remove("Authorization");
    }

    public Map<String, String> getFormParams() {
        return this.formParams;
    }

    public void setFormParams(Map<String, String> formParams) {
        this.formParams = formParams;
    }

    public Map<String, String> getPathParams() {
        return this.pathParams;
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams = pathParams;
    }

    public String getVerb() {
        return this.verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public void clear() {
        this.path = "";
        this.params.clear();
        this.headers.clear();
        this.queryParams.clear();
        this.formParams.clear();
        this.body = "";
    }

    public String toString() {
        return String.format("---- Request ----\nMethod(verb): %s\nPath:         %s\nContent-Type: %s\nHeaders:      %s\nBody:\n%s\nParams:       %s\nPath parameters:%s\nQueryParams:  %s\nVersion:      %s\n--------------------------------------------------------------", this.verb, this.path, this.contentType, JsonUtils.prettyPrintMap(this.headers), (this.contentType != null ? this.contentType : "null").contains("application/json") ? JsonUtils.prettyPrintJson(this.body) : this.body, JsonUtils.prettyPrintMap(this.params), JsonUtils.prettyPrintMap(this.pathParams), JsonUtils.prettyPrintMap(this.queryParams), this.version);
    }
}
