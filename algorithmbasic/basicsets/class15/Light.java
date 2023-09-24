package algorithmbasic.basicsets.class15;

/*
给定一个字符串str，只由'X'和'.'两种字符构成
'X'表示墙，不能放灯，也不需要点亮；'.'表示居民点，可以放灯，需要点亮
如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 */
public class Light {
    //贪心解法
    public static int minLight2(String road) {
        //将字符串拆分成char类型的数组
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                light++;
                if (i + 1 == str.length) {
                    return light;
                } else {
                    if (str[i + 1] == 'X') {
                        //注意这里的light已经在上面加了。
                        i += 2;
                    } else {
                        //注意这里的light已经在上面加了。
                        i += 3;
                    }
                }
            }
        }
        return light;
    }


    public static int minLight1(String road) {
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                light++;
                if (i + 1 == str.length) {
                    break;
                } else { // 有i位置 i+ 1 X .
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }


    public static int minLight3(String road) {
        char[] str = road.toCharArray();
        int cur = 0;
        int light = 0;
        for (char c : str) {
            if (c == 'X') {
                light += (cur + 2) / 3;
                cur = 0;
            } else {
                cur++;
            }
        }
        light += (cur + 2) / 3;
        return light;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            int ans3 = minLight3(test);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

}
