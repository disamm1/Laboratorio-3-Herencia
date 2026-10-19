import java.util.ArrayList;
import java.util.List;

public class Sistema {

    // Colecciones y acumuladores administrados por el sistema.
    private List<OrdenServicio> ordenes;
    private List<Cuadrilla> cuadrillas;
    private List<Jornada> jornadas;
    private double ingresoRegistro;
    private double ingresoVisitas;

    // Inicializa el sistema sin registros ni ingresos acumulados.
    public Sistema() {
        this.ordenes = new ArrayList<>();
        this.cuadrillas = new ArrayList<>();
        this.jornadas = new ArrayList<>();
        this.ingresoRegistro = 0;
        this.ingresoVisitas = 0;
    }

    // Obtiene el costo de una orden sin registrarla.
    public double cotizarServicio(OrdenServicio orden) {
        if (orden == null) {
            throw new IllegalArgumentException("La orden no puede ser null.");
        }

        return orden.calcularCosto();
    }

    // Registra y cobra una orden que cumpla las condiciones iniciales.
    public boolean registrarOrden(OrdenServicio orden) {
        // Verificar que la orden exista.
        if (orden == null) {
            return false;
        }

        // Verificar que el código no esté repetido.
        if (buscarOrden(orden.getCodigo()) != null) {
            return false;
        }

        // Verificar que conserve su estado inicial.
        if (
            !orden.getEstado().equals("Pendiente") ||
            orden.getVisitasRealizadas() != 0 ||
            orden.getAsignada()
        ) {
            return false;
        }

        if (orden.isCobrada()) {
            return false;
        }

        // Registrar la orden y acumular el cobro inicial.
        double costo = orden.calcularCosto();
        ordenes.add(orden);
        ingresoRegistro += costo;
        orden.setCobrada(true);

        return true;
    }

    // Registra una cuadrilla cuando existe y su código no está repetido.
    public boolean registrarCuadrilla(Cuadrilla cuadrilla) {
        // Verificar que la cuadrilla exista.
        if (cuadrilla == null) {
            return false;
        }

        // Verificar que el código no esté repetido.
        if (buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            return false;
        }

        // Registrar la cuadrilla validada.
        cuadrillas.add(cuadrilla);

        return true;
    }

    // Crea una jornada para una cuadrilla registrada que esté disponible.
    public boolean crearJornada(String codigo, Cuadrilla cuadrilla) {
        if (cuadrilla == null) {
            return false;
        }

        if (codigo == null || codigo.isBlank()) {
            return false;
        }

        if (buscarJornada(codigo) != null) {
            return false;
        }

        Cuadrilla cuadrillaRegistrada = buscarCuadrilla(cuadrilla.getCodigo());

        if (cuadrillaRegistrada == null) {
            return false;
        }

        for (Jornada jornada : jornadas) {
            if (
                jornada
                    .getCuadrilla()
                    .getCodigo()
                    .equalsIgnoreCase(cuadrillaRegistrada.getCodigo()) &&
                !jornada.getEstado().equalsIgnoreCase("Cerrada")
            ) {
                return false;
            }
        }
        Jornada jornada = new Jornada(codigo, cuadrillaRegistrada);
        jornadas.add(jornada);
        return true;
    }

    // Inicia una jornada y registra cobros por visitas anteriores.
    public boolean iniciarJornada(String codigoJornada) {
        Jornada jornada = buscarJornada(codigoJornada);

        // Verificar que la jornada exista.
        if (jornada == null) {
            return false;
        }

        // Iniciar la jornada.
        if (!jornada.iniciarJornada()) {
            return false;
        }

        // Cobrar Q40 por cada orden con visitas anteriores.
        for (OrdenServicio orden : jornada.getOrdenes()) {
            if (orden.getVisitasRealizadas() > 0) {
                ingresoVisitas += 40;
                orden.agregarIngresoAdicional(40);
            }
        }

        return true;
    }

    // Busca una cuadrilla por código; devuelve null si no existe.
    public Cuadrilla buscarCuadrilla(String codigo) {
        for (Cuadrilla cuadrilla : cuadrillas) {
            if (cuadrilla.getCodigo().equalsIgnoreCase(codigo)) {
                return cuadrilla;
            }
        }
        return null;
    }

    // Busca una jornada por código; devuelve null si no existe.
    public Jornada buscarJornada(String codigo) {
        for (Jornada jornada : jornadas) {
            if (jornada.getCodigo().equalsIgnoreCase(codigo)) {
                return jornada;
            }
        }
        return null;
    }

    // Busca una orden por código; devuelve null si no existe.
    public OrdenServicio buscarOrden(String codigo) {
        for (OrdenServicio orden : ordenes) {
            if (orden.getCodigo().equalsIgnoreCase(codigo)) {
                return orden;
            }
        }
        return null;
    }

    // Muestra órdenes filtradas por categoría y estado junto con su total.
    public void mostrarOrdenesPorEstado(String categoria, String estado) {
        int contador = 0;

        System.out.println("");
        System.out.println("Por estado: " + estado);

        for (OrdenServicio orden : ordenes) {
            if (
                orden.getCategoria().equalsIgnoreCase(categoria) &&
                orden.getEstado().equalsIgnoreCase(estado)
            ) {
                System.out.println(orden);
                contador++;
            }
        }

        System.out.println("Cantidad de ordenes: " + contador);
    }

