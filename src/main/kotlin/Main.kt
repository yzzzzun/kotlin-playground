import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.plus
import spitz.reminder.*
import spitz.reminder.scheduler.Once
import spitz.reminder.scheduler.RepeatDay
import spitz.reminder.scheduler.Scheduler
import spitz.reminder.sender.GmailSender
import spitz.reminder.sender.PrintSender

fun main(args: Array<String>) {
    threadLooper.start()
    val user = User("test")

    do {
        println("add Item")
        val item = Item(read("title: "), read("content: "))
        readN("add Schedulers", "1.once 2.repeatDay", "1", "2") {
            val scheduler = getScheduler(it)
            setSender(scheduler)
            item.addSchedule(scheduler)
        }
        user.addItem(item)
    } while (true)
    while (threadLooper.isRunning) {
    }
}

private fun setSender(scheduler: Scheduler) {
    readN("add Sender", "1.print 2.gmail", "1", "2") {
        scheduler.addSender(
            when (it) {
                "1" -> PrintSender()
                "2" -> GmailSender(read("user:"), read("pw:"), read("sender:"), read("receiver:"))
                else -> throw Throwable("")
            }
        )
    }
}

private fun getScheduler(it: String): Scheduler {
    val scheduler = when (it) {
        "1" -> getOnce()
        "2" -> getRepeatDay()
        else -> throw Throwable()
    }
    return scheduler
}


fun getOnce(): Scheduler {
    println("once from now")
    println("unit:")
    val unit: DateTimeUnit.TimeBased = when (read("1.hours 2.minutes 3.seconds", "1", "2", "3")) {
        "1" -> DateTimeUnit.HOUR
        "2" -> DateTimeUnit.MINUTE
        "3" -> DateTimeUnit.SECOND
        else -> throw Throwable("")
    }
    val count = read("count(int):") { it.toIntOrNull() != null }.toInt()
    return Once(Clock.System.now().plus(count, unit))
}

fun getRepeatDay(): Scheduler {
    println("repeat day")
    val time = read("time(hh:mm):") { """^\d{2}:\d{2}$""".toRegex().matches(it) }.split(':').map { it.toInt() }
    return RepeatDay(time[0], time[1], *read("days(mon,tue,wed,thu,fri,sat,sun):") {
        it.split(',').all { el -> "mon,tue,wed,thu,fri,sat,sun".indexOf(el.trim()) != -1 }
    }.split(',').map {
        when (it) {
            "mon" -> DayOfWeek.MONDAY
            "tue" -> DayOfWeek.TUESDAY
            "wed" -> DayOfWeek.WEDNESDAY
            "thu" -> DayOfWeek.THURSDAY
            "fri" -> DayOfWeek.FRIDAY
            "sat" -> DayOfWeek.SATURDAY
            "sun" -> DayOfWeek.SUNDAY
            else -> throw Throwable("")
        }
    }.toTypedArray())
}
