package game;

import buildings.Building;
import buildings.TownHall;
import slimes.Slime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Správca budov a nepriateľov v hre.
 * Zodpovedá za ich aktualizáciu a správu.
 */
public class BuildingManager {

    private final List<Building> buildings = new ArrayList<>();
    private final List<Slime> slimes = new ArrayList<>();

    /**
     * Pridá budovu do zoznamu spravovaných budov, ak tam ešte nie je.
     *
     * @param building Budova na pridanie
     */
    public void addBuilding(Building building) {
        if (!this.buildings.contains(building)) {
            this.buildings.add(building);
        }
    }

    /**
     * Pridá nepriateľa typu Slime do zoznamu, ak tam ešte nie je.
     *
     * @param slime Slime na pridanie
     */
    public void addSlime(Slime slime) {
        if (!this.slimes.contains(slime)) {
            this.slimes.add(slime);
        }
    }

    /**
     * Aktualizuje stav všetkých slimov a zabezpečí, že budovy útočia na blízkych nepriateľov.
     * Odstraňuje mŕtvych slimov zo zoznamu.
     */
    public void update() {
        // Aktualizácia slimov
        Iterator<Slime> slimeIterator = this.slimes.iterator();
        while (slimeIterator.hasNext()) {
            Slime slime = slimeIterator.next();
            slime.update();
            if (slime.isDead()) {
                slimeIterator.remove();
            }
        }

        // Budovy útočia na blízkych slimov
        for (Building building : this.buildings) {
            System.out.println(building.getName());
            building.attack(this.slimes);
        }
    }

    /**
     * Odstráni všetky zničené budovy zo zoznamu.
     */
    public void cleanup() {
        List<Building> filtered = new ArrayList<>();
        for (Building b : this.buildings) {
            if (!b.isDestroyed()) {
                filtered.add(b);
            }
        }

        this.buildings.clear();
        this.buildings.addAll(filtered);
    }

    /**
     * Vráti zoznam všetkých budov spravovaných touto triedou.
     *
     * @return Zoznam budov
     */
    public List<Building> getBuildings() {
        return this.buildings;
    }

    /**
     * Vráti zoznam všetkých slimov, ktoré sa momentálne nachádzajú v hre.
     *
     * @return Zoznam slimov
     */
    public List<Slime> getSlimes() {
        return this.slimes;
    }

    /**
     * Vráti TownHall, ak sa nachádza medzi budovami.
     *
     * @return Inštancia TownHall, alebo null, ak neexistuje
     */
    public Building getTownHall() {
        for (Building b : this.buildings) {
            if (b instanceof TownHall) {
                return b;
            }
        }
        return null;
    }

}
