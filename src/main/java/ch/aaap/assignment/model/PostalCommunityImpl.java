package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class PostalCommunityImpl implements PostalCommunity {

    private  String name;
    @NonNull
    private  String zipCode;
    @NonNull
    private  String zipCodeAddition;
    @NonNull
    private   String politicalCommunityNumber;

    // TODO: consider giving polCom a Set of Zips(cd and addCd). Would that result in passing returnsCorrectDistrictNamesForZipCode AND testModel?
/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostalCommunityImpl that = (PostalCommunityImpl) o;

        if (!zipCode.equals(that.zipCode)) return false;
        return zipCodeAddition.equals(that.zipCodeAddition);
    }

    @Override
    public int hashCode() {
        int result = zipCode.hashCode();
        result = 31 * result + zipCodeAddition.hashCode();
        return result;
    }*/

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostalCommunityImpl that = (PostalCommunityImpl) o;

        if (!zipCode.equals(that.zipCode)) return false;
        if (!zipCodeAddition.equals(that.zipCodeAddition)) return false;
        return politicalCommunityNumber.equals(that.politicalCommunityNumber);
    }

    @Override
    public int hashCode() {
        int result = zipCode.hashCode();
        result = 31 * result + zipCodeAddition.hashCode();
        result = 31 * result + politicalCommunityNumber.hashCode();
        return result;
    }
}
