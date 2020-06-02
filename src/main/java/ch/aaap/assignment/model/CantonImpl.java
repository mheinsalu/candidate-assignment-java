package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CantonImpl implements Canton {

    @NonNull
    String code;
    @NonNull
    String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CantonImpl canton = (CantonImpl) object;

        if (!code.equals(canton.code)) return false;
        return name.equals(canton.name);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
