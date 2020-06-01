package ch.aaap.assignment.model;

import lombok.Data;

import java.util.Set;

@Data
public class ModelImpl implements Model {

    Set<PoliticalCommunity> politicalCommunities;
    Set<PostalCommunity> postalCommunities;
    Set<Canton> cantons;
    Set<District> districts;

}
