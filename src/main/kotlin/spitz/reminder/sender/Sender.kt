package spitz.reminder.sender

import spitz.reminder.Item

interface Sender {
    fun send(item: Item)
}
