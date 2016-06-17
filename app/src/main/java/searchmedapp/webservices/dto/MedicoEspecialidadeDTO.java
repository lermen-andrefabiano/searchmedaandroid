package searchmedapp.webservices.dto;


public class MedicoEspecialidadeDTO {

	private Long id;
	private Long valor;
	private EspecialidadeDTO especialidade;
	private Long medicoId;
	private String medicoNome;

	public MedicoEspecialidadeDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public EspecialidadeDTO getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeDTO especialidade) {
		this.especialidade = especialidade;
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
