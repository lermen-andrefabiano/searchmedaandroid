package searchmedapp.webservices.dto;

import java.util.Date;




public class InformacaoNotificarDTO {

	private String observacao;

	private Date agendamento;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Date agendamento) {
		this.agendamento = agendamento;
	}

}
