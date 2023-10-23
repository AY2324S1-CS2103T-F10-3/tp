package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;

public interface ReadOnlyRecipeBook {
    /**
     * Returns an unmodifiable view of the recipe list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getRecipeList();
}
