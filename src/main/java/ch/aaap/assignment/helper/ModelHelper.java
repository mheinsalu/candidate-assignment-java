package ch.aaap.assignment.helper;

import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Märten Heinsalu
 */
public class ModelHelper {

    public static Set<String> getPoliticalCommunityNumbersInZipCode(Set<PostalCommunity> postalCommunities, String zipCode) {
        Set<String> numbers = new HashSet<>(postalCommunities.size());
        postalCommunities
                .stream()
                .filter(postalCommunity -> postalCommunity.getZipCode().equalsIgnoreCase(zipCode))
                .forEach(postalCommunity -> numbers.addAll(postalCommunity.getPoliticalCommunityNumbers()));
        return numbers;
    }

    public static Set<PoliticalCommunity> getPoliticalCommunitiesMatchingSetOfPoliticalCommunityNumbers(Set<PoliticalCommunity> politicalCommunities, Set<String> numbers) {
        return politicalCommunities
                .stream()
                .filter(politicalCommunity -> numbers.contains(politicalCommunity.getNumber()))
                .collect(Collectors.toSet());
    }

    public static Set<String> getDistrictNumbersOfPoliticalCommunities(Set<PoliticalCommunity> politicalCommunities) {
        return politicalCommunities
                .stream()
                .map(PoliticalCommunity::getDistrictNumber)
                .collect(Collectors.toSet());
    }

    public static Set<String> getDistrictNamesFromDistrictNumbers(Set<District> districts, Set<String> districtNumbers) {
        return districts
                .stream()
                .filter(district -> districtNumbers.contains(district.getNumber()))
                .map(District::getName)
                .collect(Collectors.toSet());
    }


    public static Set<PostalCommunity> getPostalCommunitiesBasedOnName(Set<PostalCommunity> postalCommunities, String postalCommunityName) {
        Set<PostalCommunity> foundPostalCommunities = postalCommunities
                .stream()
                .filter(postalCommunity -> postalCommunity.getName().equalsIgnoreCase(postalCommunityName))
                .collect(Collectors.toSet());
        if (foundPostalCommunities.size() > 0) {
            return foundPostalCommunities;
        }
        throw new IllegalArgumentException("Found 0 postal communities with postal community name " + postalCommunityName);
    }

    public static PoliticalCommunity getPoliticalCommunityFromNumber(Set<PoliticalCommunity> politicalCommunities, String number) {
        List<PoliticalCommunity> foundCommunities = politicalCommunities.stream()
                .filter(politicalCommunity -> politicalCommunity.getNumber().equalsIgnoreCase(number))
                .collect(Collectors.toList());
        if (foundCommunities.size() > 0) {
            return foundCommunities.get(0);
        }
        throw new IllegalArgumentException("Found 0 political communities with political community number " + number);
    }

    public static Set<String> getAllPoliticalCommunityNumbersFromPostalCommunities(Set<PostalCommunity> postalCommunities) {
        Set<String> numbers = new HashSet<>(postalCommunities.size());
        postalCommunities.forEach(postalCommunity -> numbers.addAll(postalCommunity.getPoliticalCommunityNumbers()));
        return numbers;
    }

    public static Set<String> getAllPoliticalCommunityNumbersFromPoliticalCommunities(Set<PoliticalCommunity> politicalCommunities) {
        return politicalCommunities
                .stream()
                .map(PoliticalCommunity::getNumber)
                .collect(Collectors.toSet());
    }
}
