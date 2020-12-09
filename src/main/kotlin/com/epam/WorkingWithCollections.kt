package com.epam

data class Product(var id: Int, var name: String)

class WorkingWithCollections {
    private val _list: MutableList<Product> = ArrayList()

     val list: MutableList<Product>
        get() = _list

    init {
        _list.addAll(
            listOf(Product(1, "Product"), Product(2, "SubProduct"))
        )
    }

    fun add(product: Product) = _list.add(product)
}
