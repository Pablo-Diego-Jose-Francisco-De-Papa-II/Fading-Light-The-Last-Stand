import buildings.Building;
import game.PlayingArea;
import zombies.Biter;
import zombies.Zombie;

import java.util.ArrayList;
import java.util.List;

import static buildings.Building.createBuilding;

public class Main {

    public static void main(String[] args) {
        // Initialize game map
        PlayingArea map = new PlayingArea();

        // List of active zombies
        List<Zombie> zombies = new ArrayList<>();

        // Place a Watch Tower at (10, 10) using the Shop
        Building tower = createBuilding("Watch Tower", map, 10, 10);
        if (tower != null) {
            map.getTile(10, 10).setBuilding(tower);
            System.out.println("Placed Watch Tower at (10, 10)");
        }

        // Spawn a zombie at (15, 10)
        Zombie biter = new Biter(map, 15, 10);
        zombies.add(biter);
        System.out.println("Spawned zombie at (15, 10)");

        // Simulate turns
        for (int turn = 0; turn < 10; turn++) {
            System.out.println("\n--- Turn " + turn + " ---");

            // Zombie actions
            for (Zombie z : zombies) {
                z.move();
                z.dealDamage();
                System.out.println("Zombie moved to (" + z.getX() + ", " + z.getY() + ")");
            }

            // Building attacks
            if (!tower.isDestroyed()) {
                tower.attack(zombies);
            }

            // Remove dead zombies
            zombies.removeIf(z -> z.getHealth() <= 0);
        }

        System.out.println("\nSimulation ended.");
    }
}
