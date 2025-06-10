package dominio.cuentas;

public class CuentaAhorro extends CuentaBancaria {

    public CuentaAhorro(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public double calcularInteres() {
        return saldo * 0.03;
    }
}

