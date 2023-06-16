package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadCenterObj {
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
    public Item createItem(int pos1,int aId2,String aTitle3,String aPath4,String aType5,String aIsUploaded6,String aIsReUploaded7,String aIsRemoved8) {
        return new Item(String.valueOf(pos1),aId2,aTitle3,aPath4,aType5,aIsUploaded6,aIsReUploaded7,aIsRemoved8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aTitle3;
        public final String aPath4;
        public final String aType5;
        public final String aIsUploaded6;
        public final String aIsReUploaded7;
        public final String aIsRemoved8;

        public Item(String aPos1,int aId2,String aTitle3,String aPath4,String aType5,String aIsUploaded6,String aIsReUploaded7,String aIsRemoved8) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aTitle3=aTitle3;
            this.aPath4=aPath4;
            this.aType5=aType5;
            this.aIsUploaded6=aIsUploaded6;
            this.aIsReUploaded7=aIsReUploaded7;
            this.aIsRemoved8=aIsRemoved8;
        }

        @Override
        public String toString() {
            return aTitle3;
        }
    }
}
