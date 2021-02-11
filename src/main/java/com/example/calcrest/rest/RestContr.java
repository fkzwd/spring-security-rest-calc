package com.example.calcrest.rest;

import com.example.calcrest.dto.ExpressionDTO;
import com.example.calcrest.dto.UserDTO;
import com.example.calcrest.model.Expression;
import com.example.calcrest.service.CalcService;
import com.example.calcrest.service.ExpressionService;
import com.example.calcrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class RestContr {

    @Autowired
    ExpressionService expressionService;

    @Autowired
    CalcService calcService;

    @Autowired
    UserService userService;

    private Queue<Double> RESULTLIST = new PriorityQueue<>(10);


    @PostMapping("/add")
    public Map<ExpressionDTO, Double> add(@RequestBody Expression expression) throws Exception {
        if (calcService.isValid(expression.getExpression())) {
            double result = calcService.getAnswer(calcService.ReversePolishNotation(expression.getExpression()));
            result = calcService.roundAvoid(result, Integer.parseInt(expression.getPrecision()));
            RESULTLIST.add(result);
        }
        ExpressionDTO expressionDTO = expressionService.setExpressionDTO(expression);

        Map<ExpressionDTO, Double> map = new HashMap<>();
        map.put(expressionDTO, RESULTLIST.peek());

        expressionService.addExpressionForUser(expression);
        return map;
    }

    @GetMapping("/{username}")
    public UserDTO show(@PathVariable String username) throws NotFoundException {
        return userService.getUserDTO(username);
    }

}
