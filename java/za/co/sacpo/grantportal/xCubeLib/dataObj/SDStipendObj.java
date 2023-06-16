package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDStipendObj {
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
    public Item createItem(int pos1,int aId2,String aMonth3,String aYear4,String aAmount5,String aMApproved6,String aAApproved7) {
        return new Item(String.valueOf(pos1),aId2,aMonth3,aYear4,aAmount5,aMApproved6,aAApproved7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aMonth3;
        public final String aYear4;
        public final String aAmount5;
        public final String aMApproved6;
        public final String aAApproved7;

        public Item(String aPos1, int aId2, String aMonth3, String aYear4,String aAmount5,String aMApproved6,String aAApproved7) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.aMonth3 = aMonth3;
            this.aYear4 = aYear4;
            this.aAmount5 = aAmount5;
            this.aMApproved6 = aMApproved6;
            this.aAApproved7 = aAApproved7;
        }

        @Override
        public String toString() {
            return aMonth3;
        }
    }
}