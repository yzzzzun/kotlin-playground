package collection

import kotlin.random.Random
import kotlin.system.measureTimeMillis

//class PrimeAccessRepository(
//    private val primeAccessList: PrimeAccessList
//) {
//    fun isOnAllowList(userId: String): Boolean = primeAccessList.entries.find { it.userId==userId }?.allowList?:false
//    fun isOnDenyList(userId: String): Boolean = primeAccessList.entries.find { it.userId == userId }?.denyList?:false
//}

class PrimeAccessRepository(
    private val primeAccessList: PrimeAccessList
) {
    val entries = primeAccessList.entries.associateBy { it.userId }
    fun isOnAllowList(userId: String): Boolean = entries[userId]?.allowList?:false
    fun isOnDenyList(userId: String): Boolean = entries[userId]?.denyList?:false
}


class PrimeAccessList(
    val entries: List<PrimeAccessEntry>
)

class PrimeAccessEntry(
    val userId: String,
    val allowList: Boolean,
    val denyList: Boolean,
)

fun main() {
    val entries = List(200_000) {
        PrimeAccessEntry(
            userId = it.toString(),
            allowList = Random.nextBoolean(),
            denyList = Random.nextBoolean()
        )
    }.shuffled()
    val accessList = PrimeAccessList(entries)

    val repo: PrimeAccessRepository
    measureTimeMillis {
        repo = PrimeAccessRepository(accessList)
    }.also { println("Class creation took $it ms") }

    measureTimeMillis {
        for (userId in 1L..10_000L) {
            repo.isOnAllowList(userId.toString())
        }
    }.also { println("Operation took $it ms") }

    measureTimeMillis {
        for (userId in 1L..10_000L) {
            repo.isOnDenyList(userId.toString())
        }
    }.also { println("Operation took $it ms") }
}
