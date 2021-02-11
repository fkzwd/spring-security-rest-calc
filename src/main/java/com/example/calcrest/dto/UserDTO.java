package com.example.calcrest.dto;

import com.example.calcrest.model.Expression;

import java.util.List;
import java.util.Set;

public class UserDTO {
    String username;
    List<ExpressionDTO> expressions;

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ExpressionDTO> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionDTO> expressions) {
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", expressions=" + expressions +
                '}';
    }
}
