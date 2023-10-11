package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;

/**
 * An Immutable Inventory that is serializable to JSON format.
 */
@JsonRootName(value = "inventory")
class JsonSerializableInventory {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventory} with the given persons.
     */
    @JsonCreator
    public JsonSerializableInventory(@JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Converts a given {@code ReadOnlyInventory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventory}.
     */
    public JsonSerializableInventory(ReadOnlyInventory source) {
        ingredients.addAll(source.getIngredientList().stream().map(JsonAdaptedIngredient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (inventory.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            inventory.addIngredient(ingredient);
        }
        return inventory;
    }

}
