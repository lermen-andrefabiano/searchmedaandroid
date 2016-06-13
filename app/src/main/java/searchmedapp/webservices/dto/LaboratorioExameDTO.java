package searchmedapp.webservices.dto;

public class LaboratorioExameDTO {

	private Long id;

	private Long valor;

	private ExameDTO exame;

	private LaboratorioDTO laboratorio;

	public LaboratorioExameDTO() {
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

	public void setValorCobrado(Long valor) {
		this.valor = valor;
	}

	public ExameDTO getExame() {
		return exame;
	}

	public void setExame(ExameDTO exame) {
		this.exame = exame;
	}

	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(LaboratorioDTO laboratorio) {
		this.laboratorio = laboratorio;
	}

}
