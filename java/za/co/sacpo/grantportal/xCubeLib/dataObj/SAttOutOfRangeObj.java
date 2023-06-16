package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAttOutOfRangeObj {
    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(String pos1, String aId2, String aDate3, String aDay4, String aDisCord5) {
        return new Item(pos1,aId2,aDate3,aDay4,aDisCord5);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String aDate3;
        public final String aDay4;
        public final String aDisCord5;


        public Item(String aPos1,String aId2, String aDate3, String aDay4,String aDisCord5) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aDate3= aDate3;
            this.aDay4= aDay4;
            this.aDisCord5= aDisCord5;

        }

        @Override
        public String toString() {
            return aDate3;
        }
    }
}



