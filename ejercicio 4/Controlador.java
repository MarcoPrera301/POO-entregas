import java.util.*;

public class Controlador 
{
    private Vista vista;
    private ArrayList<Combatiente> combatientes = new ArrayList<>();

    // Jugadores 
    private Vegetal vegetal1;
    private Vegetal vegetal2;
    private Vegetal vegetal3;

    // Enemigos 
    private Caminante caminante1;
    private Caminante caminante2;
    private Caminante caminante3;

    // Estado de batalla
    private int nRonda = 1;
    private boolean turnoEquipo = true; // true: jugador, false: enemigos
    private boolean hayJugadores = true;
    private int SalidaMenu = 0;

    // Registro de últimas 3 acciones
    private ArrayDeque<String> registroAcciones = new ArrayDeque<>(3);

    // Constructor 
    public Controlador(Vista vista,Vegetal v1, Vegetal v2, Vegetal v3,Caminante c1, Caminante c2, Caminante c3) 
    {
        this.vista = vista;
        this.vegetal1 = v1;
        this.vegetal2 = v2;
        this.vegetal3 = v3;
        this.caminante1 = c1;
        this.caminante2 = c2;
        this.caminante3 = c3;
    }

    public void iniciarJuego() 
    {
        // 1) Elegir jugador
        int idxJugador = vista.escogerJugador(vegetal1, vegetal2, vegetal3);

        Vegetal jugador = switch (idxJugador) 
        {
            case 2 -> vegetal2;
            case 3 -> vegetal3;
            default -> vegetal1;
        };

        // 2) Generar enemigos
        ArrayList<Combatiente> enemigos = generarCombatientes();

        //lista de combatientes
        combatientes.clear();
        combatientes.add(jugador);
        combatientes.addAll(enemigos);

        // 4) Mensajes iniciales 
        pushAccion(jugador.getLinea());
        for (Combatiente e : enemigos) pushAccion(e.getLinea());

        boolean enCurso = true;
        while (enCurso) 
        {
            vista.mostrarBatalla(nRonda, mostrarStatus(combatientes), ultimasAcciones());

            // turno jugador
            turnoEquipo = true;
            hayJugadores = jugador.getVida() > 0;
            if (!hayJugadores) 
            {
                enCurso = false;
                break;
            }

            vista.mostrarMenuDecision1(jugador);
            int accion = vista.escogerAccion();
            if (accion == 4) 
            {
                SalidaMenu = 4;
                pushAccion("El jugador decidió salir del combate.");
                break;
            }
            switch (accion) 
            {
                case 1 -> 
                { // Atacar
                    if (enemigos.isEmpty()) 
                    {
                        pushAccion("No hay enemigos para atacar.");
                    } 
                    else 
                    {
                        int idxObj = vista.mostrarMenuDecision2("Elige objetivo", enemigos);
                        idxObj = clampIndex(idxObj, enemigos.size());
                        Combatiente obj = enemigos.get(idxObj);
                        atacarContrario(jugador, obj);

                        if (obj.getVida() <= 0) 
                        {
                            pushAccion(obj.getNombre() + " ha caído.");
                            enemigos.remove(idxObj);
                        }
                    }
                }
                case 2 -> 
                { // Ítem 
                    int cual = vista.mostrarMenuItems(jugador);
                    Item elegido = (cual == 2) ? jugador.getItem2() : jugador.getItem1();
                    // objetivos posibles
                    ArrayList<Combatiente> objetivos = new ArrayList<>();
                    objetivos.addAll(enemigos);
                    objetivos.add(jugador);
                    BoostItem(elegido, jugador, objetivos);
                }

                case 3 -> pushAccion(jugador.getNombre() + " pasa su turno.");

                default -> pushAccion("Opción inválida, se considera pasar turno.");
            }

            if (enemigos.isEmpty()) 
            {
                pushAccion("¡" + jugador.getNombre() + " ganó la batalla!");
                enCurso = false;
                break;
            }

            //Turno enemigos 
            turnoEquipo = false;
            for (int i = 0; i < enemigos.size(); i++) 
            {
                Combatiente enemigo = enemigos.get(i);
                if (enemigo.getVida() <= 0) continue;

                vista.mostrarMenuDecision1(enemigo);
                int accionE = vista.escogerAccion();

                if (accionE == 4) 
                {
                    SalidaMenu = 4;
                    pushAccion("Se eligió salir durante el turno de " + enemigo.getNombre() + ".");
                    enCurso = false;
                    break;
                }

                switch (accionE) 
                {
                    case 1 -> 
                    { // Atacar al jugador
                        atacarContrario(enemigo, jugador);
                        if (jugador.getVida() <= 0) 
                        {
                            pushAccion(jugador.getNombre() + " ha caído.");
                            enCurso = false;
                        }
                    }
                    case 2 -> 
                    { // Especial del enemigo 
                        if (enemigo instanceof Caminante) 
                        {
                            Item especial = ((Caminante) enemigo).getHespecial();
                            int conf = vista.mostrarMenuEspecialEnemigo((Caminante) enemigo, especial);
                            if (conf == 1) {
                                ArrayList<Combatiente> objetivos = new ArrayList<>();
                                objetivos.add(jugador);
                                objetivos.add(enemigo); // para curación/buff propio
                                BoostItem(especial, enemigo, objetivos);
                            } 
                            else 
                            {
                                pushAccion(enemigo.getNombre() + " canceló su especial.");
                            }
                        } 
                        else 
                        {
                            pushAccion(enemigo.getNombre() + " no tiene habilidad especial.");
                        }
                    }

                    case 3 -> pushAccion(enemigo.getNombre() + " pasa su turno.");

                    default -> pushAccion("Opción inválida para " + enemigo.getNombre() + ", se pasa turno.");
                }

                if (!enCurso) break;
            }

            if (jugador.getVida() <= 0) 
            {
                pushAccion("¡Los enemigos ganaron la batalla!");
                enCurso = false;
                break;
            }

            nRonda++;
        }

        // Pantalla final
        vista.mostrarBatalla(nRonda, mostrarStatus(combatientes), ultimasAcciones());
    }

