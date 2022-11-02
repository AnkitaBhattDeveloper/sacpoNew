package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class MTCObj {
        public List<Item> ITEMS = new ArrayList<Item>();
        public Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
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
        public Item createItem(int pos1,int aId2,String lblRef3,String getUserImg4, String getUserName5,String getlblDate6,String getTextComment7) {
            return new Item(String.valueOf(pos1),aId2,lblRef3,getUserImg4,getUserName5,getlblDate6,getTextComment7);
        }

        public static class Item {
            public final String aPos1;
            public final int aId2;
            public final String lblRef3;
            public final String getUserImg4;
            public final String getUserName5;
            public final String getlblDate6;
            public final String getTextComment7;

            public Item(String aPos1, int aId2, String lblRef3, String getUserImg4, String getUserName5, String getlblDate6, String getTextComment7) {
                this.aPos1 = aPos1;
                this.aId2 = aId2;
                this.lblRef3 = lblRef3;
                this.getUserImg4 = getUserImg4;
                this.getUserName5 = getUserName5;
                this.getlblDate6 = getlblDate6;
                this.getTextComment7 = getTextComment7;
            }

            @Override
            public String toString() {
                return lblRef3;
            }
        }
    }


