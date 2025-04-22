class Entrada {
    private String tipo;
    private String ubicacion;
    private double precioFinal;
    private String estado;

    public Entrada(String tipo, String ubicacion, double precioFinal, String estado) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.precioFinal = precioFinal;
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    @Override
    public String toString() {
        return 
                "Tipo de entrada: " + tipo.toUpperCase()+ ", Ubicación: " + ubicacion.toUpperCase() +
                ", Precio: $" + precioFinal + ", Estado: " + estado.toUpperCase();
    }
}

