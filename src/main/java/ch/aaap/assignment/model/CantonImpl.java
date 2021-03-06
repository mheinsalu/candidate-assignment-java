package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author Märten Heinsalu
 */
@Getter
@Builder
public class CantonImpl implements Canton {

    @NonNull
    private String code;

    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CantonImpl canton = (CantonImpl) object;

        return code.equals(canton.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
