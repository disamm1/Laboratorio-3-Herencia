public class MantenimientoAireAcondicionado extends OrdenServicio {

    // Capacidad nominal del equipo de aire acondicionado.
    private int capacidadBTU;

    // Construye el mantenimiento y valida capacidad y zona.
    public MantenimientoAireAcondicionado(
        String codigo,
        String nombreCliente,
        String direccion,
        double horasEstimadas,
        String zona,
        int capacidadBTU
    ) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (capacidadBTU < 9000 || capacidadBTU > 60000) {
            throw new IllegalArgumentException(
                "La capacidad debe estar entre 9000 y 60000."
            );
        }

        if (!zona.equalsIgnoreCase("Metropolitana")) {
            throw new IllegalArgumentException(
                "El mantenimiento de aire acondicionado solo se permite en zona Metropolitana."
            );
        }

        this.capacidadBTU = capacidadBTU;
    }

    // Método de acceso.
    public int getCapacidadBTU() {
        return capacidadBTU;
    }

    // Retorna el costo base, refrigerante y valor por hora estimada.
    @Override
    public double calcularCosto() {
        return 100 + 80 + (110 * horasEstimadas);
    }

    // Devuelve los dos días de atención establecidos.
    @Override
    public int calcularDiasAtencion() {
        return 2;
    }

    // Devuelve el máximo de visitas permitido.
    @Override
    public int obtenerMaximoVisitas() {
        return 1;
    }

    // Devuelve la categoría utilizada en los reportes.
    @Override
    public String getCategoria() {
        return "Mantenimiento";
    }

    // Exige una cuadrilla existente y certificada en refrigeración.
    @Override
    public boolean esCompatible(Cuadrilla cuadrilla) {
        return (
            super.esCompatible(cuadrilla) &&
            cuadrilla.isCertificadaRefrigeracion()
        );
    }

    // Incluye la capacidad BTU en la representación de la orden.
    @Override
    public String toString() {
        return (
            super.toString() +
            "\nCapacidad BTU: " +
            capacidadBTU +
            "\n----------------------"
        );
    }
}
