public class IO extends Proceso
{
    private boolean click;

    public IO(String PID, String nombre, boolean click) 
    {
        super(PID, nombre);
        this.click = click;
    }

    public String execute()
    {
        return ejecutarPorClick();
    }

    public String ejecutarPorClick()
    {
        if(this.click)
        {
            return "El proceso " + getNombre() + " con PID " + getPID() + " ha realizado por la deteccion de un click.";
        }
        else
        {
            return "El proceso " + getNombre() + " con PID " + getPID() + " no se ha ejecutado, por que no se ha detectado un click.";
        }
    }

}