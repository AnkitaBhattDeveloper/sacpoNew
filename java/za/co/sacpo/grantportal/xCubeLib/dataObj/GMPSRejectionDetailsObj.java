package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GMPSRejectionDetailsObj {
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
    public Item createItem(int pos1,int aId2,String GMPSLearnerName3,String GMPSContactNumber4,String GMPSEmail5,
                           String GMPSRejectReason6) {

        return new Item(String.valueOf(pos1),aId2,GMPSLearnerName3,GMPSContactNumber4,GMPSEmail5,GMPSRejectReason6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GMPSLearnerName3;
        public final String GMPSContactNumber4;
        public final String GMPSEmail5;
        public final String GMPSRejectReason6;



        public Item(String aPos1, int aId2, String GMPSLearnerName3, String GMPSContactNumber4, String GMPSEmail5,
                    String GMPSRejectReason6) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMPSLearnerName3 = GMPSLearnerName3;
            this.GMPSContactNumber4 = GMPSContactNumber4;
            this.GMPSEmail5 = GMPSEmail5;
            this.GMPSRejectReason6 = GMPSRejectReason6;



        }

        @Override
        public String toString() {
            return GMPSLearnerName3;
        }
    }
}