    public void generarReporteOrdenes() {
        String[] categorias = {
            "Diagnostico",
            "Instalacion",
            "Instalacion Electrica",
            "Mantenimiento",
        };

        String[] estados = {
            "Pendiente",
            "En atención",
            "Completada",
            "Cancelada",
        };

        System.out.println("");
        System.out.println("====== REPORTE DE ORDENES ======");

        for (String categoria : categorias) {
            System.out.println("");
            System.out.println("-----------------------");
            System.out.println("ORDENES DE " + categoria.toUpperCase());
            System.out.println("-----------------------");

            for (String estado : estados) {
                mostrarOrdenesPorEstado(categoria, estado);
            }
        }
    }

    public void generarReporteIngresos() {
        double ingresosInstalacion = 0;
        double ingresosDiagnostico = 0;
        double ingresosInstalacionElectrica = 0;
        double ingresosMantenimiento = 0;

        // Calcular ingresos por categoría.
        for (OrdenServicio orden : ordenes) {
            double ingresoOrden = 0;

            // Sumar el costo inicial solo si fue cobrado.
            if (orden.isCobrada()) {
                ingresoOrden += orden.calcularCosto();
            }

            // Sumar los ingresos de visitas adicionales.
            ingresoOrden += orden.getIngresosAdicionales();

            // Clasificar ingresos por categoría.
            if (orden.getCategoria().equalsIgnoreCase("Diagnostico")) {
                ingresosDiagnostico += ingresoOrden;
            } else if (
                orden.getCategoria().equalsIgnoreCase("Instalacion Electrica")
            ) {
                ingresosInstalacionElectrica += ingresoOrden;
            } else if (orden.getCategoria().equalsIgnoreCase("Instalacion")) {
                ingresosInstalacion += ingresoOrden;
            } else if (orden.getCategoria().equalsIgnoreCase("Mantenimiento")) {
                ingresosMantenimiento += ingresoOrden;
            }
        }

        System.out.println("");
        System.out.println("====== REPORTE DE INGRESOS ======");

        System.out.println("");
        System.out.println("INGRESOS POR REGISTRO");
        System.out.printf("Q%.2f%n", ingresoRegistro);

        System.out.println("");
        System.out.println("INGRESOS POR VISITAS ADICIONALES");
        System.out.printf("Q%.2f%n", ingresoVisitas);

        System.out.println("");
        System.out.println("INGRESOS TOTALES");
        System.out.printf("Q%.2f%n", ingresoRegistro + ingresoVisitas);

        System.out.println("");
        System.out.println("INGRESOS POR CATEGORIA");

        System.out.println("Diagnostico:");
        System.out.printf("Q%.2f%n", ingresosDiagnostico);

        System.out.println("Instalacion:");
        System.out.printf("Q%.2f%n", ingresosInstalacion);

        System.out.println("Instalacion Electrica:");
        System.out.printf("Q%.2f%n", ingresosInstalacionElectrica);

        System.out.println("Mantenimiento de Aire Acondicionado:");
        System.out.printf("Q%.2f%n", ingresosMantenimiento);

        System.out.println("");
        System.out.println("===============================");
    }

    // Genera un reporte de las jornadas que todavía no están cerradas.
    public void generarReporteJornadas() {
        boolean hayPreparacion = false;
        boolean hayCurso = false;

        System.out.println("");
        System.out.println("JORNADAS EN PREPARACION");
        System.out.println("");

        for (Jornada jornada : jornadas) {
            if (jornada.getEstado().equalsIgnoreCase("Preparacion")) {
                System.out.println(jornada);
                System.out.println(
                    "Horas asignadas: " + jornada.calcularHorasAsignadas()
                );
                System.out.println(
                    "Capacidad disponible: " +
                    jornada.calcularCapacidadDisponible()
                );
                System.out.println("");
                hayPreparacion = true;
            }
        }

        if (!hayPreparacion) {
            System.out.println("No hay jornadas en preparación.");
        }

        System.out.println("");
        System.out.println("JORNADAS EN CURSO");
        System.out.println("");

        for (Jornada jornada : jornadas) {
            if (jornada.getEstado().equalsIgnoreCase("En curso")) {
                System.out.println(jornada);
                System.out.println(
                    "Horas asignadas: " + jornada.calcularHorasAsignadas()
                );
                System.out.println(
                    "Capacidad disponible: " +
                    jornada.calcularCapacidadDisponible()
                );
                System.out.println("");
                hayCurso = true;
            }
        }

        if (!hayCurso) {
            System.out.println("No hay jornadas en curso.");
        }
    }

    // Agrega órdenes iniciales sin generar ingresos adicionales.
    public boolean agregarOrdenInicial(OrdenServicio orden) {
        if (orden == null) {
            return false;
        }

        if (buscarOrden(orden.getCodigo()) != null) {
            return false;
        }

        if (
            !orden.getEstado().equals("Pendiente") ||
            orden.getVisitasRealizadas() != 0 ||
            orden.getAsignada() ||
            orden.isCobrada()
        ) {
            return false;
        }

        ordenes.add(orden);
        return true;
    }
}
