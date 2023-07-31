package my.arts.arts2;


import java.util.HashSet;

public class Solution {

    // 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> hash = new HashSet<>(); // 存储不重复的字符
        int n = s.length(); // 字符串长度
        int left = 0; // 左指针,从第一个下标=0开始
        int right = -1; // 右指针,初始值为-1,相当于我们在字符串的左边界的左侧，还没有开始移动
        int ans = 0; // 记录答案,即无重复字符的最长子串
        for (; left < n; left++) {
            if (left != 0) {
                // 左指针移动一次,删除一个字符串,缩小窗口
                hash.remove(s.charAt(left - 1));
            }
            // 如果不存在重复字符则不断向右移动右指针
            while (right + 1 < n && !hash.contains(s.charAt(right + 1))) {
                hash.add(s.charAt(right + 1));
                right++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int ans = s.lengthOfLongestSubstring("bbbbb");
        System.out.println("ans=" + ans);
    }
}
