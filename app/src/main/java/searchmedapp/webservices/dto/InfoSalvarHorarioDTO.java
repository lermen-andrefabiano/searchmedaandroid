package searchmedapp.webservices.dto;


import java.util.ArrayList;
import java.util.List;

public class InfoSalvarHorarioDTO {

	private String horarioId;
	private String dia;
	private String inicio;
	private String fim;
	private Boolean orderChegada;
	private Boolean repetirHorario;

	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public Boolean getOrderChegada() {
		return orderChegada;
	}

	public void setOrderChegada(Boolean orderChegada) {
		this.orderChegada = orderChegada;
	}

	public Boolean getRepetirHorario() {
		return repetirHorario;
	}

	public void setRepetirHorario(Boolean repetirHorario) {
		this.repetirHorario = repetirHorario;
	}

}
