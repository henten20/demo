package com.demo.demo.rest;

import java.util.Stack;

import org.springframework.stereotype.Service;

/**
 * Simple parser class for validating closing parenthesis with a given LISP string
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@Service
public class LispParserService {
    public boolean parseLisp(String lisp) {
        if (lisp == null) {
            throw new IllegalArgumentException("LISP input is required.");
        }

        Stack<Character> stack = new Stack<>();
        char [] chars = lisp.toCharArray();

        for(char character : chars) {
            if (character == '(') {
                stack.push(character);
            }
            if (character == ')') {
                if (stack.empty()) {
                    // invalid
                    return false;
                } 
                else {
                    char top = stack.peek();
                    if (top == '(') {
                        stack.pop();
                    }
                    else {
                        // invalid
                        return false;
                    }
                }
            }
        }

        if (!stack.empty()) {
            // invalid
            return false;
        }

        // pass the string if all parenthesis checks are clear
        // and the resulting stack is empty
        return true;
    }
}
