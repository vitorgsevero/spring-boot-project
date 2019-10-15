package io.vitorgsevero.project.error;

public class CustomErrrorType {
    private String errorMessage;

    public CustomErrrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
