public enum Enemies {
    // Základný zombie bez špeciálnych schopností, len neúnavný útočník so stredným množstvom života.
    BITER(100, 1, 10, 1, 5, 5, "esteNemam.txt"),

    // Zombie, ktorý môže útočiť na diaľku pomocou projektilov, ale má menej života ako Biter.
    SPITTER(),

    // Zombie s extrémne veľa životmi, ale spôsobuje minimálne poškodenie.
    TANK(),

    // Rýchla verzia Bitera, rýchly a smrteľný v boji na blízko.
    FERAL(75, 2, 10, 1, 20, 10, "esteNemam.txt"),

    //  Zombík s veľkým množstvom života, ktorý vydrží viac poškodenia ako ostatní zombies.
    CAVEBORN(),

    // Boss, pomalý, ale s obrovským množstvom života a vysokým poškodením.
    JUGGERNAUT(),

    // Boss, ktorý je viacero zombies spojených do jedného organizmu a po zabití ostávajúci zombies pokračujú v útoku.
    BITER_KING(),

    // Zombifikovaný medveď, ktorý je rýchly, odolný a spôsobuje veľké poškodenie.
    RAVANGER();

    int heath;
    int size;
    int speed;
    int attackDamage;
    int attackRange;
    int attackSpeed;
    String icon;

    private Enemies(int heath, int size, int attackDamage, int attackRange, int speed, int attackSpeed, String icon) {
        this.heath = heath;
        this.size = size;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        this.icon = icon;
    }
}