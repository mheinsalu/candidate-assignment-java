package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class DistrictImpl implements District {

    @NonNull
    private  String number;
    private  String name;

    private  String cantonCode;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DistrictImpl district = (DistrictImpl) object;

        return number.equals(district.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
