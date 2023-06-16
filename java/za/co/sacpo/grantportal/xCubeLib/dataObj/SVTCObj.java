package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVTCObj {
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

    public Item createItem(int pos1, int aId2, String lblRef3, String getUserName4, String getlblDate5, String getImage6) {
        return new Item(String.valueOf(pos1), aId2, lblRef3, getUserName4, getlblDate5, getImage6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String lblRef3;
        public final String getUserName4;
        public final String getlblDate5;

        public final String getImage6;


        public String getGetImage6() {
            return getImage6;
        }

        public Item(String aPos1, int aId2, String lblRef3, String getUserName4, String getlblDate5, String getImage6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.lblRef3 = lblRef3;
            this.getUserName4 = getUserName4;
            this.getlblDate5 = getlblDate5;
            this.getImage6 = getImage6;
        }

        @Override
        public String toString() {
            return lblRef3;
        }


    }
}
