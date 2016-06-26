package searchmedapp.webservices.dto;

import java.util.Date;


public class ConsultaExameDTO {

	private Long id;
	private Long data;
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

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
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
