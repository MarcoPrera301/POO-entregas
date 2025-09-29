public class Main {
    public static void main(String[] args) {
        // Ítems para vegetales
        Item espejo = new Item("Espejo", "Refleja el carácter: +0 HP, +25 ATK por un turno", 0, 25);
        Item replantamiento = new Item("Replantamiento", "Se cuestiona su existencia: +25 HP", 25, 0);
        Item shake = new Item("Shake de prote", "Sube todo: +15 HP, +15 ATK por un turno", 15, 15);
        Item intercambio = new Item("Intercambio equivalente", "Gana +75 ATK por un turno, pierde 100 HP", -100, 75);
        Item hieloSeco = new Item("Hielo seco", "Endurece: +50 HP", 50, 0);
        Item descongelacion = new Item("Descongelación", "Activa: -40 HP, +25 ATK por un turno", -40, 25);

        // Ítems para caminantes
        Item rabia = new Item("Rabia", "Enfurece: +25 ATK por un turno", 0, 25);
        Item cubeta = new Item("Cubeta", "Curación: +75 HP", 75, 0);

        // vegetales
        Vegetal v1 = new Vegetal("Coliflor malhumorada", "Refleja insultos con gracia.", "¡Alguien va a salar la sopa hoy!", 500, 10, "Reflejante", espejo, replantamiento);
        Vegetal v2 = new Vegetal("Vaina de alberjas", "Golpea rápido y seguido.", "¡Pum-pum-pum!", 300, 50, "Atacante", shake, intercambio);
        Vegetal v3 = new Vegetal("Zanahoria congelada", "Resiste muchísimo.", "Brrrr... ¡pero aguanto!", 800, 5, "Tanque", hieloSeco, descongelacion);

        // caminantes
        Caminante c1 = new Caminante("Caminante común", "Estándar, daño no ignorable.", "Grrrrr...", 300, 15, rabia, false);
        Caminante c2 = new Caminante("Caminante fuerte", "Más resistente.", "Grrrrr... (más fuerte)", 500, 25, cubeta, false);
        Caminante c3 = new Caminante("Caminante gordo (JEFE)", "Pega duro como jefe.", "Grrrrr... (te deja sordo)", 800, 40, cubeta, true);

        Vista vista = new Vista();
        Controlador ctrl = new Controlador(vista, v1, v2, v3, c1, c2, c3);
        ctrl.iniciarJuego();
    }
}
