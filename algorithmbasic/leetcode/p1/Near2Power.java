package algorithmbasic.leetcode.p1;

// 已知n是正数
// 返回大于等于，且最接近n的，2的某次方的值
public class Near2Power {
    //思路：二进制形式全变成1111...   然后再加1
    public static final int tableSizeFor(int n) {

        //刚上来n就是2的某次方的值
        //1000
        //111

        //11000
        //10111
        //11111
        n -= 1;
        n = n | n >>> 1;
        n = n | n >>> 2;
        n = n | n >>> 4;
        n = n | n >>> 8;
        n = n | n >>> 16;

        //全变成1111.。。

        return n + 1;

    }
}
