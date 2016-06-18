package searchmedapp.webservices.dto;

import java.util.List;

public class MedicoDTO {

	private Long id;
	private String crm;
	private String medicoNome;
	private String medicoEndereco;
	private List<MedicoEspecialidadeDTO> especialidades;
	private List<MedicoConvenioDTO> convenios;

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

	public String getMedicoNome() {
		return medicoNome;
	}

	public void setMedicoNome(String medicoNome) {
		this.medicoNome = medicoNome;
	}

	public String getMedicoEndereco() {
		return medicoEndereco;
	}

	public void setMedicoEndereco(String medicoEndereco) {
		this.medicoEndereco = medicoEndereco;
	}

	public List<MedicoEspecialidadeDTO> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<MedicoEspecialidadeDTO> especialidades) {
		this.especialidades = especialidades;
	}

	public List<MedicoConvenioDTO> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<MedicoConvenioDTO> convenios) {
		this.convenios = convenios;
	}

}
