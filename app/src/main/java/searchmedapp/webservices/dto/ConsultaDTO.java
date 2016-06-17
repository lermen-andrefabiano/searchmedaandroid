package searchmedapp.webservices.dto;

import java.util.Date;
import java.util.List;


public class ConsultaDTO {

	private Long id;
	private Date data;
	private String status;
	private String endereco;
	private EspecialidadeDTO especialidade;
	private UsuarioDTO usuario;
	private UsuarioDTO medico;
	private ConsultaClassificacaoDTO classificacao;
	private List<ConsultaExameDTO> exames;

	public ConsultaDTO() {
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public EspecialidadeDTO getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeDTO especialidade) {
		this.especialidade = especialidade;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public UsuarioDTO getMedico() {
		return medico;
	}

	public void setMedico(UsuarioDTO medico) {
		this.medico = medico;
	}

	public ConsultaClassificacaoDTO getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ConsultaClassificacaoDTO classificacao) {
		this.classificacao = classificacao;
	}

	public List<ConsultaExameDTO> getExames() {
		return exames;
	}

	public void setExames(List<ConsultaExameDTO> exames) {
		this.exames = exames;
	}
}