    // Genera 1..3 enemigos clonando desde el pool
    public ArrayList<Combatiente> generarCombatientes() 
    {
        ArrayList<Combatiente> enemigos = new ArrayList<>();
        ArrayList<Caminante> pool = new ArrayList<>();

        if (caminante1 != null) pool.add(caminante1);

        if (caminante2 != null) pool.add(caminante2);

        if (caminante3 != null) pool.add(caminante3);

        int cuantos = vista.randomInclusive(1, 3);

        for (int i = 0; i < cuantos; i++) 
        {
            Caminante base = pool.get(vista.random(pool.size()));
            enemigos.add(clonarCaminante(base));
        }

        return enemigos;
    }

    private Caminante clonarCaminante(Caminante b) 
    {
        return new Caminante(
                b.getNombre(),
                b.getDescripcion(),
                b.getLinea(),
                b.getVida(),
                b.getAtaque(),
                b.getHespecial(),
                /* bonusJefe */ false 
        );
    }

    public void atacarContrario(Combatiente atacante, Combatiente objetivo) 
    {
        int danio = atacante.getAtaque();
        String msg = objetivo.perderVida(danio);
        pushAccion(atacante.getNombre() + " ataca a " + objetivo.getNombre() + " por " + danio + " de daño. " + msg);

        if (objetivo.getVida() <= 0) 
        {
            pushAccion(objetivo.getNombre() + " al morir: " + objetivo.getLinea());
        }
    }

    // Ítems generales: curan (modVida), buffean ataque por un turno (modDanio)
    public void BoostItem(Item item, Combatiente usuario, List<Combatiente> posibles) 
    {
        int mV = item.getModVida();
        int mD = item.getModDanio();

        Combatiente objetivo = usuario;
        if (posibles != null && !posibles.isEmpty()) 
        {
            int idx = vista.mostrarMenuDecision2("Selecciona objetivo para " + item.getNombre(), posibles);
            idx = clampIndex(idx, posibles.size());
            objetivo = posibles.get(idx);
        }

        if (mV != 0) 
        {
            String s = objetivo.ganarVida(mV);
            pushAccion(usuario.getNombre() + " usa " + item.getNombre() + " sobre " + objetivo.getNombre() + ": " + s);
        }

        if (mD != 0) 
        {
            String s1 = usuario.ganarAtaque(mD);
            pushAccion(usuario.getNombre() + " obtiene +" + mD + " ATK por este turno (" + item.getNombre() + ").");
            // Si el objetivo es rival directo y no es el mismo
            if (objetivo != usuario) atacarContrario(usuario, objetivo);
            usuario.perderAtaque(mD); // revertir buff
        }
    }

    public String mostrarStatus(ArrayList<Combatiente> lista) 
    {
        StringBuilder sb = new StringBuilder();
        for (Combatiente c : lista) 
        {
            sb.append(String.format("- %-22s  HP:%4d  ATK:%3d%n",c.getNombre(), c.getVida(), c.getAtaque()));
        }
        return sb.toString();
    }

    public void cambioDeTurno() 
    { 
        turnoEquipo = !turnoEquipo; 
    }

    private void pushAccion(String s) 
    {
        if (s == null || s.isEmpty()) return;
        if (registroAcciones.size() == 3) registroAcciones.removeFirst();
        registroAcciones.addLast(s);
    }

    private List<String> ultimasAcciones() 
    { 
        return new ArrayList<>(registroAcciones); 
    }

    private int clampIndex(int idx1, int size) 
    {
        if (size <= 0) return 0;
        int i = idx1 - 1;
        if (i < 0) i = 0;
        if (i >= size) i = size - 1;
        return i;
    }
}
