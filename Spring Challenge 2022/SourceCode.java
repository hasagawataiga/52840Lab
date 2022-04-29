import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {


    public static void main(String args[]) {
        int myBase = -1;   //0: left, 1: right
        Scanner in = new Scanner(System.in);
        int baseX = in.nextInt(); // The corner of the map representing your base
        int baseY = in.nextInt();
        int heroesPerPlayer = in.nextInt(); // Always 3

        //Needed variables
        Coordinate myRoot = new Coordinate();
        if(baseX > 10){
            myBase = 1;
            myRoot = new Coordinate(17630, 9000);
        }else{
            myBase = 0;
            myRoot = new Coordinate(0, 0);
        }
        Resource mine = new Resource();
        Resource enemy = new Resource();
        Entity[] myHeroes = new Entity[3];
        int myHeroesIndex = 0;
        Entity[] enemyHeroes = new Entity[3];
        int enemyHeroesIndex = 0;
        Entity[] monsters;
        int monstersIndex = 0;
        ArrayList<Entity> myMonsters = new ArrayList<Entity>();
        // game loop
        while (true) {
            //Reseted parameters
            myHeroesIndex = 0;
            enemyHeroesIndex = 0;
            monstersIndex = 0;
            myMonsters = new ArrayList<Entity>();
            //Take new value
            for (int i = 0; i < 2; i++) {
                int health = in.nextInt(); // Each player's base health
                int mana = in.nextInt(); // Ignore in the first league; Spend ten mana to cast a spell
                if(i == 0){
                    mine = new Resource(health, mana);
                }else{
                    enemy = new Resource(health, mana);
                }
            }
            int entityCount = in.nextInt(); // Amount of heros and monsters you can see
            monsters = new Entity[entityCount - 6];
            for (int i = 0; i < entityCount; i++) {
                int id = in.nextInt(); // Unique identifier
                int type = in.nextInt(); // 0=monster, 1=your hero, 2=opponent hero
                int x = in.nextInt(); // Position of this entity
                int y = in.nextInt();
                int shieldLife = in.nextInt(); // Ignore for this league; Count down until shield spell fades
                int isControlled = in.nextInt(); // Ignore for this league; Equals 1 when this entity is under a control spell
                int health = in.nextInt(); // Remaining health of this monster
                int vx = in.nextInt(); // Trajectory of this monster
                int vy = in.nextInt();
                int nearBase = in.nextInt(); // 0=monster with no target yet, 1=monster targeting a base
                int threatFor = in.nextInt(); // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                if(type == 0){
                    monsters[monstersIndex] = new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor);
                    monstersIndex++;
                }else if(type == 1){
                    myHeroes[myHeroesIndex] = new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor);
                    myHeroesIndex++;
                }else {
                    enemyHeroes[enemyHeroesIndex] = new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor);
                    enemyHeroesIndex++;
                }
            }

            //Find the monsters which will eventually reach my base
            for(int i = 0; i < monsters.length; i++){
                if(monsters[i].getThreatFor() == 1){
                    myMonsters.add(monsters[i]);
                }
            }
            sortList(myMonsters, myRoot);
            int amountOfMonstersReachBase = -1;
            if(!myMonsters.isEmpty()){
                amountOfMonstersReachBase = myMonsters.size();           
            }

            for (int i = 0; i < heroesPerPlayer; i++) {
                if(amountOfMonstersReachBase > 0){
                    System.out.println("MOVE " + myMonsters.get(myMonsters.size() - amountOfMonstersReachBase).getCoordinate());
                    amountOfMonstersReachBase--;
                }else{
                    System.out.println("MOVE 6800 3800");
                }          


                // In the first league: MOVE <x> <y> | WAIT; In later leagues: | SPELL <spellParams>;
                
            }
            //Debug
            System.err.println("My base is: " + myBase);
            System.err.println("Mine's stats: " + mine.getBaseHealth() + " " + mine.getMana());
            System.err.println("Enemy's stats: " + enemy.getBaseHealth() + " " + enemy.getMana());
            System.err.println("Monsters: " + myMonsters.size());
            System.err.println("\t " + myMonsters);
        }
    }
    
    //Sorting array list by distance
    public static void sortList(ArrayList<Entity> myMonsters, Coordinate root){
        Collections.sort(myMonsters, new Comparator<Entity>(){
            @Override public int compare(Entity e1, Entity e2){
                return e1.getDistance(root).compareTo(e2.getDistance(root));
            }
        });
    }

    //Calculate distance
    public int Distance(Entity unit, Coordinate root){         
        int distance = 0;
        int slotX = unit.getCoordinate().getX();
        int slotY = unit.getCoordinate().getY();
        distance = (int) Math.sqrt(Math.pow(Math.abs(slotX - root.getX()), 2) + Math.pow(Math.abs(slotY - root.getY()), 2));
        return distance;
    }
}

//Coordinate class
class Coordinate{
    private int x;
    private int y;
    //Constructor
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Coordinate() {
    }
    //GETTER
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    //SETTER
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    @Override
    public String toString(){return x + " " + y;}
}


//Player's remain
class Resource{
    private int baseHealth;
    private int mana;
    public Resource() {
    }
    public int getBaseHealth() {
        return baseHealth;
    }
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }
    public int getMana() {
        return mana;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public Resource(int baseHealth, int mana) {
        this.baseHealth = baseHealth;
        this.mana = mana;
    }
}

class Entity{
    private int id;
    private int type;
    public Entity() {
    }
    private Coordinate coordinate;
    private int shieldLife;
    private int isControlled;
    private int health;
    private SpeedVector speedVector;
    private int nearBase;
    private int threatFor;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public int getShieldLife() {
        return shieldLife;
    }
    public void setShieldLife(int shieldLife) {
        this.shieldLife = shieldLife;
    }
    public int getIsControlled() {
        return isControlled;
    }
    public void setIsControlled(int isControlled) {
        this.isControlled = isControlled;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public SpeedVector getSpeedVector() {
        return speedVector;
    }
    public void setSpeedVector(SpeedVector speedVector) {
        this.speedVector = speedVector;
    }
    public int getNearBase() {
        return nearBase;
    }
    public void setNearBase(int nearBase) {
        this.nearBase = nearBase;
    }
    public int getThreatFor() {
        return threatFor;
    }
    public void setThreatFor(int threatFor) {
        this.threatFor = threatFor;
    }
    public Entity(int id, int type, Coordinate coordinate, int shieldLife, int isControlled, int health,
            SpeedVector speedVector, int nearBase, int threatFor) {
        this.id = id;
        this.type = type;
        this.coordinate = coordinate;
        this.shieldLife = shieldLife;
        this.isControlled = isControlled;
        this.health = health;
        this.speedVector = speedVector;
        this.nearBase = nearBase;
        this.threatFor = threatFor;
    }
    public Float getDistance(Coordinate root){
        float distance = (int) Math.sqrt(Math.pow(Math.abs(coordinate.getX() - root.getX()), 2) + Math.pow(Math.abs(coordinate.getY() - root.getY()), 2));
        return distance;
    }
    @Override
    public String toString(){
        return this.id + ":" + this.health;
    }
}

//SpeedVector
class SpeedVector{
    private int vx;
    private int vy;
    public SpeedVector(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }
    public int getVx() {
        return vx;
    }
    public void setVx(int vx) {
        this.vx = vx;
    }
    public int getVy() {
        return vy;
    }
    public void setVy(int vy) {
        this.vy = vy;
    }
}