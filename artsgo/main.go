package main

import (
	"artsgo/arts2"
)

func main() {
	//fmt.Println("Hello,Arts")
	//arts1.AddTwoNumbers(&arts1.ListNode{Val: 100}, &arts1.ListNode{Val: 1})
	ans := arts2.LengthOfLongestSubstring("abcabcbb")
	println("ans=", ans)
}
