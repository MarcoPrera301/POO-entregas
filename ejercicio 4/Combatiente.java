public class Combatiente 
{
    protected String nombre, descripcion, linea;
    protected int vida, ataque;

    public Combatiente(String nombre, String descripcion,String linea, int vida, int ataque) 
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vida = vida;
        this.ataque = ataque;
        this.linea = linea;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public String getDescripcion() 
    {
        return descripcion;
    }

    public int getVida() 
    {
        return vida;
    }

    public int getAtaque() 
    {
        return ataque;
    }

    public String getLinea() 
    {
        return linea;
    }

    public String perderVida(int danio) 
    {
        this.vida= this.vida - danio;

        if (this.vida < 0) 
        {
            this.vida = 0;
        }

        return this.nombre + " ha perdido " + danio + " puntos de vida. Vida restante: " + this.vida;
    }

    public String ganarAtaque(int incremento) 
    {
        this.ataque = this.ataque + incremento;

        return this.nombre + " ha incrementado su ataque en " + incremento + ". Ataque actual: " + this.ataque;
    }

    public String ganarVida(int incremento) 
    {
        this.vida = this.vida + incremento;

        return this.nombre + " ha incrementado su vida en " + incremento + ". Vida actual: " + this.vida;
    }

    public String perderAtaque(int decremento) 
    {
        this.ataque = this.ataque - decremento;

        if (this.ataque < 0) 
        {
            this.ataque = 0;
        }

        return this.nombre + " ha perdido " + decremento + " puntos de ataque. Ataque actual: " + this.ataque;
    }

    
}