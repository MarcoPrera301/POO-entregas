import java.util.ArrayList;
import java.util.Arrays;

public class Controlador
{
    private Vista vista;
    private ArrayList<Proceso> procesos;

    public Controlador(Vista vista) 
    {
        this.vista = vista;
        this.procesos = new ArrayList<Proceso>(Arrays.asList(new Deamon("001", "Ejecucion de antivirus"), new CPU("002", "Proceso de edicion de video", 16), new IO("003", "Ejecicion de CSGO", true)));
    }


    public void ejecutarProcesos()
    {
        for(Proceso proceso : procesos)
        {
            vista.mostrarProceso(proceso.execute());
            vista.enter();
        }
    }

    public void iniciarSistema()
    {
        vista.iniciar();
        ejecutarProcesos();
        vista.apagar();
    }
}