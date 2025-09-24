package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

    /*
     - add new product to empty inventory (sku, name, qty, price) - success [x]
     - add new product to an inventory with some elements - success [x]
     - add existing product - failure [x]
     - remove existing product - success
     - remove not existing product - failure
     - update quantity for an exisiting product - success
     - remove quantity for an exisiting product - failure
     - compute total value of the inventory - empty
     - compute total value of the inventory - some items
     - search product by sku - product found
     - search product by sku - product not found
     - search product by name (complete) - product found
     - search product by name (complete) - product not found
     */

    @Test
    fun `add new product to empty inventory`() {
        val inventory = anEmptyInventory()

        val product = Product("::sku::", "::product_name::", 3, 40_00)

        val additionOutcome = inventory.add(product)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.get("::sku::")).isEqualTo(product)
    }

    @Test
    fun `add new product to inventory with some elements`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val product3 = Product("::sku_3::", "::product_name_3::", 15, 100_00)

        val additionOutcome = inventory.add(product3)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.get("::sku_3::")).isEqualTo(product3)
    }

    @Test
    fun `add already existing product to inventory`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val additionOutcome = inventory.add(product2)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Failure)
    }

    private fun anEmptyInventory(): Inventory = Inventory(mutableListOf())

    private fun anInventoryContaining(
        product1: Product,
        product2: Product
    ): Inventory {
        return Inventory(mutableListOf(product1, product2))
    }
}

data class Product(
    val sku: String,
    val name: String,
    val quantity: Int,
    val priceInEurDecimals: Int,
)

class Inventory(private val products: MutableList<Product>) {

    fun add(product: Product): AdditionOutcome {
        if (products.any { it.sku == product.sku }) {
            return AdditionOutcome.Failure
        } else {
            this.products.addFirst(product)
            return AdditionOutcome.Success
        }
    }

    fun get(sku: String): Product {
        return products.first { it.sku == sku }
    }
}

sealed interface AdditionOutcome {
    data object Success : AdditionOutcome
    data object Failure : AdditionOutcome
}
