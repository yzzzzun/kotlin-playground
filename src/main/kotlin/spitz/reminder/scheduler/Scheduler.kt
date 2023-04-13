package spitz.reminder.scheduler

import kotlinx.datetime.Instant
import spitz.reminder.Item
import spitz.reminder.sender.Sender

abstract class Scheduler {

    private val senders = hashSetOf<Sender>()

    fun addSender(vararg sender: Sender) {
        senders += sender
    }

    fun send(item: Item, now: Instant) {
        if (!isSend(now)) senders.forEach { it.send(item) }
    }

    protected abstract fun isSend(now: Instant): Boolean
}
