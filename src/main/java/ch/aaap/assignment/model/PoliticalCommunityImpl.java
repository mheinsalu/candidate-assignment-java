package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

    @NonNull
    String number;
    @NonNull
    String name;
    String shortName;
    LocalDate lastUpdate;

    String cantonCode;
    String districtNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticalCommunityImpl that = (PoliticalCommunityImpl) o;

        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
