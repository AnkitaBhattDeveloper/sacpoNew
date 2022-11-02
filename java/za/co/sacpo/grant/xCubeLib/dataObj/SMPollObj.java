package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMPollObj {
    public List<SMPollObj.Item> ITEMS = new ArrayList<SMPollObj.Item>();
    public Map<Integer, SMPollObj.Item> ITEM_MAP = new HashMap<Integer, SMPollObj.Item>();
    public void addItem(SMPollObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMPollObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMPollObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMPollObj.Item createItem(int pos1, int aId2, String poll_3, String poll_4, String poll_5, String poll_6, String poll_7, String poll_8, String poll_9, String poll_10) {
        return new SMPollObj.Item(String.valueOf(pos1),aId2,poll_3,poll_4,poll_5,poll_6,poll_7,poll_8,poll_9,poll_10);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String poll_3;
        public final String poll_4;
        public final String poll_5;
        public final String poll_6;
        public final String poll_7;
        public final String poll_8;
        public final String poll_9;
        public final String poll_10;

           public Item(String aPos1,int aId2, String poll_3, String poll_4,String poll_5,String poll_6,String poll_7,String poll_8,String poll_9,String poll_10) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.poll_3= poll_3;
            this.poll_4= poll_4;
            this.poll_5= poll_5;
            this.poll_6= poll_6;
            this.poll_7= poll_7;
            this.poll_8= poll_8;
            this.poll_9= poll_9;
            this.poll_10= poll_10;

        }

        @Override
        public String toString() {
            return poll_3;
        }
    }
    
}
