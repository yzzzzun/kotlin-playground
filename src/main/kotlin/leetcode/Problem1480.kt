package leetcode

class Problem1480 {
    fun runningSum(nums: IntArray): IntArray {
        val result: IntArray = IntArray(nums.size);
        for (idx in nums.indices) {
            var sum = 0
            for (j in 0..idx) {
                sum += nums[j]
            }
            result[idx] = sum
        }
        return result
    }
}
