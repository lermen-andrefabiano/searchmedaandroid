package searchmedapp.webservices.dto;

import java.util.Date;


public class ConsultaAgendaDTO {

	private Long id;
	private Date data;
	private String status;
	private ConsultaDTO consulta;

	public ConsultaAgendaDTO() {
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

	public ConsultaDTO getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaDTO consulta) {
		this.consulta = consulta;
	}

}
