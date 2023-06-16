package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NObj {
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
    public Item createItem(int pos1,int aId2,String aNTitle3,String aNData4,String aNDate5,String aNReadStatus6,String group_id7) {
        return new Item(String.valueOf(pos1),aId2,aNTitle3,aNData4,aNDate5,aNReadStatus6,group_id7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aNTitle3;
        public final String aNData4;
        public final String aNDate5;
        public final String aNReadStatus6;
        public final String group_id7;

        public Item(String aPos1,int aId2, String aNTitle3, String aNData4,String aNDate5,String aNReadStatus6,String group_id7) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aNTitle3= aNTitle3;
            this.aNData4= aNData4;
            this.aNDate5= aNDate5;
            this.aNReadStatus6= aNReadStatus6;
            this.group_id7 = group_id7;
        }

        @Override
        public String toString() {
            return aNTitle3;
        }
    }
}
