package spitz.reminder.sender

import spitz.reminder.Item

class GmailSender(
    private val user: String,
    private val pw: String,
    private val sender: String,
    private val receiver: String,
) : Sender {

    override fun send(item: Item) {
        println("send gmail")
        println("$user, $pw, $sender, $receiver")
        println("[${item.title}]")
        println(item.content)
    }
}
