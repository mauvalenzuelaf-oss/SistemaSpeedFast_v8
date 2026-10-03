package modelo;

/**
 * Representa un repartidor de SpeedFast.
 */
public class Repartidor {

    // Atributos
    private int id;
    private String nombre;

    /**
     * Constructor para registrar un repartidor nuevo.
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para recuperar un repartidor desde la base de datos.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}