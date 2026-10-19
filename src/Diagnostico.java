public class Diagnostico extends OrdenServicio {

    // Constructor de la clase Diagnostico
    public Diagnostico(
        String codigo,
        String nombreCliente,
        String direccion,
        double horasEstimadas,
        String zona
    ) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException(
                "Un diagnóstico no puede superar las 1.5 horas."
            );
        }
    }

    // Calcula la tarifa fija correspondiente a la zona del servicio.
    @Override
    public double calcularCosto() {
        if (zona.equalsIgnoreCase("Metropolitana")) {
            return 150; // Costo fijo
        } else if (zona.equalsIgnoreCase("Periferica")) {
            return 225; // Costo Fijo
        } else {
            throw new IllegalArgumentException(
                "Zona no válida para calcular el costo."
            );
        }
    }

    // Devuelve la categoría utilizada en los reportes.
    @Override
    public String getCategoria() {
        return "Diagnostico";
    }

    // Calcula los días de atención según la zona registrada.
    @Override
    public int calcularDiasAtencion() {
        if (zona.equalsIgnoreCase("Metropolitana")) {
            return 1; // Días de atención en zona Metropolitana
        } else if (zona.equalsIgnoreCase("Periferica")) {
            return 2; // Días de atención en zona Periférica
        } else {
            throw new IllegalArgumentException(
                "Zona no válida para calcular los días de atención."
            );
        }
    }

    // Define el máximo de visitas permitido para un diagnóstico.
    @Override
    public int obtenerMaximoVisitas() {
        return 3; // Máximo de visitas para diagnóstico
    }

    // Agrega la línea que cierra la representación de la orden.
    @Override
    public String toString() {
        return super.toString() + "\n----------------------";
    }
}
