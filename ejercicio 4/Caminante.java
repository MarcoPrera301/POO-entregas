class Caminante extends Combatiente
{
    private Item hespecial;
    private boolean bonusJefe;

    public Caminante(String nombre, String descripcion, String linea, int vida, int ataque, Item hespecial, boolean bonusJefe) 
    {
        super(nombre, descripcion, linea, vida, ataque);
        this.hespecial = hespecial;
        this.bonusJefe = bonusJefe;
    }

    public Item getHespecial() 
    {
        return this.hespecial;
    }

    public boolean isBonusJefe() 
    {
        return this.bonusJefe;
    }

    public String buffearJefe() 
    {
        if (this.bonusJefe) 
        {
            this.ganarVida(this.vida);

            int aumento = this.ataque*2;

            this.ganarAtaque(aumento);
            return this.getNombre() + " es un jefe, ha recibido su bono de vida y ataque.";
        } 
        else 
        {
            return this.getNombre() + " este caminante es normal.";
        }
    }
}