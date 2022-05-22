import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * Version:3.4
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
        Coordinate spyHero = new Coordinate();
        if(baseX > 10){
            myBase = 1;
            myRoot = new Coordinate(17630, 9000);
            enemyRoot = new Coordinate(0, 0);
            spyHero = new Coordinate(8800, 4500);
        }else{
            myBase = 0;
            myRoot = new Coordinate(0, 0);
            enemyRoot = new Coordinate(17630, 9000);
            spyHero = new Coordinate(8800, 4500);
        }
        Resource mine = new Resource();
        Resource enemy = new Resource();
        Entity[] myHeroes = new Entity[heroesPerPlayer];
        int myHeroesIndex = 0;
        Entity[] enemyHeroes = new Entity[heroesPerPlayer];
        int enemyHeroesIndex = 0;
        ArrayList<Entity> enemyHeroesInsight = new ArrayList<Entity>();
        ArrayList<Entity> monsters = new ArrayList<Entity>();
        int monstersIndex = 0;
        ArrayList<Entity> myMonsters = new ArrayList<Entity>();
        ArrayList<Entity> spyHeroMonsters = new ArrayList<Entity>();
        ArrayList<Entity> defendHeroMonsters = new ArrayList<Entity>();
        ArrayList<Entity> thirdHeroMonster = new ArrayList<Entity>();
        ArrayList<Entity> otherMonsters = new ArrayList<Entity>();
        int turnsPass = 0;
        // game loop
        while (true) {
            //Reseted parameters
            turnsPass++;
            myHeroesIndex = 0;
            enemyHeroesIndex = 0;
            monstersIndex = 0;
            myMonsters = new ArrayList<Entity>();
            monsters = new ArrayList<Entity>();
            otherMonsters = new ArrayList<Entity>();
            enemyHeroesInsight = new ArrayList<Entity>();
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
                }else{
                    enemyHeroesInsight.add(new Entity(id, type, new Coordinate(x, y), shieldLife, isControlled, health, new SpeedVector(vx, vy), nearBase, threatFor, myRoot));
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
            boolean isPassedHalfGame = false;
            if(turnsPass > 90){
                isPassedHalfGame = true;
            }
            if(!monsters.isEmpty()){
                if(isPassedHalfGame){
                    spyHeroMonsters = DistributedMonster(monsters, 5000f, 0, myRoot);
                    sortList(spyHeroMonsters, myRoot);
                }else{
                    spyHeroMonsters = DistributedMonster(monsters, 3000f, 0, spyHero);
                    sortList(spyHeroMonsters, myRoot);    
                }                
            }
            if(!myMonsters.isEmpty()){
                defendHeroMonsters = DistributedMonster(myMonsters, 10000f, 0, myRoot);
                for (Entity entity : defendHeroMonsters) {
                    entity.TurnsToReachBase(myRoot);
                }
                Collections.sort(defendHeroMonsters);
            }

            int otherMonsterIndex = 0;
            boolean isSeeEnemyHero = false;
            if(enemyHeroesInsight.size() > 0){isSeeEnemyHero = true;}
            //System.err.println("enemyHeroes: " + enemyHeroes[0].getId() + " " + enemyHeroes[1].getId() + " " + enemyHeroes[2].getId());
            System.err.println("myHeroes: " + myHeroes[0].getId() + " " + myHeroes[1].getId() + " " + myHeroes[2].getId());
            for (int i = 0; i < heroesPerPlayer; i++) {
                if(i==0){
                    if(myHeroes[1].getIsControlled() == 1){
                        System.out.println("SPELL SHIELD " + myHeroes[1].getId());
                        continue;
                    }
                    if(!isPassedHalfGame){
                        if(isSeeEnemyHero){
                            boolean isTouchedEnemyHero = false;
                            int touchedEnemyHeroId = -1;
                            for(int j = 0; j < enemyHeroesInsight.size(); j++){
                                if(Distance(enemyHeroesInsight.get(j), myHeroes[i].getCoordinate()) < 1280){
                                    isTouchedEnemyHero = true;
                                    touchedEnemyHeroId = j;
                                    continue;
                                }
                            }                    
                            if((isTouchedEnemyHero) && (mine.getMana() > 50)){
                                if(Distance(myHeroes[0], enemyRoot) < Distance(enemyHeroesInsight.get(touchedEnemyHeroId), enemyRoot)){
                                    System.out.println("SPELL CONTROL " + enemyHeroesInsight.get(touchedEnemyHeroId).getId() + " " + myRoot);
                                    continue;
                                }
    
                            }
                        }
                    }
                    
                    /* for(Entity enemyHero : enemyHeroes){
                        if(Distance(enemyHero, myHeroes[i].getCoordinate()) < 1280){
                            isTouchedEnemyHero = true;
                            touchedEnemyHeroId = enemyHero.getId();
                            continue;
                        }
                    } */
                    if(spyHeroMonsters.size() > 0){
                        if(mine.getMana() > 100){
                            if(Distance(myHeroes[0], spyHeroMonsters.get(0).getCoordinate()) < 1280){
                                if((spyHeroMonsters.get(0).getThreatFor() == 1) || (spyHeroMonsters.get(0).getThreatFor() == 0)){
                                    System.out.println("SPELL CONTROL " + spyHeroMonsters.get(0).getId() + " " + enemyRoot);
                                }else if(spyHeroMonsters.get(0).getThreatFor() == 2){
                                    System.out.println("SPELL SHIELD " + spyHeroMonsters.get(0).getId());
                                }else{
                                    System.out.println("SPELL WIND " + enemyRoot);
                                }
                            }else{
                                System.out.println("MOVE " + spyHeroMonsters.get(0).getCoordinate());
                            }
                        }else{System.out.println("MOVE " + spyHeroMonsters.get(0).getCoordinate());}
                    }else{
                        if(isSeeEnemyHero && (enemyHeroesInsight.size() > 2)){
                            System.out.println("MOVE " + enemyHeroesInsight.get(1).getCoordinate());
                        }else{
                            StablePosition(myBase, i);
                        }
                    }
                }else{
                    if(mine.getMana() > 20){
                        if(myHeroes[0].getIsControlled() == 1){
                            System.out.println("SPELL SHIELD " + myHeroes[0].getId());
                        }
                        else if(defendHeroMonsters.size() >= i){
                            if(Distance(myHeroes[i], defendHeroMonsters.get(i - 1).getCoordinate()) < 1280){
                                System.out.println("SPELL WIND " + enemyRoot);
                            }else{
                                System.out.println("MOVE " + defendHeroMonsters.get(i - 1).getCoordinate());
                            }
                        }else if(otherMonsters.size() > 1){
                            System.out.println("MOVE " + otherMonsters.get(i - 1).getCoordinate());
                        }else{
                            StablePosition(myBase, i);
                        }
                    }else{
                        if(defendHeroMonsters.size() >= i){
                            System.out.println("MOVE " + defendHeroMonsters.get(i - 1).getCoordinate());
                        }else if(otherMonsters.size() >= i){
                            System.out.println("MOVE " + otherMonsters.get(i - 1).getCoordinate());
                        }else{
                            StablePosition(myBase, i);
                        }
                    }
                }
                // In the first league: MOVE <x> <y> | WAIT; In later leagues: | SPELL <spellParams>;
                
            }
            //Debug
            System.err.print("defend:");
            for (Entity defendHeroeMonster : defendHeroMonsters) {
                System.err.print(" " + defendHeroeMonster.getId());
            }
            System.err.println();
            System.err.println("myHeroes: " + myHeroes[0].getId() + " " + myHeroes[1].getId() + " " + myHeroes[2].getId());
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
                System.out.println("MOVE 8800 4500");
            }else if(i == 1){
                System.out.println("MOVE 6500 2500");
            }else{
                System.out.println("MOVE 3000 5000");
            }
        }else{
            if(i == 0){
                System.out.println("MOVE 8800 4500");
            }else if(i == 1){
                System.out.println("MOVE 10500 6500");
            }else {
                System.out.println("MOVE 14000 4000");
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