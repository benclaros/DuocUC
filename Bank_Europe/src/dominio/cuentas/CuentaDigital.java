package dominio.cuentas;

public class CuentaDigital extends CuentaBancaria {

    public CuentaDigital(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public double calcularInteres() {
        return saldo * 0.025;
    }
}

