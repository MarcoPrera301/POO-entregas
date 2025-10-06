public abstract class Proceso 
{
    protected String PID;
    protected String nombre;

    public Proceso(String PID, String nombre) 
    {
        this.PID = PID;
        this.nombre = nombre;
    }

    public String getPID() 
    {
        return PID;
    }

    public String getNombre() 
    {
        return nombre;
    }   

    abstract public String execute();
}

