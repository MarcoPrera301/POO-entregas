public class Juego 
{

    private final Tablero tablero;
    private final Jugador jugador1;
    private final Jugador jugador2;
    private int turno; // 1 o 2

    public Juego(int filas, int columnas, String nombre1, String nombre2) 
    {
        this.tablero = new Tablero(filas, columnas);
        this.jugador1 = new Jugador(nombre1);
        this.jugador2 = new Jugador(nombre2);
        this.turno = 1; // inicia Jugador 1
    }

    public Tablero getTablero() 
    { 
        return this.tablero; 
    }

    public Jugador getJugadorActual() 
    { 
        return (this.turno == 1 ? this.jugador1 : this.jugador2); 
    }

    public Jugador getJugador1() 
    { 
        return this.jugador1; 
    }

    public Jugador getJugador2() 
    { 
        return this.jugador2; 
    }

    public boolean juegoTerminado() 
    { 
        return tablero.todasLasCartasEmparejadas(); 
    }

    
    // Ejecuta una ronda: dos destapes. Devuelve true si hubo pareja (y el mismo jugador sigue);
    // false si no hubo pareja (cambia el turno).
    // Valida:
    // 1) no destapar descubiertas/emparejadas
    // 2) no repetir la misma casilla en el mismo turno (esto se maneja desde Vista con try-catch)
    
    public boolean jugarRonda(int f1, int c1, int f2, int c2) 
    {
        Tarjeta t1 = tablero.getTarjeta(f1, c1);
        Tarjeta t2 = tablero.getTarjeta(f2, c2);

        if (t1 == null || t2 == null) throw new IllegalArgumentException("Posición fuera de rango.");

        if (t1.getEmparejada() || t1.estaDescubierta())
            throw new IllegalStateException("La primera casilla ya está descubierta.");

        if (t2.getEmparejada() || t2.estaDescubierta())
            throw new IllegalStateException("La segunda casilla ya está descubierta.");

        // destapar temporal para mostrar
        t1.destapar();
        t2.destapar();

        boolean pareja = t1.getTapaD().equals(t2.getTapaD()); //confirma si la pareja es correcta

        if (pareja) 
        {
            t1.setEmparejada(true);
            t2.setEmparejada(true);
            getJugadorActual().sumarPunto();
            // El turno NO cambia si acierta
            return true;
        } 
        else 
        {
            // volver a tapar si no son pareja
            t1.tapar();
            t2.tapar();
            // cambiar turno
            turno = (turno == 1 ? 2 : 1);
            return false;
        }
    }
}