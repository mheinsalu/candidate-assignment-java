package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

    String number;
    String name;
    String shortName;
    LocalDate lastUpdate;
    String cantonCode;
    String districtName;

}
