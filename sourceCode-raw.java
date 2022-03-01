import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Coordinate{
    private int x;
    private int y;
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public String toString(){return x + " " + y;}
}

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
    public int getSiteId(){return this.siteId;}
    public Coordinate getCoordinate(){return this.coordinate;}
    public int getRadius(){return this.radius;}
    public int getIgnore1(){return this.ignore1;}
    public int getIgnore2(){return this.ignore2;}
    public int getStructureType(){return this.structureType;}
    public int getOwner(){return this.owner;}
    public int getParam1(){return this.param1;}
    public int getParam2(){return this.param2;}
    public void setSiteId(int siteId){this.siteId = siteId;}
    public void setCoordinate(int x, int y){this.coordinate = new Coordinate(x, y);}
    public void setRadius(int radius){this.radius = radius;}
    public void setIgnore1(int ignore1){this.ignore1 = ignore1;}
    public void setIgnore2(int ignore2){this.ignore2 = ignore2;}
    public void setStructureType(int structureType){this.structureType = structureType;}
    public void setOwner(int owner){this.owner = owner;}
    public void setParam1(int param1){this.param1 = param1;}
    public void setParam2(int param2){this.param2 = param2;}
}

class Unit{
    private Coordinate coordinate;
    private int owner;
    private int unitType;
    private int health;
    public Unit(int x, int y, int owner, int unitType, int health){
        this.coordinate = new Coordinate(x, y);
        this.owner = owner;
        this.unitType = unitType;
        this.health = health;
    }
    public Coordinate getCoordinate(){return this.coordinate;}
    public int getOwner(){return this.owner;}
    public int getUnitType(){return this.unitType;}
    public int getHealth(){return this.health;}
    public void setCoordinate(int x, int y){this.coordinate = new Coordinate(x, y);}
    public void setOwner(int owner){this.owner = owner;}
    public void setUnitType(int unitType){this.unitType = unitType;}
    public void setHealth(int health){this.health = health;}
}

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int numSites = in.nextInt();
        Slot[] slots = new Slot[numSites];
        for (int i = 0; i < numSites; i++) {
            int siteId = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            int radius = in.nextInt();
            slots[i] = new Slot(siteId, x, y, radius, -1, -1, -1, -1, -1, -1);
        }
        int side = 0;                   //0 for not know, 1 for left, 2 for right\

        while (true) {
            ArrayList<Slot> myKnightBarracks = new ArrayList<Slot>();
            ArrayList<Slot> enemyStructures = new ArrayList<Slot>();
            ArrayList<Slot> blankSlots = new ArrayList<Slot>();
            ArrayList<Slot> mineStructures = new ArrayList<Slot>();
            boolean isTouchedMine = false;
            int gold = in.nextInt();
            int touchedSite = in.nextInt(); // -1 if none
            int touchedSiteOwner = -2;
            int levelOfMine = 0;
            for (int i = 0; i < numSites; i++) {
                int siteId = in.nextInt();
                int ignore1 = in.nextInt(); // gold
                int ignore2 = in.nextInt(); // maxMineSize
                int structureType = in.nextInt(); // -1 = No structure, 2 = Barracks
                int owner = in.nextInt(); // -1 = No structure, 0 = Friendly, 1 = Enemy
                int param1 = in.nextInt();
                int param2 = in.nextInt();
                for(int j = 0; j < slots.length; j++){
                    if(slots[j].getSiteId() == siteId){
                        slots[j].setIgnore1(ignore1);
                        slots[j].setIgnore2(ignore2);
                        slots[j].setStructureType(structureType);
                        slots[j].setOwner(owner);
                        slots[j].setParam1(param1);
                        slots[j].setParam2(param2);
                    }
                }
                if(siteId == touchedSite){
                    touchedSiteOwner = owner;
                    if(structureType == 0){
                        isTouchedMine = true;
                        levelOfMine = param1;
                    }
                }
            }

            for(int i = 0; i < slots.length;i++){
                if(slots[i].getOwner() == 0){
                    if(slots[i].getStructureType() == 2){
                        myKnightBarracks.add(slots[i]);
                    }
                    else if((slots[i].getStructureType() == 0)){
                            mineStructures.add(slots[i]);
                    }
                }
                else if(slots[i].getOwner() == 1){
                    enemyStructures.add(slots[i]);
                }
                else if(slots[i].getStructureType() == -1){
                    blankSlots.add(slots[i]);
                }
            }
            int numUnits = in.nextInt();
            Unit[] units = new Unit[numUnits];
            Coordinate queenCoordinate = new Coordinate(0, 0);
            for (int i = 0; i < numUnits; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int owner = in.nextInt();
                int unitType = in.nextInt(); // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER
                int health = in.nextInt();
                units[i] = new Unit(x, y, owner, unitType, health);
                if(owner == 0 && unitType == -1){
                    queenCoordinate = new Coordinate(x, y);
                    if((x > 1200) && side == 0){
                        side = 2;
                    }
                    else if((x < 1200) && side == 0){
                        side = 1;
                    }
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            Player playerObj = new Player();
            //Queen actions command
            Slot theNearestBlankSlot = playerObj.NearestBlankSlot(blankSlots, side);
            String queenAction = "WAIT";
            if((touchedSite != -1) && (touchedSiteOwner == -1)){
                if(mineStructures.size() < 3){
                    queenAction = "BUILD " + touchedSite + " MINE";
                }
                else if(myKnightBarracks.size() < 2){
                    queenAction = "BUILD " + touchedSite + " BARRACKS-KNIGHT";
                }
                else{
                    queenAction = "MOVE " + playerObj.HideSpot(side, queenCoordinate);
                }
            }
            else if((touchedSite != -1) && (touchedSiteOwner == 0) && isTouchedMine && (levelOfMine < 5)){
                if(myKnightBarracks.size() != 0){
                    queenAction = "BUILD " + touchedSite + " MINE";
                }
                else{
                    queenAction = playerObj.Move(slots, queenCoordinate, theNearestBlankSlot);
                }
            }else{
                queenAction = playerObj.Move(slots, queenCoordinate, theNearestBlankSlot);
            }

            //Train command
            String trainCommand = "TRAIN";
            if((gold > 80) && (myKnightBarracks.size() > 0)){
                trainCommand = playerObj.Train(slots, myKnightBarracks, gold);
            }
            // First line: A valid queen action
            System.out.println(queenAction);
            // Second line: A set of training instructions
            System.out.println(trainCommand);
            System.err.println("Side: " + side);
            System.err.println("Mine:" + mineStructures.size());
            System.err.println("Knight Barracks:" + myKnightBarracks.size());
            System.err.println(playerObj.Train(slots, myKnightBarracks, gold));
            System.err.println("blankSlot: " + blankSlots.size());
            System.err.println("First blankslot: " + blankSlots.get(0).getCoordinate());
            System.err.println("Coordinate of blankslot:" + theNearestBlankSlot.getCoordinate());
            System.err.println("TouchedSite and owner:" + touchedSite + " " + touchedSiteOwner);
            System.err.println("isMine: " + isTouchedMine);
            System.err.println("The queen actions:" + queenAction);
        }
    }

    public String HideSpot(int side, Coordinate queenCoordinate){
        int x = 0;
        int y = 0;
        if(side == 1){
            x = queenCoordinate.getX() - 40;
            y = queenCoordinate.getY() - 40;
        }else{
            x = queenCoordinate.getX() + 40;
            y = queenCoordinate.getY() + 40;
        }
        return x + " " + y;
    }
    public Slot NearestBlankSlot(ArrayList<Slot> blankSlots, int side){
        Slot nearestSlot = blankSlots.get(1);
        int minDistance = 3000;
        Coordinate root;
        if(side == 1){
            root = new Coordinate(0, 0);
        }
        else{
            root = new Coordinate(1920, 1000);
        }
        for(int i = 0; i < blankSlots.size(); i++){
            int X = blankSlots.get(i).getCoordinate().getX();
            int Y = blankSlots.get(i).getCoordinate().getY();
            int distance = (int) Math.sqrt(Math.pow(Math.abs(X-root.getX()), 2) + (Math.pow(Math.abs(Y - root.getY()), 2)));
            if(distance < minDistance){
                minDistance = distance;
                nearestSlot.getCoordinate().setX(X);
                nearestSlot.getCoordinate().setY(Y);
            }
        }
        return nearestSlot;
    }

    public String Train(Slot[] slots, ArrayList<Slot> myKnightBarracks, int gold){
        String train = "TRAIN";
        for(int i = 0; i < myKnightBarracks.size(); i++){
            train += " " + myKnightBarracks.get(i).getSiteId();
            gold -= 80;
            if(gold < 80){
                break;
            }
        }
        return train;
    }

    public String Move(Slot[] slots, Coordinate queenCoordinate, Slot theNearestBlankSlot){
        String moveCommand = "MOVE";
        int queenX = queenCoordinate.getX();
        int queenY = queenCoordinate.getY();
        int slotX = theNearestBlankSlot.getCoordinate().getX();
        int slotY = theNearestBlankSlot.getCoordinate().getY();
        int distance = (int) Math.sqrt(Math.pow(queenX - slotX, 2)
            + Math.pow(queenY - slotY, 2));
        if((distance - theNearestBlankSlot.getRadius() - 30) <= 60){
            moveCommand += " " + theNearestBlankSlot.getCoordinate();
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