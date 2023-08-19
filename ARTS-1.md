## Algorithm
LeetCode链接： [2.Add Two Numbers](https://leetcode.cn/problems/add-two-numbers/) <br>
给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
请你将两个数相加，并以相同形式返回一个表示和的链表。
你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
示例1：<br>
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1475094/1690618464324-8e1a0afe-3a04-4e64-891f-e8412b932417.png#averageHue=%23f4f4f4&clientId=ub28826d4-3dca-4&from=paste&id=ue6f23a6b&originHeight=342&originWidth=483&originalType=binary&ratio=2&rotation=0&showTitle=false&size=29531&status=done&style=none&taskId=u9f882b13-4e76-460c-b4e7-65b1395e489&title=)
> Input: l1 = [2,4,3], l2 = [5,6,4] 
> Output: [7,0,8] 
> Explanation: 342 + 465 = 807.


**分析**<br>
2个十进制数相加，按个位数，十位数对齐相加，逢十进一，根据题意，一个数字通过链表来逆序存储，从第一个节点开始，意味着已经按个位数，十位数两两对齐，因此可直接从第一个节点开始，同时遍历两个节点，然后取对应的数字相加，过程中通过一个进位变量来记录进位。可申请一个新的链表，把相加结果保存到新的链表结点中。
其中，关于两位对应位置数字相加的进位与结果的处理有一个小技巧，新进位等于两个数字+上一次的进位相加除10，加法结果等于两个数字+上一次的进位相加模10，这样计算可以简化代码的写法。
除此之外，涉及到链表结点的构建，一般可借助虚拟头结点来简化边界操作。
根据上述思路，对应的java代码如下：
```java

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

    // 测试用例
    public static void main(String[] args) {
        Solution s = new Solution();
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode listNode = s.addTwoNumbers(l1, l2);
        listNode.print();
    }
}
```

- 时间复杂度: O(m) 或 O(n), 其中m,n分别为两个链表的长度
- 空间复杂度：O(1), 返回结果不计入空间复杂度
## Review
**原文链接**： [The Twelve-Factor App](https://12factor.net/)<br>
**解读**<br>
我近期主要是围绕「软件设计」专题在学习，资料来源主要是耗子叔的极客时间-左耳听风专栏中的关于软件设计的文章，学习路线大致是：一、学习领域驱动相关的书，二、同时看一看文章中提到的相关文章，其中一篇就：十二要素应用程序。十二要素设计是来自全世界的开发人员总结的Saas应用开发的经验和智慧，他们分享了在现代软件开发过程中发现的一些系统性问题，同时提供了一些通用的解决方案，我这里谈谈我对这些设计要素的理解与认知。
**整体认知上我非常震撼**，这篇文章的贡献者是来自世界范围内的开发者，参与过数以百计的应用程序的开发和部署，他们会使用各式各样的技术来构建软件，例如Perl, Rails, Django，PHP等。反思自己在工作中都只用Java，经历的几个公司的技术体系都是Java，感觉自己的技术太单一化，这也是我下定决心要学习多元化技术的原因，目前是打算学习Golang技术体系。
**十二要素摘要如下：**

1. **构建Saas应用只有一份代码基准库**
2. **显示申明依赖关系**
3. **推荐将应用的配置存储于 _环境变量_ 中**
4. **将后端使用的数据库，消息队列，缓存系统作为附加资源，并与保持松耦合**
5. **严格分离构建与运行**
6. **软件服务进程应该保持无状态**
7. **通过端口对外提供服务**
8. **通过进程维度的伸缩来支持高并发（实例扩容）**
9. **快速启动，优雅停机，最大化健壮性**
10. **尽可能的保持开发，预发布，线上环境相同**
11. **把日志当事件流**
12. **后台管理任务当做一次性的进程处理（意味着开发各种运维工具）**

**我认为我们日常的开发多数还是遵循了上述设计要素**，但1、3、12没有做到。
**一份代码库（1）：**特别是在国内的互联网公司，多数都采用了前后端分离，各自做各自职责范围的事情，导致都是在做软件应用的局部，这种模式在管理上具备较多优势，但是却限制了开发者个人的综合成长，我们的代码库是分离的，我们将一个Saas应用，认为的拆分为前端后端，各自有自己的代码库，我们只能相互依赖才能构建一个完整的软件应用。
**将配置存储在环境变量中（3）：**这一点我是保持怀疑的，我们都是采用dev,stage,production来管理配置，文章说这种方式在于无法轻易扩展，如果出现新的环境则对应的配置文件会越来越多，导致很难维护，影响效率。但是依赖环境变量具备一定的操作成本，特别是配置变量较多的情况下。所以这个设计要是要对应简单的应用，适合一些小工具类应用。
**后台管理任务当做一次性的进程处理（12）：** 这种设计很有启发，可以指导我们多使用脚本语言来开发多种工具，提高运维效率，同时可以拓展自己的技术边界，将其他技术用起来。

以上
## Tip
**如何用vscode debug go**
我日常工作中的技术栈是Java体系，但是对Golang很感兴趣，因此过去学了一些基础。在工作无法运用，因此计划日常刷LeetCode时用golang来写。之前用了一段时间goland, 个人体会golang编程最好的ide还是goland, 由于暂时不想花钱购买，日常用的goland 因激活问题导致无法使用。因此只能切换到vsvode+go插件（[The VS Code Go extension](https://marketplace.visualstudio.com/items?itemName=golang.go)），插件安装还是比较简单的，但是默认情况下无法debug代码。因此今天研究了一下如果在vscode中debug go。
解决思路：google 搜索，然后看了2篇文章，其中是golang的[官方文档](https://github.com/golang/vscode-go/wiki/debugging)，另一篇是搜索的第一个结果：
[How To Debug Go Code with Visual Studio Code](https://www.digitalocean.com/community/tutorials/debugging-go-code-with-visual-studio-code#step-2-debugging-with-breakpoints)，其中都提到，需要安装一个工具叫：delve, 安装文档比较简单，MacOS上用brew即可：`brew install delve`
安装好以后，在vscode中的，在源代码文件的某行最左侧打上端点后，切换到：Run And Debug View，点击运行即可，用默认的配置即可，不需要做其他的配置。
## Share
**原文链接**：[The difference between healthy and unhealthy love | Katie Hood](https://www.youtube.com/watch?v=ON4iy8hq2hM&list=WL&index=1)
这是一个Youtube的视频，来自Ted的演讲，作者分享观点如下：
生活中存在很多以爱的名义造成的伤害，甚至是情感虐待与暴力，其中主要是有四种标志：

- 具有太高强度的关系
- 孤立朋友与家人
- 极度的嫉妒
- 轻视
- 易变

作者认为：爱是我们人类的本能与情感，更好的爱是可以培养的技能，随着时间的推移，这种爱的能力会不断提高与改善。
