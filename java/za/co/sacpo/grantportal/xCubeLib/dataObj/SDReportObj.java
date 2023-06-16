package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class SDReportObj {
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
    public Item createItem(int pos1, String aId2, String aMonth3, String aYear4, String aCount5) {
        return new Item(String.valueOf(pos1),aId2,aMonth3,aYear4,aCount5);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String aMonth3;
        public final String aYear4;
        public final String aCount5;
       
        public Item(String aPos1, String aId2, String aMonth3, String aYear4,String aCount5) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.aMonth3 = aMonth3;
            this.aYear4 = aYear4;
            this.aCount5 = aCount5;
            
        }

        @Override
        public String toString() {
            return aMonth3;
        }
    }
}