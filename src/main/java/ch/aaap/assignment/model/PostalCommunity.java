package ch.aaap.assignment.model;

import java.util.Set;

public interface PostalCommunity {

    public String getZipCode();

    public String getZipCodeAddition();

    public String getName();

    public Set<String> getPoliticalCommunityNumbers();

    public void setPoliticalCommunityNumbers(Set<String> politicalCommunityNumbers);
}
