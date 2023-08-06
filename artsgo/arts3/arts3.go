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
