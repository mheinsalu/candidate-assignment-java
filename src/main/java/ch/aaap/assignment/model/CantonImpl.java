package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CantonImpl implements Canton {

    String code;
    String name;

}
