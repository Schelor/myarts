package arts2

// Longest Substring Without Repeating Characters
func LengthOfLongestSubstring(s string) int {
	left := 0                  // 左指针
	right := -1                // 右指针,初始值为-1,相当于我们在字符串的左边界的左侧，还没有开始移动
	ans := 0                   // 记录答案,即无重复字符的最长子串
	hash := make(map[byte]int) // 定义一个哈希表,存储不重复的字符
	n := len(s)
	for ; left < n; left++ {
		if left != 0 {
			delete(hash, s[left-1]) // 删除上一个字符
		}
		for right+1 < n && hash[s[right+1]] == 0 {
			hash[s[right+1]]++
			right++
		}
		ans = max(ans, right-left+1)
	}
	return ans
}

func max(x, y int) int {
	if x > y {
		return x
	}
	return y
}
