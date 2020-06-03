package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.Set;

/**
 * @author Märten Heinsalu
 */
@Getter
@Builder
public class PostalCommunityImpl implements PostalCommunity {

    @NonNull
    private String zipCode;
    @NonNull
    private String zipCodeAddition;

    private String name;

    @Setter
    private Set<String> politicalCommunityNumbers;

    @Override
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
    }
}
