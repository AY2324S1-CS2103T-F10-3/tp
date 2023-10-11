package seedu.address.model.ingredient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient {

    // Identity fields
    private final Name name;
    /*
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    */

    /**
     * Every field must be present and not null.
     */
    public Ingredient(Name name) {
        // requireAllNonNull(name, phone, email, address, tags);
        requireAllNonNull(name);
        this.name = name;
        /*
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        */
    }


    public Name getName() {
        return name;
    }
    /*
    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
    */
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    /*
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

     */

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;
        return name.equals(otherIngredient.name);
                //&& phone.equals(otherIngredient.phone)
                //&& email.equals(otherIngredient.email)
                //&& address.equals(otherIngredient.address)
                //&& tags.equals(otherIngredient.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        // return Objects.hash(name, phone, email, address, tags);
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                //.add("phone", phone)
                //.add("email", email)
                //.add("address", address)
                //.add("tags", tags)
                .toString();
    }

}
