package ss1.back.psi_firm.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteRecoveryPasswordDto {


    private String username;

    private String newPassword;

    private String confirmNewPassword;

}
