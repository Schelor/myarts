package my.arts.arts3;


import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int mod = (m + n) % 2;
        int half = (m + n) / 2;
        // 声明一个大顶堆，一个小顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(mod == 0 ? half : half + 1, Comparator.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(half);

        
    }

    public static void main(String[] args) {
        Solution s = new Solution();


    }
}
