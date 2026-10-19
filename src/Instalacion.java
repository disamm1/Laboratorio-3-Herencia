public class Instalacion extends OrdenServicio {

    // Cantidad de puntos solicitados en la instalación.
    private int puntosInstalar;

    // Constructor de la clase Instalacion
    public Instalacion(
        String codigo,
        String nombreCliente,
        String direccion,
        double horasEstimadas,
        String zona,
        int puntosInstalar
    ) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (puntosInstalar <= 0) {
            throw new IllegalArgumentException(
                "Los puntos a instalar tienen que ser mayor que cero."
            );
        }
        this.puntosInstalar = puntosInstalar;
    }

    // Método de acceso.
    public int getPuntosInstalar() {
        return puntosInstalar;
    }

    // Calcula las horas facturables a partir de los puntos por instalar.
    public double calcularHorasFacturables() {
        double horasPorPuntos = puntosInstalar * 0.75;
        double horasFacturables = 0;

        if (horasEstimadas > horasPorPuntos) {
            horasFacturables = horasEstimadas;
        } else {
            horasFacturables = horasPorPuntos;
        }

        // Redondea hacia arriba cualquier fracción de hora.
        int horasEnteras = (int) horasFacturables;

        if (horasFacturables > horasEnteras) {
            horasEnteras++;
        }

        return horasEnteras;
    }

    @Override
    // Calcula el costo con la tarifa base de la zona y las horas facturables.
    public double calcularCosto() {
        double tarifaBase = 0;
        if (zona.equalsIgnoreCase("Metropolitana")) {
            tarifaBase = 100;
        } else if (zona.equalsIgnoreCase("Periferica")) {
            tarifaBase = 175;
        } else {
            throw new IllegalArgumentException(
                "Zona no válida para calcular el costo."
            );
        }

        return tarifaBase + (90 * calcularHorasFacturables());
    }

    @Override
    // Calcula los días de atención para las dos zonas permitidas.
    public int calcularDiasAtencion() {
        if (zona.equalsIgnoreCase("Metropolitana")) {
            return 3; // Días de atención en zona Metropolitana
        } else if (zona.equalsIgnoreCase("Periferica")) {
            return 5; // Días de atención en zona Periférica
        } else {
            throw new IllegalArgumentException(
                "Zona no válida para calcular los días de atención."
            );
        }
    }

    // Devuelve el máximo de visitas permitido.
    @Override
    public int obtenerMaximoVisitas() {
        return 3; // Máximo de visitas para instalacion
    }

    // Devuelve la categoría utilizada en los reportes.
    @Override
    public String getCategoria() {
        return "Instalacion";
    }

    // Incluye los puntos por instalar en la representación de la orden.
    @Override
    public String toString() {
        return (
            super.toString() +
            "\nPuntos a instalar: " +
            puntosInstalar +
            "\n----------------------"
        );
    }
}
