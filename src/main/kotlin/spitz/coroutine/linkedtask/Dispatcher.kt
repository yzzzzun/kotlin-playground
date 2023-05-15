package spitz.coroutine.linkedtask

import java.util.concurrent.Executors

interface Dispatcher {
    fun start(looper: EventLooper)
    fun join()
    class FixedDispatcher(private val threads: Int) : Dispatcher {
        private val executor = Executors.newFixedThreadPool(threads)
        override fun start(looper: EventLooper) {
            for (i in 1..threads) executor.execute(looper)
        }

        override fun join() {
            while (!executor.isShutdown) {
            }
        }
    }
}
