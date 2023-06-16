package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SChatListObj {
    public List<Item> ITEMS = new ArrayList<Item>();
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
    public Item createItem(int pos1, int aId2, String mcUserName3, String mcChatCount4,String mcDirect5,String mcGroup6) {
        return new Item(String.valueOf(pos1), aId2, mcUserName3, mcChatCount4, mcDirect5,mcGroup6);
    }
    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mcUserName3;
        public final String mcChatCount4;
        public final String mcDirect5;
        public final String mcGroup6;


        public Item(String aPos1,int aId2, String mcUserName3, String mcChatCount4, String mcDirect5,String mcGroup6) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.mcUserName3= mcUserName3;
            this.mcChatCount4= mcChatCount4;
            this.mcDirect5= mcDirect5;
            this.mcGroup6=mcGroup6;

        }

        @Override
        public String toString() {
            return mcUserName3;
        }
    }
}
