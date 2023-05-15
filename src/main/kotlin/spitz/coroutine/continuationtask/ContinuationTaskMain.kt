package spitz.coroutine.continuationtask

fun main() {
    val dispatcher = Dispatcher.FixedDispatcher(10)
    for (i in 0..5) {
        ContinuationTask(dispatcher, false) {
            when (it.step) {
                0 -> {
                    println("$i-0 ${Thread.currentThread().id}")
                    it.resume(1)
                }
                1 -> {
                    println("$i-1 ${Thread.currentThread().id}")
                    it.resume(2)
                }
                2 -> {
                    println("$i-2 ${Thread.currentThread().id}")
                    it.complete()
                }
            }
        }
    }
    dispatcher.join()
}
