class Vegetal extends Combatiente
{
    private Item item1, item2;
    private String rol;

    public Vegetal(String nombre, String descripcion, String linea, int vida, int ataque, String rol, Item item1, Item item2) 
    {
        super(nombre, descripcion, linea, vida, ataque);
        this.rol = rol;
        this.item1 = item1;
        this.item2 = item2;
    }

    public String getRol() 
    {
        return rol;
    }

    public Item getItem1() 
    {
        return item1;
    }

    public Item getItem2() 
    {
        return item2;
    }

    public String toString() 
    {
        return "Vegetal: " + getNombre() + "\nDescripcion: " + getDescripcion() + "\nLinea: " + getLinea() + "\nVida: " + getVida() + "\nAtaque: " + getAtaque() + "\nRol: " + rol + "\n\nItem 1:\n" + item1.toString() + "\n\nItem 2:\n" + item2.toString();
    }
}