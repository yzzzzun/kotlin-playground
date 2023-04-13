package spitz.reminder

import kotlinx.datetime.Instant
import spitz.reminder.scheduler.Scheduler


class Item(
    var title: String,
    var content: String,
) {

    private val schedules = hashSetOf<Scheduler>()

    fun addSchedule(vararg scheduler: Scheduler) {
        schedules += scheduler
    }

    fun send(now: Instant) {
        schedules.forEach {
            it.send(this, now)
        }
    }
}
