package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

    @NonNull
    private  String number;
    @NonNull
    private  String name;
    private  String shortName;
    private  LocalDate lastUpdate;

    private   String cantonCode;
    private   String districtNumber;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PoliticalCommunityImpl that = (PoliticalCommunityImpl) object;

        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
