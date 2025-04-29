import java.util.*;

public class Teatro_Moro {

    // Variables estáticas (estadísticas globales)
    static int totalEntradasVendidas = 0;
    static double ingresosTotales = 0;
    static int cantidadVentas = 0;

    // Variables de instancia (información persistente de ventas)
    static ArrayList<String> ubicaciones = new ArrayList<>();
    static ArrayList<Double> preciosBase = new ArrayList<>();
    static ArrayList<Double> valoresDescuento = new ArrayList<>();
    static ArrayList<Double> preciosFinales = new ArrayList<>();
    static ArrayList<String> descuentosAplicados = new ArrayList<>();

    // Datos del teatro
    static String nombreTeatro = "Teatro Moro";
    static int capacidadSala = 100;
    static int entradasDisponibles = 100;

    // Precios por ubicación
    static double precioVIP = 20000;
    static double precioPlatea = 15000;
    static double precioBalcon = 10000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== MENÚ PRINCIPAL - " + nombreTeatro + " =====");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Visualizar resumen de ventas");
            System.out.println("3. Generar boleta");
            System.out.println("4. Calcular ingresos totales");
            System.out.println("5. Salir");
            System.out.println("6. Cancelar una venta");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1:
                    ventaEntrada(scanner);
                    break;
                case 2:
                    mostrarResumenVentas();
                    break;
                case 3:
                    generarBoleta();
                    break;
                case 4:
                    calcularIngresosTotales();
                    break;
                case 5:
                    System.out.println("Gracias por su compra.");
                    continuar = false;
                    break;
                case 6:
                    cancelarVenta(scanner);
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    // Método para venta de entradas
    public static void ventaEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        String tipoUbicacion;
        double precioBase = 0;
        double descuento = 0;
        String tipoDescuento = "Ninguno";

        boolean ubicacionValida = false;
        do {
            System.out.print("Seleccione ubicación (VIP, Platea, Balcón): ");
            tipoUbicacion = scanner.nextLine().toUpperCase();

            switch (tipoUbicacion) {
                case "VIP":
                    precioBase = precioVIP;
                    ubicacionValida = true;
                    break;
                case "PLATEA":
                    precioBase = precioPlatea;
                    ubicacionValida = true;
                    break;
                case "BALCÓN":
                case "BALCON":
                    precioBase = precioBalcon;
                    ubicacionValida = true;
                    break;
                default:
                    System.out.println("Ubicación inválida. Intente nuevamente.");
            }
        } while (!ubicacionValida);

        String esEstudiante;
        do {
            System.out.print("¿Es estudiante? (s/n): ");
            esEstudiante = scanner.nextLine();
        } while (!esEstudiante.equalsIgnoreCase("s") && !esEstudiante.equalsIgnoreCase("n"));

        String esMayor;
        do {
            System.out.print("¿Es persona de la tercera edad? (s/n): ");
            esMayor = scanner.nextLine();
        } while (!esMayor.equalsIgnoreCase("s") && !esMayor.equalsIgnoreCase("n"));

        if (esEstudiante.equalsIgnoreCase("s")) {
            descuento = 0.10;
            tipoDescuento = "Estudiante";
        } else if (esMayor.equalsIgnoreCase("s")) {
            descuento = 0.15;
            tipoDescuento = "Tercera Edad";
        }

        double valorDescuento = precioBase * descuento;
        double precioFinal = precioBase - valorDescuento;

        // Guardar en listas
        ubicaciones.add(tipoUbicacion);
        preciosBase.add(precioBase);
        valoresDescuento.add(valorDescuento);
        preciosFinales.add(precioFinal);
        descuentosAplicados.add(tipoDescuento);

        // Actualizar estadísticas
        entradasDisponibles--;
        totalEntradasVendidas++;
        ingresosTotales += precioFinal;
        cantidadVentas++;

        System.out.printf("Entrada vendida con éxito. Precio final: $%.2f\n", precioFinal);
    }

    // Mostrar resumen de ventas
    public static void mostrarResumenVentas() {
        System.out.println("\n===== Resumen de Ventas =====");
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            for (int i = 0; i < ubicaciones.size(); i++) {
                System.out.printf("%d. Ubicación: %s | Precio final: $%.2f | Descuento: %s\n",
                        (i + 1), ubicaciones.get(i), preciosFinales.get(i), descuentosAplicados.get(i));
            }
        }
    }

    // Generar boletas detalladas
    public static void generarBoleta() {
        System.out.println("\n===== BOLETAS DETALLADAS =====");

        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas para generar boleta.");
            return;
        }

        for (int i = 0; i < ubicaciones.size(); i++) {
            System.out.println("------------- BOLETA #" + (i + 1) + " -------------");
            System.out.println("🎭 Teatro: " + nombreTeatro);
            System.out.println("🪑 Ubicación: " + ubicaciones.get(i));
            System.out.printf("💰 Costo base: $%.2f\n", preciosBase.get(i));
            System.out.printf("📉 Descuento (%s): -$%.2f\n", descuentosAplicados.get(i), valoresDescuento.get(i));
            System.out.printf("💸 Total a pagar: $%.2f\n", preciosFinales.get(i));
            System.out.println("🙏 ¡Gracias por apoyar al teatro nacional!");
            System.out.println("------------------------------------\n");
        }
    }

    // Calcular ingresos totales
    public static void calcularIngresosTotales() {
        System.out.println("\n===== Ingresos Totales =====");
        System.out.println("Entradas vendidas: " + totalEntradasVendidas);
        System.out.printf("Total recaudado: $%.2f\n", ingresosTotales);
    }

    // Cancelar una venta específica
    public static void cancelarVenta(Scanner scanner) {
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas que cancelar.");
            return;
        }

        mostrarResumenVentas();

        System.out.print("Ingrese el número de la venta a cancelar: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (index < 1 || index > ubicaciones.size()) {
            System.out.println("Número de venta inválido.");
            return;
        }

        index--; // Ajustar al índice del array

        // Revertir estadísticas
        entradasDisponibles++;
        totalEntradasVendidas--;
        ingresosTotales -= preciosFinales.get(index);
        cantidadVentas--;

        // Eliminar datos de la venta
        ubicaciones.remove(index);
        preciosBase.remove(index);
        valoresDescuento.remove(index);
        preciosFinales.remove(index);
        descuentosAplicados.remove(index);

        System.out.println("Venta cancelada exitosamente.");
    }
}
