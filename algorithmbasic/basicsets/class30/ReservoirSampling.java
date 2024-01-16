package algorithmbasic.basicsets.class30;

/**
 * 《蓄水池算法》
 * 解决的问题：
 * 假设有一个源源吐出不同球的机器，
 * 只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
 * 如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里，保证不会出现先吐出的球放进袋子的概率大于后吐出球的概率
 */
public class ReservoirSampling {
    static int[] bag = new int[50];
    static int bagI = 0;
    static int testTime = 1000000;

    //求在bag中的概率  长度范围->[1,maxNub]
    public static void reservoir(int maxNub) {
        int[] count = new int[maxNub + 1];
        for (int k = 0; k < testTime; k++) {
            bagI = 0;
            for (int i = 1; i <= maxNub; i++) {
                if (i <= 50) {
                    bag[bagI++] = i;
                } else { //i > 50
                    // 50 / i * 1 / 50
                    int j = (int) (Math.random() * i) + 1;
                    if (j <= 50) {
                        bag[(int) (Math.random() * 50)] = i;
                    }
                }
            }
            //统计一下bag中的数值
            for (int i = 0; i < bag.length; i++) {
                count[bag[i]]++;
            }
        }
        for (int i = 0; i < count.length; i++) {
            System.out.println("第" + i + "对应->" + count[i]);

        }
    }


    public static void main(String[] args) {
        reservoir(150);
    }

}