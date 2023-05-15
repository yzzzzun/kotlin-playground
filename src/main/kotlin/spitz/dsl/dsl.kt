package spitz.dsl

class CoffeeShop {
    private val orders = mutableListOf<Order>()

    fun order(init: Order.() -> Unit) {
        val newOrder = Order().apply(init)
        orders.add(newOrder)
        println("New order created: $newOrder")
    }
}

class Order {
    var customerName: String? = null
    val items = mutableListOf<MenuItem>()

    fun item(name: String, init: MenuItem.() -> Unit) {
        val menuItem = MenuItem(name).apply(init)
        items.add(menuItem)
    }

    override fun toString(): String {
        return "Order(customerName=$customerName, items=$items)"
    }
}

class MenuItem(val name: String) {
    var quantity: Int = 1
    var price: Double = 0.0

    override fun toString(): String {
        return "MenuItem(name='$name', quantity=$quantity, price=$price)"
    }
}

fun main() {
    val coffeeShop = CoffeeShop()
    coffeeShop.order {
        customerName = "Alice"
        item("Latte") {
            quantity = 2
            price = 4.5
        }
        item("Croissant") {
            price = 3.0
        }
    }
}
