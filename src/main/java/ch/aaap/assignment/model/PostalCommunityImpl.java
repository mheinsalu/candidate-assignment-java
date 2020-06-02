package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class PostalCommunityImpl implements PostalCommunity {

    @NonNull
    String name;
    @NonNull
    String zipCode;
    @NonNull
    String zipCodeAddition;
    @NonNull
    String politicalCommunityNumber;

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
