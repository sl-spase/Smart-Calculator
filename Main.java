package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, BigInteger> variables = new HashMap<>();
    static Solution solution = new Solution();

    public static final String REGEX = "[a-zA-Z]+";
    public static final String REGEX_DIGIT = "\\d+";
    public static final String UNKNOWN_VARIABLE = "Unknown variable";
    public static final String INVALID_IDENTIFIER = "Invalid identifier";
    public static final String INVALID_ASSIGMENT = "Invalid assignment";
    public static final String INVALID_EXPRESION = "Invalid expression";
    public static final String UNKNOWN_COMMAND = "Unknown command";
    public static final String BYE = "Bye!";


    public static void main(String[] args) {
        init();
    }

    private static void init() {

        String input;
        while (!(input = scanner.nextLine()).equals("/exit")) {
            if (input.startsWith("/")) {
                helpCommand(input);
            } else if (input.contains("=")) {
                assignVariables(input);
            } else if (contains(input)) {
                calculateResult(input);
            } else if (input.matches(REGEX)) {
                outputValue(input);
            }

        }
        System.out.println(BYE);

    }

    private static void calculateResult(String input) {
        input = stringForCount(input);

        if (!input.isEmpty()) {
            calculate(input);
        } else {
            System.out.println(UNKNOWN_VARIABLE);
        }
    }

    private static void calculate(String input) {
        Pattern pattern = Pattern.compile("(\\*+|/+)|([+]+)|([-]+)");
        Matcher matcher = pattern.matcher(input);
        boolean canCount = true;
        while (matcher.find()) {
            String str;
            if (matcher.group(1) != null) {
                int multipleCount = matcher.group(1).replaceAll("(\\*)", "*").length();
                int divisionCount = matcher.group(1).replaceAll("(/)", "/").length();
                if (multipleCount > 1 || divisionCount > 1) {
                    System.out.println(INVALID_EXPRESION);
                    canCount = false;
                    break;
                }
            }

            if (matcher.group(2) != null) {
                str = matcher.group(2);
                if (str.length() > 1) {
                    input = input.replaceAll("[+]+", "+");
                }
            }

            if (matcher.group(3) != null) {
                str = matcher.group(3);
                if (str.length() % 2 == 0) {
                    input = input.replaceAll(str, "+");
                } else {
                    input = input.replaceAll(str, "-");
                }
            }
        }

        if (canCount) {
            solution.calculate(input);
        }
    }

    private static String stringForCount(String input) {
        String[] arrOfParam = input.split("\\s*[+\\-*/]+\\s*");
        for (String str : arrOfParam) {
            if (variables.containsKey(str)) {
                BigInteger in = variables.get(str);
                input = input.replaceAll(str, String.valueOf(in));
            }
        }
        boolean isValid = true;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            isValid = false;
        }

        return isValid ? input : "";
    }

    private static void outputValue(String input) {
        if (variables.containsKey(input)) {
            System.out.println(variables.get(input));
        } else {
            System.out.println(UNKNOWN_VARIABLE);
        }
    }

    private static boolean contains(String input) {
        return input.contains("-") || input.contains("+")
                || input.contains("*") || input.contains("/") || input.contains("^");
    }

    private static void helpCommand(String input) {
        if ("/help".equals(input)) {
            System.out.println("SMART CALCULATOR.\n" +
                    "Support " +
                    "as a simple (such as: 2 + 2 * 2 = 6) " +
                    "as a complicated operations (1 +++ 2 * 3 -- 4 = 11)\n" +
                    "- support parentheses\n" +
                    "8 * 3 + 12 * (4 - 2)\n" +
                    "- support variables:\n" +
                    "a = 4\n" +
                    "b = 5\n" +
                    "c = 6\n" +
                    "a*2+b*3+c*(2+3) = 53\n" +
                    "- can be used for really big numbers:\n" +
                    "112234567890 + 112234567890 * (10000000999 - 999) = 1122345679012234567890\n" +
                    "You can't do something like:\n" +
                    "n = a2a | a = 7 = 8 | 2 *** 5 | 6 /// 2 | (2+3 * 4 | 1 + 1) * 18");
        } else {
            System.out.println(UNKNOWN_COMMAND);
        }
    }

    private static void assignVariables(String input) {
        String[] keyValueArr = input.split("\\s*=\\s*");
        if (keyValueArr.length != 2) {
            System.out.println(INVALID_ASSIGMENT);
            return;
        }
        String value = keyValueArr[1];
        String key = keyValueArr[0];
        if (key.matches(REGEX)) {
            if (value.matches(REGEX)) {
                if (variables.containsKey(value)) {
                    variables.put(key, variables.get(value));
                } else {
                    System.out.println(UNKNOWN_VARIABLE);
                }
            } else if (value.matches(REGEX_DIGIT)) {
                variables.put(key, new BigInteger(value));
            } else {
                System.out.println(INVALID_ASSIGMENT);
            }
        } else {
            System.out.println(INVALID_IDENTIFIER);
        }

    }
}

