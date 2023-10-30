package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.ingredient.Ingredient;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX = "The recipe index provided is invalid";
    public static final String MESSAGE_INGREDIENTS_LISTED_OVERVIEW = "%1$d ingredients listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_RECIPE_LISTED = "Recipe of uuid %1$d opened";
    public static final String MESSAGE_RECIPE_LISTED_OVERVIEW = "%1$d recipes listed!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code ingredient} for display to the user.
     */
    public static String format(Ingredient ingredient) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ingredient.getName())
                .append("; \nQuantity Left: ")
                .append(ingredient.getQuantity().toString());
        if (ingredient.getQuantity().getValue() == 0) {
            builder.append("\nIngredient Removed");
        }
        return builder.toString();
    }

}
