package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewPacienteCuentaDto;
import ss1.back.psi_firm.repository.crud.CuentaPacienteCrud;
import ss1.back.psi_firm.repository.entities.CuentaPacienteEntity;
import ss1.back.psi_firm.repository.entities.PacienteEntity;

@Slf4j
@RequiredArgsConstructor
@Service
public class CuentaPacienteService {

    private final CuentaPacienteCrud cuentaPacienteCrud;
    private final PacienteService pacienteService;

    public void createCuentaPaciente(NewPacienteCuentaDto newPacienteCuentaDto){

        PacienteEntity pacienteEntity = pacienteService.getById(newPacienteCuentaDto.getDpi());

        CuentaPacienteEntity cuentaPacienteEntity = new CuentaPacienteEntity();
        cuentaPacienteEntity.setUsername(newPacienteCuentaDto.getUsername());
        cuentaPacienteEntity.setPassword(newPacienteCuentaDto.getPassword());
        cuentaPacienteEntity.setPaciente(pacienteEntity);

        cuentaPacienteCrud.save(cuentaPacienteEntity);
    }

}
