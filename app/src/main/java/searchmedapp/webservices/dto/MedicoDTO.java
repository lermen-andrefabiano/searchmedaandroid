package searchmedapp.webservices.dto;

public class MedicoDTO {

	private Long id;
	private String crm;
	private UsuarioDTO usuario;
	//private List<MedicoEspecialidadeDTO> especialidades;

	public MedicoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

//	public List<MedicoEspecialidadeDTO> getEspecialidades() {
//		return especialidades;
//	}
//
//	public void setEspecialidades(List<MedicoEspecialidadeDTO> especialidades) {
//		this.especialidades = especialidades;
//	}

}
