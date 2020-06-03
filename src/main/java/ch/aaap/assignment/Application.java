package ch.aaap.assignment;

import ch.aaap.assignment.helper.ModelHelper;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelImpl;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Märten Heinsalu
 */
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
        long count = model.getDistricts()
                .stream()
                .filter(district -> district.getCantonCode().equalsIgnoreCase(cantonCode)).count();
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
        Set<String> politicalCommunityNumbersInZipCode = ModelHelper.getPoliticalCommunityNumbersInZipCode(model.getPostalCommunities(), zipCode);
        Set<PoliticalCommunity> politicalCommunitiesInZipCode = ModelHelper
                .getPoliticalCommunitiesMatchingSetOfPoliticalCommunityNumbers(model.getPoliticalCommunities(), politicalCommunityNumbersInZipCode);
        Set<String> districtNumbers = ModelHelper.getDistrictNumbersOfPoliticalCommunities(politicalCommunitiesInZipCode);
        return ModelHelper.getDistrictNamesFromDistrictNumbers(model.getDistricts(), districtNumbers);
    }


    /**
     * @param postalCommunityName name
     * @return lastUpdate of the political community by a given postal community name
     */
    public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(String postalCommunityName) {
        Set<PostalCommunity> postalCommunitiesWithTargetName = ModelHelper.getPostalCommunitiesBasedOnName(model.getPostalCommunities(), postalCommunityName);
        // getPostalCommunitiesBasedOnName throws IllegalArgumentException if none are found
        PostalCommunity firstPostalCommunity = postalCommunitiesWithTargetName.iterator().next();
        String firstPoliticalCommunityNumber;
        if (firstPostalCommunity.getPoliticalCommunityNumbers().size() > 0) {
            firstPoliticalCommunityNumber = firstPostalCommunity.getPoliticalCommunityNumbers().iterator().next();
        } else {
            throw new IllegalArgumentException("Found 0 political communities in  postal community with name " + postalCommunityName);
        }
        PoliticalCommunity politicalCommunity = ModelHelper.getPoliticalCommunityFromNumber(model.getPoliticalCommunities(), firstPoliticalCommunityNumber);
        return politicalCommunity.getLastUpdate();
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
    public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
        Set<String> postalCommunitiesPoliticalCommunityNumbers = ModelHelper.getAllPoliticalCommunityNumbersFromPostalCommunities(model.getPostalCommunities());
        Set<String> politicalCommunitiesPoliticalCommunityNumbers = ModelHelper.getAllPoliticalCommunityNumbersFromPoliticalCommunities(model.getPoliticalCommunities());
        politicalCommunitiesPoliticalCommunityNumbers.removeAll(postalCommunitiesPoliticalCommunityNumbers);
        long count = politicalCommunitiesPoliticalCommunityNumbers.size();
        if (count > 0) {
            return count;
        }
        throw new IllegalArgumentException("Found 0 political communities without postal communities");
    }
}
