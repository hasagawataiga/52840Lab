import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * Version:2.0
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
        Coordinate enemyRoot = new Coordinate();
        if(baseX > 10){
            myBase = 1;
            myRoot = new Coordinate(17630, 9000);
            enemyRoot = new Coordinate(0, 0);
        }else{
            myBase = 0;
            myRoot = new Coordinate(0, 0);
            enemyRoot = new Coordinate(17630, 9000);
        }
        Resource mine = new Resource();
        Resource enemy = new Resource();
        Entity[] myHeroes = new Entity[3];
        int myHeroesIndex = 0;
        Entity[] enemyHeroes = new Entity[3];
        int enemyHeroesIndex = 0;
        ArrayList<Entity> monsters = new ArrayList<Entity>();
        int monstersIndex = 0;
        ArrayList<Entity> myMonsters = new ArrayList<Entity>();
        ArrayList<Entity> firstHeroMonsters = new ArrayList<Entity>();
        ArrayList<Entity> secondHeroMonsters = new ArrayList<Entity>();
        ArrayList<Entity> thirdHeroMonster = new ArrayList<Entity>();
        ArrayList<Entity> otherMonsters = new ArrayList<Entity>();
        // game loop
        while (true) {
            //Reseted parameters
            myHeroesIndex = 0;
            enemyHeroesIndex = 0;
            monstersIndex = 0;
            myMonsters = new ArrayList<Entity>();
            monsters = new ArrayList<Entity>();
            otherMonsters = new ArrayList<Entity>();
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
                    monsters.add(new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor, myRoot)); 
                    monstersIndex++;
                }else if(type == 1){
                    myHeroes[myHeroesIndex] = new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor, myRoot);
                    myHeroesIndex++;
                }else {
                    enemyHeroes[enemyHeroesIndex] = new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor, myRoot);
                    enemyHeroesIndex++;
                }
            }

            //Find the monsters which will eventually reach my base
            for(int i = 0; i < monsters.size(); i++){
                if(monsters.get(i).getThreatFor() == 1){
                    myMonsters.add(monsters.get(i));
                }else{
                    otherMonsters.add(monsters.get(i));
                };
            }
            for (Entity entity : myMonsters) {
                entity.TurnsToReachBase(myRoot);
            }
            Collections.sort(myMonsters);
            sortList(otherMonsters, myRoot);
            int myMonstersIndex = -1;
            if(!myMonsters.isEmpty()){
                myMonstersIndex = myMonsters.size();
                firstHeroMonsters = DistributedMonster(myMonsters, 5000f, 0f, myRoot);
                Collections.sort(firstHeroMonsters);
                secondHeroMonsters = DistributedMonster(myMonsters, 10000f, 5000f, myRoot);
                Collections.sort(secondHeroMonsters);
            }

            int otherMonsterIndex = 0;

            for (int i = 0; i < heroesPerPlayer; i++) {
                if(i==0){
                    if(firstHeroMonsters.size() > 0){
                        if(mine.getMana() > 30){
                            if(Distance(myHeroes[0], firstHeroMonsters.get(0).getCoordinate()) < 1280){
                                System.out.println("SPELL WIND " + enemyRoot);
                            }else{
                                System.out.println("MOVE " + firstHeroMonsters.get(0).getCoordinate());
                            }
                        }else{System.out.println("MOVE " + firstHeroMonsters.get(0).getCoordinate());}
                    }else{
                        StablePosition(myBase, i);
                    }
                }else{
                    if(mine.getMana() > 30){
                        if(secondHeroMonsters.size() >= i){
                            if(Distance(myHeroes[i], secondHeroMonsters.get(i - 1).getCoordinate()) < 1280){
                                System.out.println("SPELL CONTROL " + secondHeroMonsters.get(i - 1).getId() + " " + enemyRoot);
                            }else{
                                System.out.println("MOVE " + secondHeroMonsters.get(i - 1).getCoordinate());
                            }
                        }else if(otherMonsters.size() > 1){
                            System.out.println("MOVE " + otherMonsters.get(i - 1).getCoordinate());
                        }else{
                            StablePosition(myBase, i);
                        }
                    }else{
                        if(secondHeroMonsters.size() >= i){
                            System.out.println("MOVE " + secondHeroMonsters.get(i - 1).getCoordinate());
                        }else if(otherMonsters.size() > 1){
                            System.out.println("MOVE " + otherMonsters.get(i - 1).getCoordinate());
                        }else{
                            StablePosition(myBase, i);
                        }
                    }
                }

                /*
                if(myMonstersIndex > 0){
                    Entity myMonstersObj = myMonsters.get(myMonsters.size() - myMonstersIndex);
                    if(mine.getMana() > 20){
                        if((myMonstersObj.getDistance(myRoot) < 8000f) && (Distance(myHeroes[i], myMonstersObj.getCoordinate()) < 1280)){
                            System.out.println("SPELL WIND " + enemyRoot);
                        }else if((myMonstersObj.getDistance(myRoot) >= 8000f) && (Distance(myHeroes[i], myMonstersObj.getCoordinate()) < 1280) && (Distance(myHeroes[i], myMonstersObj.getCoordinate()) > 800)){
                            System.out.println("SPELL CONTROL " + myMonstersObj.getId() + " " + enemyRoot);
                        }else{
                            System.out.println("MOVE " + myMonstersObj.getCoordinate());
                        }
                    }else{
                        System.out.println("MOVE " + myMonstersObj.getCoordinate());
                    }
                    myMonstersIndex--;
                }else if(otherMonsters.size() > otherMonsterIndex){
                    System.out.println("MOVE " + otherMonsters.get(otherMonsterIndex).getCoordinate());
                    otherMonsterIndex++;
                }else{
                    StablePosition(myBase, i);
                }*/
         


                // In the first league: MOVE <x> <y> | WAIT; In later leagues: | SPELL <spellParams>;
                
            }
            //Debug
            /*
            if(!myMonsters.isEmpty()){
                System.err.println("Vector of monsters " + myMonsters.get(0).getId() + ": " + myMonsters.get(0).getSpeedVector());
            }*/
            if(!firstHeroMonsters.isEmpty()){
                System.err.println("7000radius: " + firstHeroMonsters.get(0).getId());
            }
            if(!secondHeroMonsters.isEmpty()){
                System.err.println(">7000radius: " + secondHeroMonsters.get(0).getId());
            }
            if(!otherMonsters.isEmpty()){
                System.err.println("Other monster: " + monsters.get(0).getId());
            }
            //System.err.println("My base is: " + myBase);
            //System.err.println("My heroes are: " + myHeroes[0].getId() + ":" + myHeroes[0].getCoordinate() + ", " + myHeroes[1].getId() + ":" + myHeroes[1].getCoordinate() + ", " + myHeroes[2].getId()+ ":" + myHeroes[2].getCoordinate());
            //System.err.println("Mine's stats: " + mine.getBaseHealth() + " " + mine.getMana());
            //System.err.println("Enemy's stats: " + enemy.getBaseHealth() + " " + enemy.getMana());
            System.err.println("Monsters: " + myMonsters.size());
            System.err.println("\t " + myMonsters);
        }
    }

    public static ArrayList<Entity> DistributedMonster(ArrayList<Entity> myMonsters, float maxDistance, float minDistance, Coordinate myRoot){
        ArrayList<Entity> result = new ArrayList<Entity>();
        for(Entity entity : myMonsters){
            if((entity.getDistance(myRoot) < maxDistance) && (entity.getDistance(myRoot) > minDistance)){
                result.add(entity);
            }
        };
        return result;
    }

    //Heroes move
    public void HeroesMove(ArrayList<Entity> myMonsters, Entity[] myHeroes, int i){
    
    }

    public static void StablePosition(int myBase, int i){
        if(myBase == 0){
            if(i == 0){
                System.out.println("MOVE 1500 1500");
            }else if(i == 1){
                System.out.println("MOVE 9000 3000");
            }else{
                System.out.println("MOVE 5000 6000");
            }
        }else{
            if(i == 0){
                System.out.println("MOVE 16130 7500");
            }else if(i == 1){
                System.out.println("MOVE 8630 6000");
            }else {
                System.out.println("MOVE 12630 3000");
            }
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
    public static int Distance(Entity unit, Coordinate root){         
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

class Entity implements Comparable<Entity>{
    private int id;
    private int type;
    private Coordinate coordinate;
    private int shieldLife;
    private int isControlled;
    private int health;
    private SpeedVector speedVector;
    private int nearBase;
    private int threatFor;
    private int turns;
    private float distance;
    public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public int getTurns() {
        return turns;
    }
    public void setTurns(int turns) {
        this.turns = turns;
    }
    public Entity() {
    }
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
    public Entity(int id, int type, Coordinate coordinate, int shieldLife, int isControlled, int health,
            SpeedVector speedVector, int nearBase, int threatFor, Coordinate myRoot) {
        this.id = id;
        this.type = type;
        this.coordinate = coordinate;
        this.shieldLife = shieldLife;
        this.isControlled = isControlled;
        this.health = health;
        this.speedVector = speedVector;
        this.nearBase = nearBase;
        this.threatFor = threatFor;
        getDistance(myRoot);
        TurnsToReachBase(myRoot);
    }
    public Float getDistance(Coordinate myRoot){
        float distance = (int) Math.sqrt(Math.pow(Math.abs(coordinate.getX() - myRoot.getX()), 2) + Math.pow(Math.abs(coordinate.getY() - myRoot.getY()), 2));
        return distance;
    }


    public Float getDistance(Coordinate monster, Coordinate myRoot){
        float distance = (int) Math.sqrt(Math.pow(Math.abs(monster.getX() - monster.getX()), 2) + Math.pow(Math.abs(monster.getY() - monster.getY()), 2));
        return distance;
    }

    public void TurnsToReachBase(Coordinate myRoot){
        Coordinate temp = new Coordinate(coordinate.getX(), coordinate.getY());
        float tempDistance = getDistance(temp, myRoot);
        while (tempDistance > 300f){
            temp = new Coordinate(temp.getX() + this.speedVector.getVx(), temp.getY() + this.speedVector.getVy());
            turns++;
        }
    }

    @Override
    public int compareTo(Entity obj){
        return this.turns - obj.turns;
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
    @Override
    public String toString(){
        return this.vx + " " + this.vy;
    }
}