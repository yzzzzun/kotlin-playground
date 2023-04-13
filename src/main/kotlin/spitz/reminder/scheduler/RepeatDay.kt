package spitz.reminder.scheduler

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RepeatDay(
    private val hour: Int,
    private val minute: Int,
    private vararg val days: DayOfWeek,
) : Scheduler() {
    private val isSent = hashMapOf<String, Boolean>()

    override fun isSend(now: Instant): Boolean {
        val dateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val nowDay = dateTime.dayOfWeek
        val nowHour = dateTime.hour
        val nowMinute = dateTime.minute
        val key = "$nowDay $nowHour:$nowMinute"

        if (isSent[key] == true) return true
        if (nowDay !in days) return true
        if (nowHour > hour) return true
        if (nowHour == hour && nowMinute > minute) return true
        isSent[key] = true
        return false
    }
}
