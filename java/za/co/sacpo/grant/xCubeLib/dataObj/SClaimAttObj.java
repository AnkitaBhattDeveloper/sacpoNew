package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SClaimAttObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int aId2,String aDate3,String aDay4,String aSignIn5,String aSignOut6,String aHoursWorked7,String aLeave8,String aDistanceFromWorkstation9,String aSLeave10,String aOPLeave11, String aUPLeave12) {
        return new Item(String.valueOf(pos1),aId2,aDate3,aDay4,aSignIn5,aSignOut6,aHoursWorked7,aLeave8,aDistanceFromWorkstation9,aSLeave10,aOPLeave11, aUPLeave12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aDate3;
        public final String aDay4;
        public final String aSignIn5;
        public final String aSignOut6;
        public final String aHoursWorked7;
        public final String aLeave8;
        public final String aDistanceFromWorkstation9;
        public final String aSLeave10;
        public final String aOPLeave11;
        public final String aUPLeave12;

        public Item(String aPos1,int aId2,String aDate3,String aDay4,String aSignIn5,String aSignOut6,String aHoursWorked7,String aLeave8,String aDistanceFromWorkstation9,String aSLeave10,String aOPLeave11, String aUPLeave12) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aDate3=aDate3;
            this.aDay4=aDay4;
            this.aSignIn5=aSignIn5;
            this.aSignOut6=aSignOut6;
            this.aHoursWorked7=aHoursWorked7;
            this.aLeave8=aLeave8;
            this.aDistanceFromWorkstation9=aDistanceFromWorkstation9;
            this.aSLeave10=aSLeave10;
            this.aOPLeave11=aOPLeave11;
            this.aUPLeave12=aUPLeave12;

        }

        @Override
        public String toString() {
            return aDate3;
        }
    }
}
