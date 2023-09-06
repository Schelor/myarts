<a name="unu45"></a>
## Algorithm
LeetCode: [5.Longest Palindromic Substring](https://leetcode.cn/problems/longest-palindromic-substring/)
<br />给你一个字符串 s，找到 s 中最长的回文子串。
<br />如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。<br />示例 1：
<br />输入：s = "babad" 输出："bab" 解释："aba" 同样是符合题意的答案。 
<br />示例 2：<br />输入：s = "cbbd" 输出："bb" 
<br />**分析**
<br />解法一，动态规划。
<br />定义 dp[i][j] 表示从字符串第 i 个字符到第 j 个字符这一段子串是否是回文串。由回文串的性质可以得知，回文串去掉一头一尾相同的字符以后，剩下的还是回文串。所以状态转移方程是 dp[i][j] = (s[i] == s[j]) && ((j-i < 3) || dp[i+1][j-1])，注意特殊的情况，j - i == 1 的时候，即只有 2 个字符的情况，只需要判断这 2 个字符是否相同即可。j - i == 2 的时候，即只有 3 个字符的情况，只需要判断除去中心以外对称的 2 个字符是否相等。每次循环动态维护保存最长回文串即可。时间复杂度 O(n^2)，空间复杂度 O(n^2)。
```go
func LongestPalindrome3(s string) string {
    res, dp := "", make([][]bool, len(s))
    for i := 0; i < len(s); i++ {
        dp[i] = make([]bool, len(s))
    }
    for i := len(s) - 1; i >= 0; i-- {
        for j := i; j < len(s); j++ {
            dp[i][j] = (s[i] == s[j]) && ((j-i < 3) || dp[i+1][j-1])
            if dp[i][j] && (res == "" || j-i+1 > len(res)) {
                res = s[i : j+1]
            }
        }
    }
    return res
}
```
注：这道题其实还是没有深刻理解！动态规划也是我的一个拦路虎！
<a name="aXcDc"></a>
## Review
原文链接：[The Problem With Logging](https://blog.codinghorror.com/the-problem-with-logging/)<br />这篇文章讨论了记日志那些事儿。<br />起因是：作者说在StackOverflow上，一个程序员描述了他认为的日志记录风格，以下是他的记录：
> INFO Level 信息级别
> The start and end of the method<br />方法的开始和结束
> The start and end of any major loops<br />任何主要循环的开始和结束
> The start of any major case/switch statements<br />任何主要案例/开关语句的开头
> DEBUG Level 调试级别
> Any parameters passed into the method<br />传递到方法中的任何参数
> Any row counts from result sets I retrieve<br />我检索的结果集中的任何行计数
> Any datarows that may contain suspicious data when being passed down to the method<br />传递到方法时可能包含可疑数据的任何数据行
> Any "generated" file paths, connection strings, or other values that could get mungled up when being "pieced together" by the environment.<br />任何“生成的”文件路径、连接字符串或其他值，这些值在被环境“拼凑”时可能会被弄乱。
> ERROR Level 错误级别
> Handled exceptions 已处理的异常
> Invalid login attempts (if security is an issue)<br />无效的登录尝试（如果存在安全性问题）
> Bad data that I have intercepted forreporting<br />我截获的不良数据报告
> FATAL Level 致命级别
> Unhandled exceptions. 未处理的异常。

本文作者认为：有点过渡！<br />本文总体上逻辑是：认同应该打日志，但是不能过度，因为

- 记日志意味着更多的代码，对正常的代码造成干扰，最终会是程序越来越混乱
- 记日志不是免费的，也有一定的性能损耗。对应一些复杂对象，这需要额外的时间。
- 如果要记录日志，那么日志就应该用起来。但是大多数都是无用的，这就是形成一个矛盾。日志应该被用起来
- 日志记录的太多，就越难找。

在日志记录方面，正确的答案不是“是的，总是，并且尽可能多”。抵制记录一切的趋势。从小而简单开始，只记录最明显和最严重的错误。仅添加（或理想情况下，注入）更多日志记录，仅当特定的、可验证的需求所证明时。<br />本文的有很多人参与讨论，并给出评论，其中有个朋友提到他自己的规则：
> FATAL Level 致命级别<br />Unrecoverable Errors (the application will end after this log)<br />不可恢复的错误（应用程序将在此日志后结束）
> ERROR Level 错误级别<br />Recoverable Errors (the application is not going to stop)<br />可恢复的错误（应用程序不会停止）
> NORMAL Level 正常级别<br />Here is the main trick. I like to show what is logged at this level to the end user in a way he can read what the application is doing. Is just like if the application could tell to the user what is doing.<br />这是主要技巧。我喜欢向最终用户展示在此级别记录的内容，以便最终用户可以读取应用程序正在执行的操作。就像应用程序可以告诉用户在做什么一样。
> Example: 例：<br />Reading application file …<br />正在读取应用程序文件...<br />… done. ...。<br />Parsing the application data …<br />正在解析应用程序数据...<br />… done. ...。<br />Found 3 items to process.<br />找到 3 个要处理的项目。<br />Processing item 1. 处理项目 1.
> You can not put here a lot of information, just the main things the application is doing, like if you write your own diary.<br />你不能在这里放很多信息，只是应用程序正在做的主要事情，比如你写自己的日记。
> HIGHER LEVELS 更高级别<br />Here I put as much info as i want. It can be excesive if you want. 99% of time the information will be filtered out because at run-time you have to work in NORMAL debug level.<br />在这里，我尽可能多地提供信息。如果你愿意，它可以是过度的。99% 的时间信息将被过滤掉，因为在运行时您必须在 NORMAL 调试级别工作。
> This is how I see logging, completely agree to the point that you should show to the user what you’re logging.<br />这就是我看待日志记录的方式，完全同意您应该向用户显示您正在记录的内容的观点。


以上
<a name="mkWwT"></a>
## Tip
这里分享一个在实际工程中，如何写一个更通用的策略工厂。<br />该技巧也记录到最近整理的一篇文章中。<br />**基于枚举与策略的双重分派**<br />如果是平台类型的系统，一般承载了多种业务，所以平台会抽象一些共性的模型，以及共性的流程，这些流程一般通过建模阶段识别出来，最终通过代码来固化。<br />随着平台业务方越来越多，一定会出现差异化的需求，因此共性的流程或处理逻辑不在满足差异化的业务需求。因此这种情况，一般平台的解决方式是开放扩展点出来，在一个通用的处理流程中，通过扩展点机制来加载不同的实现，类似为插件。这种解法很好分离了平台的共性逻辑与业务的差异化逻辑，同时满足了总体流程的统一性。<br />比如典型的下单类型，不同业务的下单逻辑有共性，也有差异性。往往共性逻辑占据80%，所以总体原则是尽可能的复用这部分共性逻辑，同时总体的下单流程通过抽象后也是统一的。<br />比如：订单检查 -> 订单保存 -> 后置处理（简化）对应的伪代码如下：
> check(); 
> save();
> postProcess();

在实际工程中，一种解决方案是：基于业务标识与策略代码的双重分派。<br />其核心逻辑就是通过哈希表来管理多种业务接口(interface)或服务(service)，业务接口或服务本身代表一层分离，因为不同业务的代码逻辑是需要解耦与分离的。<br />在第一层的分离基础上，再增加一层策略的分离。相同业务下，通过枚举或业务标识来定义不同的策略。这一层与上述提到的本质是一样的。<br />最后形成一个通用的策略工厂，统一注册，统一获取并分发(Dispatch)。示例代码如下
```java
public class StrategyFactory {


    // 通用的策略注册器
    private final static Map<Class<?>, Map<String, Object>> service_factory = new HashMap<>();

    /**
     * 查找接口实例
     *
     * @param serviceInterface 接口
     * @param bizCode          业务标识
     * @param <I>              I 是接口类型, T 是I的实现
     * @return
     */
    public static <I> I find(Class<I> serviceInterface, String bizCode) {
        Map<String, Object> group = service_factory.get(serviceInterface);
        Object service = group.get(bizCode);
        return (I) service; // 返回接口类型的实例
    }

    /**
     * 注册不同的接口实例
     *
     * @param serviceInterface 接口
     * @param bizCode          业务标识
     * @param instance         接口实例
     * @param <I>              I 是接口类型, T 是I的实现
     */
    public static <I, T extends I> void register(Class<I> serviceInterface, String bizCode, T instance) {
        Map<String, Object> group = service_factory.get(serviceInterface);
        if (group == null) {
            group = new HashMap<>();
            group.put(bizCode, instance);
            service_factory.put(serviceInterface, group);
            return;
        }
        group.put(bizCode, instance); 
    }
}
```
<a name="hytuu"></a>
## Share
原文地址：[https://www.youtube.com/watch?v=n3kNlFMXslo](https://www.youtube.com/watch?v=n3kNlFMXslo)<br />如何控制自己的自由时间？<br />其实，核心还是在于你认为：什么事情重要，什么事情是优先事项，如果很重要你就会花更多的时间去做。 哪里有什么自由的时间，一切都是选择！

