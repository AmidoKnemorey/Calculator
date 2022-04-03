package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputHandler {

    private static final Scanner MAIN_SCANNER = new Scanner(System.in);
    private static final Pattern INPUT_FILTER_PATTERN = Pattern.compile("\\d+|[+\\-*()/]");
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    public void input() {
        List<String> wholeEquation = new ArrayList<>();
        Matcher matcher = INPUT_FILTER_PATTERN.matcher(scanningConsole());
        while (matcher.find()) {
                wholeEquation.add(matcher.group());
            }
        if (checkingSyntaxOfEquation(wholeEquation)){
            PriorityHandler.getInstance().isEquationHasBrackets(wholeEquation);
        } else {
            input();
        }
    }

    public String scanningConsole() {
        System.out.println("\nInput an equation using next symbols: Digits and + - * / ( )");
            return MAIN_SCANNER.nextLine();
    }

    private boolean checkingSyntaxOfEquation(List<String> equation) {
        final Set<String> setOfAllowedOperators = Set.of("+", "-", "*", "/");
        int amountOfOpeningBrackets = 0, amountOfClosingBrackets = 0;
        if (equation.size() < 3) {
            System.err.println("Incorrect syntax, minimum 3 symbols including one operator.");
            return false;
        } if (setOfAllowedOperators.contains(equation.get(0)) | setOfAllowedOperators.contains(equation.get(equation.size() - 1))) {
            System.err.println("Incorrect syntax, first ot last element shouldn't be as operators.");
            return false;
        }
        for (int i = 0; i < equation.size()-1; i++) {
            if (setOfAllowedOperators.contains(equation.get(i)) & setOfAllowedOperators.contains(equation.get(i+1))) {
                System.err.println("Incorrect syntax, double the same operator.");
                return false;
            } if (equation.get(i).equals(")") & equation.get(i + 1).equals("(")) {
                System.err.println("No one operator or equation between brackets.");
                return false;
            } if (equation.get(i).equals("(") & equation.get(i + 1).equals(")")) {
                System.err.println("Empty brackets.");
                return false;
            } if (equation.get(i).equals("(")) {
                amountOfOpeningBrackets++;
            } else if (equation.get(i).equals(")")){
                amountOfClosingBrackets++;
            }
        } if (equation.get(equation.size()-1).equals("(")) {
                amountOfOpeningBrackets++;
            } else if (equation.get(equation.size()-1).equals(")")){
            amountOfClosingBrackets++;
        }
        if (amountOfOpeningBrackets != amountOfClosingBrackets){
            System.err.println("Unsymmetrical amount of opened & closed brackets.");
            return false;
        }
        return true;
    }
}