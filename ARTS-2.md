<a name="KEC8j"></a>
## Algorithm
LeetCode链接: [3.Longest Substring Without Repeating Characters](https://leetcode.cn/problems/longest-substring-without-repeating-characters/)<br />Given a string s, find the length of the longest substring without repeating characters.<br />Example 1:<br />Input: s = "abcabcbb" <br />Output: 3 <br />Explanation: The answer is "abc", with the length of 3.

**分析**<br />**滑动窗口+哈希表**<br />
使用2个指针来表示一个子串，移动两个指针时形成一个滑动窗口，滑动窗口的右边界不断的右移，只要没有重复的字符，就持续向右扩大窗口边界。一旦出现了重复字符，就需要缩小左边界，即向右移动左指针，直到重复的字符移出了左边界，即删除重复的字符。然后继续移动滑动窗口的右边界。以此类推，每次移动需要计算当前左右指针之间的长度并记录下来，判断是否需要更新最大长度。<br />如何判断是否存在重复字符呢？可以用一个哈希表<br />**java代码如下：**
```java
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

```
**golang代码如下：**
```go
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

```
<a name="rDY8Z"></a>
## Review
原文链接：[Avoid Over Engineering](https://medium.com/@rdsubhas/10-modern-software-engineering-mistakes-bc67fbef4fc8)<br />**解读**<br />本文作者有15年的开发经验，文章讨论了现代软件设计中常见的过度设计（over-engineer），个人整理并解读如下：<br />**1）一味的对业务逻辑做分组和概括, 因为业务需求永远是有差异的，而不会趋同（**group and generalize logic as much as possible）<br />意思是让新来的业务需求都落到共性的业务逻辑中，如果不满足，就迭代系统中存在的共性逻辑。这种设计的初衷是为了尽量复用业务逻辑，但是这种最终会让共享逻辑越来越重，以至于难以修改，最终不得不重写。作者认为应该按业务来隔离代码，隔离逻辑，允许少量的共享逻辑，如图所示：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/1475094/1690791059379-c6447ae8-4d2d-4de9-9ed0-463c115e088a.png#averageHue=%234ca4d1&clientId=ufd0044ce-0ab7-4&from=paste&height=224&id=ufd1bf843&originHeight=448&originWidth=1396&originalType=binary&ratio=2&rotation=0&showTitle=false&size=316226&status=done&style=none&taskId=uff32f81c-760c-4a54-baf6-5268f5ea991&title=&width=698)<br />这种可以比较灵活的响应新的需求。这种设计需要有边界限制，有架构限制，有职责限制，不然很容易形成重复。<br />**2）一切都使用泛型**<br />泛型的数据库适配器，泛型的数据库查询，泛型参数，泛型构建器，泛型请求... <br />作者认为这种是一种错误的抽象，这种抽象往往存在过期时间，总有一天无法满足业务需求。相反，应该允许重复，因为系统中出现越来越多相似的代码后，才会出现更好的抽象共享，意思是：更多的重复可以重构为更通用的抽象，这个时候的抽象才是稳定的。<br />**3）使用浅包装层**<br />包装层设计是一种例外而不是标准，特别是对于一些好的类库，不要为了包装而包装，因为包装显得多余。<br />4）**过度热心综合征（Overzealous Adopter Syndrome).  **按中文来理解作者的意思，我觉得可能是：「手里有锤子看什么都是钉子」，因为这里的中文直译基本看不出是啥意思。他这里举例有：

- 发现有 if 语句，上策略模式
- 有一个地方可以使用DSL，则到处都可以用
- 测试代码中处处都是Mock
- 元编程（Metaprogramming）很酷，得用起来

5）**错误的预估。**对于业务与实现采用错误的预估，这种是灾难性的过度设计，因为这是再做错误的事情，相当于埋坑！

以上
<a name="Sm4Qa"></a>
## Tip
**IDEA 工具使用小技巧**<br />使用新版的IDEA CE，切换新工程时，默认会使用Tab的形式打开窗口，这个功能叫Merge All Project Window，默认情况下会自动开启，可以在Tab 上右键：Move Tab To New Window，这样可以切换为独立的窗口，减少干扰。

**开发者使用Google的搜索技巧**<br />**原文链接：**<br />[**https://www.makeuseof.com/21-tips-and-tricks-to-master-the-art-of-googling-as-a-developer/**](https://www.makeuseof.com/21-tips-and-tricks-to-master-the-art-of-googling-as-a-developer/)<br />1）使用`-`操作符排除搜索结果
> Best backend development courses -Django

2) 使用站点内关键词`site:`在某站点内指定关键词搜索
> site:makeuseof.com Python

3) 使用术语定义关键词`define:` 查询搜索词的术语定义，即查询该词是什么定义
> define:inheritance in oops

4) 使用双引号`“”`搜索精确的短语
> "ddd programming"

