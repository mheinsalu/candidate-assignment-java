package ch.aaap.assignment;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

    private Model model = null;

    public Application() {
        initModel();
    }

    public static void main(String[] args) {
        new Application();
    }

    /**
     * Reads the CSVs and initializes a memory model
     */
    private void initModel() {
        Set<CSVPoliticalCommunity> politicalCommunities = CSVUtil.getPoliticalCommunities();
        Set<CSVPostalCommunity> postalCommunities = CSVUtil.getPostalCommunities();

        // general note: iteration can be faster than streams
        Set<PoliticalCommunity> politicalCommunitySet = politicalCommunities
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
        Set<PostalCommunity> postalCommunitySet = postalCommunities
                .stream()
                .map(csvPostalCommunity ->
                        PostalCommunityImpl.builder()
                                .name(csvPostalCommunity.getName())
                                .zipCode(csvPostalCommunity.getZipCode())
                                .zipCodeAddition(csvPostalCommunity.getZipCodeAddition())
                                .politicalCommunityNumber(csvPostalCommunity.getPoliticalCommunityNumber())
                                .build())
                .collect(Collectors.toSet());
        Set<Canton> cantonSet = politicalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        CantonImpl.builder()
                                .name(csvPoliticalCommunity.getCantonName())
                                .code(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
        Set<District> districtSet = politicalCommunities
                .stream()
                .map(csvPoliticalCommunity ->
                        DistrictImpl.builder()
                                .name(csvPoliticalCommunity.getDistrictName())
                                .number(csvPoliticalCommunity.getDistrictNumber())
                                .cantonCode(csvPoliticalCommunity.getCantonCode())
                                .build())
                .collect(Collectors.toSet());
        model = new ModelImpl(politicalCommunitySet, postalCommunitySet, cantonSet, districtSet);
    }

    /**
     * @return model
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * @param cantonCode of a canton (e.g. ZH)
     * @return amount of political communities in given canton
     */
    public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
        long count = getModel().getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunity.getCantonCode().equalsIgnoreCase(cantonCode))
                .count();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 political communities in in canton with code " + cantonCode);
    }

    /**
     * @param cantonCode of a canton (e.g. ZH)
     * @return amount of districts in given canton
     */
    public long getAmountOfDistrictsInCanton(String cantonCode) {
        int count = getModel().getDistricts()
                .stream()
                .filter(district -> district.getCantonCode().equalsIgnoreCase(cantonCode))
                .collect(Collectors.toSet()).size();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 districts in canton with canton code " + cantonCode);
    }

    /**
     * @param districtNumber of a district (e.g. 101)
     * @return amount of political communities in given district
     */
    public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
        long count = getModel().getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunity.getDistrictNumber().equalsIgnoreCase(districtNumber))
                .count();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 political communities in district with name " + districtNumber);
    }

    /**
     * @param zipCode 4 digit zip code
     * @return district names that belong to specified zip code
     */
    public Set<String> getDistrictsForZipCode(String zipCode) {
        Set<String> politicalCommunityNumbersInZipCode = getModel().getPostalCommunities()
                .stream()
                .filter(postalCommunity -> postalCommunity.getZipCode().equalsIgnoreCase(zipCode))
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        Set<String> districtNumbers = getModel().getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunityNumbersInZipCode.contains(politicalCommunity.getNumber()))
                .map(PoliticalCommunity::getDistrictNumber)
                .collect(Collectors.toSet());
        return getModel().getDistricts()
                .stream()
                .filter(district -> districtNumbers.contains(district.getNumber()))
                .map(District::getName)
                .collect(Collectors.toSet()); // TODO: new method districtNameFromDistrictNumber?
    }

    /**
     * @param postalCommunityName name
     * @return lastUpdate of the political community by a given postal community name
     */
    public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(String postalCommunityName) {
        List<PostalCommunity> postalCommunitiesWithTargetName = getModel().getPostalCommunities()
                .stream()
                .filter(postalCommunity -> postalCommunity.getName().equalsIgnoreCase(postalCommunityName))
                .collect(Collectors.toList());
        if (postalCommunitiesWithTargetName.size() > 0) { // TODO: think about this method. post to pol bijection? I can remove this if
            String politicalCommunityNumber = postalCommunitiesWithTargetName.get(0).getPoliticalCommunityNumber();
            List<PoliticalCommunity> politicalCommunities = getModel().getPoliticalCommunities()
                    .stream()
                    .filter(politicalCommunity -> politicalCommunity.getNumber().equalsIgnoreCase(politicalCommunityNumber))
                    .collect(Collectors.toList());
            if (politicalCommunities.size() > 0) {
                return politicalCommunities.get(0).getLastUpdate();
            }
            throw new IllegalArgumentException("Found 0 political communities with political community number " + politicalCommunityNumber);
        }
        throw new IllegalArgumentException("Found 0 postal communities with postal community name " + postalCommunityName);
    }

    /**
     * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
     *
     * @return amount of canton
     */
    public long getAmountOfCantons() {
        return getModel().getCantons().size();
    }

    /**
     * https://de.wikipedia.org/wiki/Kommunanz
     *
     * @return amount of political communities without postal communities
     */
    public long getAmountOfPoliticalCommunityWithoutPostalCommunities() { // TODO: new method politicalNumberToPostalCommunity
        Set<String> postalCommunitiesPoliticalCommunityNumbers = getModel().getPostalCommunities()
                .stream()
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        Set<String> politicalCommunitiesPoliticalCommunityNumbers = getModel().getPoliticalCommunities()
                .stream()
                .map(PoliticalCommunity::getNumber)
                .collect(Collectors.toSet());
        politicalCommunitiesPoliticalCommunityNumbers.removeAll(postalCommunitiesPoliticalCommunityNumbers);
        long count = politicalCommunitiesPoliticalCommunityNumbers.size();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 political communities without postal communities");
    }
}
