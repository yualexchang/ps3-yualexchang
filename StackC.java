import java.util.*;
import static java.lang.Math.pow;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
@SuppressWarnings("unchecked")
public class StackC<T> implements Stack<T> {

    private final int SIZE = 2;
    private T[] myStack;
    private int top = 0;


    public StackC () {
        this.myStack = (T[]) new Object[SIZE];
        this.top = 0;
    }

    public void push(T element) {
        if (top == myStack.length) resize (2 * myStack.length);
        this.myStack[top++] = element;
    }

    public T pop() {
        if (this.isEmpty()) throw new NoSuchElementException("Stack Underflow");
        this.top--;
        T answer = this.myStack[this.top];
        this.myStack[this.top] = null;

        if (top > 0 && top == this.myStack.length/4 ) resize ( this.myStack.length/2 );
        return answer;
    }

    public T peek() {
        if (this.isEmpty()) throw new NoSuchElementException("Stack underflow");
        return this.myStack[this.top-1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return this.top;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < top; i++) {
            temp[i] = this.myStack[i];
        }
        this.myStack = temp;
    }

    public static Double calculate(String operator, Double value1, Double value2) {
        Double result = 0.0;
        if (operator.equals("+")) {
            result = value1 + value2;
        }
        else if (operator.equals("-")) {
            result = value1 - value2;
        }
        else if (operator.equals("*")) {
            result = value1 * value2;
        }
        else if (operator.equals("/")) {
            result = value1 / value2;
        }
        else if (operator.equals("^")) {
            result = Math.pow(value1,value2);
        }
        return result;
    }

    public static boolean hasPrecedence (String firstOperator, String secondOperator) {
        if (firstOperator.equals("(")) {
            return false;
        }
        else if (firstOperator.equals(secondOperator)) {
            return true;
        }
        else if (firstOperator.equals("^")) {
            return true;
        }
        else if (firstOperator.equals("*") || firstOperator.equals("/")) {
            if (secondOperator.equals("+") || secondOperator.equals("-") || secondOperator.equals("*") || secondOperator.equals("/")) {
                return true;
            }
            return false;
        }
        else if (firstOperator.equals("+") || firstOperator.equals("-")) {
            if (secondOperator.equals("+") || secondOperator.equals("-")) {
                return true;
            }
            return false;
        }
        return true;
    }


    public static void main (String[] args) {
        StackC<String> myOperatorStack = new StackC<String>();
        StackC<Double> myValueStack = new StackC<Double>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            try {
                Double input = Double.parseDouble(item);
                myValueStack.push(input);
            }
            catch (Exception e) {

            }

            if (item.equals("(")) {
                myOperatorStack.push(item);
            }

            if (item.equals(")")) {
                while (!myOperatorStack.peek().equals("(")) {
                    String operator = myOperatorStack.pop();
                    Double value1 = myValueStack.pop();
                    Double value2 = myValueStack.pop();
                    myValueStack.push(calculate(operator, value2, value1));
                }
                myOperatorStack.pop();
            }

            if (item.equals("+") || item.equals("-") || item.equals("*") || item.equals("/") || item.equals("^")) {
                while (!myOperatorStack.isEmpty() && hasPrecedence(myOperatorStack.peek(),item)) {
                    String operator = myOperatorStack.pop();
                    Double value1 = myValueStack.pop();
                    Double value2 = myValueStack.pop();
                    myValueStack.push(calculate(operator, value2, value1));
                }
                myOperatorStack.push(item);
            }
            if (myOperatorStack.isEmpty() && myValueStack.size() == 1) {
                System.out.println("The solution is "+myValueStack.pop() + "! You got this~");
            }
        }
    }
}
