package com.example.calcrest.service;

import com.example.calcrest.dto.ExpressionDTO;
import com.example.calcrest.dto.UserDTO;
import com.example.calcrest.exceptions.NotFoundException;
import com.example.calcrest.model.Expression;
import com.example.calcrest.model.User;
import com.example.calcrest.repository.ExpressionRepository;
import com.example.calcrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpressionRepository expressionRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user");
        }
        return user;
    }


    public UserDTO getUserDTO(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("Такого пользователя нет в бд!");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        List<Expression> list = expressionRepository.findAll();
        List<ExpressionDTO> expressionDTOList = new ArrayList<>();

        for (Expression expression: list){
            ExpressionDTO expressionDTO = new ExpressionDTO();
            if (expression.getUser().getUsername().equals(username)){
                expressionDTO.setExpression(expression.getExpression());
                expressionDTO.setPrecision(expression.getPrecision());
                expressionDTO.setDate(expression.getDate());
                expressionDTO.setTime(expression.getTime());
                expressionDTOList.add(expressionDTO);
            }
        }
        userDTO.setExpressions(expressionDTOList);
        return userDTO;
    }
}
