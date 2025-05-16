package game;

import buildings.Building;
import slimes.Slime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildingManager {

    private final List<Building> buildings = new ArrayList<>();
    private final List<Slime> slimes = new ArrayList<>();

    public void addBuilding(Building building) {
        if (!buildings.contains(building)) {
            buildings.add(building);
        }
    }

    public void addSlime(Slime slime) {
        if (!slimes.contains(slime)) {
            slimes.add(slime);
        }
    }

    public void update() {
        // Update slimes
        Iterator<Slime> slimeIterator = slimes.iterator();
        while (slimeIterator.hasNext()) {
            Slime slime = slimeIterator.next();
            slime.update();
            if (slime.isDead()) {
                slimeIterator.remove();
            }
        }

        // Buildings attack nearby slimes
        for (Building building : buildings) {
            building.attack(slimes);
        }
    }

    public void cleanup() {
        // Remove destroyed buildings
        buildings.removeIf(Building::isDestroyed);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Slime> getSlimes() {
        return slimes;
    }
}
