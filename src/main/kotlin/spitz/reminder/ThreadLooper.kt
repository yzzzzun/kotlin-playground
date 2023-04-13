package spitz.reminder

import kotlinx.datetime.Clock

val threadLooper = Looper({
    val thread = Thread {
        while (it.isRunning && !Thread.currentThread().isInterrupted) {
            val now = Clock.System.now()
            Looper.users.forEach { it.send(now) }
            Thread.sleep(1000)
        }
    }
    if (!thread.isAlive) {
        thread.start()
    }
}, {})
