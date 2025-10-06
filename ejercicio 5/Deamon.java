public class Deamon extends Proceso
{
    public Deamon(String PID, String nombre) 
    {
        super(PID, nombre);
    }

    public String execute()
    {
        return "El proceso " + getNombre() + " con PID " + getPID() + " esta ejecutando el anti virus en segundo plano.";
    }
}