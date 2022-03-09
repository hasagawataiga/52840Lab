import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

//Coordinate class
class Coordinate{
    private int x;
    private int y;
    //Constructor
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
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

//Slot class stored slots in arena field
class Slot{
    private int siteId;
    private Coordinate coordinate;
    private int radius;
    private int ignore1;
    private int ignore2;
    private int structureType;
    private int owner;
    private int param1;
    private int param2;
    private int distance = -1;
    //Constructor
    public Slot(int siteId, int x, int y, int radius, int ignore1, int ignore2, int structureType, int owner, int param1, int param2){
        this.siteId = siteId;
        this.coordinate = new Coordinate(x, y);
        this.radius = radius;
        this.ignore1 = ignore1;
        this.ignore2 = ignore2;
        this.structureType = structureType;
        this.owner = owner;
        this.param1 = param1;
        this.param2 = param2;
    }
    //Constructor
    public Slot(){
        this.siteId = -1;
        this.coordinate = new Coordinate(-1, -1);
        this.radius = -1;
        this.ignore1 = -1;
        this.ignore2 = -1;
        this.structureType = -1;
        this.owner = -1;
        this.param1 = -1;
        this.param2 = -1;
    }
    //getter
    public int getSiteId(){return this.siteId;}
    public Coordinate getCoordinate(){return this.coordinate;}
    public int getRadius(){return this.radius;}
    public int getIgnore1(){return this.ignore1;}
    public int getIgnore2(){return this.ignore2;}
    public int getStructureType(){return this.structureType;}
    public int getOwner(){return this.owner;}
    public int getParam1(){return this.param1;}
    public int getParam2(){return this.param2;}
    public int getDistance(){return this.distance;}
    //setter
    public void setSiteId(int siteId){this.siteId = siteId;}
    public void setCoordinate(int x, int y){this.coordinate = new Coordinate(x, y);}
    public void setRadius(int radius){this.radius = radius;}
    public void setIgnore1(int ignore1){this.ignore1 = ignore1;}
    public void setIgnore2(int ignore2){this.ignore2 = ignore2;}
    public void setStructureType(int structureType){this.structureType = structureType;}
    public void setOwner(int owner){this.owner = owner;}
    public void setParam1(int param1){this.param1 = param1;}
    public void setParam2(int param2){this.param2 = param2;}
    public void setDistance(int distance){this.distance = distance;}
}

//Unit class stored units on the arena field
class Unit{
    private Coordinate coordinate;
    private int owner;
    private int unitType;
    private int health;
    //Constructor
    public Unit(int x, int y, int owner, int unitType, int health){
        this.coordinate = new Coordinate(x, y);
        this.owner = owner;
        this.unitType = unitType;
        this.health = health;
    }
    //GETTER
    public Coordinate getCoordinate(){return this.coordinate;}
    public int getOwner(){return this.owner;}
    public int getUnitType(){return this.unitType;}
    public int getHealth(){return this.health;}
    //SETTER
    public void setCoordinate(int x, int y){this.coordinate = new Coordinate(x, y);}
    public void setOwner(int owner){this.owner = owner;}
    public void setUnitType(int unitType){this.unitType = unitType;}
    public void setHealth(int health){this.health = health;}
}

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int numSites = in.nextInt();
        Slot[] slots = new Slot[numSites];      //stored all available slots
        Player player = new Player();
        Coordinate queenCoordinate = new Coordinate(0, 0);  //queen position
        for (int i = 0; i < numSites; i++) {
            int siteId = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            int radius = in.nextInt();
            slots[i] = new Slot(siteId, x, y, radius, -1, -1, -1, -1, -1, -1);
        }
        int side = 0;                   //0 for not know, 1 for left, 2 for right\
        boolean isFirstFrame = true;
        Coordinate root = new Coordinate(0, 0);         //use the check which the nearest corner is (top-left or bottom-right)
        Slot[] knightBarracks = new Slot[] {new Slot(), new Slot()};        //stored my knight barracks
        Slot[] minerals = new Slot[]{new Slot(), new Slot(), new Slot()};       //stored my minerals
        Slot[] towers = new Slot[]{new Slot(), new Slot(), new Slot()};     //stored my towers
        Slot nextTarget = new Slot();
        // game loop
        while (true) {
            int gold = in.nextInt();
            int touchedSite = in.nextInt(); // -1 if none
            boolean isTouchedTarget = false;
            for (int i = 0; i < numSites; i++) {
                int siteId = in.nextInt();
                int goldRemaining = in.nextInt(); // -1 if unknown
                int maxMineSize = in.nextInt(); // -1 if unknown
                int structureType = in.nextInt(); // -1 = No structure, 0 = Goldmine, 1 = Tower, 2 = Barracks
                int owner = in.nextInt(); // -1 = No structure, 0 = Friendly, 1 = Enemy
                int param1 = in.nextInt();
                int param2 = in.nextInt();
                for(int j = 0; j < slots.length; j++){      //updated the slots' attribute each frame
                    if(slots[j].getSiteId() == siteId){
                        slots[j].setIgnore1(goldRemaining);
                        slots[j].setIgnore2(maxMineSize);
                        slots[j].setStructureType(structureType);
                        slots[j].setOwner(owner);
                        slots[j].setParam1(param1);
                        slots[j].setParam2(param2);
                    }
                }                
            }
            int numUnits = in.nextInt();
            Unit[] units = new Unit[numUnits];      //stored the all the units on the arena field
            for (int i = 0; i < numUnits; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int owner = in.nextInt();
                int unitType = in.nextInt(); // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER, 2 = GIANT
                int health = in.nextInt();
                units[i] = new Unit(x, y, owner, unitType, health);
                if(owner == 0 && unitType == -1){       //find my queen position
                    queenCoordinate = new Coordinate(x, y);
                    if((x > 1200) && side == 0){
                        side = 2;
                    }
                    else if((x < 1200) && side == 0){
                        side = 1;
                    }
                }
            }
            if(isFirstFrame){
                if(side == 1){      //corner is top-left
                    root = new Coordinate(0, 0);
                }else{                                  //corner is bottom-right
                    root = new Coordinate(1920, 1000);
                }
                Slot[] slotsClone = new Slot[slots.length];         //create a clone of slots array to sort
                slotsClone = slots.clone();
                for(int i = 0; i < slotsClone.length; i++){
                    slotsClone[i].setDistance(player.Distance(slots[i], root));
                }
                Arrays.sort(slotsClone, new Comparator<Slot>(){
                    public int compare(Slot o1, Slot o2){
                        return o1.getDistance() - o2.getDistance();
                    }
                });
                knightBarracks[0] = slotsClone[0];          //take the nearest slots from clone of slots for each my structures
                knightBarracks[1] = slotsClone[1];
                minerals[0] = slotsClone[2];
                minerals[1] = slotsClone[3];
                minerals[2] = slotsClone[4];
                towers[0] = slotsClone[5];
                towers[1] = slotsClone[6];
                towers[2] = slotsClone[7];
                nextTarget = slotsClone[0];
                isFirstFrame = false;
            }else{
                for(int i = 0; i < slots.length; i++){          //updated my structures' attribute each frame
                    for(int k = 0; k < knightBarracks.length; k++){
                        if(slots[i].getSiteId() == knightBarracks[k].getSiteId()){
                            knightBarracks[k].setParam1(slots[i].getParam1());
                            break;
                        }
                    }
                    for(int k = 0; k < minerals.length; k++){
                        if(slots[i].getSiteId() == minerals[k].getSiteId()){
                            minerals[k].setParam1(slots[i].getParam1());
                            break;
                        }
                    }
                    for(int k = 0; k < towers.length; k++){
                        if(slots[i].getSiteId() == towers[k].getSiteId()){
                            towers[k].setParam1(slots[i].getParam1());
                            break;
                        }
                    }
                }
            }
            String queenCommand = "WAIT";
            String trainCommand = "TRAIN";
            //Build the structure if the queen touched blank site, otherwise continuously move to the target
            if(touchedSite == nextTarget.getSiteId()){
                isTouchedTarget = true;
                queenCommand = player.Build(knightBarracks, minerals, towers, touchedSite);
            }else{
                queenCommand = player.Move(/*slots, */queenCoordinate, nextTarget);
            }
            //Take the next target if queen touched the target
            if(isTouchedTarget){
                nextTarget = player.NextTarget(knightBarracks, minerals, towers);
            }

            //Train command
            if(gold > 80){
                trainCommand = player.Train(slots, knightBarracks, gold);
            }
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.err.println("Side: " + side);
            System.err.println("Slots: " + slots.length);
            System.err.println("Mine: " + minerals[0].getSiteId() + " " + minerals[1].getSiteId() + " " + minerals[2].getSiteId());
            System.err.println("Knight Barracks: " + knightBarracks[0].getSiteId() + " " + knightBarracks[1].getSiteId());
            System.err.println("Towers: " + towers[0].getSiteId() + " " + towers[1].getSiteId() + " " + towers[2].getSiteId());
            System.err.println("Next target: " + nextTarget.getSiteId());
            System.err.println(player.Train(slots, knightBarracks, gold));
            System.err.println("The queen actions: " + queenCommand);

            // First line: A valid queen action
            // Second line: A set of training instructions
            System.out.println(queenCommand);
            System.out.println(trainCommand);
        }
    }

    //Train method
    public String Train(Slot[] slots, Slot[] myKnightBarracks, int gold){
        String train = "TRAIN";
        for(int i = 0; i < myKnightBarracks.length; i++){
            train += " " + myKnightBarracks[i].getSiteId();
            gold -= 80;
            if(gold < 80){
                break;
            }
        }
        return train;
    }
    
    //Build method
    public String Build(Slot[] knightBarracks, Slot[] minerals, Slot[] towers, int touchedSite){
        for(int i = 0; i < knightBarracks.length; i++){
            if(touchedSite == knightBarracks[i].getSiteId()){
                return "BUILD " + touchedSite + " BARRACKS-KNIGHT";
            }
        }
        for(int i = 0; i < minerals.length; i++){
            if(touchedSite == minerals[i].getSiteId()){
                return "BUILD " + touchedSite + " MINE";
            }
        }
        for(int i = 0; i < towers.length; i++){
            if(touchedSite == towers[i].getSiteId()){
                return "BUILD " + touchedSite + " TOWER";
            }
        }
        return "WAIT";
    }

    //Take next target
    public Slot NextTarget(Slot[] knightBarracks, Slot[] minerals, Slot[] towers){
        for(int i = 0; i < knightBarracks.length; i++){
            if(knightBarracks[i].getParam1() == -1){
                return knightBarracks[i];
            }
        }
        for(int i = 0; i < minerals.length; i++){
            if(minerals[i].getParam1() == -1){
                return minerals[i];
            }
        }
        for(int i = 0; i < towers.length; i++){
            if(towers[i].getParam1() == -1){
                return towers[i];
            }
        }
        for(int i = 0; i < minerals.length; i++){
            if(minerals[i].getParam1() < 5){
                return minerals[i];
            }
        }
        return towers[0];
    }

    //Calculate distance
    public int Distance(Slot slot, Coordinate root){
        int distance = 0;
        int slotX = slot.getCoordinate().getX();
        int slotY = slot.getCoordinate().getY();
        distance = (int) Math.sqrt(Math.pow(Math.abs(slotX - root.getX()), 2) + Math.pow(Math.abs(slotY - root.getY()), 2));
        return distance;
    }

    //Move
    public String Move(/*Slot[] slots,*/ Coordinate queenCoordinate, Slot targetSlot){
        String moveCommand = "MOVE";
        int queenX = queenCoordinate.getX();
        int queenY = queenCoordinate.getY();
        int slotX = targetSlot.getCoordinate().getX();
        int slotY = targetSlot.getCoordinate().getY();
        int distance = (int) Math.sqrt(Math.pow(queenX - slotX, 2)
            + Math.pow(queenY - slotY, 2));
        if((distance - targetSlot.getRadius() - 30) <= 60){
            moveCommand += " " + targetSlot.getCoordinate();
        }
        else{
            int x = 0;
            int y = 0;
            if(queenX > slotX){
                x = (int)(queenX - (queenX - slotX)*60/distance);
            }
            else{
                x = (int)(queenX + (slotX - queenX)*60/distance);
            }
            if(queenY > slotY){
                y = (int)(queenY - (queenY - slotY)*60/distance);
            }
            else{
                y = (int)(queenY + (slotY - queenY)*60/distance);
            }
            moveCommand += " " + x + " " + y;
        }
        return moveCommand;
    }
}