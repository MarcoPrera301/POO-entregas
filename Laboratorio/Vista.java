import java.util.Scanner;

public class Vista 
{
    private final Scanner sc = new Scanner(System.in);
    Tablero tablero; // se reconfigura con el tamaño elegido

    public void jugar() 
    {
        println("====== MEMORIA ======");

        // seleccion de tamanio
        int filas = pedirEnteroEnRango("Elije la cantidad de filas (no debe de haber mas de 52 tarjetas en el tablero): ", 2, 10);
        int columnas = pedirEnteroEnRango("Elije la cantidad de columnas (no debe de haber mas de 52 tarjetas en el tablero): ", 2, 10);
        int total = filas * columnas;

        boolean dimensionesValidas = (total % 2 == 0) && ((total / 2) <= 25); // debe ser par y max 25 parejas (50 casillas)

        while (!dimensionesValidas) 
        {
            println(" Dimensiones inválidas. El producto filas*columnas debe ser par y no mayor a 50.");

            filas = pedirEnteroEnRango("Elije la cantidad de filas (no debe de haber mas de 52 tarjetas en el tablero): ", 2, 10);
            columnas = pedirEnteroEnRango("Elije la cantidad de columnas (no debe de haber mas de 52 tarjetas en el tablero): ", 2, 10);
            total = filas * columnas;

            dimensionesValidas = (total % 2 == 0) && ((total / 2) <= 25);
        }

        // crear tablero
        tablero = new Tablero(filas, columnas);
        tablero.generarLetras();
        tablero.generarTarjetas();

        // Nombres
        print("Nombre del Jugador 1: ");
        String jugador1 = leer("Jugador 1");
        print("Nombre del Jugador 2: ");
        String jugador2 = leer("Jugador 2");

        int puntos1 = 0, puntos2 = 0;
        int turno = 1; // 1 o 2

        // while de turnos hasta finalizar juego
        
        while (!tablero.todasLasCartasEmparejadas()) 
        {
            mostrarTablero();

            String actual = (turno == 1 ? jugador1 : jugador2);
            println("\nTurno de: " + actual);

            // Primera selección 
            int f1 = -1, c1 = -1;
            boolean okPrimera = false;
            while (!okPrimera) 
            {
                int[] pos1 = pedirPosicion("Primera carta", filas, columnas);
                f1 = pos1[0]; c1 = pos1[1];

                if (tablero.tarjetaDescubierta(f1, c1)) 
                {
                    println(" Esa casilla ya está descubierta. Elige otra.");
                } 
                else 
                {
                    okPrimera = true;
                }
            }

            // Segunda selección con try-catch (prohibir la misma casilla)
            int f2 = -1, c2 = -1;
            boolean okSegunda = false;
            while (!okSegunda) 
            {
                try 
                {
                    int[] pos2 = pedirPosicion("Segunda carta", filas, columnas);
                    f2 = pos2[0]; c2 = pos2[1];

                    if (f1 == f2 && c1 == c2) 
                    {
                        // requisito: manejar con try-catch
                        throw new IllegalArgumentException("No puedes elegir la misma tarjeta dos veces en el mismo turno.");
                    }
                    if (tablero.tarjetaDescubierta(f2, c2)) 
                    {
                        println("⚠ Esa casilla ya está descubierta. Elige otra.");
                    } 
                    else 
                    {
                        okSegunda = true;
                    }
                } 
                catch (IllegalArgumentException e) 
                {
                    println(e.getMessage());
                }
            }

            // Voltear temporalmente y evaluar pareja
            Tarjeta t1 = tablero.getTarjeta(f1, c1);
            Tarjeta t2 = tablero.getTarjeta(f2, c2);


            if (t1 == null || t2 == null) 
            {
                println("Posición fuera de rango. Se pierde el turno.");
                turno = (turno == 1 ? 2 : 1);
                continue;
            }

            t1.destapar();
            t2.destapar();
            mostrarTablero();

            boolean pareja = t1.getTapaD().equals(t2.getTapaD());

            if (pareja)
            {
                t1.setEmparejada(true);
                t2.setEmparejada(true);
                if (turno == 1) puntos1++; else puntos2++;
                println("¡Pareja encontrada! Punto para " + actual + ". Continúa su turno.");
            } 
            else 
            {
                t1.tapar();
                t2.tapar();
                println("No son pareja. Cambio de turno.");
                turno = (turno == 1 ? 2 : 1);
            }

            mostrarPuntos(puntos1, puntos2, jugador1, jugador2);
        }

        // 3) Fin del juego, reporte
        println("\n=== ¡Juego terminado! ===");
        mostrarTablero();
        mostrarResultados(puntos1, puntos2, jugador1, jugador2);
    }

    //helpers

    private String leer(String porDefecto) {
        String s = sc.nextLine().trim();
        if (s.isEmpty()) s = porDefecto;
        return s;
    }

    private int pedirEnteroEnRango(String prompt, int min, int max) 
    {
        boolean ok = false;
        int valor = min;
        while (!ok) 
        {
            print(prompt);
            String linea = sc.nextLine().trim();

            try {
                int v = Integer.parseInt(linea);
                if (v < min || v > max) 
                {
                    println("Valor fuera de rango (" + min + " a " + max + ")."); //para que el valor no sea fuera de rango
                }
                else 
                {
                    valor = v;
                    ok = true;// sale hasta que un valor sea correcto
                }
            } catch (NumberFormatException e) {
                println("Ingresa un entero válido.");
            }
        }
        return valor;
    }

    private int[] pedirPosicion(String etiqueta, int filas, int columnas) 
    {
        println(etiqueta + " ->");
        int f = pedirEnteroEnRango("Fila (0.." + (filas - 1) + "): ", 0, filas - 1);
        int c = pedirEnteroEnRango("Columna (0.." + (columnas - 1) + "): ", 0, columnas - 1);
        return new int[]{f, c};
    }

    public void mostrarTablero() {
    // Encabezado de columnas
    print("   ");
    for (int j = 0; j < tablero.getColumnas(); j++) {
        if (j < 10) {
            print( j + " ");
        } else {
            print(j + " ");
        }
    }
    println("");

    // Filas con índice + contenido
    for (int i = 0; i < tablero.getFilas(); i++) {
        if (i < 10) {
            print(" " + i + " ");
        } else {
            print(i + " ");
        }
        for (int j = 0; j < tablero.getColumnas(); j++) {
            Tarjeta x = tablero.getTarjeta(i, j);
            print(x.toString() + " ");
        }
        println("");
    }
}


    public void mostrarPuntos(int p1, int p2, String n1, String n2) 
    {
        println("Marcador: " + n1 + " " + p1 + " - " + p2 + " " + n2);
    }

    public void mostrarResultados(int p1, int p2, String n1, String n2) 
    {
        println("\n===== Resumen =====");
        println(n1 + ": " + p1 + " punto(s)");
        println(n2 + ": " + p2 + " punto(s)");
        if (p1 > p2) println("Ganador: " + n1);
        else if (p2 > p1) println("Ganador: " + n2);
        else println("Empate ");
    }

    public void println(String mensaje) { System.out.println(mensaje); }
    public void print(String mensaje) { System.out.print(mensaje); }
}
