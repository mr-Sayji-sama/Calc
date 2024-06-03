import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int first = 0, second = 0, sum;
        boolean isint = false, isroma = false;
        char operator = ' ';
        String contrval="";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");
        String instr = scanner.nextLine();
        System.out.println("Output: " );
        try {
            //Извлечение арифметических операций
            String regex = "(\\d+|[A-Z]+)\\s*([+\\-*/])\\s*(\\d+|[A-Z]+)"; // Регулярное выражение для извлечения операторов

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(instr);
            if (matcher.find()) {

                contrval = matcher.group();

                String num1 = matcher.group(1);
                String romanNumeral = num1.toUpperCase(); // Преобразуйте введенное значение в верхний регистр
                if (romanNumeral.matches("^[IVXLCDM]+$")) {
                    first = convertRomanToInt(romanNumeral);
                    isroma = true;
                } else {
                    first = Integer.parseInt(num1);
                    isint = true;
                }
                String num2 = matcher.group(3);
                romanNumeral = num2.toUpperCase();
                if (romanNumeral.matches("^[IVXLCDM]+$")) {
                    second = convertRomanToInt(romanNumeral);
                    isroma = true;
                } else {
                    second = Integer.parseInt(num2);
                    isint = true;
                }
                operator = matcher.group(2).charAt(0);
            }
            if (isroma && isint) {
                throw new ArithmeticException("т.к. используются одновременно разные системы счисления");
            }
            if (isroma && operator == '-' && second > first) {
                throw new ArithmeticException("т.к. в римской системе нет отрицательных чисел");
            }
            if (operator == ' ') {
                throw new ArithmeticException("т.к. строка не является математической операцией");
            }
            if (instr.length() > contrval.length()) {
                throw new ArithmeticException("т.к. формат математической операции не удовлетворяет заданию");
            }
            double result;

            switch (operator) {
                case '+':
                    result = first + second;
                    break;
                case '-':
                    result = first - second;
                    break;
                case '*':
                    result = first * second;
                    break;
                case '/':
                    result = first / second;
                    break;
                default:
                    System.out.println("Неверный оператор.");
                    return;
            }
            if (isroma && (int)result==0) {
                throw new ArithmeticException("т.к. в римской системе нет 0");
            }
            if (isroma) {
                System.out.println(integerToRoman((int)result));
            } else {
                System.out.println((int)result);
            }
        } catch (ArithmeticException e) {
            System.out.println("throws Exception: " + e.getMessage());
        }
    }

    public static String integerToRoman(int input) {
        StringBuilder result = new StringBuilder();
        while (input >= 1000) {
            result.append("M");
            input -= 1000;
        }
        while (input >= 900) {
            result.append("CM");
            input -= 900;
        }
        while (input >= 500) {
            result.append("D");
            input -= 500;
        }
        while (input >= 400) {
            result.append("CD");
            input -= 400;
        }
        while (input >= 100) {
            result.append("C");
            input -= 100;
        }
        while (input >= 90) {
            result.append("XC");
            input -= 90;
        }
        while (input >= 50) {
            result.append("L");
            input -= 50;
        }
        while (input >= 40) {
            result.append("XL");
            input -= 40;
        }
        while (input >= 10) {
            result.append("X");
            input -= 10;
        }
        while (input >= 9) {
            result.append("IX");
            input -= 9;
        }
        while (input >= 5) {
            result.append("V");
            input -= 5;
        }
        while (input >= 4) {
            result.append("IV");
            input -= 4;
        }
        while (input >= 1) {
            result.append("I");
            input -= 1;
        }

        return result.toString();
    }

    public static int convertRomanToInt(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = value(s.charAt(i));
            if (i < s.length() - 1) {
                int next = value(s.charAt(i + 1));
                if (current >= next) {
                    total += current;
                } else {
                    total -= current;
                }
            } else {
                total += current;
            }
        }
        return total;
    }

    public static int value(char r) {
        switch (r) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}