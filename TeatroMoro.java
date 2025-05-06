import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Entrada {
    private String ubicacion;
    private double precioBase;
    private double descuentoAplicado;
    private String tipoDescuento;
    private double precioFinal;

    public Entrada(String ubicacion, double precioBase, String tipoDescuento, double descuentoAplicado) {
        this.ubicacion = ubicacion;
        this.precioBase = precioBase;
        this.tipoDescuento = tipoDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.precioFinal = precioBase - descuentoAplicado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public String formatoVenta() {
        return ubicacion + "," + precioBase + "," + tipoDescuento + "," + descuentoAplicado + "," + precioFinal;
    }

    public void mostrarBoleta(int numero, String nombreTeatro) {
        System.out.println("------------- BOLETA #" + numero + " -------------");
        System.out.println("🎭 Teatro: " + nombreTeatro);
        System.out.println("🪑 Ubicación: " + ubicacion);
        System.out.printf("💰 Costo base: $%.2f\n", precioBase);
        System.out.printf("📉 Descuento (%s): -$%.2f\n", tipoDescuento, descuentoAplicado);
        System.out.printf("💸 Total a pagar: $%.2f\n", precioFinal);
        System.out.println("🙏 ¡Gracias por apoyar al teatro nacional!");
        System.out.println("------------------------------------\n");
    }
}

public class TeatroMoro {

    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_SALA = 100;

    private static final double PRECIO_VIP = 20000;
    private static final double PRECIO_PLATEA = 15000;
    private static final double PRECIO_BALCON = 10000;

    private List<Entrada> entradasVendidas = new ArrayList<>();
    private int entradasDisponibles = CAPACIDAD_SALA;
    private double ingresosTotales = 0;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new TeatroMoro().iniciar();
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> venderEntrada();
                case 2 -> mostrarResumenVentas();
                case 3 -> generarBoletas();
                case 4 -> mostrarIngresosTotales();
                case 5 -> mostrarEstadisticasPorTipo();
                case 6 -> {
                    System.out.println("Gracias por su compra.");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL - " + NOMBRE_TEATRO + " =====");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Visualizar resumen de ventas");
        System.out.println("3. Generar boleta");
        System.out.println("4. Calcular ingresos totales");
        System.out.println("5. Estadísticas por tipo de entrada");
        System.out.println("6. Salir");
    }

    private void venderEntrada() {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        String ubicacion = leerUbicacion();
        double precioBase = obtenerPrecioPorUbicacion(ubicacion);
        String tipoDescuento = "Ninguno";
        double descuento = 0;

        if (leerSiNo("¿Es estudiante?")) {
            descuento = 0.10;
            tipoDescuento = "Estudiante";
        } else if (leerSiNo("¿Es persona de la tercera edad?")) {
            descuento = 0.15;
            tipoDescuento = "Tercera Edad";
        }

        double valorDescuento = precioBase * descuento;
        Entrada entrada = new Entrada(ubicacion, precioBase, tipoDescuento, valorDescuento);

        entradasVendidas.add(entrada);
        entradasDisponibles--;
        ingresosTotales += entrada.getPrecioFinal();

        guardarVentaEnArchivo(entrada);

        // Mostrar boleta automáticamente después de la venta
        entrada.mostrarBoleta(entradasVendidas.size(), NOMBRE_TEATRO);
    }

    private void guardarVentaEnArchivo(Entrada entrada) {
        try (FileWriter fw = new FileWriter("ventas.txt", true)) {
            fw.write(entrada.formatoVenta() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar la venta en el archivo.");
        }
    }

    private void mostrarResumenVentas() {
        System.out.println("\n===== Resumen de Ventas =====");
        if (entradasVendidas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            int i = 1;
            for (Entrada entrada : entradasVendidas) {
                System.out.printf("%d. Ubicación: %s | Precio final: $%.2f | Descuento: %s\n",
                        i++, entrada.getUbicacion(), entrada.getPrecioFinal(), entrada.getTipoDescuento());
            }
        }
    }

    private void generarBoletas() {
        System.out.println("\n===== BOLETAS DETALLADAS =====");
        if (entradasVendidas.isEmpty()) {
            System.out.println("No hay ventas para generar boleta.");
        } else {
            int i = 1;
            for (Entrada entrada : entradasVendidas) {
                entrada.mostrarBoleta(i++, NOMBRE_TEATRO);
            }
        }
    }

    private void mostrarIngresosTotales() {
        System.out.println("\n===== Ingresos Totales =====");
        System.out.println("Entradas vendidas: " + entradasVendidas.size());
        System.out.printf("Total recaudado: $%.2f\n", ingresosTotales);
    }

    private void mostrarEstadisticasPorTipo() {
        System.out.println("\n===== Estadísticas por Tipo de Entrada =====");

        Map<String, Integer> cantidadPorTipo = new HashMap<>();
        Map<String, Double> ingresosPorTipo = new HashMap<>();

        for (Entrada entrada : entradasVendidas) {
            String tipo = entrada.getUbicacion();
            cantidadPorTipo.put(tipo, cantidadPorTipo.getOrDefault(tipo, 0) + 1);
            ingresosPorTipo.put(tipo, ingresosPorTipo.getOrDefault(tipo, 0.0) + entrada.getPrecioFinal());
        }

        for (String tipo : List.of("VIP", "PLATEA", "BALCÓN")) {
            int cantidad = cantidadPorTipo.getOrDefault(tipo, 0);
            double ingresos = ingresosPorTipo.getOrDefault(tipo, 0.0);
            System.out.printf("- %s: %d entradas vendidas | Ingresos: $%.2f\n", tipo, cantidad, ingresos);
        }
    }

    // ===================== MÉTODOS AUXILIARES =====================

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensaje);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); 
        return valor;
    }

    private boolean leerSiNo(String mensaje) {
        String respuesta;
        do {
            System.out.print(mensaje + " (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
        } while (!respuesta.equals("s") && !respuesta.equals("n"));
        return respuesta.equals("s");
    }

    private String leerUbicacion() {
        String ubicacion = "";
        boolean valida = false;

        while (!valida) {
            System.out.print("Seleccione ubicación (VIP, Platea, Balcón): ");
            ubicacion = scanner.nextLine().trim().toUpperCase();
            switch (ubicacion) {
                case "VIP", "PLATEA", "BALCON", "BALCÓN" -> valida = true;
                default -> System.out.println("Ubicación inválida. Intente nuevamente.");
            }
        }

        return ubicacion.equals("BALCON") ? "BALCÓN" : ubicacion;
    }

    private double obtenerPrecioPorUbicacion(String ubicacion) {
        return switch (ubicacion) {
            case "VIP" -> PRECIO_VIP;
            case "PLATEA" -> PRECIO_PLATEA;
            case "BALCÓN" -> PRECIO_BALCON;
            default -> 0;
        };
    }
}
