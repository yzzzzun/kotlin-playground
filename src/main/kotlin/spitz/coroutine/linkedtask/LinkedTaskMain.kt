package spitz.coroutine.linkedtask

fun main() {
    val looper = EventLooper(Dispatcher.FixedDispatcher(10))
    for (i in 0..5) looper.linkedTask({
        println("$i-0 ${Thread.currentThread().id}")
        Thread.sleep(i * 100L)
        it.resume()
    }, {
        println("$i-1 ${Thread.currentThread().id}")
        Thread.sleep(i * 100L)
        it.resume()
    }, {
        println("$i-2 ${Thread.currentThread().id}")
        Thread.sleep(i * 100L)
        it.resume()
    })
    looper.launch()
    looper.join()
}

