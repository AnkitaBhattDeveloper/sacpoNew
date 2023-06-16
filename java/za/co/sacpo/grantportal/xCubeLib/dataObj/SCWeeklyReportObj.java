package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCWeeklyReportObj {

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
    public Item createItem(int pos1,int aId2,String SMonth3,String SYear5,String SDepartment6,String STitile7,String DateInput8,String grant_id) {
        return new Item(String.valueOf(pos1),aId2,SMonth3,SYear5,SDepartment6,STitile7,DateInput8,grant_id);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String SMonth3;
        public final String SYear5;
        public final String SDepartment6;
        public final String STitile7;
        public final String DateInput8;
        public final String grant_id;


        public Item(String aPos1, int aId2, String SMonth3, String SYear5,String SDepartment6,String STitile7,String DateInput8,String grant_id) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SMonth3 = SMonth3;
            this.SYear5 = SYear5;
            this.SDepartment6 = SDepartment6;
            this.STitile7 = STitile7;
            this.DateInput8 = DateInput8;
            this.grant_id = grant_id;

        }

        @Override
        public String toString() {
            return STitile7;
        }
    }
}


