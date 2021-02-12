package com.example.calcrest.dto;

import com.example.calcrest.model.Expression;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


public class ExpressionDTO {
    String expression;
    String precision;
    LocalTime time;
    LocalDate date;


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionDTO that = (ExpressionDTO) o;
        return Objects.equals(getExpression(), that.getExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpression());
    }

    @Override
    public String toString() {
        return "ExpressionDTO{" +
                "expression='" + expression + '\'' +
                ", precision='" + precision + '\'' +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
