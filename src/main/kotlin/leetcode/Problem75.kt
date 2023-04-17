package leetcode

class Problem75 {
    fun sortColors(nums: IntArray): Unit {

        var tmp: Int = 0
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                if (nums[i] > nums[j]) {
                    tmp = nums[i]
                    nums[i] = nums[j]
                    nums[j] = tmp
                }
            }
        }
    }
}
