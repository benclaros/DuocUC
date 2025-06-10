package app;

import dominio.clientes.Cliente;
import dominio.clientes.validacion.ValidacionRUT;
import dominio.cuentas.*;

import java.util.Scanner;
import java.util.Random;

public class MainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("=== CREACIÓN DE CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Apellido Materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("RUT (formato 12.345.678-9): ");
        String rut = scanner.nextLine();

        while (!ValidacionRUT.esRutValido(rut)) {
            System.out.print("RUT inválido. Intente de nuevo: ");
            rut = scanner.nextLine();
        }

        System.out.print("Fecha de nacimiento (DD/MM/AAAA): ");
        String fechaNacimiento = scanner.nextLine();
        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, apellidoPaterno, apellidoMaterno, rut, fechaNacimiento, domicilio);

        // === Selección del tipo de cuenta ===
        System.out.println("\nSeleccione el tipo de cuenta que desea abrir:");
        System.out.println("1. Cuenta Corriente");
        System.out.println("2. Cuenta de Ahorros");
        System.out.println("3. Cuenta Digital");
        System.out.print("Opción: ");
        int tipoCuenta = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // === Generar número de cuenta aleatorio de 8 dígitos ===
        String numeroCuenta = String.format("%08d", random.nextInt(100000000));
        double saldoInicial = 0;
        CuentaBancaria cuenta = null;

        switch (tipoCuenta) {
            case 1:
                cuenta = new CuentaCorriente(numeroCuenta, saldoInicial);
                break;
            case 2:
                cuenta = new CuentaAhorro(numeroCuenta, saldoInicial);
                break;
            case 3:
                cuenta = new CuentaDigital(numeroCuenta, saldoInicial);
                break;
            default:
                System.out.println("Tipo de cuenta inválido. Se asignará Cuenta Corriente por defecto.");
                cuenta = new CuentaCorriente(numeroCuenta, saldoInicial);
        }

        // === Mostrar datos de la cuenta creada ===
        System.out.println("\n¡Cuenta creada exitosamente!");
        System.out.println("Número de cuenta asignado: " + numeroCuenta);
        System.out.println("Si olvida su número de cuenta, por favor contacte al soporte del banco.\n");

        // === Menú principal ===
        int opcion = -1;

while (opcion != 0) {
    System.out.println("\n=== MENÚ PRINCIPAL ===");
    System.out.println("1. Ver información del cliente");
    System.out.println("2. Ver saldo");
    System.out.println("3. Girar dinero");
    System.out.println("4. Calcular interés");
    System.out.println("5. Depositar dinero");
    System.out.println("0. Salir");
    System.out.print("Seleccione una opción: ");

    if (scanner.hasNextInt()) {
        opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            case 1:
                cliente.mostrarInformacionCliente();
                break;
            case 2:
                System.out.println("Saldo actual: $" + cuenta.getSaldo());
                break;
            case 3:
                System.out.print("Ingrese monto a girar: ");
                if (scanner.hasNextDouble()) {
                    double monto = scanner.nextDouble();
                    scanner.nextLine();
                    if (cuenta.girar(monto)) {
                        System.out.println("Giro realizado.");
                    } else {
                        System.out.println("Fondos insuficientes.");
                    }
                } else {
                    System.out.println("Monto inválido.");
                    scanner.nextLine();
                }
                break;
            case 4:
                System.out.println("Interés generado: $" + cuenta.calcularInteres());
                break;
            case 5:
                System.out.print("Ingrese monto a depositar: ");
                if (scanner.hasNextDouble()) {
                    double deposito = scanner.nextDouble();
                    scanner.nextLine();
                    if (deposito > 0) {
                        cuenta.depositar(deposito);
                        System.out.println("Depósito exitoso. Nuevo saldo: $" + cuenta.getSaldo());
                    } else {
                        System.out.println("Debe ingresar un monto positivo.");
                    }
                } else {
                    System.out.println("Monto inválido.");
                    scanner.nextLine();
                }
                break;
            case 0:
                System.out.println("Gracias por usar Bank Europe.");
                break;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
        }

    } else {
        System.out.println("Entrada no válida. Por favor ingrese un número.");
        scanner.nextLine(); // Limpiar entrada inválida
    }
}
        scanner.close();
    }
}
