package ch.aaap.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelImpl implements Model {

    Set<PoliticalCommunity> politicalCommunities;
    Set<PostalCommunity> postalCommunities;
    Set<Canton> cantons;
    Set<District> districts;

    public List<PoliticalCommunity> getPoliticalCommunitiesByNumber(String targetNumber) {
        return getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunity.getNumber().equalsIgnoreCase(targetNumber))
                .collect(Collectors.toList());
    }

    public List<PostalCommunity> getPostalCommunitiesByName(String targetNumber) {
        return getPostalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunity.getName().equalsIgnoreCase(targetNumber))
                .collect(Collectors.toList());
    }

}
