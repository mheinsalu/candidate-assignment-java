package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class DistrictImpl implements District {

    @NonNull
    String number;
    @NonNull
    String name;

    String cantonCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DistrictImpl district = (DistrictImpl) object;

        if (!number.equals(district.number)) return false;
        return name.equals(district.name);
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
