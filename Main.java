package com.example.android_test_n2;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj wyrazenie w notacji infix: ");
        String infixFromUser = scanner.nextLine();

        Stack<String> RPN = InfixToRpnConverter.infixToRpn(infixFromUser);
        System.out.printf("Wyrazenie w postaci RPN: %s%n", String.join(" ", RPN));

        Double result = RpnCalculator.RpnToResult(RPN);
        System.out.printf("Wynik wyrazenia: %f%n", result);
        scanner.close();
    }
}
