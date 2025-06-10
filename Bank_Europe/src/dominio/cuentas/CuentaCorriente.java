package dominio.cuentas;

public class CuentaCorriente extends CuentaBancaria {

    public CuentaCorriente(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public double calcularInteres() {
        return saldo * 0.01;
    }
}
