package searchmedapp.webservices.dto;

public class ExameDTO {

	private Long id;
	private String descricao;

	public ExameDTO() {
	}

	public ExameDTO(Long id) {
		this.id = id;
	}

	public ExameDTO(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
