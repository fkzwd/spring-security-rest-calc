package com.example.calcrest.repository;

import com.example.calcrest.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression,Long> {
}
