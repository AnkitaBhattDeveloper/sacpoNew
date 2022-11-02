package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DCenterObj {
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
    public Item createItem(int pos1,int aId2,String aName3,String aDay4,String aPrevious5,String aIsPrevious6,String aIsUpload7,String aUploadType8,String aIsDownload9,String aDownload10) {
        return new Item(String.valueOf(pos1),aId2,aName3,aDay4,aPrevious5,aIsPrevious6,aIsUpload7,aUploadType8,aIsDownload9,aDownload10);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aName3;
        public final String aDay4;
        public final String aPrevious5;
        public final String aIsPrevious6;
        public final String aIsUpload7;
        public final String aUploadType8;
        public final String aIsDownload9;
        public final String aDownload10;

        public Item(String aPos1,int aId2,String aName3,String aDay4,String aPrevious5,String aIsPrevious6,String aIsUpload7,String aUploadType8,String aIsDownload9,String aDownload10) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aName3=aName3;
            this.aDay4=aDay4;
            this.aPrevious5=aPrevious5;
            this.aIsPrevious6=aIsPrevious6;
            this.aIsUpload7=aIsUpload7;
            this.aUploadType8=aUploadType8;
            this.aIsDownload9=aIsDownload9;
            this.aDownload10=aDownload10;
        }

        @Override
        public String toString() {
            return aName3;
        }
    }
}
