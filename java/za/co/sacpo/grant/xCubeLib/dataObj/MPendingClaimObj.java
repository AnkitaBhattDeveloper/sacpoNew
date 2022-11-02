package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPendingClaimObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,String aId2,String month_year3,String PcYear4,String PcMonth5, String PcAmount6,
                           String PcLearnerName7,String PcApproval8){

        return new Item(String.valueOf(pos1),aId2,month_year3,PcYear4,PcMonth5,PcAmount6,
                PcLearnerName7,PcApproval8);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String month_year3;
        public final String PcYear4;
        public final String PcMonth5;
        public final String PcAmount6;
        public final String PcLearnerName7;
        public final String PcApproval8;

        public Item(String aPos1, String aId2, String month_year3, String PcYear4, String PcMonth5,
                    String PcAmount6, String PcLearnerName7, String PcApproval8){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.month_year3= month_year3;
            this.PcYear4= PcYear4;
            this.PcMonth5= PcMonth5;
            this.PcAmount6= PcAmount6;
            this.PcLearnerName7= PcLearnerName7;
            this.PcApproval8= PcApproval8;
        }
        @Override
        public String toString() {
            return month_year3;
        }
    }
}
