# Inventory Manager
Create a function or class that manages an inventory. The requirements are as follows:

- Add products with a name, SKU (unique code), quantity, and price.
- Remove specific products by SKU.
- Update the quantity of an existing product.
- Calculate the total value of the inventory.
- Search for products by name or SKU.

---

## Adding Products
- Adding a New Product: If the product with the specified SKU does not already exist, the operation is successful, and the product is added to the inventory.

- Adding an Existing Product: If you attempt to add a product using an SKU that already exists, the operation must fail. This prevents data duplication and maintains inventory integrity. The system should throw an error or return a value indicating the operation's failure.

---

## Removing and Updating Products
- Removing a Non-Existent Product: If you attempt to remove a product using an SKU that is not in the inventory, the operation must fail.

- Updating a Non-Existent Product: If you try to update the quantity of a product whose SKU does not exist, the operation must fail.

---

## Searching
### Search by Name
- Partial Match: Searching by name must allow for partial matching. For example, searching for "pen" should return products named "red pen," "pencil and pen," or "pen sharpener." The search should be **case-insensitive**.
- Multiple Results: The name search can and should return more than one result (a list or collection of products) if multiple products match the partial search criterion.

### Search by SKU
- Exact Match: Searching by SKU must require an exact and complete match. Partial matching is not allowed.
- Single Result: Since the SKU is defined as a unique code, searching by SKU must return at most one result (the matching product) or indicate that the product was not found.