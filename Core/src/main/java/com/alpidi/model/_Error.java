package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class _Error {
    private String error;
    private String error_description;

    public _Error(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return this.error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public String toString() {
        return "_Error{" + "error=" + this.error + '\'' + ", error_description='" + this.error_description + '\'' + '}';
    }
}
