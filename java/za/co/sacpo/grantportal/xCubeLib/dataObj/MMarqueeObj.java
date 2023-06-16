package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMarqueeObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> ITEMS() {
        return this.ITEMS;
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int aId2,String marq3) {
        return new Item(String.valueOf(pos1),aId2,marq3);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String marq3;
  


        public Item(String aPos1,int aId2, String marq3) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.marq3= marq3;
       
        }

        @Override
        public String toString() {
            return marq3;
        }
    }
}
