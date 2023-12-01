package algorithmbasic.questionsets.class1.MakeACalculator;

import java.util.Scanner;
import java.util.Stack;

public class MyCalculator {
    private static MyCalculator instance;

    private MyCalculator() {
    }

    /**
     * 制造计算器
     *
     * @return 计算器
     */
    public static MyCalculator make() {
        if (instance == null) {
            synchronized (MyCalculator.class) {
                if (instance == null) {
                    instance = new MyCalculator();
                }
            }
        }
        return instance;
    }

    /**
     * 使用计算器
     *
     * @param args
     */
    public static void main(String[] args) {
        MyCalculator calculator = MyCalculator.make();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入需要计算式子(输入end退出计算器)：如-1.1+2*3(4*(-6+5))/(6(2+0.2)(7-3))= ");
        String str = null;
        while (scanner.hasNextLine()) {
            str = scanner.nextLine();
            if (str.equals("end")) {
                break;
            }
        }
        //信息输入完毕，开始计算
        //prepareParam函数可以格式校验与转换，然后再计算.
        Double result = calculator.prepareParam(str);
        System.out.println("计算的结果是" + result);
    }

    /**
     * 格式的校验，转化，结果计算
     */
    private Double prepareParam(String str) {
        if (verify(str)) {
            String s = transfer(str);
            Double result = cauclate(s);
            return result;
        } else {
            return null;
        }
    }

    /**
     * 校验
     */
    private Boolean verify(String str) {
        char[] charArray = str.toCharArray();
        //空值校验
        if (str == null || str.equals("")) {
            return false;
        }
        //结尾=校验
        if (charArray[str.length() - 1] != '=') {
            return false;
        }
        //开头符号校验
        if (!('-' == charArray[0] || '(' == charArray[0] || isNum(charArray[0]))) {
            return false;
        }
        //字符不合法校验
        for (int i = 0; i < str.length(); i++) {
            if (charArray[i] != '+' && charArray[i] != '-' && charArray[i] != '*' && charArray[i] != '/' && charArray[i] != '('
                    && charArray[i] != '.' && charArray[i] != ')' && !isNum(charArray[i])) {
                return false;
            }
        }
        //字符写法校验 ++ -- 计算符号前面不是数字或者）
        for (int i = 0; i < str.length(); i++) {
            char c = charArray[i];
            if (i != 0 && c == '-' && charArray[i - 1] == '(') {
                continue;
            }
            // 若符号前一个不是数字或者“）”时
            if (i != 0 && (c == '+' || c == '-' || c == '*' || c == '/')) {
                if (!(charArray[i - 1] == ')' || isNum(charArray[i - 1]))) {
                    return false;
                }
            }
            //.的前后不是数字
            if (c == '.') {
                if (i == 0) {
                    return false;
                } else if (!isNum(charArray[i - 1]) || !isNum(charArray[i - 1])) {
                    return false;
                }
            }
        }
        //括号匹配
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (charArray[i] == '(') {
                stack.push(i);
            }
            if (stack.isEmpty() && charArray[i] == ')') {
                return false;
            }
            if (charArray[i] == ')' && charArray[stack.peek()] == '(') {
                stack.pop();
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }

    }

    /**
     * 转化
     */
    private String transfer(String str) {

        return null;
    }

    /**
     * 结果计算
     */
    private Double cauclate(String s) {

        return 0.0;
    }


    /**
     * 是否是数字
     *
     * @param c
     * @return
     */
    private Boolean isNum(char c) {
        for (int i = 48; i < 58; i++) {
            if (c != i) {
                return false;
            }
        }
        return true;
    }
}

































