<a name="KEC8j"></a>
## Algorithm
LeetCode链接：[Median of Two Sorted Arrays](https://leetcode.cn/problems/median-of-two-sorted-arrays/)<br />Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.<br />The overall run time complexity should be O(log (m+n)).<br />Example 1:<br />Input: nums1 = [1,3], nums2 = [2] <br />Output: 2.00000 <br />Explanation: merged array = [1,2,3] and median is 2.

Example 2:<br />Input: nums1 = [1,2], nums2 = [3,4] <br />Output: 2.50000 <br />Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5. 

**分析**<br />题目要求，整体的时间复杂度为O(log (m+n)). <br />一种很容易想到的思路是，将2个有序数组做合并，类似归并排序中的合并，但是该合并算法的时间复杂度为：O(m) 或 O(n)，不满足题目要求。<br />不过这里是做算法逻辑的训练，因此先尝试用这种算法解题<br />**合并有序数组后取中位数**
```go
package arts3

// 合并有序数组查找中位数
func FindMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	m, n := len(nums1), len(nums2)
	merge := make([]int, (m + n))
	i, j, k := 0, 0, 0 // i,j 分别是两个数组的下标,k为合并数组的下标
	for i < m && j < n {
		if nums1[i] < nums2[j] {
			merge[k] = nums1[i]
			k++
			i++
		} else {
			merge[k] = nums2[j]
			k++
			j++
		}

	}
	for i < m { // num1还未遍历完成
		merge[k] = nums1[i]
		k++
		i++
	}
	for j < n { // num2还未遍历完成
		merge[k] = nums2[j]
		k++
		j++
	}
	mod, mid := len(merge)%2, len(merge)/2
	if mod == 0 { // 新数组大小为偶数，中位数为中间2个数之和/2
		left, right := mid-1, mid
		return float64(merge[left]+merge[right]) / 2
	}
	return float64(merge[mid])

}

```

- 时间复杂度：O(m) 或 O(n)
- 空间复杂度：O(m+n)

看了LeetCode官方的题解，用二分法思路，我表示这种解法暂时看不懂<br />后续再来重看！
<a name="Mg9M1"></a>
## Review
原文链接：[How To Design A Good API and Why it Matters - Joshua Bloch](https://www.infoq.com/presentations/effective-api-design/)<br />文章来自Infoq，记录的2006年的JavaPolis大会上，**Joshua Bloch** 演讲的<br />「How to Design a Good API & Why it Matters」。<br />演讲内容的PDF目前互联网上可随意搜索到，我尝试给出自己的整理与解读。<br />**Joshua**老师主要分享了他设计新版Java集合框架API的思考，其中谈到了类、方法、异常、重构等方面的设计方法。<br />内容比较多，我将原文翻译到个人的另一篇文章中「[如何设计好的API](https://www.yuque.com/scherrer/architect/kq45w8os2tx1tfsb)」<br />值得多几次。
<a name="Sm4Qa"></a>
## Tip
**一种拟人化的对象建模技巧**<br />当我们采用面向对象的建模范式后，可以将某对象做拟人化，即认为对象是具有自主意识，自主行为的人，对象他自己知道自己该做什么。 这种设计可以避免对象拥有不合理的行为或逻辑。特别是采用DDD的建模模式时，我们对现实世界做模型抽象时，更容易设计合理的模型。<br />最近读《解构领域驱动设计》一书，其中有一个案例，与我日常做代码开发中的模型设计的思路如出一辙，用该案例来描述结构化建模，对象化建模的对比。<br />想象一个场景：
> 一个顾客在超市购物，选好某个商品后，加入到购物车，然后走到收营员那里做结账。

结构化建模其对应的就是结构化编程范式，其核心就是将数据与算法的分离，自顶向下的采用一层层的结构块（主要是函数与结构体）来协作并构建应用程序。<br />例如上述场景采用Go语言+结构化编程的示例代码如下：
```go
// 定义钱包结构体
type Wallet struct {
	money int
}

// 定义顾客结构体
type Customer struct {
	id     int
	name   string
	wallet Wallet
}

// 定义收营员
type Cashier struct {
}

// 收银员收费
// 这种逻辑的现实语义为：收营员把顾客的钱包拿到，然后自己取钱
func (staff Cashier) charge(customer Customer, expense int) {
	// 获取钱包，然后减去本次花费
	w := customer.wallet
	w.money -= expense
	
}
```
注意，采用面向对象的编程语言仍然能写出结构化的编程逻辑：即将数据与行为分开的编码方式，示例代码如下：
```java
class Wallet {
    int money;
}
class Customer {
    int id;
    String name;
    Wallet wallet;
    public Wallet getWallet() {
        return wallet;
    }
}

class Cashier {

    // 收营员收费
    // 这种逻辑的现实语义为：收营员把顾客的钱包拿到，然后自己取钱
    void charge(Customer customer, int expense) {
        Wallet wallet = customer.getWallet();
        wallet.money -= expense;

    }
}
```
模型是对现实世界的抽象，上述建模方式是与现实世界相违背的，因为顾客很少会自己把钱包给收银员让他自己取，这种是不安全的，也是不符合的实际的。在实际的工程中，采用这种操作数据编程逻辑却是最流行的，因为代码简单。<br />**但是这种方式不适合复杂度高的业务，而且随着操作的数据越来越多，最终导致代码越来越混合，无法理解，无法维护。**

对象化建模范式对应的就是面向对象编程范式，其核心是将数据与算法封装在同一个主体中，这个主体被称为对象。对象之间通过消息来协作，通过对象与对象的协作来构建应用程序。上述场景采用对象化建模后，让对象视作“活的，有生命的”的物体，对应的编程模型如下：
```java
class Wallet {
    int money;
    // 钱包是有生命的，它自己知道如何支出
    public void expend(int money) {
        this.money -= money;
    }
}
class Customer {
    int id;
    String name;
    Wallet wallet;
    // 顾客自然也是知道是自己来支付
    // 但是顾客有一个钱包，直接交给钱包来支出，顾客就不用自己从钱包拿钱了
    public void pay(int expense) {
        this.wallet.expend(expense);
    }
}

class Cashier {
    // 收营员收费
    // 这种逻辑的现实语义为：收营员把顾客的钱包拿到，然后自己取钱
    void charge(Customer customer, int expense) {
        // 顾客自己来支付
        customer.pay(expense);
    }
}
```
可以看到，将对象视作有生命的物体后，就会各司其职，每个对象有自己的「合理」的行为。这里可能与现实世界有点违背，现实世界中钱包是没有生命的，顾客支付时还是自己从钱包里取钱。但是面向对象的程序世界是基于现实世界的抽象，是虚拟的，是高度概括的，是概念的，是允许一切对象都是活的。因此当我们采用拟人化的对象设计后，会让对象活起来，他有自己的财产（数据），有自己的行为，进而有自己的职责，即它也有自己的权利与义务。
<a name="BYV4U"></a>
## Share
原文链接：[https://www.youtube.com/watch?v=y9Trdafp83U](https://www.youtube.com/watch?v=y9Trdafp83U)<br />本文来自Ted的一个演讲：生活中比幸福更重要的事<br />作者提到，生活中幸福可能是短暂的，可能是求而不得让人不开心，可能是不停的在追求幸福，应为得到了后幸福感就会减少，然后开始继续追求。<br />这种追求的过程反而让人不幸福，难道人生就是这样追求的过程吗？<br />作者说：不是的，寻找有意义的人生比幸福更重要，就是说：意义让人生变得不一样。<br />意义是什么？意义来源于归属和献身高于自身的事物<br />通过研究大量的心理学，神经学，哲学，作者找到了有意义的人生的四个支柱：

- 归属感，这是一种超越自己的内在价值，同时也是对别人价值的认可
- 目标感，目标的意义是在于所得到的，而是付出的
- 超然感，我理解这是一种心流状态，让人可以忘我的投入
- 故事感，就是学会讲故事，将自己的生活作为故事来讲述，你可以是主角，你决定了如何演绎，如何讲好故事（如何让自己过得好）

所以，这对我很有启发，我过去总是认为幸福感是生活的全部，我发现不是。<br />意义才是，因为找到了人生的意义，这些是不变的，而幸福来得快也走得快，是时常变化的。我要追求那些永恒不变的。



