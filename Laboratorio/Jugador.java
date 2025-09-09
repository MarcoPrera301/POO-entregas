public class Jugador 
{
    private final String nombre;
    private int puntos;

    public Jugador(String nombre) 
    {
        this.nombre = nombre;
        this.puntos = 0;
    }

    public String getNombre() 
    { 
        return this.nombre; 
    }

    public int getPuntos() 
    { 
        return this.puntos; 
    }

    public void sumarPunto() 
    { 
        this.puntos++; 
    }
}