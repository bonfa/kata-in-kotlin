package it.fbonfadelli.playground.inventory

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InventoryTest {

    @Test
    fun `add new product to empty inventory`() {
        val inventory = anEmptyInventory()

        val product = Product(Sku("::sku::"), "::product_name::", 3, 40_00)

        val additionOutcome = inventory.add(product)

        Assertions.assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        Assertions.assertThat(inventory.retrieve(Sku("::sku::"))).isEqualTo(RetrievalOutcome.Success(product))
    }

    @Test
    fun `add new product to inventory with some elements`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val product3 = Product(Sku("::sku_3::"), "::product_name_3::", 15, 100_00)

        val additionOutcome = inventory.add(product3)

        Assertions.assertThat(additionOutcome).isEqualTo(AdditionOutcome.Success)
        Assertions.assertThat(inventory.retrieve(Sku("::sku_3::"))).isEqualTo(RetrievalOutcome.Success(product3))
    }

    @Test
    fun `add already existing product to inventory`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val additionOutcome = inventory.add(product2)

        Assertions.assertThat(additionOutcome).isEqualTo(AdditionOutcome.Failure)
    }

    @Test
    fun `retrieve existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve(Sku("::sku_2::"))

        Assertions.assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Success(product2))
    }

    @Test
    fun `retrieve not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieve(Sku("::sku_3::"))

        Assertions.assertThat(retrievalOutcome).isEqualTo(RetrievalOutcome.Failure)
    }

    @Test
    fun `contains product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        Assertions.assertThat(inventory.contains(Sku("::sku_1::"))).isTrue
    }

    @Test
    fun `does not contain product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory = anInventoryContaining(product1, product2)

        Assertions.assertThat(inventory.contains(Sku("::sku_3::"))).isFalse
    }

    @Test
    fun `remove existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val removalOutcome = inventory.remove(Sku("::sku_2::"))

        Assertions.assertThat(removalOutcome).isEqualTo(RemovalOutcome.Success)
        Assertions.assertThat(inventory.contains(Sku("::sku_2::"))).isFalse()
    }

    @Test
    fun `remove not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)
        Assertions.assertThat(inventory.contains(Sku("::sku_3::"))).isFalse()

        val removalOutcome = inventory.remove(Sku("::sku_3::"))

        Assertions.assertThat(removalOutcome).isEqualTo(RemovalOutcome.Failure)
        Assertions.assertThat(inventory.contains(Sku("::sku_3::"))).isFalse()
    }

    @Test
    fun `update quantity for existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val updateOutcome = inventory.updateQuantity(Sku("::sku_2::"), 10)

        Assertions.assertThat(updateOutcome).isEqualTo(UpdateOutcome.Success)
        Assertions.assertThat(inventory.retrieve(Sku("::sku_2::"))).satisfies({ retrievalOutcome: RetrievalOutcome ->
            Assertions.assertThat(retrievalOutcome).isInstanceOf(RetrievalOutcome.Success::class.java)
            Assertions.assertThat((retrievalOutcome as RetrievalOutcome.Success).product.quantity).isEqualTo(10)
        })
    }

    @Test
    fun `update quantity for not existing product`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val updateOutcome = inventory.updateQuantity(Sku("::sku_3::"), 10)
        Assertions.assertThat(updateOutcome).isEqualTo(UpdateOutcome.Failure)
    }

    @Test
    fun `compute total value of an empty inventory`() {
        val inventory: Inventory = anEmptyInventory()

        val totalValue = inventory.totalValue()

        Assertions.assertThat(totalValue).isEqualTo(0L)
    }

    @Test
    fun `total value of an inventory with some items`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val totalValue = inventory.totalValue()

        Assertions.assertThat(totalValue).isEqualTo(3 * 40_00 + 25_00)
    }

    @Test
    fun `find product by name - one product found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("::product_name_1::")

        Assertions.assertThat(retrievalOutcome).containsOnly(product1)
    }

    @Test
    fun `find product by name - more products found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("product_name")

        Assertions.assertThat(retrievalOutcome).containsOnly(product1, product2)
    }

    @Test
    fun `find product by name is case insensitive`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("PRODUCT_NAME")

        Assertions.assertThat(retrievalOutcome).containsOnly(product1, product2)
    }

    @Test
    fun `find product by name - product not found`() {
        val product1 = Product(Sku("::sku_1::"), "::product_name_1::", 3, 40_00)
        val product2 = Product(Sku("::sku_2::"), "::product_name_2::", 1, 25_00)

        val inventory: Inventory = anInventoryContaining(product1, product2)

        val retrievalOutcome = inventory.retrieveProductByName("NOT_FOUND")

        Assertions.assertThat(retrievalOutcome).isEmpty()
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