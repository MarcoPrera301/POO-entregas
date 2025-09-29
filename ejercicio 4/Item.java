public class Item 
{
    private int modVida, modDanio;
    private String nombre, descripcion;
    
    public Item(String nombre, String descripcion, int modVida, int modDanio) 
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modVida = modVida;
        this.modDanio = modDanio;
    }

    public int getModVida() 
    {
        return modVida;
    }

    public int getModDanio() 
    {
        return modDanio;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public String getDescripcion() 
    {
        return descripcion;
    }

    public String toString() 
    {
        return "Item: " + nombre + "\nDescripcion: " + descripcion + "\nModificador de Vida: " + modVida + "\nModificador de Daño: " + modDanio;
    }

}
