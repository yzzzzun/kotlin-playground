package spitz.coroutine.linkedtask

import java.util.*


class EventLooper(
    private val dispatcher: Dispatcher,
) : Runnable {
    private val tasks: Queue<Task> = LinkedList()
    private var currTask: Task? = null
    fun linkedTask(vararg blocks: (Controller) -> Unit) {
        if (blocks.isEmpty()) return
        synchronized(tasks) {
            var prev = Task(blocks[0])
            tasks.add(prev)
            for (i in 1..blocks.lastIndex) {
                val task = Task(blocks[i])
                prev.next = task
                prev = task
            }
        }
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(16)
            synchronized(this) {
                if (currTask != null) {
                    currTask?.let { curr ->
                        if (curr.isCompleted) {
                            curr.next?.let { tasks.add(it) }
                            currTask = null
                        }
                    }
                } else tasks.poll()?.let {
                    currTask = it
                    it.run(Controller(it))
                }
            }
        }
    }

    fun launch() {
        dispatcher.start(this)
    }

    fun join() {
        dispatcher.join()
    }
}
