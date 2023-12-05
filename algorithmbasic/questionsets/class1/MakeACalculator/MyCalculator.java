package algorithmbasic.questionsets.class1.MakeACalculator;

import java.util.*;

public class MyCalculator {
    private static MyCalculator instance;
    //表达式的最大长度
    private static final int MAXLENGTH = 500;

    public static final Map<Character, Integer> symLvMap = new HashMap<>();

    static {
        symLvMap.put('+', 1);
        symLvMap.put('-', 1);
        symLvMap.put('*', 2);
        symLvMap.put('/', 2);
        symLvMap.put('=', 0);
        symLvMap.put('(', 3);
        symLvMap.put(')', 1);
    }

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
            //信息输入完毕，开始计算
            //prepareParam函数可以格式校验与转换，然后再计算.
            Double result = calculator.prepareParam(str);
            System.out.println("计算的结果是" + result);
        }
    }

    /**
     * 格式的校验，转化，结果计算
     */
    private Double prepareParam(String str) {
        //格式校验
        if (verify(str)) {
            //格式转化
            String s = transfer(str);
            //去空格
            s = s.replaceAll(" ", "");
            //符号与数字分离
            String[] nums = s.split("[^.0-9]");//正则表达式[^.0-9] -> 匹配任何不是小数点和数字的其他符号
            //"-1.1+2*3(4*(-6+5))/(6(2+0.2)(7-3))="  -->  " -1.1, 2, 3, 4, -6, 5, 6, 2, 0.2, 7, 3 "
            List<Double> numList = new LinkedList<>();
            for (int i = 0; i < nums.length; i++) {
                if (!nums[i].equals("")) {
                    numList.add(Double.parseDouble(nums[i]));
                }
            }
            String symStr = s.replaceAll("[.0-9]", "");
            //结果计算
            Double result = cauclate(symStr, numList);
            return result;
        } else {
            return null;
        }
    }

    /**
     * 校验
     */
    private Boolean verify(String str) {
        //空值校验
        if (str == null || str.equals("")) {
            return false;
        }
        System.out.println("1");
        //长度校验
        if (str.length() > MAXLENGTH) {
            return false;
        }
        System.out.println("2");
        //去空格
        str = str.replaceAll(" ", "");
        char[] charArray = str.toCharArray();
        //结尾=校验
        if (charArray[str.length() - 1] != '=') {
            return false;
        }
        System.out.println("3");
        //开头符号校验
        if (!('-' == charArray[0] || '(' == charArray[0] || isNum(charArray[0]))) {
            return false;
        }
        System.out.println("4");
        //字符不合法校验
        for (int i = 0; i < charArray.length - 1; i++) {
            if (charArray[i] != '+' && charArray[i] != '-' && charArray[i] != '*' && charArray[i] != '/' && charArray[i] != '('
                    && charArray[i] != '.' && charArray[i] != ')' && !isNum(charArray[i])) {
                return false;
            }
        }
        System.out.println("5");
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
        System.out.println("6");
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
        System.out.println("7");
        return true;
    }

    /**
     * 格式转化 ---------------- stringbuffer用的非常的精妙
     */
    private String transfer(String str) {
        char[] charArray = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            //开头-格式转化
            if (i == 0 && charArray[i] == '-') {
                sb.append("0-");
                continue;
            }
            //8(  )( 格式转化
            if (charArray[i] == '(' && (isNum(charArray[i - 1]) || charArray[i - 1] == ')')) {
                sb.append("*(");
                continue;
            }
            if (charArray[i] == '-' && charArray[i - 1] == '(') {
                sb.append("0-");
                continue;
            }
            sb.append(charArray[i]);
        }
        System.out.println(sb.toString());
        //(-格式转化
        return sb.toString();
    }


    /**
     * 结果计算
     */
    private Double cauclate(String symStr, List<Double> numList) {
        LinkedList<Character> symStack = new LinkedList<>();
        LinkedList<Double> numStack = new LinkedList<>();
        char[] symChars = symStr.toCharArray();
        int i = 0; //numList
        int j = 0; //symChars
        while (!(symChars[j] == '=' && symStack.getLast() == '=')) {
            if (symStack.isEmpty() && numStack.isEmpty()) {
                symStack.add('=');
                numStack.add(numList.get(i++));
            }

            if (symLvMap.get(symChars[j]) > symLvMap.get(symStack.getLast())) { //当前符号的优先级大于栈顶
                if (symChars[j] == '(') {
                    symStack.add(symChars[j++]);
                    continue;
                }
                symStack.add(symChars[j++]);
                numStack.add(numList.get(i++));
            } else { //当前符号的优先级小于等于栈顶
                if (symChars[j] == ')' && symStack.getLast() == '(') {
                    symStack.removeLast();
                    j++;
                    continue;
                }
                if (symStack.getLast() == '(') {
                    symStack.add(symChars[j++]);
                    numStack.add(numList.get(i++));
                    continue;
                }
                Double num2 = numStack.removeLast();
                Double num1 = numStack.removeLast();
                Character sym = symStack.removeLast();
                switch (sym) {
                    case '+':
                        numStack.add(num1 + num2);
                        break;
                    case '-':
                        numStack.add(num1 - num2);
                        break;
                    case '*':
                        numStack.add(num1 * num2);
                        break;
                    case '/':
                        numStack.add(num1 / num2);
                        break;
                }
            }
        }
        return numStack.removeLast();
    }

    /**
     * 是否是数字
     *
     * @param c
     * @return
     */
    private Boolean isNum(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }
}