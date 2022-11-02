package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMonthlyFeedbackObj {

    public  List<Item> ITEMS = new ArrayList<Item>();
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
    public Item createItem(int pos1,int aId2,String SFQues3,String SFQ_Id4,String SANS_TYPE5,String SANS_ID6,String SANS_TYPE_17,String SANS_TYPE_ID18,String SANS_TYPE_29,String SANS_TYPE_ID210,String SANS_TYPE_311,String SANS_TYPE_ID312,String SANS_TYPE_413,String SANS_TYPE_ID414,String SANS_TYPE_515,String SANS_TYPE_ID516) {
        return new Item(String.valueOf(pos1),aId2,SFQues3,SFQ_Id4,SANS_TYPE5,SANS_ID6,SANS_TYPE_17,SANS_TYPE_ID18,SANS_TYPE_29,SANS_TYPE_ID210,SANS_TYPE_311,SANS_TYPE_ID312,SANS_TYPE_413,SANS_TYPE_ID414,SANS_TYPE_515,SANS_TYPE_ID516);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String SFQues3;
        public final String SFQ_Id4;
        public final String SANS_TYPE5;
        public final String SANS_ID6;
        public final String SANS_TYPE_17;
        public final String SANS_TYPE_ID18;
        public final String SANS_TYPE_29;
        public final String SANS_TYPE_ID210;
        public final String SANS_TYPE_311;
        public final String SANS_TYPE_ID312;

        public final String SANS_TYPE_413;
        public final String SANS_TYPE_ID414;
        public final String SANS_TYPE_515;
        public final String SANS_TYPE_ID516;


        public Item(String aPos1, int aId2, String SFQues3, String SFQ_Id4,String SANS_TYPE5,String SANS_ID6,String SANS_TYPE_17,String SANS_TYPE_ID18,String SANS_TYPE_29,String SANS_TYPE_ID210,String SANS_TYPE_311,String SANS_TYPE_ID312,String SANS_TYPE_413,String SANS_TYPE_ID414,String SANS_TYPE_515,String SANS_TYPE_ID516) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SFQues3 = SFQues3;
            this.SFQ_Id4 = SFQ_Id4;
            this.SANS_TYPE5 = SANS_TYPE5;
            this.SANS_ID6 = SANS_ID6;
            this.SANS_TYPE_17 = SANS_TYPE_17;
            this.SANS_TYPE_ID18 = SANS_TYPE_ID18;
            this.SANS_TYPE_29 = SANS_TYPE_29;
            this.SANS_TYPE_ID210 = SANS_TYPE_ID210;
            this.SANS_TYPE_311 = SANS_TYPE_311;
            this.SANS_TYPE_ID312 = SANS_TYPE_ID312;
            this.SANS_TYPE_413 = SANS_TYPE_413;
            this.SANS_TYPE_ID414 = SANS_TYPE_ID414;
            this.SANS_TYPE_515 = SANS_TYPE_515;
            this.SANS_TYPE_ID516 = SANS_TYPE_ID516;


        }

        @Override
        public String toString() {
            return SFQ_Id4;
        }
    }
}


