package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Name;
import seedu.address.model.ingredient.Quantity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Inventory inventory;
    private final UserPrefs userPrefs;
    private final FilteredList<Ingredient> filteredIngredients;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with address book: " + inventory + " and user prefs " + userPrefs);

        this.inventory = new Inventory(inventory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredIngredients = new FilteredList<>(this.inventory.getIngredientList());
    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getInventoryFilePath() {
        return userPrefs.getInventoryFilePath();
    }

    @Override
    public void setInventoryFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setInventoryFilePath(addressBookFilePath);
    }

    //=========== StockBook ================================================================================

    @Override
    public void setInventory(ReadOnlyInventory stock) {
        this.inventory.resetData(stock);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean hasIngredient(Name ingredientName) {
        requireNonNull(ingredientName);

        return inventory.hasIngredient(ingredientName);
    }

    @Override
    public Quantity getQuantityOf(Name ingredientName) {
        requireNonNull(ingredientName);
        return inventory.getQuantityOf(ingredientName);
    }

    @Override
    public void deleteIngredient(Name ingredientName) {
        inventory.removeIngredient(ingredientName);
    }

    @Override
    public void deleteIngredients() {
        inventory.clear();
    }
    @Override
    public void addIngredient(Ingredient ingredient) {
        inventory.addIngredient(ingredient);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void useIngredient(Name ingredientName, Quantity quantity) {
        requireAllNonNull(ingredientName, quantity);
        inventory.useIngredient(ingredientName, quantity);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return filteredIngredients;
    }

    @Override
    public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredIngredients.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return inventory.equals(otherModelManager.inventory)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredIngredients.equals(otherModelManager.filteredIngredients);
    }

}
