package spitz.coroutine.serialtask

class SerialTask(
    private val dispatcher: Dispatcher, vararg blocks: (Controller) -> Unit
) : Runnable {
    private var task: Task

    init {
        if (blocks.isEmpty()) throw Throwable("no blocks")
        var prev = Task(blocks[0])
        task = prev
        prev.isStarted = Stat.MARK
        for (i in 1..blocks.lastIndex) {
            val task = Task(blocks[i])
            prev.next = task
            prev = task
        }
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(5)
            if (task.isCompleted == Stat.MARK) {
                task.next?.let {
                    it.isStarted = Stat.MARK
                    task = it
                }
            }
            if (task.isStarted == Stat.MARK) {
                task.run(Controller(task))
                task.isStarted = Stat.CONFIRM
            }
        }
    }

    fun launch() {
        dispatcher.start(this)
    }
}
