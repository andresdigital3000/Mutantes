package dto;

import java.util.Objects;

public class ResponseDTO {
    private String code;
    private String message;
    private ResponseInsertDTO responseInsertDTO;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO that = (ResponseDTO) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    public ResponseInsertDTO getResponseInsertDTO() {
        return responseInsertDTO;
    }

    public void setResponseInsertDTO(ResponseInsertDTO responseInsertDTO) {
        this.responseInsertDTO = responseInsertDTO;
    }
}
