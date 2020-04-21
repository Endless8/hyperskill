package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, BigInteger> vars = new HashMap<>();

        scanning: while (scanner.hasNext()) {
            String line = scanner.nextLine();

            switch (line) {
                case "/exit":
                    System.out.println("Bye!");
                    break scanning;
                case "/help":
                    System.out.println("The program calculates the sum of numbers");
                    break;
                default:
                    if (!line.isEmpty()) {
                        if (!line.matches("/[a-zA-Z].*")) {
                            if (line.contains("=")) {
                                if (line.matches("[a-zA-Z]\\d\\s*=\\s*\\d+")) {
                                    System.out.println("Invalid identifier");
                                } else if (line.matches("([a-zA-Z]\\s*=\\s*[a-zA-Z]\\d+[a-zA-Z]|[a-zA-Z]\\s*=\\s*\\d\\s*=\\s*\\d)")) {
                                    System.out.println("Invalid assignment");
                                } else {
                                    addVar(line, vars);
                                }
                            } else if (line.contains("+") || line.contains("-") || line.contains("*") || line.contains("/")) {
                                line = line
                                        .replaceAll("--", "+")
                                        .replaceAll("\\+\\+", "")
                                        .replaceAll("\\+-", "-");

                                if (!line.matches(".+\\s.+")) {
                                    char[] lineCharArray = line.toCharArray();
                                    String lineWithSpaces = "" + lineCharArray[0];


                                    for (int i = 1; i < lineCharArray.length; i++) {
                                        String charStr = String.valueOf(lineCharArray[i]);
                                        if (charStr.matches("\\w")) {
                                            String prevCharStr = String.valueOf(lineCharArray[i - 1]);
                                            if (prevCharStr.matches("\\w")) {
                                                lineWithSpaces += charStr;
                                            } else {
                                                lineWithSpaces += " " + charStr;
                                            }
                                        } else {
                                            lineWithSpaces += " " + charStr + " ";
                                        }
                                    }

                                    line = lineWithSpaces;
                                }

                                String postfixLine = getPostfixNotation(line.toCharArray());

                                if ("Invalid expression".equals(postfixLine)) {
                                    System.out.println(postfixLine);
                                } else {
                                    System.out.println(calculatePostfixNotation(postfixLine, vars));
                                }

                            } else {
                                if (vars.containsKey(line)) {
                                    System.out.println(vars.get(line));
                                } else {
                                    System.out.println("Unknown variable");
                                }
                            }
                        } else {
                            System.out.println("Unknown command");
                        }
                    }
            }
        }
    }

    public static void addVar(String line, Map<String, BigInteger> vars) {
        if (line.matches("(.*[0-9]|[0-9].*)")) {
            vars.put(line.replaceAll("\\s*=\\s*\\d+", ""),
                    new BigInteger(line.replaceAll("[a-zA-Z]+\\s*=\\s*", "")));
        } else {
            vars.put(line.replaceAll("\\s*=\\s*\\w+", ""),
                    vars.get(line.replaceAll("[a-zA-Z]+\\s*=\\s*", "")));
        }
    }

    public static String getPostfixNotation(char[] line) {
        String postfixLine = "";
        Deque<Character> postfixStack = new ArrayDeque<>();
        boolean balanced = true;

        for (char ch : line) {
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
                while (
                        !postfixStack.isEmpty()
                                && postfixStack.peekLast() != '('
                                && hasHigherPriority(postfixStack.peekLast(), ch)
                ) {
                    postfixLine += postfixStack.pollLast() + " ";
                }

                postfixStack.offerLast(ch);
            } else if (ch == ')') {
                balanced = false;

                while (!postfixStack.isEmpty() && postfixStack.peekLast() != '(') {
                    postfixLine += " " + postfixStack.pollLast() + " ";
                }

                if (!postfixStack.isEmpty()) {
                    postfixStack.pollLast();
                    balanced = true;
                } else {
                    balanced = false;
                }
            } else if (ch == '(') {
                postfixStack.offerLast(ch);
                balanced = false;
            } else {
                postfixLine += ch;
            }
        }

        while (!postfixStack.isEmpty()) {
            postfixLine += " " + postfixStack.pollLast();
        }

        if (!balanced) {
            postfixLine = "Invalid expression";
        }

        return postfixLine;
    }

    public static boolean hasHigherPriority(char operator, char lastOperator) {
        if (operator == '+' || operator == '-') {
            if (lastOperator == '*' || lastOperator == '/' || lastOperator == '^') {
                return false;
            } else {
                return true;
            }
        }
        if (operator == '*' || operator == '/') {
            if (lastOperator == '^') {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static BigInteger calculatePostfixNotation(String postfixNotation, Map<String, BigInteger> vars) {
        BigInteger result = BigInteger.ZERO;
        Deque<BigInteger> postfixStack = new ArrayDeque<>();
        String[] elements = postfixNotation.split("\\s");

        for (String element : elements) {
            if (element.matches("\\d+")) {
                postfixStack.offerLast(new BigInteger(element));
            } else if (element.matches("[a-zA-Z]+")) {
                postfixStack.offerLast(vars.get(element));
            } else if (element.matches("[\\+\\*-/^]")) {
                BigInteger secondOperand = postfixStack.pollLast();
                BigInteger firstOperand = postfixStack.pollLast();
                if ("+".equals(element)) {
                    result = firstOperand.add(secondOperand);
                    postfixStack.offerLast(result);
                } else if ("-".equals(element)) {
                    result = firstOperand.subtract(secondOperand);
                    postfixStack.offerLast(result);
                } else if ("*".equals(element)) {
                    result = firstOperand.multiply(secondOperand);
                    postfixStack.offerLast(result);
                } else if ("/".equals(element)) {
                    result = firstOperand.divide(secondOperand);
                    postfixStack.offerLast(result);
                } else if ("^".equals(element)) {
                    for (BigInteger i = BigInteger.ONE; i.compareTo(secondOperand) >= 0; i.add(BigInteger.ONE)) {
                        result.add(firstOperand.multiply(firstOperand));
                    }
                    postfixStack.offerLast(result);
                }
            }
        }

        return result;
    }
}
