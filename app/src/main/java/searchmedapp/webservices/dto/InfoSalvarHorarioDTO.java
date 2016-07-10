package searchmedapp.webservices.dto;


import java.util.ArrayList;
import java.util.List;

public class InfoSalvarHorarioDTO {

	private List<MedicoHorarioDTO> horarios;

	private boolean repetirHorario;

	public InfoSalvarHorarioDTO(){
		this.horarios = new ArrayList<MedicoHorarioDTO>();
	}

	public List<MedicoHorarioDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<MedicoHorarioDTO> horarios) {
		this.horarios = horarios;
	}

	public boolean getRepetirHorario() {
		return repetirHorario;
	}

	public void setRepetirHorario(boolean repetirHorario) {
		this.repetirHorario = repetirHorario;
	}

}
