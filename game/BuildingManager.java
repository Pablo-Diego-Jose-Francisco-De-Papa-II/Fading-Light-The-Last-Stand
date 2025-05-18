package game;

import buildings.Building;
import buildings.TownHall;
import slimes.Slime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildingManager {

    private final List<Building> buildings = new ArrayList<>();
    private final List<Slime> slimes = new ArrayList<>();

    public void addBuilding(Building building) {
        if (!this.buildings.contains(building)) {
            this.buildings.add(building);
        }
    }

    public void addSlime(Slime slime) {
        if (!this.slimes.contains(slime)) {
            this.slimes.add(slime);
        }
    }

    public void update() {
        // Update slimes
        Iterator<Slime> slimeIterator = this.slimes.iterator();
        while (slimeIterator.hasNext()) {
            Slime slime = slimeIterator.next();
            slime.update();
            if (slime.isDead()) {
                slimeIterator.remove();
            }
        }

        // Buildings attack nearby slimes
        for (Building building : this.buildings) {
            System.out.println(building.getName());
            building.attack(this.slimes);
        }
    }

    public void cleanup() {
        // Remove destroyed buildings
        this.buildings.removeIf(Building::isDestroyed);
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }

    public List<Slime> getSlimes() {
        return this.slimes;
    }

    public Building getTownHall() {
        for (Building b : this.buildings) {
            if (b instanceof TownHall) {
                return b;
            }
        }
        return null;
    }

}
