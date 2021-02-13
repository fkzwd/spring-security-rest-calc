package com.example.calcrest.rest;

import com.example.calcrest.dto.ExpressionDTO;
import com.example.calcrest.dto.UserDTO;
import com.example.calcrest.dto.response.SuccessResponseDTO;
import com.example.calcrest.model.Expression;
import com.example.calcrest.service.CalcService;
import com.example.calcrest.service.ExpressionService;
import com.example.calcrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
public class RestContr {
    private final ExpressionService expressionService;
    private final CalcService calcService;
    private final UserService userService;

    @Value("${app.configuration.cache.size}")
    private int cacheMaxSize;
    private Map<CacheKey, SuccessResponseDTO> cacheMap;

    private static class CacheKey {
        private String expression;
        private String precision;

        private CacheKey(String expression, String precision) {
            this.expression = expression;
            this.precision = precision;
        }

        private static CacheKey of(String expression, String precision) {
            return new CacheKey(expression, precision);
        }
    }

    @PostConstruct
    private void initMap() {
        cacheMap = new LinkedHashMap<>(){
            //all oldest entries will be removed if map size grows bigger then cacheMaxSize
            @Override
            protected boolean removeEldestEntry(Map.Entry<CacheKey, SuccessResponseDTO> eldest) {
                return this.size() > cacheMaxSize;
            }
        };
    }

    public RestContr(ExpressionService expressionService, CalcService calcService, UserService userService) {
        this.expressionService = expressionService;
        this.calcService = calcService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public SuccessResponseDTO add(@RequestBody Expression expression) throws Exception {
        calcService.isValid(expression.getExpression());
        SuccessResponseDTO resultDto = findCached(expression.getExpression(), expression.getPrecision());
        if (resultDto != null) {
            return resultDto;
        }
        double result;
        int precision = Integer.parseInt(expression.getPrecision());
        String reversedInPolishNotation = calcService.ReversePolishNotation(expression.getExpression());
        result = calcService.roundAvoid(calcService.getAnswer(reversedInPolishNotation), precision);
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO()
                .setExpression(expression.getExpression())
                .setPrecision(expression.getPrecision())
                .setResult(result);

        addToCache(successResponseDTO);

        //TODO: rework this (dont know what is it)
        expressionService.addExpressionForUser(expression);

        return successResponseDTO;
    }

    private void addToCache(SuccessResponseDTO successResponseDTO) {
        CacheKey cacheKey = new CacheKey(successResponseDTO.getExpression(), successResponseDTO.getPrecision());
        cacheMap.put(cacheKey, successResponseDTO);
    }

    private SuccessResponseDTO findCached(String expression, String precision) {
        return cacheMap.get(CacheKey.of(expression, precision));
    }

    @GetMapping("/{username}")
    public UserDTO show(@PathVariable String username) throws NotFoundException {
        return userService.getUserDTO(username);
    }
}
