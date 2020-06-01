package ch.aaap.assignment.util;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

public class CsvConverterUtil {

    public static PoliticalCommunity convertCSVPoliticalCommunityToPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCommunity) {
        return PoliticalCommunityImpl.builder()
                .number(csvPoliticalCommunity.getNumber())
                .name(csvPoliticalCommunity.getName())
                .shortName(csvPoliticalCommunity.getShortName())
                .lastUpdate(csvPoliticalCommunity.getLastUpdate())
                .build();
    }

    public static PostalCommunity convertCSVPostalCommunityToPostalCommunity(CSVPostalCommunity csvPostalCommunityCommunity) {
        return PostalCommunityImpl.builder()
                .zipCode(csvPostalCommunityCommunity.getZipCode())
                .zipCodeAddition(csvPostalCommunityCommunity.getZipCodeAddition())
                .name(csvPostalCommunityCommunity.getName())
                .build();
    }

    public static Canton getCantonFromCSVPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCommunity) {
        return CantonImpl.builder()
                .code(csvPoliticalCommunity.getCantonCode())
                .name(csvPoliticalCommunity.getCantonName())
                .build();
    }

    public static District getDistrictFromCSVPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCommunity) {
        return DistrictImpl.builder()
                .number(csvPoliticalCommunity.getNumber())
                .name(csvPoliticalCommunity.getDistrictName())
                .build();
    }
}
