package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MFeedbackObj {
    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();

    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<Item> getITEMS() {
        return this.ITEMS;
    }

    public Map<Integer, Item> getITEM_MAP() {
        return this.ITEM_MAP;
    }

    public Item createItem(int pos1, int aId2, String feedback3, String name4, String date5) {
        return new Item(String.valueOf(pos1), aId2, feedback3, name4, date5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String feedback3;
        public final String name4;
        public final String date5;
        public Item(String aPos1, int aId2, String feedback3, String name4, String date5) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.feedback3 = feedback3;
            this.name4 = name4;
            this.date5 = date5;
        }
        @Override
        public String toString() {
            return feedback3;
        }
    }
}
