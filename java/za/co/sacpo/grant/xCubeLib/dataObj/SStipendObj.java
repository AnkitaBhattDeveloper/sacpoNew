package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SStipendObj {
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
    public Item createItem(int pos1,int aId2,String sStDate3,String sStMonth4,String sStYear5, String sStTotalStipend6,String sStbtnStipendDetails7) {
        return new Item(String.valueOf(pos1),aId2,sStDate3,sStMonth4,sStYear5,sStTotalStipend6,sStbtnStipendDetails7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String sStDate3;
        public final String sStMonth4;
        public final String sStYear5;
        public final String sStTotalStipend6;
        public final String sStbtnStipendDetails7;

        public Item(String aPos1, int aId2, String sStDate3, String sStMonth4, String sStYear5, String sStTotalStipend6, String sStbtnStipendDetails7) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.sStDate3 = sStDate3;
            this.sStMonth4 = sStMonth4;
            this.sStYear5 = sStYear5;
            this.sStTotalStipend6 = sStTotalStipend6;
            this.sStbtnStipendDetails7 = sStbtnStipendDetails7;
        }

        @Override
        public String toString() {
            return sStDate3;
        }
    }
}
