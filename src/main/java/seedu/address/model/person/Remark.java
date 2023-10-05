package seedu.address.model.person;

import static java.util.Objects.requireNonNull;


public class Remark {


    public final String value;


    public Remark (String remark) {
        requireNonNull(remark);
        value = remark;
    }


    @Override
    public String toString() {
            return value;
        }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.person.Remark)) {
            return false;
        }

        seedu.address.model.person.Remark otherRemark = (seedu.address.model.person.Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
            return value.hashCode();
        }

    }


