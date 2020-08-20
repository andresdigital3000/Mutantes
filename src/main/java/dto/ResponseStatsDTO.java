package dto;

public class ResponseStatsDTO {
    private int humans;
    private int mutants;
    private String mensaje;

    public int getHumans() {
        return this.humans;
    }

    public void setHumans(int humans) {
        this.humans = humans;
    }

    public int getMutants() {
        return this.mutants;
    }

    public void setMutants(int mutants) {
        this.mutants = mutants;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
