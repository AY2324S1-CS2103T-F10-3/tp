package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Name;
import seedu.address.model.ingredient.Quantity;
import seedu.address.model.ingredient.Unit;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Ingredient[] getSampleIngredients() {
        return new Ingredient[] {
            new Ingredient(new Name("Alex Yeoh"), new Quantity(100, Unit.GRAM))
            /*
            new Ingredient(new Name("Bernice Yu"), null),
            new Ingredient(new Name("Charlotte Oliveiro"), null),
            new Ingredient(new Name("David Li"), null),
            new Ingredient(new Name("Irfan Ibrahim"), null),
            new Ingredient(new Name("Roy Balakrishnan"), null)
            */

        };
    }

    public static ReadOnlyInventory getSampleAddressBook() {
        Inventory sampleAb = new Inventory();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleAb.addIngredient(sampleIngredient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
