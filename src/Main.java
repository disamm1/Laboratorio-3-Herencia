import java.util.Scanner;

public class Main {

    private static Sistema sistema;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializar el sistema y scanner
        sistema = new Sistema();
        scanner = new Scanner(System.in);

        // Crear las cuadrillas iniciales
        Cuadrilla c1 = new Cuadrilla("C001", 8.0, true);
        Cuadrilla c2 = new Cuadrilla("C002", 10.0, false);
        Cuadrilla c3 = new Cuadrilla("C003", 12.0, true);

        sistema.registrarCuadrilla(c1);
        sistema.registrarCuadrilla(c2);
        sistema.registrarCuadrilla(c3);

        // Crear dos ordenes de diagnostico
        sistema.agregarOrdenInicial(
            new Diagnostico("D001", "Carlos", "Zona 10", 1.5, "Metropolitana")
        );

        sistema.agregarOrdenInicial(
            new Diagnostico("D002", "Ana", "Zona 5", 1.0, "Periferica")
        );

        // Crear dos ordenes de instalacion
        sistema.agregarOrdenInicial(
            new Instalacion("I001", "Luis", "Zona 1", 2.5, "Metropolitana", 5)
        );

        sistema.agregarOrdenInicial(
            new Instalacion("I002", "Maria", "Zona 18", 3.0, "Periferica", 3)
        );

        // Crear dos ordenes de instalacion electrica
        sistema.agregarOrdenInicial(
            new InstalacionElectrica(
                "E001",
                "Pedro",
                "Zona 11",
                4.0,
                "Metropolitana",
                4,
                2000.0
            )
        );

        sistema.agregarOrdenInicial(
            new InstalacionElectrica(
                "E002",
                "Sofia",
                "Zona 6",
                3.0,
                "Periferica",
                3,
                1500.0
            )
        );

        // Crear dos ordenes de mantenimiento
        sistema.agregarOrdenInicial(
            new MantenimientoAireAcondicionado(
                "M001",
                "Juan",
                "Zona 14",
                2.0,
                "Metropolitana",
                12000
            )
        );

        sistema.agregarOrdenInicial(
            new MantenimientoAireAcondicionado(
                "M002",
                "Elena",
                "Zona 15",
                3.0,
                "Metropolitana",
                24000
            )
        );

        // Menu principal
        int opcion = 0;

        while (opcion != 11) {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion:");

            if (opcion == 1) {
                cotizarServicio();
            } else if (opcion == 2) {
                registrarOrden();
            } else if (opcion == 3) {
                registrarCuadrilla();
            } else if (opcion == 4) {
                registrarJornadas();
            } else if (opcion == 5) {
                asignarOrdenJornada();
            } else if (opcion == 6) {
                retirarOrdenJornada();
            } else if (opcion == 7) {
                iniciarJornada();
            } else if (opcion == 8) {
                registrarResultadoVisita();
            } else if (opcion == 9) {
                consultarInformacion();
            } else if (opcion == 10) {
                consultarMostrarReportes();
            } else if (opcion == 11) {
                System.out.println("");
                System.out.println("Gracias por utilizar TecniHogar.");
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("");
                System.out.println("Error: opcion no valida.");
                esperarEnter();
            }
        }

