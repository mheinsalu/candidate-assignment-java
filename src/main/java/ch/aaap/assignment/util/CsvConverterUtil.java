package ch.aaap.assignment.util;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

public class CsvConverterUtil {

    public static PoliticalCommunity getPoliticalCommunityFromCSVPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCommunity) {
        return PoliticalCommunityImpl.builder()
                .number(csvPoliticalCommunity.getNumber())
                .name(csvPoliticalCommunity.getName())
                .shortName(csvPoliticalCommunity.getShortName())
                .lastUpdate(csvPoliticalCommunity.getLastUpdate())
                .cantonCode(csvPoliticalCommunity.getCantonCode())
                .districtName(csvPoliticalCommunity.getDistrictName())
                .build();
    }

    public static PostalCommunity getPostalCommunityFromCSVPostalCommunity(CSVPostalCommunity csvPostalCommunityCommunity) {
        return PostalCommunityImpl.builder()
                .zipCode(csvPostalCommunityCommunity.getZipCode())
                .zipCodeAddition(csvPostalCommunityCommunity.getZipCodeAddition())
                .name(csvPostalCommunityCommunity.getName())
                .politicalCommunityNumber(csvPostalCommunityCommunity.getPoliticalCommunityNumber())
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
