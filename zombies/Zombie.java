package zombies;

public abstract class Zombie {
    int heath;
    int size;
    int speed;
    int attackDamage;
    int attackRange;
    int attackSpeed;
    String icon;

    public abstract void move();

    public abstract void dealDamage();

    public abstract void die();
}
