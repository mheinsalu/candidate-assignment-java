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

        model = new ModelImpl(politicalCommunities, postalCommunities);
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
        long count = model.getPoliticalCommunities()
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
        int count = model.getDistricts()
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
        long count = model.getPoliticalCommunities()
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
        Set<String> politicalCommunityNumbersInZipCode = model.getPostalCommunities()
                .stream()
                .filter(postalCommunity -> postalCommunity.getZipCode().equalsIgnoreCase(zipCode))
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        Set<String> districtNumbers = model.getPoliticalCommunities()
                .stream()
                .filter(politicalCommunity -> politicalCommunityNumbersInZipCode.contains(politicalCommunity.getNumber()))
                .map(PoliticalCommunity::getDistrictNumber)
                .collect(Collectors.toSet());
        return model.getDistricts()
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
        List<PostalCommunity> postalCommunitiesWithTargetName = model.getPostalCommunities()
                .stream()
                .filter(postalCommunity -> postalCommunity.getName().equalsIgnoreCase(postalCommunityName))
                .collect(Collectors.toList());
        if (postalCommunitiesWithTargetName.size() > 0) { // TODO: think about this method. post to pol bijection? I can remove this if
            String politicalCommunityNumber = postalCommunitiesWithTargetName.get(0).getPoliticalCommunityNumber();
            List<PoliticalCommunity> politicalCommunities = model.getPoliticalCommunities()
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
        return model.getCantons().size();
    }

    /**
     * https://de.wikipedia.org/wiki/Kommunanz
     *
     * @return amount of political communities without postal communities
     */
    public long getAmountOfPoliticalCommunityWithoutPostalCommunities() { // TODO: new method politicalNumberToPostalCommunity
        Set<String> postalCommunitiesPoliticalCommunityNumbers = model.getPostalCommunities()
                .stream()
                .map(PostalCommunity::getPoliticalCommunityNumber)
                .collect(Collectors.toSet());
        Set<String> politicalCommunitiesPoliticalCommunityNumbers = model.getPoliticalCommunities()
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
