package ss1.back.psi_firm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuccessDto {

    private Integer code;

    private String message;

    private Object responseObject;

}
