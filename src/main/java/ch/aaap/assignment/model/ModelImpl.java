package ch.aaap.assignment.model;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ModelImpl implements Model {

    private Set<PoliticalCommunity> politicalCommunities;
    private Set<PostalCommunity> postalCommunities;
    private Set<Canton> cantons;
    private Set<District> districts;

    public ModelImpl(Set<CSVPoliticalCommunity> csvPoliticalCommunities, Set<CSVPostalCommunity> csvPostalCommunities) {
        setPoliticalCommunitiesByCsvSet(csvPoliticalCommunities);
        setPostalCommunitiesByCsvSet(csvPostalCommunities);
        setCantonsByCsvSet(csvPoliticalCommunities);
        setDistrictsByCsvSet(csvPoliticalCommunities);
    }

    public void setPoliticalCommunitiesByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
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

    public void setPostalCommunitiesByCsvSet(Set<CSVPostalCommunity> csvPostalCommunities) {
        this.postalCommunities = csvPostalCommunities
                .stream()
                .map(csvPostalCommunity ->
                        PostalCommunityImpl.builder()
                                .name(csvPostalCommunity.getName())
                                .zipCode(csvPostalCommunity.getZipCode())
                                .zipCodeAddition(csvPostalCommunity.getZipCodeAddition())
                                .politicalCommunityNumber(csvPostalCommunity.getPoliticalCommunityNumber())
                                .build())
                .collect(Collectors.toSet());
    }

    public void setCantonsByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
        this.cantons = csvPoliticalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        CantonImpl.builder()
                                .name(csvPoliticalCommunity.getCantonName())
                                .code(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
    }

    public void setDistrictsByCsvSet(Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
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
