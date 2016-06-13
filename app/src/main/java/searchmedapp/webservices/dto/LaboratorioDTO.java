package searchmedapp.webservices.dto;

import java.util.List;


public class LaboratorioDTO {
	
	private Long id;
	private String cnpj;
	//private List<LaboratorioExameDTO> exames;

	public LaboratorioDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

//	public List<LaboratorioExameDTO> getExames() {
//		return exames;
//	}
//
//	public void setExames(List<LaboratorioExameDTO> exames) {
//		this.exames = exames;
//	}

}
