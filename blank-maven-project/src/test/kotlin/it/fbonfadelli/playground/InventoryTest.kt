package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InventoryTest {

    /*
     - add new product to empty inventory (sku, name, qty, price) - success [x]
     - add new product to an inventory with some elements - success [x]
     - add existing product - failure [x]
     - search product by sku - product found
     - search product by sku - product not found

     - remove existing product - success
     - remove not existing product - failure
     - update quantity for an exisiting product - success
     - remove quantity for an exisiting product - failure
     - compute total value of the inventory - empty
     - compute total value of the inventory - some items
     - search product by name (complete) - product found
     - search product by name (complete) - product not found
     */

    @Test
    fun `add new product to empty inventory`() {
        val inventory = anEmptyInventory()

        val product = Product("::sku::", "::product_name::", 3, 40_00)

        val additionOutcome = inventory.add(product)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.retrieve("::sku::")).isEqualTo(RetrievalOutcome.Success(product))
    }

    @Test
    fun `add new product to inventory with some elements`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val product3 = Product("::sku_3::", "::product_name_3::", 15, 100_00)

        val additionOutcome = inventory.add(product3)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.retrieve("::sku_3::")).isEqualTo(RetrievalOutcome.Success(product3))
    }

    @Test
    fun `add already existing product to inventory`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val additionOutcome = inventory.add(product2)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Failure)
    }

    @Test
    fun `retrieve existing product`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve("::sku_2::")

        assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Success(product2))
    }

    @Test
    fun `retrieve not existing product`() {
        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve("::sku_3::")

        assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Failure)
    }

    //    @Test
//    fun `remove existing product from inventory`() {
//        val product1 = Product("::sku_1::", "::product_name_1::", 3, 40_00)
//        val product2 = Product("::sku_2::", "::product_name_2::", 1, 25_00)
//
//        val inventory = anInventoryContaining(product1, product2)
//
//        val removalOutcome = inventory.remove("::sku_1")
//
//        assertThat(removalOutcome).isEqualTo(RemovalOutcome.Success)
//        assertThat(inventory.get("::sku_2::")).isEqualTo(product3)
//    }

    private fun anEmptyInventory(): Inventory = Inventory(mutableMapOf())

    private fun anInventoryContaining(
        product1: Product,
        product2: Product
    ): Inventory = Inventory(mutableMapOf(
        product1.sku to product1,
        product2.sku to product2,
    ))
}

data class Product(
    val sku: String,
    val name: String,
    val quantity: Int,
    val priceInEurDecimals: Int,
)

class Inventory(private val productsMap: MutableMap<String, Product>) {

    fun add(product: Product): AdditionOutcome =
        if (productsMap.containsKey(product.sku)) {
            AdditionOutcome.Failure
        } else {
            this.productsMap[product.sku] = product
            AdditionOutcome.Success
        }

    fun retrieve(sku: String): RetrievalOutcome =
        if (productsMap.containsKey(sku))
            RetrievalOutcome.Success(productsMap[sku]!!)
        else
            RetrievalOutcome.Failure
}

sealed interface AdditionOutcome {
    data object Success : AdditionOutcome
    data object Failure : AdditionOutcome
}

sealed interface RetrievalOutcome {
    @JvmInline
    value class Success(val product: Product) : RetrievalOutcome
    data object Failure : RetrievalOutcome
}
