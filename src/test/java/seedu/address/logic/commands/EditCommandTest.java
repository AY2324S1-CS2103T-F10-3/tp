package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIngredients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditIngredientDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.EditIngredientDescriptorBuilder;
import seedu.address.testutil.IngredientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedIngredient));

        Model expectedModel = new ModelManager(new AddressBook(model.getInventory()), new UserPrefs());
        expectedModel.setIngredient(model.getFilteredIngredientList().get(0), editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIngredient = Index.fromOneBased(model.getFilteredIngredientList().size());
        Ingredient lastIngredient = model.getFilteredIngredientList().get(indexLastIngredient.getZeroBased());

        IngredientBuilder ingredientInList = new IngredientBuilder(lastIngredient);
        Ingredient editedIngredient = ingredientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastIngredient, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedIngredient));

        Model expectedModel = new ModelManager(new AddressBook(model.getInventory()), new UserPrefs());
        expectedModel.setIngredient(lastIngredient, editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditIngredientDescriptor());
        Ingredient editedIngredient = model.getFilteredIngredientList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedIngredient));

        Model expectedModel = new ModelManager(new AddressBook(model.getInventory()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showIngredientAtIndex(model, INDEX_FIRST_PERSON);

        Ingredient ingredientInFilteredList = model.getFilteredIngredientList().get(INDEX_FIRST_PERSON.getZeroBased());
        Ingredient editedIngredient = new IngredientBuilder(ingredientInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedIngredient));

        Model expectedModel = new ModelManager(new AddressBook(model.getInventory()), new UserPrefs());
        expectedModel.setIngredient(model.getFilteredIngredientList().get(0), editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIngredientUnfilteredList_failure() {
        Ingredient firstIngredient = model.getFilteredIngredientList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(firstIngredient).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateIngredientFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST_PERSON);

        // edit ingredient in filtered list into a duplicate in address book
        Ingredient ingredientInList = model.getInventory().getIngredientList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditIngredientDescriptorBuilder(ingredientInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidIngredientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidIngredientIndexFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getIngredientList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditIngredientDescriptor copyDescriptor = new EditIngredientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
        EditCommand editCommand = new EditCommand(index, editIngredientDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editIngredientDescriptor="
                + editIngredientDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
