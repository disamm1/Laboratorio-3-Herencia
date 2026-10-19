public class Cuadrilla {

    // Atributos que describen la capacidad y certificación de la cuadrilla.
    private String codigo;
    private double capacidadHoras;
    private boolean certificadaRefrigeracion;

    // Construye una cuadrilla y valida que sus datos sean utilizables.
    public Cuadrilla(
        String codigo,
        double capacidadHoras,
        boolean certificadaRefrigeracion
    ) {
        if (!Double.isFinite(capacidadHoras) || capacidadHoras <= 0) {
            throw new IllegalArgumentException(
                "La capacidad de horas debe ser positiva y finita."
            );
        }

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                "El código no puede estar vacío."
            );
        }

        this.codigo = codigo;
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
    }

    // Métodos de acceso.
    public String getCodigo() {
        return codigo;
    }

    public double getCapacidadHoras() {
        return capacidadHoras;
    }

    public boolean isCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }

    // Presenta los datos principales de la cuadrilla.
    @Override
    public String toString() {
        return (
            "----------------------" +
            "\nCódigo: " +
            codigo +
            "\nCapacidad de horas: " +
            capacidadHoras +
            "\nCertificada en refrigeración: " +
            certificadaRefrigeracion +
            "\n----------------------"
        );
    }
}
