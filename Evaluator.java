// This program prompts for fully-parenthesized arithmetic
// expressions and it evaluates each expression.  It uses two
// stacks to evaluate the expressions.

/*** Comments: this programs has been improved by adding a try/catch block to handle illegal tokens,
 *** thereby making it more robust. Extensive comments have also been added for better understanding
 *** of the overall program. Also includes other minor tweaks for personal preference.
 - by CIMAN 5/31/18 ***/

import java.util.*;

public class Evaluator {
    public static void main(String[] args) {
        System.out.println("This program evaluates fully");
        System.out.println("parenthesized expressions with the");
        System.out.println("operators +, -, *, +, and ^");
        System.out.println();
        Scanner console = new Scanner(System.in);
        System.out.print("expression (return to quit)? ");
        String line = console.nextLine().trim();
        while (line.length() > 0) {
            evaluate(line);
            System.out.print("expression (return to quit)? ");
            line = console.nextLine().trim();
        }
    }

    // pre : line contains a fully parenthesized expression
    // post: prints the value of the expression or an error
    //       message if the expression is not legal.
    public static void evaluate(String line) {
        StringSplitter data = new StringSplitter(line);
        Stack<String> symbols = new Stack<>();
        Stack<Double> values = new Stack<>();
        String specialChars = "(+-*/^";
        // a boolean flag that keeps track of whether an
        // error has been seen.
        boolean error = false;
        // Loop processing the tokens
        while (!error && data.hasNext()) {
            String next = data.next();
            // If it is a left parenthesis or (else-if) operator, push it on the symbol stack.
            // Otherwise (it is a number), push the number on the value stack.
            /** When it is a right parenthesis ")" check for errors and evaluate. **/
            if (next.equals(")")) {
                // Use the stack size to make sure that we have an operator
                // and that below it on the stack is a left parenthesis.
                if (symbols.size() < 2 || symbols.peek().equals("(")) {
                    error = true;
                }
                // Remove the operator and left parenthesis, remove the two numbers,
                // and then apply the operator.
                else {
                    String operator = symbols.pop();
                    // Make sure we really have a left "(" after removing operator
                    if (!symbols.peek().equals("(")) {
                        error = true;
                    // Go ahead and remove the two numbers and apply the operator
                    } else {
                        symbols.pop(); // to remove the "("
                        double operand2 = values.pop(); // get operand
                        double operand1 = values.pop(); // get operand
                        // Perform the arithmetic operation
                        double value = evaluate(operator, operand1, operand2);
                        values.push(value); // push result to the number stack
                    }
                }
            }
            /** when it is an operator push it on the symbol stack **/
            else if (specialChars.indexOf(next) >= 0) {
                symbols.push(next);
            }
            /** when it is a number push on it on the number stack **/
            else {  // it should be a number
                // a try/catch block that sets the error flag to true if the
                // NumberFormatException is thrown.
                try {
                    // convert the token from a string into a double.
                    values.push(Double.parseDouble(next));
                }
                // handle any illegal tokens.
                catch (NumberFormatException e){
                    //sets the error flag to true.
                    error = true;
                    // print the error
                    System.out.println("error: " + e);
                }
            }
            // Shows evaluation process
            System.out.println(next + "\t\t" + symbols + "\t" + values);
        }
        if (error || values.size() != 1 || !symbols.isEmpty()) {
            System.out.println("illegal expression");
        } else {
            System.out.println(values.pop());
        }
    }

    // pre : operator is one of +, -, *, or /
    // post: returns the result of applying the given operator to
    //       the given operands
    public static double evaluate(String operator, double operand1,
                                  double operand2) {
        if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        } else if (operator.equals("/")) {
            return operand1 / operand2;
        } else if (operator.equals("^")) {
            return Math.pow(operand1, operand2);
        } else {
            throw new RuntimeException("illegal operator " + operator);
        }
    }
}
9
