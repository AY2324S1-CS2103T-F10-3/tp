package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class to help with building Inventory objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withIngredient("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Ingredient} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withIngredient(Ingredient ingredient) {
        addressBook.addIngredient(ingredient);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
