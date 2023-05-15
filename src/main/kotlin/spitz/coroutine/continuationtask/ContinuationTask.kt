package spitz.coroutine.continuationtask

class ContinuationTask(
    private val dispatcher: Dispatcher,
    isLazy: Boolean,
    block: (Continuation) -> Unit
) : Runnable {
    private val task = Task(block)

    init {
        if (!isLazy) launch()
    }

    fun launch() {
        dispatcher.start(this)
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(5)
            if (task.isCompleted == Stat.MARK) break
            if (task.isStarted == Stat.READY) {
                task.isStarted = Stat.CONFIRM
                task.run(task.continuation)
            }
        }
        task.continuation.failed?.let { throw it }
    }
}
