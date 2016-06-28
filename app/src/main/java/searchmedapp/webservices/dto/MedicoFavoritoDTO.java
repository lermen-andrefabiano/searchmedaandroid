package searchmedapp.webservices.dto;


public class MedicoFavoritoDTO {

	private Long id;
	private MedicoDTO medico;

	public MedicoFavoritoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicoDTO getMedico() {
		return medico;
	}

	public void setMedico(MedicoDTO medico) {
		this.medico = medico;
	}

}