5) 移除不必要的搜索词<br />6) 使用文件类型关键词`filetype:xx`搜索指定文件类型
> Hello World Program filetype:go

7) 使用`OR`操作符来组合搜索
> Python OR JavaScript tutorial 

8) 使用`related:`关键字搜索与关键词的相关站点
> related:leetcode.com 查询与leetcode.com相关的站点

9) 使用`*`操作符来替换缺少的单词（用于想不起有些词的情况）
> * Object Model in JavaScript

10) 使用`location:xx`关键词来过滤搜索结果的地址
> Python Developer Meetup location:Beijing

11) 使用精确的关键词<br />12）使用`before:` `after:`关键词来限制日期范围
> Advantages of PHP after:2020-01-01

13) 使用Google来执行数学计算
> log 10 + log 100

14) 使用波浪号运算符 (~) 搜索与给定术语相似的术语<br />15) 使用 # 搜索主题标签
> #100DaysOfCode

16) 使用`vs`关键词来比较搜索词的差异
> java vs go


<a name="BYV4U"></a>
## Share
**前言**<br />最近打算重新回顾一下数据库范式设计，之前在大学里学过，在日常工作中做数据库设计也是按业务直接做，基本是按直觉或经验来设计的。现阶段打算重新学习一些理论层面的内容，从方法论维度来审视过去，改善未来。审视过去的做法是否是达到最佳实践，对于未来，是否有改善的设计方法。<br />总体上，看来几篇文章，英文中文都看过，目前英文水平还不够，需要结合中文来补充输入。文章地址如下：

- [https://www.freecodecamp.org/news/database-normalization-1nf-2nf-3nf-table-examples/](https://www.freecodecamp.org/news/database-normalization-1nf-2nf-3nf-table-examples/)

**什么是数据库范式**<br />数据库范式（normalization form，简称NF) 是一种数据库设计规范或设计标准，用以减少数据冗余，提高数据完整性。取决于不同的场景与需求，目前在理论层面存在多种范式，如第一范式（1NF），第二范式（2NF），第三范式（NF), BCNF等，在实际工程中，多数只用到前三种。<br />**1NF： 数据库表中的任何字段属性都是原子的，不可再分的，同时存在一个主键属性**<br />1NF是数据库设计的基本要求，但是随着DDD的设计理念的流行，按照领域模型的概念来设计对应的数据库表，有时候模型的属性可能会采用一个聚合的数据，例如采用一个JSON数据来存在到某一个列中，此时该属性可能不满足1NF。<br />**2NF：满足1NF，数据库表里的非主属性完全依赖主属性（主键）**<br />一个表中不能存在2个主属性，属性具有业务或语义相关性，这些相关属性都关联一个主键，这样形成一个整体和内聚不相关的属性不应该放在一起，这样形成隔离。<br />**3NF：满足2NF，数据库表的非主属性之间不能存在传递依赖**<br />意味着，任意字段不能由其他字段派生出来，要求字段没有冗余，即不存在传递依赖。例如
> 用户表：用户ID，用户名称
> 订单表：订单ID, 用户ID, 用户名称  -> 不符合3NF，因为用户名称是通过用户ID派生出来的，用户名称属性可通过 订单到用ID传递依赖到

3NF消除了冗余，但是带来了额外的复杂度，比如额外的查询。在实际工程中，有时候会通过冗余来降低额外的查询，即空间换时间的算法思想。<br />可见3NF也不是绝对的标准，一切都要看场景（It Depends!)

<br />



