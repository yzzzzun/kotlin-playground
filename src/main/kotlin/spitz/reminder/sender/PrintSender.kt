package spitz.reminder.sender

import spitz.reminder.Item

class PrintSender : Sender {
    override fun send(item: Item) {
        println("Reminder")
        println("[${item.title}]")
        println(item.content)
    }
}
