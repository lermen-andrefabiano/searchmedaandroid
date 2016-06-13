package searchmedapp.webservices.dto;

import java.util.Date;


public class ExameAgendaDTO {

	private Long id;
	private Date data;
	private String status;
	private ExameConsultaDTO exameConsulta;

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

	public ExameConsultaDTO getExameConsulta() {
		return exameConsulta;
	}

	public void setExameConsulta(ExameConsultaDTO exameConsulta) {
		this.exameConsulta = exameConsulta;
	}

}
