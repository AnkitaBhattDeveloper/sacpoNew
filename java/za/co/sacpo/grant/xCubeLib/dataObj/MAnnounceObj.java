package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MAnnounceObj {
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
    public Item createItem(int pos1,int aId2,String aNTitle3,String aSData4,String aEData5,String aNReadStatus6,String group_id7) {
        return new Item(String.valueOf(pos1),aId2,aNTitle3,aSData4,aEData5,aNReadStatus6,group_id7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aNTitle3;
        public final String aSData4;
        public final String aEData5;
        public final String aNReadStatus6;
        public final String group_id7;

        public Item(String aPos1,int aId2, String aNTitle3, String aSData4,String aEData5,String aNReadStatus6,String group_id7) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aNTitle3= aNTitle3;
            this.aSData4= aSData4;
            this.aEData5= aEData5;
            this.aNReadStatus6= aNReadStatus6;
            this.group_id7 = group_id7;
        }

        @Override
        public String toString() {
            return aNTitle3;
        }
    }
}
