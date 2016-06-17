package searchmedapp.webservices.dto;

import java.util.List;

public class MedicoDTO {

	private Long id;
	private String crm;	
	private List<MedicoEspecialidadeDTO> especialidades;

	public MedicoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}	

	public List<MedicoEspecialidadeDTO> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<MedicoEspecialidadeDTO> especialidades) {
		this.especialidades = especialidades;
	}

}
