package model;

public class RestauranteResumen {
    private String nombre;
    private String urlLogo;

    public RestauranteResumen(String nombre, String urlLogo) {
        this.nombre = nombre;
        this.urlLogo = urlLogo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }
}
