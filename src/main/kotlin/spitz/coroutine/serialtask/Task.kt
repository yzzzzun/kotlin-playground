package spitz.coroutine.serialtask

class Task internal constructor(
    internal val run: (Controller) -> Unit
) {
    internal var isStarted = Stat.READY
    internal var isCompleted = Stat.READY
    internal var result: Result<Any?>? = null
    internal var next: Task? = null
}
