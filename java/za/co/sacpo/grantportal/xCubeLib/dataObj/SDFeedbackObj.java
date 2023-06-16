package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDFeedbackObj {
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
    public Item createItem(int pos1,int aId2,String aFeedbackpending3,String aFeedbackcomplete4,String aDepartment5) {
        return new Item(String.valueOf(pos1),aId2,aFeedbackpending3,aFeedbackcomplete4,aDepartment5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aFeedbackpending3;
        public final String aFeedbackcomplete4;
        public final String aDepartment5;

        public Item(String aPos1, int aId2, String aFeedbackpending3, String aFeedbackcomplete4,String aDepartment5) {
           this.aPos1 = aPos1;
           this.aId2 = aId2;
           this.aFeedbackpending3 = aFeedbackpending3;
           this.aFeedbackcomplete4 = aFeedbackcomplete4;
           this.aDepartment5 = aDepartment5;
        }

        @Override
        public String toString() {
            return aFeedbackpending3;
        }
    }
}