package algorithmbasic.basicsets.class38;

import java.util.HashSet;
import java.util.LinkedList;

public class CountofRangeSum2 {
    //思路 - 题目要求是给我一个数组，然后寻找有多少个子数组的累加和在[lower,upper]之间。
    //首先使用的是前缀和数组 - 每一个下标对应的数值都是一个累加和，想要求某个区间子数组累加和，只需要拿出两个区间边界的值做差即可
    //即将问题转化为如何快速拿到两个边界点。
    //现在的问题是如何快速的遍历一遍数组，从而求出在指定范围的子数组个数。
    //-- 当遍历到累加和数组的某个节点时，可以假设以当前节点结尾的有多少个子数组的累加和在区间范围上。
    //-- node(当前) - x = [lower,upper]
    //-- x = [node - upper, node - lower]
    //-- 即node节点之前的节点只要在 [node - upper, node - lower]这个范围上就可以满足以x节点开始，以node节点结尾的子数组在区间上。
    //-- 如何快速的计算出满足条件的节点的数量呢？
    //-- [node - upper, node - lower] = [10,20]
    //-- <10 ?  <21 ?
    //-- a = lessKeySize(10) 累加和小于10的节点数量
    //-- b = lessKeySize(21) 累加和小于21的节点数量
    //-- b - a 累加和在 [10,20] 这个区间的节点数量
    public long countRangeSum(int[] nums, int lower, int upper) {
        //黑盒，加入数字(前缀和)，不去重，可接受重复数字
        SizeBalancedTreeSet SBTreeSet = new SizeBalancedTreeSet();
        SBTreeSet.add(0);//一个也没有的时候，前缀和是0
        long sum = 0;//累加和
        long ans = 0;//记录全局满足区间条件的子数组数量
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            long a = SBTreeSet.lessKeySize(sum - upper);
            long b = SBTreeSet.lessKeySize(sum - lower + 1);
            ans += b - a;
            SBTreeSet.add(sum);
        }
        return ans;
    }

    public static class SBTreeNode {
        public long key;
        public SBTreeNode left;
        public SBTreeNode right;
        public long size;//平衡因子，不同key的数量
        public long all;//总的数量

        public SBTreeNode(long key) {
            this.key = key;
            size = 1;
            all = 1;
        }
    }

    //黑盒 加入的节点不去重 可接收重复数字
    //可以快速的计算出小于某个数值的节点个数
    public static class SizeBalancedTreeSet {
        private SBTreeNode root;
        private HashSet<Long> set = new HashSet<>();

        public void add(long sum) {
            Boolean contains = set.contains(sum);
            root = add(root, sum, contains);
            set.add(sum);
        }

        //小于key的有几个
        public long lessKeySize(long key) {
            SBTreeNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (cur.key == key) {
                    ans += cur.left != null ? cur.left.all : 0;
                    break;
                } else if (cur.key > key) {
                    cur = cur.left;
                } else {
                    ans += cur.all - (cur.right != null ? cur.right.all : 0);
                    cur = cur.right;
                }
            }
            return ans;
        }

        public SBTreeNode add(SBTreeNode cur, long key, Boolean contains) {
            if (cur == null) {
                return new SBTreeNode(key);
            } else {
                cur.all++;
                if (cur.key == key) {
                    return cur;
                }
                //向左滑还是向右滑
                if (!contains) {
                    cur.size++;
                }
                if (cur.key < key) {
                    cur.right = add(cur.right, key, contains);
                } else {
                    cur.left = add(cur.left, key, contains);
                }
                return maintain(cur);
            }
        }

        public SBTreeNode maintain(SBTreeNode cur) {
            if (cur == null) {
                return null;
            }
            long leftSize = cur.left != null ? cur.left.size : 0;
            long leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            long leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            long rightSize = cur.right != null ? cur.right.size : 0;
            long rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            long rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            //LL
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        public SBTreeNode leftRotate(SBTreeNode cur) {
            if (cur == null) {
                return null;
            }
            long same = cur.all - (cur.right != null ? cur.right.all : 0) - (cur.left != null ? cur.left.all : 0);
            SBTreeNode rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.all = cur.all;
            rightNode.size = cur.size;
            cur.all = same + (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0);
            cur.size = 1 + (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0);
            return rightNode;
        }

        public SBTreeNode rightRotate(SBTreeNode cur) {
            if (cur == null) {
                return null;
            }
            long same = cur.all - (cur.left != null ? cur.left.all : 0) - (cur.right != null ? cur.right.all : 0);
            SBTreeNode leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            leftNode.all = cur.all;
            cur.size = (cur.right != null ? cur.right.size : 0) + (cur.left != null ? cur.left.size : 0) + 1;
            cur.all = same + (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0);
            return leftNode;
        }
    }

    public static void main(String[] args) {
        int[] nums = {-2,2,-2,-3,2,-2};
        int lower = -3;
        int upper = 1;
        CountofRangeSum2 countofRangeSum2 = new CountofRangeSum2();
        long l = countofRangeSum2.countRangeSum(nums, lower, upper);
        System.out.println(l);
    }
}
