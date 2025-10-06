import java.util.Random;

public class CPU extends Proceso
{
    Random random = new Random();
    private int usoRAM;
    private int RAM;

    public CPU(String PID, String nombre, int RAM) 
    {
        super(PID, nombre);
        this.RAM = RAM;
        this.usoRAM = random.nextInt(0, RAM + 1);
    }

    public String execute()
    {
        return usoMemoriaRAM();
    }

    public String usoMemoriaRAM()
    {
        int i=(this.usoRAM/this.RAM)*100;

        return "El proceso " + getNombre() + " con PID " + getPID() + " esta usando " + i + "% de la memoria RAM.";
    }
}