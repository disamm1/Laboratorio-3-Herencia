public abstract class OrdenServicio {

    // Datos comunes de cualquier orden de servicio.
    protected String codigo;
    protected String nombreCliente;
    protected String direccion;
    protected double horasEstimadas;
    protected String zona;
    protected String estado;
    protected int visitasRealizadas;
    protected boolean asignada;
    protected boolean cobrada;
    private double ingresosAdicionales;

    public OrdenServicio(
        String codigo,
        String nombreCliente,
        String direccion,
        double horasEstimadas,
        String zona
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                "El código no puede estar vacío."
            );
        }

        if (nombreCliente == null || nombreCliente.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del cliente no puede estar vacío."
            );
        }

        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException(
                "La dirección no puede estar vacía."
            );
        }

        if (!Double.isFinite(horasEstimadas) || horasEstimadas <= 0) {
            throw new IllegalArgumentException(
                "Las horas estimadas deben ser positivas y finitas."
            );
        }

        if (
            zona == null ||
            (!zona.equalsIgnoreCase("Metropolitana") &&
                !zona.equalsIgnoreCase("Periferica"))
        ) {
            throw new IllegalArgumentException(
                "La zona debe ser Metropolitana o Periferica."
            );
        }

        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = "Pendiente";
        this.visitasRealizadas = 0;
        this.asignada = false;
        this.cobrada = false;
        this.ingresosAdicionales = 0;
    }

    // Métodos de acceso.
    public String getCodigo() {
        return codigo;
    }

    public boolean getAsignada() {
        return asignada;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getIngresosAdicionales() {
        return ingresosAdicionales;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    public String getZona() {
        return zona;
    }

    public String getEstado() {
        return estado;
    }

    public int getVisitasRealizadas() {
        return visitasRealizadas;
    }

    public boolean isCobrada() {
        return cobrada;
    }

    // Métodos de modificación controlada.
    public void setAsignada(boolean asignada) {
        this.asignada = asignada;
    }

    public void setCobrada(boolean cobrada) {
        this.cobrada = cobrada;
    }

    // Operaciones que cada tipo de servicio debe implementar.
    public abstract double calcularCosto();

    public abstract int calcularDiasAtencion();

    public abstract int obtenerMaximoVisitas();

    public abstract String getCategoria();

    // Verifica la compatibilidad básica con una cuadrilla existente.
    public boolean esCompatible(Cuadrilla cuadrilla) {
        return cuadrilla != null;
    }

    // Inicia la atención únicamente cuando la orden está pendiente.
    public boolean iniciarAtencion() {
        if (!estado.equals("Pendiente")) {
            return false;
        }

        estado = "En atención";
        return true;
    }

    // Registra una visita y actualiza el estado según su resultado.
    public boolean registrarResultadoVisita(boolean completada) {
        if (!estado.equals("En atención")) {
            return false;
        }

        visitasRealizadas++;

        if (completada) {
            estado = "Completada";
        } else {
            if (visitasRealizadas >= obtenerMaximoVisitas()) {
                estado = "Cancelada";
            } else {
                estado = "Pendiente";
            }
        }

        return true;
    }

    // Acumula cobros producidos por visitas adicionales.
    public void agregarIngresoAdicional(double cantidad) {
        if (!Double.isFinite(cantidad) || cantidad <= 0) {
            throw new IllegalArgumentException("El ingreso debe ser positivo.");
        }

        ingresosAdicionales += cantidad;
    }

    // Presenta los datos comunes de la orden.
    @Override
    public String toString() {
        return (
            "----------------------" +
            "\nCódigo: " +
            codigo +
            "\nNombre del Cliente: " +
            nombreCliente +
            "\nDirección: " +
            direccion +
            "\nHoras Estimadas: " +
            horasEstimadas +
            "\nZona: " +
            zona +
            "\nEstado: " +
            estado +
            "\nVisitas Realizadas: " +
            visitasRealizadas
        );
    }
}
