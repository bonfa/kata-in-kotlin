package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InventoryTest {

    @Test
    fun `add new product to empty inventory`() {
        val inventory = anEmptyInventory()

        val product = Product(Sku("::sku::"), "::product_name::", 3, 40_00)

        val additionOutcome = inventory.add(product)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.retrieve(Sku("::sku::"))).isEqualTo(RetrievalOutcome.Success(product))
    }

    @Test
    fun `add new product to inventory with some elements`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val product3 = Product(Sku("::sku_3::"), "::product_name_3::", 15, 100_00)

        val additionOutcome = inventory.add(product3)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        assertThat(inventory.retrieve(Sku("::sku_3::"))).isEqualTo(RetrievalOutcome.Success(product3))
    }

    @Test
    fun `add already existing product to inventory`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val additionOutcome = inventory.add(product2)

        assertThat(additionOutcome).isEqualTo(AdditionOutcome.Failure)
    }

    @Test
    fun `retrieve existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve(Sku("::sku_2::"))

        assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Success(product2))
    }

    @Test
    fun `retrieve not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve(Sku("::sku_3::"))

        assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Failure)
    }

    @Test
    fun `contains product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        assertThat(inventory.contains(Sku("::sku_1::"))).isTrue
    }

    @Test
    fun `does not contain product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        assertThat(inventory.contains(Sku("::sku_3::"))).isFalse
    }

    @Test
    fun `remove existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val removalOutcome = inventory.remove(Sku("::sku_2::"))

        assertThat(removalOutcome).isEqualTo(RemovalOutcome.Success)
        assertThat(inventory.contains(Sku("::sku_2::"))).isFalse()
    }

    @Test
    fun `remove not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)
        assertThat(inventory.contains(Sku("::sku_3::"))).isFalse()

        val removalOutcome = inventory.remove(Sku("::sku_3::"))

        assertThat(removalOutcome).isEqualTo(RemovalOutcome.Failure)
        assertThat(inventory.contains(Sku("::sku_3::"))).isFalse()
    }

    @Test
    fun `update quantity for existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val updateOutcome = inventory.updateQuantity(Sku("::sku_2::"), 10)

        assertThat(updateOutcome).isEqualTo(UpdateOutcome.Success)
        assertThat(inventory.retrieve(Sku("::sku_2::"))).satisfies({ retrievalOutcome: RetrievalOutcome ->
            assertThat(retrievalOutcome).isInstanceOf(RetrievalOutcome.Success::class.java)
            assertThat((retrievalOutcome as RetrievalOutcome.Success).product.quantity).isEqualTo(10)
        })
    }

    @Test
    fun `update quantity for not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val updateOutcome = inventory.updateQuantity(Sku("::sku_3::"), 10)
        assertThat(updateOutcome).isEqualTo(UpdateOutcome.Failure)
    }

    @Test
    fun `compute total value of an empty inventory`() {
        val inventory: Inventory = anEmptyInventory()

        val totalValue = inventory.totalValue()

        assertThat(totalValue).isEqualTo(0L)
    }

    @Test
    fun `total value of an inventory with some items`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val totalValue = inventory.totalValue()

        assertThat(totalValue).isEqualTo(3 * 40_00 + 25_00)
    }

    @Test
    fun `find product by name - one product found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("::product_name_1::")

        assertThat(retrievalOutcome).containsOnly(product1)
    }

    @Test
    fun `find product by name - more products found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("product_name")

        assertThat(retrievalOutcome).containsOnly(product1, product2)
    }

    @Test
    fun `find product by name is case insensitive`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("PRODUCT_NAME")

        assertThat(retrievalOutcome).containsOnly(product1, product2)
    }

    @Test
    fun `find product by name - product not found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("NOT_FOUND")

        assertThat(retrievalOutcome).isEmpty()
    }

    private fun anEmptyInventory(): Inventory = Inventory(mutableMapOf())

    private fun anInventoryContaining(
        product1: Product,
        product2: Product
    ): Inventory = Inventory(
        mutableMapOf(
            product1.sku to product1,
            product2.sku to product2,
        )
    )
}

data class Product(
    val sku: Sku,
    val name: String,
    val quantity: Int,
    val singleProductPriceInEurDecimals: Int,
)

data class Sku(val value: String)

class Inventory(private val productsMap: MutableMap<Sku, Product>) {
    fun add(product: Product): AdditionOutcome =
        if (productsMap.containsKey(product.sku)) {
            AdditionOutcome.Failure
        } else {
            this.productsMap[product.sku] = product
            AdditionOutcome.Success
        }

    fun retrieve(sku: Sku): RetrievalOutcome =
        if (productsMap.containsKey(sku))
            RetrievalOutcome.Success(productsMap[sku]!!)
        else
            RetrievalOutcome.Failure

    fun contains(sku: Sku): Boolean =
        productsMap.containsKey(sku)

    fun remove(sku: Sku): RemovalOutcome =
        if (productsMap.containsKey(sku)) {
            productsMap.remove(sku)
            RemovalOutcome.Success
        } else {
            RemovalOutcome.Failure
        }

    fun updateQuantity(sku: Sku, newQuantity: Int): UpdateOutcome =
        if (productsMap.containsKey(sku)) {
            productsMap[sku] = productsMap[sku]!!.copy(quantity = newQuantity)
            UpdateOutcome.Success
        } else
            UpdateOutcome.Failure

    fun totalValue(): Long =
        productsMap.values.sumOf { it.quantity.toLong() * it.singleProductPriceInEurDecimals }

    fun retrieveProductByName(toSearch: String): List<Product> =
        productsMap.values.filter { it.name.contains(toSearch, ignoreCase = true) }

}

sealed interface AdditionOutcome {
    data object Success : AdditionOutcome
    data object Failure : AdditionOutcome
}

sealed interface RetrievalOutcome {
    data class Success(val product: Product) : RetrievalOutcome
    data object Failure : RetrievalOutcome
}

sealed interface RemovalOutcome {
    data object Success : RemovalOutcome
    data object Failure : RemovalOutcome
}

sealed interface UpdateOutcome {
    data object Success : UpdateOutcome
    data object Failure : UpdateOutcome
}
