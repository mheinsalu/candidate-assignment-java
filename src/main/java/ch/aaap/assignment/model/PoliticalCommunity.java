package ch.aaap.assignment.model;

import java.time.LocalDate;

public interface PoliticalCommunity {

  public String getNumber();

  public String getName();

  public String getShortName();

  public LocalDate getLastUpdate();

  // TODO add more features here representing the relations

  public String getDistrictNumber();

  public String getCantonCode();

}
