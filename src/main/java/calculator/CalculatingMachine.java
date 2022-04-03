package calculator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CalculatingMachine {

    private static final String [] ALL_OPERATORS = {"*","/","+","-"};
    private static final CalculatingMachine INSTANCE = new CalculatingMachine();

    private CalculatingMachine(){
    }

    public static CalculatingMachine getInstance() {
        return INSTANCE;
    }

    public List<String> pullingOutCertainPart (List<String> equation) {
        System.out.println("pullingOutCertainPart method "+ equation);
        int indexOfOperator = operatorIndexDefiner(equation);
        int resultOfFirstMostPriorityOperator = simpleTaskSolving(IntStream.range(indexOfOperator - 1, indexOfOperator + 2)
                .mapToObj(equation::get)
                .collect(Collectors.toList()));

        equation.set(indexOfOperator, String.valueOf(resultOfFirstMostPriorityOperator));
        equation.remove(indexOfOperator-1);
        equation.remove(indexOfOperator);
        return equation;
    }

    public int operatorIndexDefiner (List<String> equation) {
        System.out.println("operatorIndexDefiner method " + equation);
        String firstsOperator, secondOperator;

        if (equation.contains("*") || equation.contains("/")) {
            firstsOperator = ALL_OPERATORS[0];
            secondOperator = ALL_OPERATORS[1];
        } else if (equation.contains("+") || equation.contains("-")) {
            firstsOperator = ALL_OPERATORS[2];
            secondOperator = ALL_OPERATORS[3];
        } else {
            throw new ArithmeticException("Something went wrong within 'operatorDefiner' method.");
        }

        return equation.indexOf(equation.stream()
                .filter(element -> element.equals(firstsOperator) || element.equals(secondOperator))
                .findFirst()
                .orElseThrow());
    }

    public int simpleTaskSolving (List<String> simpleTask) {
        System.out.println("solveSimpleTask method: "+simpleTask);
        String operator = simpleTask.get(1);
        return switch (operator) {
            case ("+") -> Integer.parseInt(simpleTask.get(0)) + Integer.parseInt(simpleTask.get(2));
            case ("-") -> Integer.parseInt(simpleTask.get(0)) - Integer.parseInt(simpleTask.get(2));
            case ("*") -> Integer.parseInt(simpleTask.get(0)) * Integer.parseInt(simpleTask.get(2));
            case ("/") -> Integer.parseInt(simpleTask.get(0)) / Integer.parseInt(simpleTask.get(2));
            default -> throw new ArithmeticException("Something went wrong in [solveSimpleTask] method.");
        };
    }
}