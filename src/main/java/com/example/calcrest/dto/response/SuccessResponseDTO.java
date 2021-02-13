package com.example.calcrest.dto.response;

public class SuccessResponseDTO {
    private String expression;
    private String precision;
    private Double result;

    public String getExpression() {
        return expression;
    }

    public SuccessResponseDTO setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public String getPrecision() {
        return precision;
    }

    public SuccessResponseDTO setPrecision(String precision) {
        this.precision = precision;
        return this;
    }

    public Double getResult() {
        return result;
    }

    public SuccessResponseDTO setResult(Double result) {
        this.result = result;
        return this;
    }
}
