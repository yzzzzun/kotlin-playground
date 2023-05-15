package spitz.coroutine.continuationtask

class Task internal constructor(internal val run: (Continuation) -> Unit) {
    internal val continuation = Continuation(this)
    internal var isStarted = Stat.READY
    internal var isCompleted = Stat.READY
    internal var env: MutableMap<String, Any?> = hashMapOf()
}
