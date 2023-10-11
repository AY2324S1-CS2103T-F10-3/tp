package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.ALICE;
import static seedu.address.testutil.TypicalIngredients.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.testutil.IngredientBuilder;

public class UniqueIngredientListTest {

    private final UniqueIngredientList uniqueIngredientList = new UniqueIngredientList();

    @Test
    public void contains_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.contains(null));
    }

    @Test
    public void contains_ingredientNotInList_returnsFalse() {
        assertFalse(uniqueIngredientList.contains(ALICE));
    }

    @Test
    public void contains_ingredientInList_returnsTrue() {
        uniqueIngredientList.add(ALICE);
        assertTrue(uniqueIngredientList.contains(ALICE));
    }

    @Test
    public void contains_ingredientWithSameIdentityFieldsInList_returnsTrue() {
        uniqueIngredientList.add(ALICE);
        Ingredient editedAlice = new IngredientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueIngredientList.contains(editedAlice));
    }

    @Test
    public void add_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.add(null));
    }

    @Test
    public void add_duplicateIngredient_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(ALICE);
        assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.add(ALICE));
    }

    @Test
    public void setIngredient_nullTargetIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(null, ALICE));
    }

    @Test
    public void setIngredient_nullEditedIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(ALICE, null));
    }

    @Test
    public void setIngredient_targetIngredientNotInList_throwsIngredientNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.setIngredient(ALICE, ALICE));
    }

    @Test
    public void setIngredient_editedIngredientIsSameIngredient_success() {
        uniqueIngredientList.add(ALICE);
        uniqueIngredientList.setIngredient(ALICE, ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(ALICE);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasSameIdentity_success() {
        uniqueIngredientList.add(ALICE);
        Ingredient editedAlice = new IngredientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueIngredientList.setIngredient(ALICE, editedAlice);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(editedAlice);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasDifferentIdentity_success() {
        uniqueIngredientList.add(ALICE);
        uniqueIngredientList.setIngredient(ALICE, BOB);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(BOB);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasNonUniqueIdentity_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(ALICE);
        uniqueIngredientList.add(BOB);
        assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredient(ALICE, BOB));
    }

    @Test
    public void remove_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.remove(null));
    }

    @Test
    public void remove_ingredientDoesNotExist_throwsIngredientNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.remove(ALICE));
    }

    @Test
    public void remove_existingIngredient_removesIngredient() {
        uniqueIngredientList.add(ALICE);
        uniqueIngredientList.remove(ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullUniqueIngredientList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((UniqueIngredientList) null));
    }

    @Test
    public void setIngredients_uniqueIngredientList_replacesOwnListWithProvidedUniqueIngredientList() {
        uniqueIngredientList.add(ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(BOB);
        uniqueIngredientList.setIngredients(expectedUniqueIngredientList);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((List<Ingredient>) null));
    }

    @Test
    public void setIngredients_list_replacesOwnListWithProvidedList() {
        uniqueIngredientList.add(ALICE);
        List<Ingredient> ingredientList = Collections.singletonList(BOB);
        uniqueIngredientList.setIngredients(ingredientList);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(BOB);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_listWithDuplicateIngredients_throwsDuplicateIngredientException() {
        List<Ingredient> listWithDuplicateIngredients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredients(listWithDuplicateIngredients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueIngredientList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueIngredientList.asUnmodifiableObservableList().toString(), uniqueIngredientList.toString());
    }
}
