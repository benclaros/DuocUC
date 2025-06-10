package dominio.clientes;

public class Cliente implements InfoCliente {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rut;
    private String fechaNacimiento;
    private String domicilio;

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno,
                   String rut, String fechaNacimiento, String domicilio) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.rut = rut;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
    }

    @Override
    public void mostrarInformacionCliente() {
        System.out.println("Cliente: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
        System.out.println("RUT: " + rut);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Domicilio: " + domicilio);
    }
}
