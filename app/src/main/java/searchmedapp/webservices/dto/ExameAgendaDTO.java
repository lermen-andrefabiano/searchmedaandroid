package searchmedapp.webservices.dto;

import java.util.Date;



public class ExameAgendaDTO {

	private Long id;
	private Date data;
	private String status;
	private ConsultaExameDTO consultaExame;
	private LaboratorioDTO laboratorio;

	public ExameAgendaDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ConsultaExameDTO getConsultaExame() {
		return consultaExame;
	}

	public void setConsultaExame(ConsultaExameDTO consultaExame) {
		this.consultaExame = consultaExame;
	}

	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(LaboratorioDTO laboratorio) {
		this.laboratorio = laboratorio;
	}

}
