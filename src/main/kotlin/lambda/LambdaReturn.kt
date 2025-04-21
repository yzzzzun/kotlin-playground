package lambda

fun main(){
    onUserChanged { user ->
        if(user == null) return@onUserChanged
        cheerUser(user)
    }
}

fun onUserChanged(any: (Any) -> Unit) {
    TODO("Not yet implemented")
}

fun cheerUser(user: Any) {
    TODO("Not yet implemented")
}

