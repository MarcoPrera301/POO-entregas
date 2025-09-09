import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class Tablero
{
    private Tarjeta[][] tablero;
    private int filas;
    private int columnas;
    public static final ArrayList<String> letras = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));

    public Tablero(int filas, int columnas) 
    {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new Tarjeta[filas][columnas];
    }

    public ArrayList<String> generarLetras()
    {
        ArrayList<String> seleccionados = new ArrayList<>();
        int totalLetras = (this.filas * this.columnas) / 2;

        for (int i=0 ; i<totalLetras ; i++)
        {
            for(int j=0 ; j<2 ; j++)
            {
                seleccionados.add(this.letras.get(i));
                Collections.shuffle(seleccionados);
            }        
        }
        return seleccionados;
    }

    public void generarTarjetas()
    {
        ArrayList<String> seleccionados = this.generarLetras();
        int index = 0;

        for (int i = 0; i < this.filas; i++) 
        {
            for (int j = 0; j < this.columnas; j++) 
            {
                if (index < seleccionados.size()) 
                {
                    this.tablero[i][j] = new Tarjeta(seleccionados.get(index));
                    index++;
                } 
                else 
                {
                    this.tablero[i][j] = new Tarjeta(null);
                }
            }
        }
    }
    

    public Tarjeta getTarjeta(int f, int c) 
    {
        if (f < 0 || f >= this.filas || c < 0 || c >= this.columnas) return null; //si la posicion es negativa o mayor al tamanio del tablero retorna null
        return this.tablero[f][c];
    }

    public int getFilas() 
    {
        return filas;
    }

    public int getColumnas() 
    {
        return columnas;
    }

    public boolean todasLasCartasEmparejadas() //si hay una tarjeta que no esta emparejada retorna un false
    {
    for (int i = 0; i < filas; i++) 
    {
        for (int j = 0; j < columnas; j++) 
        {
            if (!this.tablero[i][j].getEmparejada()) return false;
        }
    }
    return true;
    }

    public boolean tarjetaDescubierta(int fila, int columna) 
    {
        Tarjeta t = getTarjeta(fila, columna);
        
        return t != null && (t.getEmparejada() || t.estaDescubierta());
    }
}