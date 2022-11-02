package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MHolidayObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.mHid2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int mHid2,String mHName3,String mHDate4) {
        return new Item(String.valueOf(pos1),mHid2,mHName3,mHDate4);
    }

    public static class Item {
        public final String aPos1;
        public final int mHid2;
        public final String mHName3;
        public final String mHDate4;

        public Item(String aPos1, int mHid2, String mHName3, String mHDate4) {
            this.aPos1 = aPos1;
            this.mHid2 = mHid2;
            this.mHName3 = mHName3;
            this.mHDate4 = mHDate4;
        }

        @Override
        public String toString() {
            return mHName3;
        }
    }
}
