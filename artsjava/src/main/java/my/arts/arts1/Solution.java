package my.arts.arts1;


public class Solution {

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {this.val = val;}
        ListNode(int val, ListNode next) {this.val = val;this.next = next;}

        public void print() {
            StringBuilder builder = new StringBuilder();
            ListNode p = this;
            while (p != null) {
                builder.append(p.val);
                if (p.next != null) {
                    builder.append(" -> ");
                }
                p = p.next;
            }
            System.out.println(builder);
        }
    }

    // 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 保存结果
        ListNode dummy = new ListNode();
        int carry = 0;
        ListNode p = dummy;
        while (l1 != null || l2 != null) {
            int a = (l1 != null) ? l1.val : 0;
            int b = (l2 != null) ? l2.val : 0;
            // 进位等于两个数字+上一次的进位相加除10
            // 相加结果等于两个数字+上一次的进位相加模10,并将相加结果写入到新结点中
            int sum = a + b + carry;
            p.next = new ListNode(sum % 10);
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next; // 链表1下一个数字
            }
            if (l2 != null) {
                l2 = l2.next; // 链表2下一个数字
            }
            p = p.next;
        }
        if (carry == 1) { // 如果最后一位产生了进位需要增加一个进位结点
            p.next = new ListNode(carry);
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode listNode = s.addTwoNumbers(l1, l2);
        listNode.print();
    }
}
