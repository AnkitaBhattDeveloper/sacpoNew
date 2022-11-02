package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFeedbackObj {

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
    public Item createItem(int pos1,int aId2,String ReportNo3,String ReportTitle4, String Month5,String Year6,String SupervisorStatus7,String SupervisorStatusId8,String EditBtn9) {
        return new Item(String.valueOf(pos1),aId2,ReportNo3,ReportTitle4,Month5,Year6,SupervisorStatus7,SupervisorStatusId8,EditBtn9);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String ReportNo3;
        public final String ReportTitle4;
        public final String Month5;
        public final String Year6;
        public final String SupervisorStatus7;
        public final String SupervisorStatusId8;
        public final String EditBtn9;
        public Item(String aPos1, int aId2, String ReportNo3, String ReportTitle4,String Month5,String Year6,String SupervisorStatus7,String SupervisorStatusId8,String EditBtn9) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.ReportNo3 = ReportNo3;
            this.ReportTitle4 = ReportTitle4;
            this.Month5 = Month5;
            this.Year6 = Year6;
            this.SupervisorStatus7 = SupervisorStatus7;
            this.SupervisorStatusId8 = SupervisorStatusId8;
            this.EditBtn9 = EditBtn9;
        }

        @Override
        public String toString() {
            return Month5;
        }
    }
}


