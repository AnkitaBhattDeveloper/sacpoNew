package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TObj {
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
    public Item createItem(int pos1,int aId2,String aTTitle3,String aTRef4,String aTDate5,String aTStatusText6,String aTStatus7,String group_id8) {
        return new Item(String.valueOf(pos1),aId2,aTTitle3,aTRef4,aTDate5,aTStatusText6,aTStatus7,group_id8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aTTitle3;
        public final String aTRef4;
        public final String aTDate5;
        public final String aTStatus7;
        public final String group_id8;
        public final String aTStatusText6;

        public Item(String aPos1,int aId2, String aTTitle3, String aTRef4,String aTDate5,String aTStatusText6,String aTStatus7,String group_id8) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aTTitle3= aTTitle3;
            this.aTRef4= aTRef4;
            this.aTDate5= aTDate5;
            this.aTStatusText6= aTStatusText6;
            this.aTStatus7= aTStatus7;
            this.group_id8 = group_id8;
        }

        @Override
        public String toString() {
            return aTTitle3;
        }
    }
}
