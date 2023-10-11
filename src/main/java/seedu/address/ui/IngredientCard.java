package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.ingredient.Ingredient;

/**
 * An UI component that displays information of a {@code Ingredient}.
 */
public class IngredientCard extends UiPart<Region> {

    private static final String FXML = "IngredientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/inventory-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Ingredient ingredient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code IngredientCode} with the given {@code Ingredient} and index to display.
     */
    public IngredientCard(Ingredient ingredient, int displayedIndex) {
        super(FXML);
        this.ingredient = ingredient;
        id.setText(displayedIndex + ". ");
        name.setText(this.ingredient.getName().fullName);
        //phone.setText(ingredient.getPhone().value);
        //address.setText(ingredient.getAddress().value);
        //email.setText(ingredient.getEmail().value);
        //ingredient.getTags().stream()
                //.sorted(Comparator.comparing(tag -> tag.tagName))
                //.forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
