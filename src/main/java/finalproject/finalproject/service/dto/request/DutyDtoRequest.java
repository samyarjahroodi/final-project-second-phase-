package finalproject.finalproject.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
public class DutyDtoRequest {
    private String name;

    public DutyDtoRequest(String name) {
        this.name = name;
    }

    public DutyDtoRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
