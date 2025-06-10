package dominio.clientes.validacion;

public class ValidacionRUT {

    public static boolean esRutValido(String rut) {
        return rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$");
    }
}
