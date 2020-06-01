package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CantonImpl implements Canton {

    String code;
    String name;

    @Override
    public boolean equals(Object o) {
        // TODO: this is not called when building set! google set of custom
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CantonImpl canton = (CantonImpl) o;

        return code.equals(canton.code);
    }
}
