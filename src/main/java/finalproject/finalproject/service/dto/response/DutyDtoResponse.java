package finalproject.finalproject.service.dto.response;

import lombok.*;
import org.mapstruct.Mapper;

@Builder
public class DutyDtoResponse {
    private String name;

    public DutyDtoResponse() {
    }

    public DutyDtoResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
