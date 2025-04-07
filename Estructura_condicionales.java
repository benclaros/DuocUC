import java.util.Scanner;

public class Estructura_condicionales {

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            final int PRECIO_BASE = 100; // Precio base en cualquier zona
            int opcion;
            
            OUTER:
            for (;;) {
                System.out.println("\n----- MENÚ PRINCIPAL -----");
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 2 -> {
                        System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                        break OUTER;
                    }
                    case 1 -> {
                        // Paso 2: Compra de entradas
                        System.out.println("\n--- PLANO DEL TEATRO ---");
                        System.out.println("Zonas disponibles: A, B, C");
                        System.out.print("Ingrese la zona (A/B/C): ");
                        String zona = sc.next().toUpperCase();
                        if (!zona.equals("A") && !zona.equals("B") && !zona.equals("C")) {
                            System.out.println("Zona inválida. Intente nuevamente.");
                            continue;
                        }   // Edad y descuentos
                        System.out.print("Ingrese su edad: ");
                        int edad = sc.nextInt();
                        double descuento = 0;
                        if (edad >= 60)  {
                            descuento = 0.15; // 15% tercera edad
                            System.out.println("Descuento aplicado: 15% por tercera edad.");
                        } else {
                            System.out.print("¿Es estudiante? (s/n): ");
                            var estudiante = sc.next().toLowerCase().charAt(0);
                            
                            if (estudiante == 's') {
                                descuento = 0.10; // 10% estudiante
                                System.out.println("Descuento aplicado: 10% por estudiante.");
                            } else {
                                System.out.println("No se aplica descuento.");
                            }
                        }   // Cálculo con ciclo while
                        double precioFinal = PRECIO_BASE;
                        int intentos = 0;
                        while (intentos < 1) {
                            precioFinal = PRECIO_BASE - (PRECIO_BASE * descuento);
                            intentos++;
                        }   // Resumen de compra
                        System.out.println("\n--- RESUMEN DE COMPRA ---");
                        System.out.println("Ubicación del asiento: Zona " + zona);
                        System.out.println("Precio base: $" + PRECIO_BASE);
                        System.out.println("Descuento aplicado: " + (int)(descuento * 100) + "%");
                        System.out.println("Precio final a pagar: $" + precioFinal);
                    }
                    default -> System.out.println("Opción inválida.");
                }
            }
        }
    }
}
