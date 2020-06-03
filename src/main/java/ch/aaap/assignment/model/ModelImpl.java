package ch.aaap.assignment.model;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Märten Heinsalu
 */
@Getter
public class ModelImpl implements Model {

    private Set<PoliticalCommunity> politicalCommunities;
    private Set<PostalCommunity> postalCommunities;
    private Set<Canton> cantons;
    private Set<District> districts;

    public ModelImpl(Set<CSVPoliticalCommunity> csvPoliticalCommunities, Set<CSVPostalCommunity> csvPostalCommunities) {
        setPoliticalCommunitiesByCsvSet(csvPoliticalCommunities, csvPostalCommunities);
        setPostalCommunitiesByCsvSet(csvPostalCommunities);
        setCantonsByCsvSet(csvPoliticalCommunities);
        setDistrictsByCsvSet(csvPoliticalCommunities);
    }

    final void setPoliticalCommunitiesByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities, Set<CSVPostalCommunity> csvPostalCommunities) {
        this.politicalCommunities = csvPoliticalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        PoliticalCommunityImpl.builder()
                                .name(csvPoliticalCommunity.getName())
                                .shortName(csvPoliticalCommunity.getShortName())
                                .number(csvPoliticalCommunity.getNumber())
                                .lastUpdate(csvPoliticalCommunity.getLastUpdate())
                                .districtNumber(csvPoliticalCommunity.getDistrictNumber())
                                .cantonCode(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
    }

    final void setPostalCommunitiesByCsvSet(Set<CSVPostalCommunity> csvPostalCommunities) {
        HashMap<PostalCommunity, Set<String>> postalCommunitySetHashMap = createMapOfPostalCommunitiesAndTheirPoliticalNumbers(csvPostalCommunities);
        Set<PostalCommunity> postalCommunitySet = postalCommunitySetHashMap.keySet();
        postalCommunitySet.forEach(postalCommunity -> postalCommunity.setPoliticalCommunityNumbers(postalCommunitySetHashMap.get(postalCommunity)));
        this.postalCommunities = postalCommunitySet;
    }

    private HashMap<PostalCommunity, Set<String>> createMapOfPostalCommunitiesAndTheirPoliticalNumbers(Set<CSVPostalCommunity> csvPostalCommunities) {
        HashMap<PostalCommunity, Set<String>> postalCommunitySetHashMap = new HashMap<>();
        csvPostalCommunities.forEach(csvPostalCommunity -> {
            String number = csvPostalCommunity.getPoliticalCommunityNumber();
            PostalCommunity postalCommunity = PostalCommunityImpl.builder()
                    .name(csvPostalCommunity.getName())
                    .zipCode(csvPostalCommunity.getZipCode())
                    .zipCodeAddition(csvPostalCommunity.getZipCodeAddition())
                    .build();
            if (postalCommunitySetHashMap.containsKey(postalCommunity)) {
                postalCommunitySetHashMap.get(postalCommunity).add(number);
            } else {
                Set<String> set = new HashSet<>(1);
                set.add(number);
                postalCommunitySetHashMap.put(postalCommunity, set);
            }
        });
        return postalCommunitySetHashMap;
    }

    final void setCantonsByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
        this.cantons = csvPoliticalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        CantonImpl.builder()
                                .name(csvPoliticalCommunity.getCantonName())
                                .code(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
    }

    final void setDistrictsByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
        this.districts = csvPoliticalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        DistrictImpl.builder()
                                .name(csvPoliticalCommunity.getDistrictName())
                                .number(csvPoliticalCommunity.getDistrictNumber())
                                .cantonCode(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
    }

}
