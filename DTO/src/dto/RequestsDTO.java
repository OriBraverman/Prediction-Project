package dto;

import java.util.List;

public class RequestsDTO {
    List<RequestDTO> requests;

    public RequestsDTO(List<RequestDTO> requests) {
        this.requests = requests;
    }

    public List<RequestDTO> getRequests() {
        return requests;
    }
}
