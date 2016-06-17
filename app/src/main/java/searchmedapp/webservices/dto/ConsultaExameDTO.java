package searchmedapp.webservices.dto;

import java.util.Date;


public class ConsultaExameDTO {

	private Long id;
	private Date data;
	private String status;
	private ExameDTO exame;

	public ConsultaExameDTO() {
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

	public ExameDTO getExame() {
		return exame;
	}

	public void setExame(ExameDTO exame) {
		this.exame = exame;
	}

}
