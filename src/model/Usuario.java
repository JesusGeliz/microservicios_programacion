package model;

public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private int edad;
    private String celular;
    private String correo;
    private String clave;
    private Rol rol;
    private Long idRestaurante;

    public Usuario(Long id, String nombre, String apellido, String documento, int edad, String celular, String correo, String clave, Rol rol) {
        this(id, nombre, apellido, documento, edad, celular, correo, clave, rol, null);
    }

    public Usuario(Long id, String nombre, String apellido, String documento, int edad, String celular, String correo, String clave, Rol rol, Long idRestaurante) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.edad = edad;
        this.celular = celular;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.idRestaurante = idRestaurante;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documento='" + documento + '\'' +
                ", edad=" + edad +
                ", celular='" + celular + '\'' +
                ", correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                ", rol=" + rol +
                ", idRestaurante=" + idRestaurante +
                '}';
    }
}
