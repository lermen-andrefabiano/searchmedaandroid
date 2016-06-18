package searchmedapp.webservices.dto;

public class MedicoConvenioDTO {

	private Long id;
	private String convenio;
	private Long medicoId;
	private String medicoNome;

	public MedicoConvenioDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public Long getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}

	public String getMedicoNome() {
		return medicoNome;
	}

	public void setMedicoNome(String medicoNome) {
		this.medicoNome = medicoNome;
	}

}
