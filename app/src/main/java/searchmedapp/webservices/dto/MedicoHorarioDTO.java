package searchmedapp.webservices.dto;

public class MedicoHorarioDTO {

    private Long id;
    private String dia;
    private Long medicoId;
    private String inicio;
    private String fim;
    private Boolean orderChegada;
    private Boolean repetirHorario;

    public MedicoHorarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }


    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
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
