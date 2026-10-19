public class InstalacionElectrica extends Instalacion {

    // Valor total de los materiales utilizados en la instalación.
    private double valorMateriales;

    // Construye la instalación eléctrica y valida el valor de materiales.
    public InstalacionElectrica(
        String codigo,
        String nombreCliente,
        String direccion,
        double horasEstimadas,
        String zona,
        int puntosInstalar,
        double valorMateriales
    ) {
        super(
            codigo,
            nombreCliente,
            direccion,
            horasEstimadas,
            zona,
            puntosInstalar
        );
        if (!Double.isFinite(valorMateriales) || valorMateriales <= 0) {
            throw new IllegalArgumentException(
                "El valor de los materiales debe ser positivo y finito."
            );
        }

        this.valorMateriales = valorMateriales;
    }

    // Método de acceso.
    public double getValorMateriales() {
        return valorMateriales;
    }

    // Suma el recargo de materiales y la tarifa eléctrica al costo base.
    @Override
    public double calcularCosto() {
        return super.calcularCosto() + (valorMateriales * 0.05) + 120;
    }

    // Agrega dos días al tiempo de una instalación normal.
    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion() + 2;
    }

    // Devuelve el máximo de visitas para una instalación eléctrica.
    @Override
    public int obtenerMaximoVisitas() {
        return 2; // 2 visitas para la instalacion electrica
    }

    @Override
    public String getCategoria() {
        return "Instalacion Electrica";
    }

    // Se redefine para mostrar materiales antes de la línea de cierre.
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
            visitasRealizadas +
            "\nPuntos a instalar: " +
            getPuntosInstalar() +
            "\nValor de materiales: Q" +
            valorMateriales +
            "\n----------------------"
        );
    }
}
