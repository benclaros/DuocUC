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

public class EntradasDeTeatro {

    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_SALA = 100;

    private static final double PRECIO_VIP = 20000;
    private static final double PRECIO_PLATEA_ALTA = 17000;
    private static final double PRECIO_PLATEA_BAJA = 15000;
    private static final double PRECIO_GALERIA = 10000;
    private static final double PRECIO_PALCO = 22000;

    private List<Entrada> entradasVendidas = new ArrayList<>();
    private int entradasDisponibles = CAPACIDAD_SALA;
    private double ingresosTotales = 0;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new EntradasDeTeatro().iniciar();
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
                case 5 -> {
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
        System.out.println("5. Salir");
    }

    private void venderEntrada() {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        int cantidad = leerEntero("¿Cuántas entradas desea comprar? ");

        if (cantidad <= 0 || cantidad > entradasDisponibles) {
            System.out.println("Cantidad inválida. Entradas disponibles: " + entradasDisponibles);
            return;
        }

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\n--- Entrada " + i + " ---");
            String ubicacion = leerUbicacion();
            double precioBase = obtenerPrecioPorUbicacion(ubicacion);

            int edad = leerEntero("Ingrese la edad del comprador: ");
            System.out.print("Ingrese el género (M/F): ");
            String genero = scanner.nextLine().trim().toUpperCase();

            double descuento = 0;
            String tipoDescuento = "Ninguno";

            if (edad >= 1 && edad <= 10) {
                descuento = 0.10;
                tipoDescuento = "Niño";
            }
            if (genero.equals("F")) {
                if (0.20 > descuento) {
                    descuento = 0.20;
                    tipoDescuento = "Mujer";
                }
            }
            if (edad >= 18 && edad <= 25) {
                if (0.15 > descuento) {
                    descuento = 0.15;
                    tipoDescuento = "Estudiante";
                }
            }
            if (edad >= 60 && edad <= 99) {
                if (0.25 > descuento) {
                    descuento = 0.25;
                    tipoDescuento = "Adulto Mayor";
                }
            }

            double valorDescuento = precioBase * descuento;
            Entrada entrada = new Entrada(ubicacion, precioBase, tipoDescuento, valorDescuento);

            entradasVendidas.add(entrada);
            entradasDisponibles--;
            ingresosTotales += entrada.getPrecioFinal();

            guardarVentaEnArchivo(entrada);

            System.out.printf("Entrada vendida con éxito. Precio final: $%.2f\n", entrada.getPrecioFinal());
        }
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
        System.out.println("Entradas disponibles: " + entradasDisponibles);
        System.out.printf("Total recaudado: $%.2f\n", ingresosTotales);
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensaje);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private String leerUbicacion() {
        String ubicacion = "";
        boolean valida = false;

        while (!valida) {
            System.out.print("Seleccione ubicación (VIP, Platea Alta, Platea Baja, Galería, Palco): ");
            ubicacion = scanner.nextLine().trim().toUpperCase();
            switch (ubicacion) {
                case "VIP", "PLATEA ALTA", "PLATEA BAJA", "GALERÍA", "PALCO" -> valida = true;
                default -> System.out.println("Ubicación inválida. Intente nuevamente.");
            }
        }

        return ubicacion;
    }

    private double obtenerPrecioPorUbicacion(String ubicacion) {
        return switch (ubicacion) {
            case "VIP" -> PRECIO_VIP;
            case "PLATEA ALTA" -> PRECIO_PLATEA_ALTA;
            case "PLATEA BAJA" -> PRECIO_PLATEA_BAJA;
            case "GALERÍA" -> PRECIO_GALERIA;
            case "PALCO" -> PRECIO_PALCO;
            default -> 0;
        };
    }
}