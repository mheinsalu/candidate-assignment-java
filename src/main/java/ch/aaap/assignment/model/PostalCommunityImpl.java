package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalCommunityImpl implements PostalCommunity {

    String zipCode;
    String zipCodeAddition;
    String name;
    String politicalCommunityNumber;

}