class Solution {
    static boolean isValid;
    static int bracketsCount;
    private int getPreference(char c){
        if(c=='+'|| c=='-') return 1;
        else if(c=='*' || c=='/') return 2;
        else if(c=='^') return 3;
        else return -1;
    }
    private BigInteger calculatePostFix(List<String> postFixList){
        isValid = true;
        Stack<BigInteger> stack = new Stack<>();
        BigInteger number;
        BigInteger number1;
        BigInteger number2;
        for (String word : postFixList) {
            if (word.length() == 1 && (word.charAt(0) == '+' || word.charAt(0) == '-' || word.charAt(0) == '*' || word.charAt(0) == '/' || word.charAt(0) == '^')) {
                number2 = stack.pop();
                number1 = new BigInteger("0");
                try {
                    number1 = stack.pop();
                } catch (Exception ignored) {
                }
                if (word.charAt(0) == '+') {
                    number = number1.add(number2);
                    stack.push(number);
                } else if (word.charAt(0) == '-') {
                    number = number1.subtract(number2);
                    stack.push(number);
                } else if (word.charAt(0) == '*') {
                    number = number1.multiply(number2);
                    stack.push(number);
                } else if (word.charAt(0) == '/') {
                    number = number1.divide(number2);
                    stack.push(number);
                } else {
                    number = number1.pow(Math.abs(number2.intValue()));
                    stack.push(number);
                }
            } else {
                try {
                    number = new BigInteger(word);
                    stack.push(number);
                } catch (Exception e) {
                    isValid = false;
                }

            }
        }
        return stack.peek();
    }
    private List<String> getPostFixString(String s){
        Stack<Character> stack = new Stack<>();
        List<String> postFixList = new ArrayList<>();
        boolean flag = false;
        bracketsCount = 0;
        for(int i=0;i<s.length();i++){
            char word = s.charAt(i);
            if(word==' '){
                continue;
            }
            if(word=='('){
                stack.push(word);
                bracketsCount++;
                flag = false;
            }else if(word==')'){
                bracketsCount--;
                flag = false;
                while(!stack.isEmpty()){
                    if(stack.peek()=='('){
                        stack.pop();
                        break;
                    }else{
                        postFixList.add(stack.pop()+"");
                    }
                }
            }else if(word =='+' || word =='-' || word =='*' || word =='/' || word == '^'){
                flag = false;
                if (!stack.isEmpty()) {
                    while (!stack.isEmpty() && getPreference(stack.peek()) >= getPreference(word)) {
                        postFixList.add(stack.pop() + "");
                    }
                }
                stack.push(word);
            }else{
                if(flag){
                    String lastNumber = postFixList.get(postFixList.size()-1);
                    lastNumber+=word;
                    postFixList.set(postFixList.size()-1, lastNumber);
                }else
                    postFixList.add(word+"");
                flag = true;
            }
        }
        while(!stack.isEmpty()){
            postFixList.add(stack.pop()+"");
        }
        return postFixList;
    }
    public void calculate(String s) {
        List<String> postFixString = getPostFixString(s);
        BigInteger result = calculatePostFix(postFixString);
        if (isValid && bracketsCount == 0) {
            System.out.println(result);
        } else {
            System.out.println("Invalid expression");
        }
    }
}
