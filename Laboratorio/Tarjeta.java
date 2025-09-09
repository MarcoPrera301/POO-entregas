public class Tarjeta {
    private String tapaC="X";
    private String tapaD;
    private boolean emparejada=false;
    private boolean descubierta=false;


    public Tarjeta(String tapaD) 
    {
        this.tapaD = tapaD;
        this.tapaC = "X";
        this.emparejada = false;
        this.descubierta = false;
    }

    public String getTapaD() 
    {
        return this.tapaD;
    }

    public String getTapaC() 
    {
        return this.tapaC;
    }

    public boolean getEmparejada() 
    {
        return this.emparejada;
    }

    public void setEmparejada(boolean emparejada) 
    {
        this.emparejada = emparejada;
    }

    public boolean estaDescubierta() 
    {
        return this.descubierta;
    }

    public void destapar() 
    {
        this.descubierta = true;
    }

    public void tapar() 
    {
        if (!this.emparejada)  //Si la tarjeta no tiene pareja se declara descubierta como false
        {
            this.descubierta = false;
        }
    }

    public boolean tieneEmoji()
    {
        return !this.tapaD.equals(null);
    }

    @Override
    public String toString() //si la terjeta esta volteada o emparejada ensenia su letra 
    {
        return (this.emparejada || this.descubierta) ? this.tapaD : this.tapaC;
    }


}