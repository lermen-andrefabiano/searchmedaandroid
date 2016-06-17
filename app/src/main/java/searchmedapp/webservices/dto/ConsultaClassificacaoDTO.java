package searchmedapp.webservices.dto;

public class ConsultaClassificacaoDTO {

	private Long id;
	private int nota;
	private String recomendacao;
	private ConsultaDTO consulta;

	public ConsultaClassificacaoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}

	public ConsultaDTO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaDTO consulta) {
		this.consulta = consulta;
	}

}
