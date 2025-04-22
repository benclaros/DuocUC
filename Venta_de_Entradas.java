import java.util.*;

public class Venta_de_Entradas {
    private static final Scanner scanner = new Scanner(System.in);

    // Variables estáticas (estadísticas globales)
    private static int totalEntradasVendidas = 0;
    private static double ingresosTotales = 0.0;
    private static int reservasActivas = 0;

    // Variables de instancia (persistencia de entradas vendidas/reservadas)
    private final List<Entrada> entradasVendidas = new ArrayList<>();
    private final List<Entrada> entradasReservadas = new ArrayList<>();
    private final String nombreTeatro = "Teatro Moro";
    private final int capacidadSala = 100;

    // Variables locales (temporales)
    private String tipoEntradaTemporal;
    private double descuentoTemporal;
    private boolean esReservaTemporal;
    private String estadoTemporal;

    public static void main(String[] args) {
        Venta_de_Entradas sistema = new Venta_de_Entradas();
        sistema.mostrarMenu();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n----- MENÚ DE VENTAS TEATRO MORO -----");
            System.out.println("1. Reservar entradas");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Modificar una venta");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1 -> reservarEntrada();
                case 2 -> comprarEntrada();
                case 3 -> modificarVenta();
                case 4 -> imprimirBoleta();
                case 5 -> System.out.println("Gracias por usar el sistema.");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // A) Manejo de entradas
    public void reservarEntrada() {
        System.out.println("[DEBUG] Iniciando proceso de reserva.");
        Entrada entrada = crearEntrada(true);
        entradasReservadas.add(entrada);
        reservasActivas++;
        System.out.println("Entrada reservada con éxito. Recuerde confirmar la compra antes de 15 minutos.");
    }

    public void comprarEntrada() {
        System.out.println("[DEBUG] Iniciando proceso de compra.");
        Entrada entrada = crearEntrada(false);
        entradasVendidas.add(entrada);
        totalEntradasVendidas++;
        ingresosTotales += entrada.getPrecioFinal();
        System.out.println("Compra realizada con éxito.");
    }

    public void modificarVenta() {
        // Por simplicidad, este método puede buscar una entrada por ID y cambiar estado o asiento
        System.out.println("[DEBUG] Función de modificación aún no implementada.");
    }

    public void imprimirBoleta() {
        System.out.println("[DEBUG] Generando boleta...");
        for (Entrada entrada : entradasVendidas) {
            System.out.println(entrada);
        }
    }

    public Entrada crearEntrada(boolean esReserva) {
    // Validar tipo de entrada
    boolean tipoValido = false;
    while (!tipoValido) {
        System.out.print("Ingrese tipo de entrada (VIP, Platea Baja, Platea Alta, Palco): ");
        tipoEntradaTemporal = scanner.nextLine().toLowerCase();

        switch (tipoEntradaTemporal) {
            case "vip", "platea baja", "platea alta", "palco" -> tipoValido = true;
            default -> System.out.println("Tipo de entrada inválido. Intente nuevamente.");
        }
    }

    // Precio según tipo de entrada
    double precioBase = switch (tipoEntradaTemporal) {
        case "vip" -> 30000;
        case "platea baja" -> 20000;
        case "platea alta" -> 15000;
        case "palco" -> 25000;
        default -> 10000; // No se ejecuta por validación previa
    };

    // Validar asiento disponible
    String ubicacion;
    do {
        System.out.print("Ingrese ubicación del asiento: ");
        ubicacion = scanner.nextLine();
        if (!validarDisponibilidad(ubicacion)) {
            System.out.println("Asiento no disponible. Intente con otro.");
        }
    } while (!validarDisponibilidad(ubicacion));

    // Validar si es estudiante o tercera edad
    String respuesta;
    while (true) {
        System.out.print("¿Es estudiante o tercera edad? (si/no): ");
        respuesta = scanner.nextLine().toLowerCase();
        if (respuesta.equals("si") || respuesta.equals("no")) {
            break;
        } else {
            System.out.println("Respuesta inválida. Escriba 'si' o 'no'.");
        }
    }

    descuentoTemporal = (respuesta.equals("si")) ? 0.2 : 0.0;
    double precioFinal = precioBase * (1 - descuentoTemporal);
    estadoTemporal = esReserva ? "Reservado" : "Comprado";

    return new Entrada(tipoEntradaTemporal, ubicacion, precioFinal, estadoTemporal);
}


    public boolean validarDisponibilidad(String ubicacion) {
        // Punto de depuración 1: verificar si el asiento ya está tomado
        for (Entrada e : entradasVendidas) {
            if (e.getUbicacion().equalsIgnoreCase(ubicacion)) {
                System.out.println("[DEBUG] Asiento ya vendido.");
                return false;
            }
        }
        for (Entrada e : entradasReservadas) {
            if (e.getUbicacion().equalsIgnoreCase(ubicacion)) {
                System.out.println("[DEBUG] Asiento ya reservado.");
                return false;
            }
        }
        return true;
    }
}
