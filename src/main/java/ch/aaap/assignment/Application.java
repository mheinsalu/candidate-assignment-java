package ch.aaap.assignment;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import ch.aaap.assignment.util.CsvConverterUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    // TODO: !! guugelda ja leia size relations-id

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
                                .districtName(csvPoliticalCommunity.getDistrictName())
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
        long count = getModel().getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunity.getCantonCode().equalsIgnoreCase(cantonCode))
                .map(PoliticalCommunity::getDistrictName)
                .collect(Collectors.toSet())
                .size();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 districts in canton with canton code " + cantonCode);
    }

    /**
     * @param districtNumber of a district (e.g. 101)
     * @return amount of districts in given canton
     */
    public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
        List<District> districts = getModel().getDistricts()
                .stream()
                .filter(district -> district.getNumber().equalsIgnoreCase(districtNumber))
                .collect(Collectors.toList());
        if (districts.size() > 0) {
            String districtName = districts.get(0).getName();
            return getModel().getPoliticalCommunities()
                    .stream()
                    .filter(politicalCommunity -> politicalCommunity.getDistrictName().equalsIgnoreCase(districtName))
                    .count();
        }
        throw new IllegalArgumentException("Found 0 districts with district number " + districtNumber);
    }

    /**
     * @param zipCode 4 digit zip code
     * @return district that belongs to specified zip code
     */
    public Set<String> getDistrictsForZipCode(String zipCode) {
        Set<PostalCommunity> postalCommunitiesWithZipCode = getModel().getPostalCommunities()
                .stream()
                .filter(postalCommunity -> postalCommunity.getZipCode().equalsIgnoreCase(zipCode))
                .collect(Collectors.toSet());
        Set<String> politicalCommunityNumbersWithZipCode = postalCommunitiesWithZipCode
                .stream()
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        return getModel().getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunityNumbersWithZipCode.contains(politicalCommunity.getNumber()))
                .map(PoliticalCommunity::getDistrictName)
                .collect(Collectors.toSet());
    }

    /**
     * @param postalCommunityName name
     * @return lastUpdate of the political community by a given postal community name
     */
    public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(String postalCommunityName) {
        List<PostalCommunity> postalCommunitiesWithGivenName = getModel().getPostalCommunitiesByName(postalCommunityName);
        if (postalCommunitiesWithGivenName.size() > 0) {
            String targetPoliticalCommunityNumber = postalCommunitiesWithGivenName.get(0).getPoliticalCommunityNumber();
            List<PoliticalCommunity> politicalCommunities = getModel().getPoliticalCommunitiesByNumber(targetPoliticalCommunityNumber);
            if (politicalCommunities.size() > 0) {
                return politicalCommunities.get(0).getLastUpdate();
            }
            System.out.println("Found 0 political communities with number " + targetPoliticalCommunityNumber);
            return null;
        }
        System.out.println("Found 0 postal communities with name " + postalCommunityName);
        return null;

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
    public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
        Set<String> postalCommunitiesPoliticalCommunityNumbers = getModel().getPostalCommunities()
                .stream()
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        Set<String> politicalCommunitiesPoliticalCommunityNumbers = getModel().getPoliticalCommunities()
                .stream()
                .map(PoliticalCommunity::getNumber)
                .collect(Collectors.toSet());
        politicalCommunitiesPoliticalCommunityNumbers.removeAll(postalCommunitiesPoliticalCommunityNumbers);
        return politicalCommunitiesPoliticalCommunityNumbers.size();
    }
}
