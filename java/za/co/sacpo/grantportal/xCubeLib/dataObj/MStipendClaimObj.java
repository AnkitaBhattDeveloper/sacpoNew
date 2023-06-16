package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MStipendClaimObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
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
    public Item createItem(int pos1,String aId2,String stiDate3,String stileave4,String stiAmount5){

        return new Item(String.valueOf(pos1),aId2,stiDate3,stileave4,stiAmount5);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String stiDate3;
        public final String stileave4;
        public final String stiAmount5;

            public Item(String aPos1, String aId2, String stiDate3, String stileave4, String stiAmount5){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.stiDate3= stiDate3;
            this.stileave4= stileave4;
            this.stiAmount5= stiAmount5;

            }

        @Override
        public String toString() {
            return stiDate3;
        }
    }
}