        scanner.close();
    }

    // Mostrar el menu principal
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("========== TECNIHOGAR ==========");
        System.out.println("1. Cotizar servicio");
        System.out.println("2. Registrar orden de servicio");
        System.out.println("3. Registrar cuadrilla");
        System.out.println("4. Crear jornada");
        System.out.println("5. Asignar orden a jornada");
        System.out.println("6. Retirar orden de jornada");
        System.out.println("7. Iniciar jornada");
        System.out.println("8. Registrar resultado de visita");
        System.out.println("9. Consultar informacion");
        System.out.println("10. Consultar reportes");
        System.out.println("11. Salir");
        System.out.println("================================");
    }

    // Registrar una orden de servicio
    public static void registrarOrden() {
        System.out.println("");
        System.out.println("====== REGISTRAR ORDEN ======");
        System.out.println("1. Diagnostico");
        System.out.println("2. Instalacion");
        System.out.println("3. Instalacion Electrica");
        System.out.println("4. Mantenimiento de Aire Acondicionado");
        System.out.println("5. Regresar al menu principal");

        int opcion = leerEntero("Seleccione una opcion:");

        if (opcion == 5) {
            return;
        }

        if (opcion < 1 || opcion > 4) {
            System.out.println("Opcion no valida.");
            esperarEnter();
            return;
        }

        // Solicitar datos comunes
        String codigo = leerString("Ingrese el codigo de la orden:");

        if (sistema.buscarOrden(codigo) != null) {
            System.out.println("Error: ya existe una orden con ese codigo.");
            esperarEnter();
            return;
        }

        String nombre = leerString("Ingrese el nombre del cliente:");
        String direccion = leerString("Ingrese la direccion:");
        double horas = leerDouble("Ingrese las horas estimadas:");

        String zona = "";

        while (
            !zona.equalsIgnoreCase("Metropolitana") &&
            !zona.equalsIgnoreCase("Periferica")
        ) {
            zona = leerString("Ingrese la zona (Metropolitana/Periferica):");

            if (
                !zona.equalsIgnoreCase("Metropolitana") &&
                !zona.equalsIgnoreCase("Periferica")
            ) {
                System.out.println("");
                System.out.println("Error: zona no valida.");
                System.out.println("");
            }
        }

        try {
            OrdenServicio orden = null;

            if (opcion == 1) {
                orden = new Diagnostico(codigo, nombre, direccion, horas, zona);
            } else if (opcion == 2) {
                int puntos = leerEntero("Ingrese los puntos a instalar:");

                orden = new Instalacion(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    puntos
                );
            } else if (opcion == 3) {
                int puntos = leerEntero("Ingrese los puntos a instalar:");

                double materiales = leerDouble(
                    "Ingrese el valor de los materiales:"
                );

                orden = new InstalacionElectrica(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    puntos,
                    materiales
                );
            } else if (opcion == 4) {
                int capacidadBTU = leerEntero(
                    "Ingrese la capacidad del equipo en BTU:"
                );

                orden = new MantenimientoAireAcondicionado(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    capacidadBTU
                );
            }

            if (orden != null) {
                if (sistema.registrarOrden(orden)) {
                    System.out.println("");
                    System.out.println("Orden registrada correctamente.");
                    System.out.println(orden);

                    System.out.printf(
                        "Costo del servicio: Q%.2f%n",
                        orden.calcularCosto()
                    );

                    System.out.println(
                        "Dias estimados de atencion: " +
                        orden.calcularDiasAtencion()
                    );
                } else {
                    System.out.println("");
                    System.out.println("No se pudo registrar la orden.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("");
            System.out.println("Error: " + e.getMessage());
        }

        esperarEnter();
    }

    // Registrar una cuadrilla
    public static void registrarCuadrilla() {
        System.out.println("");
        System.out.println("====== REGISTRAR CUADRILLA ======");
        System.out.println("");

        String codigo = leerString("Ingrese el codigo de la cuadrilla:");

        // Verificar que el codigo no este repetido
        if (sistema.buscarCuadrilla(codigo) != null) {
            System.out.println("");
            System.out.println(
                "Error: ya existe una cuadrilla con ese codigo."
            );
            esperarEnter();
            return;
        }

        double capacidadHoras = leerDouble(
            "Ingrese la capacidad de horas por jornada:"
        );

        // Verificar la certificacion en refrigeracion
        boolean certificada = false;
        boolean respuestaValida = false;

        while (!respuestaValida) {
            String respuesta = leerString(
                "La cuadrilla esta certificada en refrigeracion? (Si/No):"
            );

            if (respuesta.equalsIgnoreCase("Si")) {
                certificada = true;
                respuestaValida = true;
            } else if (respuesta.equalsIgnoreCase("No")) {
                certificada = false;
                respuestaValida = true;
            } else {
                System.out.println("");
                System.out.println("Error: debe ingresar Si o No.");
            }
        }

        // Crear y registrar la cuadrilla
        try {
            Cuadrilla cuadrilla = new Cuadrilla(
                codigo,
                capacidadHoras,
                certificada
            );

            if (sistema.registrarCuadrilla(cuadrilla)) {
                System.out.println("");
                System.out.println("Cuadrilla registrada correctamente.");
                System.out.println("");
                System.out.println(cuadrilla);
            } else {
                System.out.println("");
                System.out.println("No se pudo registrar la cuadrilla.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("");
            System.out.println("Error: " + e.getMessage());
        }

        esperarEnter();
    }

    // Registrar una nueva jornada
    public static void registrarJornadas() {
        System.out.println("");
        System.out.println("====== REGISTRAR JORNADA ======");
        System.out.println("");

        String codigo = leerString("Ingrese el codigo de la jornada:");

        // Verificar que el codigo no este repetido
        if (sistema.buscarJornada(codigo) != null) {
            System.out.println("");
            System.out.println("Error: ya existe una jornada con ese codigo.");
            esperarEnter();
            return;
        }

        String codigoCuadrilla = leerString(
            "Ingrese el codigo de la cuadrilla:"
        );

        // Buscar la cuadrilla registrada
        Cuadrilla cuadrilla = sistema.buscarCuadrilla(codigoCuadrilla);

        if (cuadrilla == null) {
            System.out.println("");
            System.out.println(
                "Error: no existe una cuadrilla con ese codigo."
            );
            esperarEnter();
            return;
        }

        // Registrar la jornada
        if (sistema.crearJornada(codigo, cuadrilla)) {
            System.out.println("");
            System.out.println("Jornada registrada correctamente.");
            System.out.println("");
            System.out.println(sistema.buscarJornada(codigo));
            System.out.println("");
        } else {
            System.out.println("");
            System.out.println("No se pudo registrar la jornada.");
            System.out.println(
                "Verifique que la cuadrilla no tenga otra jornada abierta."
            );
        }

        esperarEnter();
    }

    // Se consulta una orden por su codigo, una cuadrilla
    public static void consultarInformacion() {
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("");
            System.out.println("====== CONSULTAR INFORMACION ======");
            System.out.println("1. Consultar orden por codigo");
            System.out.println("2. Consultar cuadrilla por codigo");
            System.out.println("3. Regresar al menu principal");
            System.out.println("==================================");

            opcion = leerEntero("Seleccione una opcion:");

            if (opcion == 1) {
                String codigo = leerString("Ingrese el codigo de la orden:");

                OrdenServicio orden = sistema.buscarOrden(codigo);

                if (orden != null) {
                    System.out.println("");
                    System.out.println(orden);
                    System.out.println("");

                    esperarEnter();
                    return;
                } else {
                    System.out.println("");
                    System.out.println("No se encontro la orden.");
                    System.out.println("");
                }
            } else if (opcion == 2) {
                String codigo = leerString(
                    "Ingrese el codigo de la cuadrilla:"
                );

                Cuadrilla cuadrilla = sistema.buscarCuadrilla(codigo);

                if (cuadrilla != null) {
                    System.out.println("");
                    System.out.println(cuadrilla);
                    System.out.println("");

                    esperarEnter();
                    return;
                } else {
                    System.out.println("");
                    System.out.println("No se encontro la cuadrilla.");
                    System.out.println("");
                }
            } else if (opcion == 3) {
                System.out.println("");
                System.out.println("Regresando al menu principal...");
                System.out.println("");
            } else {
                System.out.println("");
                System.out.println("Opcion no valida.");
                System.out.println("");
            }
        }
    }

    // Muestra para elegir los reportes
    public static void consultarMostrarReportes() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("");
            System.out.println("====== CONSULTAR REPORTES ======");
            System.out.println("1. Reporte de ordenes");
            System.out.println("2. Reporte de ingresos");
            System.out.println("3. Reporte de jornadas");
            System.out.println("4. Regresar al menu principal");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opcion:");

            if (opcion == 1) {
                System.out.println("");
                sistema.generarReporteOrdenes();
                System.out.println("");

                esperarEnter();
                return;
            } else if (opcion == 2) {
                System.out.println("");
                sistema.generarReporteIngresos();
                System.out.println("");

                esperarEnter();
                return;
            } else if (opcion == 3) {
                System.out.println("");
                sistema.generarReporteJornadas();
                System.out.println("");

                esperarEnter();
                return;
            } else if (opcion == 4) {
                System.out.println("");
                System.out.println("Regresando al menu principal...");
                System.out.println("");
            } else {
                System.out.println("");
                System.out.println("Opcion no valida.");
                System.out.println("");
            }
        }
    }

    // Leer numero int como entero
    public static int leerEntero(String mensaje) {
    while (true) {
        try {
            System.out.println("");
            System.out.println(mensaje);

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                throw new IllegalArgumentException();
            }

            int numero = Integer.parseInt(entrada);

            if (numero < 0) {
                throw new ArithmeticException();
            }

            return numero;

        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un numero entero valido.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: la entrada no puede estar vacia.");

        } catch (ArithmeticException e) {
            System.out.println("Error: el numero no puede ser negativo.");
        }
    }
}

    // metodo para leer double
    public static double leerDouble(String mensaje) {
    while (true) {
        try {
            System.out.println("");
            System.out.println(mensaje);

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                throw new IllegalArgumentException();
            }

            double numero = Double.parseDouble(entrada);

            if (!Double.isFinite(numero) || numero < 0) {
                throw new ArithmeticException();
            }

            return numero;

        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un numero decimal valido.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: la entrada no puede estar vacia.");

        } catch (ArithmeticException e) {
            System.out.println("Error: el numero debe ser finito y no negativo.");
        }
    }
}

    // Metodo para leer un String
    public static String leerString(String mensaje) {
        while (true) {
            try {
                System.out.println("");
                System.out.println(mensaje);
                System.out.println("");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                return entrada;
            } catch (IllegalArgumentException e) {
                System.out.println("");
                System.out.println("Error: la entrada no puede estar vacía.");
                System.out.println("");
            }
        }
    }

    // Esperar a que el usuario presione Enter
    public static void esperarEnter() {
        System.out.println("");
        System.out.println("Presione Enter para regresar al menu...");
        scanner.nextLine();
    }

    // Cotizar un servicio sin registrar la orden
    public static void cotizarServicio() {
        System.out.println("");
        System.out.println("====== COTIZAR SERVICIO ======");
        System.out.println("1. Diagnostico");
        System.out.println("2. Instalacion");
        System.out.println("3. Instalacion Electrica");
        System.out.println("4. Mantenimiento de Aire Acondicionado");
        System.out.println("5. Regresar al menu principal");

        int opcion = leerEntero("Seleccione una opcion:");

        if (opcion == 5) {
            return;
        }

        if (opcion < 1 || opcion > 4) {
            System.out.println("Opcion no valida.");
            esperarEnter();
            return;
        }

        // Datos comunes del servicio
        String codigo = leerString("Ingrese el codigo de la orden:");
        String nombre = leerString("Ingrese el nombre del cliente:");
        String direccion = leerString("Ingrese la direccion:");
        double horas = leerDouble("Ingrese las horas estimadas:");
        String zona = leerString("Ingrese la zona (Metropolitana/Periferica):");

        try {
            OrdenServicio orden = null;

            if (opcion == 1) {
                orden = new Diagnostico(codigo, nombre, direccion, horas, zona);
            } else if (opcion == 2) {
                int puntos = leerEntero("Ingrese los puntos a instalar:");

                orden = new Instalacion(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    puntos
                );
            } else if (opcion == 3) {
                int puntos = leerEntero("Ingrese los puntos a instalar:");
                double materiales = leerDouble(
                    "Ingrese el valor de los materiales:"
                );

                orden = new InstalacionElectrica(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    puntos,
                    materiales
                );
            } else if (opcion == 4) {
                int capacidadBTU = leerEntero("Ingrese la capacidad en BTU:");

                orden = new MantenimientoAireAcondicionado(
                    codigo,
                    nombre,
                    direccion,
                    horas,
                    zona,
                    capacidadBTU
                );
            }

            // Calcular la cotizacion
            double costo = sistema.cotizarServicio(orden);

            System.out.println("");
            System.out.println("====== RESULTADO DE COTIZACION ======");
            System.out.printf("Costo del servicio: Q%.2f%n", costo);
            System.out.println(
                "Dias estimados de atencion: " + orden.calcularDiasAtencion()
            );
        } catch (IllegalArgumentException e) {
            System.out.println("");
            System.out.println("Error: " + e.getMessage());
        }

        esperarEnter();
    }

    // Asignar una orden a una jornada
    public static void asignarOrdenJornada() {
        System.out.println("");
        System.out.println("====== ASIGNAR ORDEN A JORNADA ======");
        System.out.println("");

        // Buscar la jornada
        String codigoJornada = leerString("Ingrese el codigo de la jornada:");

        Jornada jornada = sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            System.out.println("");
            System.out.println("Error: no existe una jornada con ese codigo.");
            esperarEnter();
            return;
        }

        // Buscar la orden
        String codigoOrden = leerString("Ingrese el codigo de la orden:");

        OrdenServicio orden = sistema.buscarOrden(codigoOrden);

        if (orden == null) {
            System.out.println("");
            System.out.println("Error: no existe una orden con ese codigo.");
            esperarEnter();
            return;
        }

        // Asignar la orden
        if (jornada.asignarOrden(orden)) {
            System.out.println("");
            System.out.println("Orden asignada correctamente.");
            System.out.println("");
            System.out.println(jornada);
        } else {
            System.out.println("");
            System.out.println("No se pudo asignar la orden.");
            System.out.println("Verifique que:");
            System.out.println("- La jornada este en preparacion.");
            System.out.println("- La orden este pendiente y no asignada.");
            System.out.println("- La cuadrilla sea compatible.");
            System.out.println("- Exista capacidad de horas disponible.");
        }

        esperarEnter();
    }

    // Retirar una orden de una jornada
    public static void retirarOrdenJornada() {
        System.out.println("");
        System.out.println("====== RETIRAR ORDEN DE JORNADA ======");
        System.out.println("");

        // Buscar la jornada
        String codigoJornada = leerString("Ingrese el codigo de la jornada:");

        Jornada jornada = sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            System.out.println("");
            System.out.println("Error: no existe una jornada con ese codigo.");
            esperarEnter();
            return;
        }

        // Verificar que la jornada este en preparacion
        if (!jornada.getEstado().equalsIgnoreCase("Preparacion")) {
            System.out.println("");
            System.out.println(
                "Error: solo se pueden retirar ordenes de jornadas en preparacion."
            );
            esperarEnter();
            return;
        }

        // Solicitar el codigo de la orden
        String codigoOrden = leerString(
            "Ingrese el codigo de la orden que desea retirar:"
        );

        // Retirar la orden
        if (jornada.retirarOrden(codigoOrden)) {
            System.out.println("");
            System.out.println("Orden retirada correctamente.");
            System.out.println("");
            System.out.println(jornada);
        } else {
            System.out.println("");
            System.out.println("No se pudo retirar la orden.");
            System.out.println(
                "Verifique que la orden este asignada a esta jornada."
            );
        }

        esperarEnter();
    }

    // Iniciar una jornada
    public static void iniciarJornada() {
        System.out.println("");
        System.out.println("====== INICIAR JORNADA ======");
        System.out.println("");

        // Solicitar el codigo de la jornada
        String codigoJornada = leerString("Ingrese el codigo de la jornada:");

        // Buscar la jornada
        Jornada jornada = sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            System.out.println("");
            System.out.println("Error: no existe una jornada con ese codigo.");
            esperarEnter();
            return;
        }

        // Verificar que este en preparacion
        if (!jornada.getEstado().equalsIgnoreCase("Preparacion")) {
            System.out.println("");
            System.out.println("Error: la jornada no esta en preparacion.");
            esperarEnter();
            return;
        }

        // Iniciar la jornada desde Sistema
        if (sistema.iniciarJornada(codigoJornada)) {
            System.out.println("");
            System.out.println("Jornada iniciada correctamente.");
            System.out.println("");
            System.out.println(jornada);
        } else {
            System.out.println("");
            System.out.println("No se pudo iniciar la jornada.");
            System.out.println(
                "Verifique que tenga ordenes asignadas y pendientes."
            );
        }

        esperarEnter();
    }

    // Registrar el resultado de una visita
    public static void registrarResultadoVisita() {
        System.out.println("");
        System.out.println("====== REGISTRAR RESULTADO DE VISITA ======");
        System.out.println("");

        // Solicitar el codigo de la jornada
        String codigoJornada = leerString("Ingrese el codigo de la jornada:");

        Jornada jornada = sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            System.out.println("");
            System.out.println("Error: no existe una jornada con ese codigo.");
            esperarEnter();
            return;
        }

        // Verificar que la jornada este en curso
        if (!jornada.getEstado().equalsIgnoreCase("En curso")) {
            System.out.println("");
            System.out.println("Error: la jornada no esta en curso.");
            esperarEnter();
            return;
        }

        // Solicitar el codigo de la orden
        String codigoOrden = leerString("Ingrese el codigo de la orden:");

        // Solicitar el resultado de la visita
        boolean completada = false;
        boolean respuestaValida = false;

        while (!respuestaValida) {
            String respuesta = leerString("La visita fue completada? (Si/No):");

            if (respuesta.equalsIgnoreCase("Si")) {
                completada = true;
                respuestaValida = true;
            } else if (respuesta.equalsIgnoreCase("No")) {
                completada = false;
                respuestaValida = true;
            } else {
                System.out.println("");
                System.out.println("Error: debe ingresar Si o No.");
            }
        }

        // Registrar el resultado en la jornada
        if (jornada.registrarResultado(codigoOrden, completada)) {
            System.out.println("");
            System.out.println("Resultado registrado correctamente.");

            OrdenServicio orden = sistema.buscarOrden(codigoOrden);

            if (orden != null) {
                System.out.println("");
                System.out.println(
                    "Estado actual de la orden: " + orden.getEstado()
                );
                System.out.println(
                    "Visitas realizadas: " + orden.getVisitasRealizadas()
                );
            }

            if (jornada.estaCerrada()) {
                System.out.println("");
                System.out.println("Todas las visitas tienen resultado.");
                System.out.println("La jornada ha sido cerrada.");
            }
        } else {
            System.out.println("");
            System.out.println("No se pudo registrar el resultado.");
            System.out.println(
                "Verifique que la orden pertenezca a la jornada"
            );
            System.out.println(
                "y que no tenga un resultado registrado anteriormente."
            );
        }

        esperarEnter();
    }
}
