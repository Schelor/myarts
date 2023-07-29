package main

import (
	"artsgo/arts1"
	"fmt"
)

func main() {
	fmt.Println("Hello,Arts")
	arts1.AddTwoNumbers(&arts1.ListNode{Val: 100}, &arts1.ListNode{Val: 1})
}
