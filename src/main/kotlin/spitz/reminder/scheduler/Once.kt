package spitz.reminder.scheduler

import kotlinx.datetime.Instant

class Once(
    private val at: Instant,
) : Scheduler() {
    private var isSent = false
    override fun isSend(now: Instant): Boolean {
        return if (!isSent && at <= now) {
            isSent = true
            false
        } else true
    }
}
