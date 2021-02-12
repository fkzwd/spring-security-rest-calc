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

    LinkedHashMap<ExpressionDTO, Double> MAP = new LinkedHashMap<>(10);

    @PostMapping("/add")
    public LinkedHashMap<ExpressionDTO, Double> add(@RequestBody Expression expression) throws Exception {
        double result = 0;
        if (calcService.isValid(expression.getExpression())) {
            result = calcService.getAnswer(calcService.ReversePolishNotation(expression.getExpression()));
            result = calcService.roundAvoid(result, Integer.parseInt(expression.getPrecision()));
        }

        ExpressionDTO expressionDTO = expressionService.setExpressionDTO(expression);
        if (MAP.size() < 10) {
            MAP.put(expressionDTO, result);
        } else {

            for (Map.Entry<ExpressionDTO, Double> entry : MAP.entrySet()) {

                MAP.remove(entry.getKey());
                MAP.put(expressionDTO,result);
                break;
            }
        }

        expressionService.addExpressionForUser(expression);
        return MAP;
    }

    @GetMapping("/{username}")
    public UserDTO show(@PathVariable String username) throws NotFoundException {
        return userService.getUserDTO(username);
    }

}
