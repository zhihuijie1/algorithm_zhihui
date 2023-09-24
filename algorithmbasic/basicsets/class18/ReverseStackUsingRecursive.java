package algorithmbasic.basicsets.class18;

import java.util.Stack;

//给定一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数
public class ReverseStackUsingRecursive {

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        //拿到栈底元素lowValue。
        int lowValue = f(stack);
        reverse(stack);
        stack.push(lowValue);
    }

    //黑盒：给我一个栈，在保证栈中元素顺序不变的情况下，弹出栈底元素。
    public static int f(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        }
        int last = f(stack);
        stack.push(result);
        return last;
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }

    }
}
