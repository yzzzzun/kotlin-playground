package spitz.coroutine.serialtask

fun main() {
    val dispatcher = Dispatcher.FixedDispatcher(10)
    for (i in 0..5) {
        val looper = SerialTask(dispatcher, {
            println("$i-0 ${Thread.currentThread().id}")
            it.resume()
        }, {
            println("$i-1 ${Thread.currentThread().id}")
            it.resume()
        }, {
            println("$i-2 ${Thread.currentThread().id}")
            it.resume()
        })
        looper.launch()
    }
    dispatcher.join()
}

