package cat.deim.asm.myapplication.model;

public class BikeModel {
    private String uuid;
    private String marca;
    private int activo;

    public BikeModel(String uuid, String marca, int activo) {
        this.uuid = uuid;
        this.marca = marca;
        this.activo = activo;
    }

    public String getUuid() { return uuid; }
    public String getMarca() { return marca; }
    public int getActivo() { return activo; }

    public void setUuid(String uuid) { this.uuid = uuid; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setActivo(int activo) { this.activo = activo; }
}