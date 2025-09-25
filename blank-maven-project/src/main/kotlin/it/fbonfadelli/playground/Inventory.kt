package it.fbonfadelli.playground

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