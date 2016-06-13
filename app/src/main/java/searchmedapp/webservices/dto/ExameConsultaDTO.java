package searchmedapp.webservices.dto;

import java.util.Date;

public class ExameConsultaDTO {

	private Long id;
	private Date data;
	private String status;
	private ExameDTO exame;
	private UsuarioDTO usuario;
	private UsuarioDTO laboratorio;

	public ExameConsultaDTO() {
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

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public UsuarioDTO getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(UsuarioDTO laboratorio) {
		this.laboratorio = laboratorio;
	}

}
