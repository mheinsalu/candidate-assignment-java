package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictImpl implements District {

    String number;
    String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DistrictImpl district = (DistrictImpl) object;

        return number.equals(district.number);
    }
}
