package com.example.android_test_n2;

import java.util.Stack;
import java.util.Map;

public class InfixToRpnConverter {
    public static Stack<String> infixToRpn(String infix) {

        Stack<String> output = new Stack<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder number = new StringBuilder();
        int openParentheses = 0;
        boolean expectNumber = true;  // This flag will help us differentiate between negative numbers and subtraction

        for (int i = 0, n = infix.length(); i < n; i++) {
            char currentChar = infix.charAt(i);

            // Handle invalid characters
            if (currentChar == ' ') {
                continue;
            }
            if (!Character.isDigit(currentChar) && "()+-*/^.:×÷".indexOf(currentChar) == -1) {
                throw new IllegalArgumentException("Nieobsługiwany znak: " + currentChar);
            }

            // Check for number (with optional negative sign)
            if (Character.isDigit(currentChar) || currentChar == '.' || (currentChar == '-' && expectNumber)) {
                number.append(currentChar);
                expectNumber = false;  // We've started processing a number
                if (i + 1 >= n || (!Character.isDigit(infix.charAt(i + 1)) && infix.charAt(i + 1) != '.')) {
                    output.add(number.toString());
                    number.setLength(0);
                }
            }
            else if (currentChar == '(') {
                stack.push(currentChar);
                openParentheses++;
                expectNumber = true;  // After an opening parenthesis, we expect a number (or a negative number)
            }
            else if (currentChar == ')') {
                if (openParentheses == 0) {
                    throw new IllegalArgumentException("Za dużo zamykających nawiasów.");
                }
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.add(String.valueOf(stack.pop()));
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Za dużo otwierających nawiasów.");
                }
                stack.pop();
                openParentheses--;
                expectNumber = false;  // After a closing parenthesis, we expect an operator
            }
            else {
                // Adding to output from stack and popping
                while (!stack.isEmpty() && stack.peek() != '(' && !isHigher(currentChar, stack.peek())) {
                    output.add(String.valueOf(stack.pop()));
                }
                stack.push(currentChar);
                expectNumber = true;  // After an operator, we expect a number next
            }
        }

        if (openParentheses > 0) {
            throw new IllegalArgumentException("Za dużo otwierających nawiasów.");
        }

        while (!stack.isEmpty()) {
            char operator = stack.pop();
            if (operator == '(') {
                throw new IllegalArgumentException("Brakuje zamykającego nawiasu.");
            }
            output.add(String.valueOf(operator));
        }

        return output;
    }

    public static boolean isHigher(Character infixChar, Character stackChar) {
        Map<Character, Integer> hierarchy = Map.of(
                '(', -1,
                '+', 0,
                '-', 0,
                '*', 1,
                '×', 1,
                '÷', 1,
                ':', 1,
                '/', 1,
                '^', 2
        );
        return hierarchy.get(infixChar) > hierarchy.get(stackChar);
    }
}
