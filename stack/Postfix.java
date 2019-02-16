import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The Postfix class implements an evaluator for integer postfix expressions.
 *
 * Postfix notation is a simple way to define and write arithmetic expressions
 * without the need for parentheses or priority rules. For example, the postfix
 * expression "1 2 - 3 4 + *" corresponds to the ordinary infix expression
 * "(1 - 2) * (3 + 4)". The expressions may contain decimal 32-bit integer
 * operands and the four operators +, -, *, and /. Operators and operands must
 * be separated by whitespace.
 *
 * @author  Erik Vanhainen
 * @version 2019-01-28
 */
public class Postfix extends LinkedList<Integer> {
    public static class ExpressionException extends Exception {
        public ExpressionException(String message) {
            super(message);
        }
    }

    /**
     * Evaluates the given postfix expression.
     *
     * @param expr  Arithmetic expression in postfix notation
     * @return      The value of the evaluated expression
     * @throws      ExpressionException if the expression is wrong
     */
    public static int evaluate(String expr) throws ExpressionException {
        if(expr.length() == 0) {
            throw new ExpressionException("Empty string");
        }
        Stack<Integer> stack = new LinkedList<Integer>();
        expr = expr.trim();
        String[] arr = expr.split("\\s+");
        for(String s : arr) {
            if(isInteger(s)) {
                if(s.matches("-?0\\d+")) {
                    throw new ExpressionException("Leading 0");
                }
                int value = Integer.parseInt(s);
                stack.push(value);
            }
            else if(isOperator(s)) {
                if(stack.size() < 2) {
                    throw new ExpressionException("To few operands");
                }
                int a = stack.pop();
                int b = stack.pop();
                switch(s) {
                    case "+": {
                        stack.push(b+a);
                        break;
                    }
                    case "-": {
                        stack.push(b-a);
                        break;
                    }
                    case "*": {
                        stack.push(b*a);
                        break;
                    }
                    case "/": {
                        if(a == 0) {
                            throw new ExpressionException("Undifined");
                        }
                        stack.push(b/a);
                        break;
                    }
                }
            }
            else {
                throw new ExpressionException("Invalid symbol");
            }

        }
        if(stack.size() > 1) {
            throw new ExpressionException("To many operands");
        }
        return stack.top();
    }

    /**
     * Returns true if s is an operator.
     *
     * A word of caution on using the String.matches method: it returns true
     * if and only if the whole given string matches the regex. Therefore
     * using the regex "[0-9]" is equivalent to "^[0-9]$".
     *
     * An operator is one of '+', '-', '*', '/'.
     */
    private static boolean isOperator(String s) {
        if(s.length() == 1) {
            if(s.matches("[+\\-*/]")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if s is an integer.
     *
     * A word of caution on using the String.matches method: it returns true
     * if and only if the whole given string matches the regex. Therefore
     * using the regex "[0-9]" is equivalent to "^[0-9]$".
     *
     * We accept two types of integers:
     *
     * - the first type consists of an optional '-'
     *   followed by a non-zero digit
     *   followed by zero or more digits,
     *
     * - the second type consists of an optional '-'
     *   followed by a single '0'.
     */
    private static boolean isInteger(String s) {
        if(s.matches("-?\\d+")) {
            return true;
        }
        return false;
    }
}
