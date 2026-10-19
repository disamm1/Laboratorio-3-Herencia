import java.util.ArrayList;
import java.util.List;

public class Jornada {

    // Datos de la jornada, su cuadrilla y las órdenes asignadas.
    private String codigo;
    private Cuadrilla cuadrilla;
    private List<OrdenServicio> ordenes;
    private String estado;
    private List<String> resultadosRegistrados;

    // Construye una jornada en estado de preparación.
    public Jornada(String codigo, Cuadrilla cuadrilla) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                "El código no puede estar vacío."
            );
        }

        if (cuadrilla == null) {
            throw new IllegalArgumentException(
                "La jornada debe tener una cuadrilla asignada."
            );
        }

        this.resultadosRegistrados = new ArrayList<>();
        this.codigo = codigo;
        this.cuadrilla = cuadrilla;
        this.estado = "Preparacion";
        this.ordenes = new ArrayList<>();
    }

    // Métodos de acceso.
    public String getEstado() {
        return estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public List<OrdenServicio> getOrdenes() {
        return new ArrayList<>(ordenes);
    }

    // Asigna una orden únicamente si cumple todas las reglas de la jornada.
    public boolean asignarOrden(OrdenServicio orden) {
        // Validar que la orden exista.
        if (orden == null) {
            return false;
        }

        // Validar que la jornada esté en preparación.
        if (!estado.equalsIgnoreCase("Preparacion")) {
            return false;
        }

        // Validar que la orden esté pendiente.
        if (!orden.getEstado().equalsIgnoreCase("Pendiente")) {
            return false;
        }

        // Validar que no esté asignada a otra jornada.
        if (orden.getAsignada()) {
            return false;
        }

        // Validar que la cuadrilla sea compatible.
        if (!orden.esCompatible(cuadrilla)) {
            return false;
        }

        // Validar que todavía exista capacidad disponible.
        if (
            calcularHorasAsignadas() + orden.getHorasEstimadas() >
            cuadrilla.getCapacidadHoras()
        ) {
            return false;
        }

        // Agregar la orden y marcarla como asignada.
        ordenes.add(orden);
        orden.setAsignada(true);

        return true;
    }

    // Retira una orden por código mientras la jornada siga en preparación.
    public boolean retirarOrden(String codigo) {
        if (!estado.equalsIgnoreCase("Preparacion")) {
            return false;
        }

        for (int i = 0; i < ordenes.size(); i++) {
            OrdenServicio orden = ordenes.get(i);

            if (orden.getCodigo().equalsIgnoreCase(codigo)) {
                orden.setAsignada(false);
                ordenes.remove(i);
                return true;
            }
        }

        return false;
    }

    // Inicia la jornada cuando todas las órdenes están listas y asignadas.
    public boolean iniciarJornada() {
        if (!estado.equalsIgnoreCase("Preparacion")) {
            return false;
        }

        if (ordenes.isEmpty()) {
            return false;
        }

        for (OrdenServicio orden : ordenes) {
            if (
                !orden.getEstado().equalsIgnoreCase("Pendiente") ||
                !orden.getAsignada()
            ) {
                return false;
            }
        }

        for (OrdenServicio orden : ordenes) {
            orden.iniciarAtencion();
        }

        estado = "En curso";
        return true;
    }

    // Registra una sola vez el resultado de cada orden de la jornada.
    public boolean registrarResultado(String codigoOrden, boolean completada) {
        if (!estado.equals("En curso")) {
            return false;
        }

        if (codigoOrden == null) {
            return false;
        }

        // Evitar registrar dos veces el resultado de la misma orden.
        for (String codigoRegistrado : resultadosRegistrados) {
            if (codigoRegistrado.equalsIgnoreCase(codigoOrden)) {
        return false;
    }
}

        for (OrdenServicio orden : ordenes) {
            if (orden.getCodigo().equalsIgnoreCase(codigoOrden)) {
                if (!orden.registrarResultadoVisita(completada)) {
                    return false;
                }

                resultadosRegistrados.add(orden.getCodigo());

                // Cerrar la jornada cuando todas las órdenes tengan resultado.
                if (resultadosRegistrados.size() == ordenes.size()) {
                    estado = "Cerrada";

                    // Liberar todas las órdenes para futuras jornadas.
                    for (OrdenServicio ordenActual : ordenes) {
                        ordenActual.setAsignada(false);
                    }
                }

                return true;
            }
        }

        return false;
    }

    // Suma las horas estimadas de todas las órdenes asignadas.
    public double calcularHorasAsignadas() {
        double capacidad = 0;
        for (OrdenServicio orden : ordenes) {
            capacidad += orden.getHorasEstimadas();
        }
        return capacidad;
    }

    // Calcula la capacidad restante de la cuadrilla.
    public double calcularCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }

    // Indica si la jornada ya fue cerrada.
    public boolean estaCerrada() {
        return estado.equalsIgnoreCase("Cerrada");
    }

    // Presenta un resumen de la jornada y sus órdenes.
    @Override
    public String toString() {
        String texto =
            "--------JORNADA--------" +
            "\nCódigo: " +
            codigo +
            "\nEstado: " +
            estado +
            "\nCódigo de la cuadrilla: " +
            cuadrilla.getCodigo() +
            "\nCódigos de órdenes: ";

        for (OrdenServicio orden : ordenes) {
            texto += "\n- " + orden.getCodigo();
        }

        return texto + "\n----------------------";
    }
}
