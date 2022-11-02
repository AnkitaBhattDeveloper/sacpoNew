package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MWeeklyReportObj {

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
    public Item createItem(int pos1,int aId2,String MTitle3,String MWeekEnding4,String MDepartmentName5,String MstudentId6) {
        return new Item(String.valueOf(pos1),aId2,MTitle3,MWeekEnding4,MDepartmentName5,MstudentId6);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String MTitle3;
        public final String MWeekEnding4;
        public final String MDepartmentName5;
        public final String MstudentId6;



        public Item(String aPos1, int aId2, String MTitle3, String MWeekEnding4,String MDepartmentName5,String MstudentId6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.MTitle3 = MTitle3;
            this.MWeekEnding4 = MWeekEnding4;
            this.MDepartmentName5 = MDepartmentName5;
            this.MstudentId6 = MstudentId6;


        }

        @Override
        public String toString() {
            return MTitle3;
        }
    }
}


