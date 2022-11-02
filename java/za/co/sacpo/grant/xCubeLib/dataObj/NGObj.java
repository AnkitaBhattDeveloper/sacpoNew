package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NGObj {
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
    public Item createItem(int pos1,int aId2,String aNGTitle3,String aNGCount4) {
        return new Item(String.valueOf(pos1),aId2,aNGTitle3,aNGCount4);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aNGTitle3;
        public final String aNGCount4;
        public Item(String aPos1,int aId2, String angTitle3, String angCount4) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aNGTitle3= angTitle3;
            this.aNGCount4= angCount4;
        }

        @Override
        public String toString() {
            return aNGTitle3;
        }
    }
}
