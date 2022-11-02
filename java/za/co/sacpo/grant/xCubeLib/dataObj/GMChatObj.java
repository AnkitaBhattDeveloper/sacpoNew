package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GMChatObj {
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
    public Item createItem(int pos1,int aId2,String aFName3,String aFImage4,String aFRole5, String aFRoleId6,String aFIsGroup7,String aFUnreaChatCount8,String aFLastChatTime9,String aFInstitution10) {
        return new Item(String.valueOf(pos1),aId2,aFName3,aFImage4,aFRole5,aFRoleId6,aFIsGroup7,aFUnreaChatCount8,aFLastChatTime9,aFInstitution10);
    }
    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aFName3;
        public final String aFImage4;
        public final String aFRole5;
        public final String aFRoleId6;
        public final String aFIsGroup7;
        public final String aFUnreaChatCount8;
        public final String aFLastChatTime9;
        public final String aFInstitution10;

        public Item(String aPos1,int aId2, String aFName3, String aFImage4,String aFRole5,String aFRoleId6,String aFIsGroup7,String aFUnreaChatCount8,String aFLastChatTime9,String aFInstitution10) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aFName3= aFName3;
            this.aFImage4= aFImage4;
            this.aFRole5= aFRole5;
            this.aFRoleId6= aFRoleId6;
            this.aFIsGroup7= aFIsGroup7;
            this.aFUnreaChatCount8= aFUnreaChatCount8;
            this.aFLastChatTime9=aFLastChatTime9;
            this.aFInstitution10=aFInstitution10;
        }

        @Override
        public String toString() {
            return aFName3;
        }
    }
}
