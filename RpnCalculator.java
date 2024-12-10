package com.example.android_test_n2;

import java.util.Stack;
import java.util.LinkedList;

public class RpnCalculator {
    public static Double RpnToResult(Stack<String> RPN){
        LinkedList<String> container = new LinkedList<>();
        for(String charNow : RPN)
        {
            if(isDouble(charNow))
            {
                container.add(charNow);
            }
            else if(container.size() < 2)
            {
                throw new IllegalArgumentException("Nie wystarczająca ilość liczb" + charNow);
            }
            else
            {
                char operator = charNow.charAt(0);
                Double operand2 = Double.parseDouble(container.removeLast());
                Double operand1 = Double.parseDouble(container.removeLast());
                Double tempResult = performOperation(operator, operand1, operand2);
                container.add(String.valueOf(tempResult));
            }
        }
        if (container.size() != 1) {
            throw new IllegalArgumentException("Nieodpowiednia ilość znaków i liczb.");
        }
        return Double.parseDouble(container.getFirst());
    }
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Double performOperation(char operator, Double operand1, Double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '×':
            case '*':
                return operand1 * operand2;
            case ':':
            case '÷':
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Nie można dzielić przez 0.");
                }
                return operand1 / operand2;
            case '^':
                return (Double) Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Nieodpowiedni znak: " + operator);
        }
    }

}