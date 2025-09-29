import java.util.*;

public class Vista {

private final Scanner sc = new Scanner(System.in);
private final Random rnd = new Random();

public void mostrarBatalla(int ronda, String status, List<String> ultAcciones) 
{
    System.out.println("\n===== BATALLA - RONDA " + ronda + " =====");
    System.out.println(status);
    System.out.println("---- Últimas acciones ----");

    if (ultAcciones == null || ultAcciones.isEmpty())
    {
    System.out.println("(sin acciones)");
    } else {
    for (String a : ultAcciones) System.out.println("• " + a);
    }
    System.out.println("--------------------------\n");
}

public void mostrarMenuDecision1(Combatiente quien) 
{
    System.out.println("Turno de: " + quien.getNombre());
    System.out.println("1) Atacar");
    System.out.println("2) Usar ítem / especial");
    System.out.println("3) Pasar");
    System.out.println("4) Salir");
}

public void mostrarMenuDesicion1(Combatiente quien) 
{ 
    mostrarMenuDecision1(quien); 
}

public int escogerAccion() 
{
    System.out.print("Elige acción: ");
    return leerOpcion();
}

public int mostrarMenuDecision2(String titulo, List<Combatiente> objetivos) 
{
    System.out.println(titulo + ":");
    for (int i = 0; i < objetivos.size(); i++) 
    {
        Combatiente c = objetivos.get(i);
        System.out.printf("%d) %s (HP:%d ATK:%d)%n", i + 1, c.getNombre(), c.getVida(), c.getAtaque());
    }
    System.out.print("Opción: ");
    return leerOpcion();
}

public int mostrarMenuDesicion2(String t, List<Combatiente> o) 
{ 
    return mostrarMenuDecision2(t,o); 
}

public int mostrarMenuItems(Vegetal jugador) 
{
    System.out.println("Elige ítem a usar:");
    System.out.println("1) " + jugador.getItem1().getNombre() + " — " + jugador.getItem1().getDescripcion());
    System.out.println("2) " + jugador.getItem2().getNombre() + " — " + jugador.getItem2().getDescripcion());
    System.out.print("Opción: ");
    return leerOpcion();
}

public int mostrarMenuEspecialEnemigo(Caminante enemigo, Item especial) 
{
    System.out.println("Habilidad especial de " + enemigo.getNombre() + ": " + especial.getNombre());
    System.out.println("Descripción: " + especial.getDescripcion());
    System.out.println("1) Usar especial");
    System.out.println("2) Cancelar");
    System.out.print("Opción: ");
    return leerOpcion();
}

public int escogerJugador(Vegetal v1, Vegetal v2, Vegetal v3) 
{
    System.out.println("Elige tu combatiente:");
    System.out.println("1) " + v1.getNombre() + " — " + v1.getRol());
    System.out.println("2) " + v2.getNombre() + " — " + v2.getRol());
    System.out.println("3) " + v3.getNombre() + " — " + v3.getRol());
    System.out.print("Opción: ");
    return leerOpcion();
}

private int leerOpcion() 
{
    while (!sc.hasNextInt()) 
    { 
        sc.next(); 
    }
    return sc.nextInt();
}

public int random(int maxExclusive) 
{ 
    return rnd.nextInt(maxExclusive); 
}

public int randomInclusive(int min, int max) 
{ 
    return rnd.nextInt((max - min) + 1) + min; 
}
}
