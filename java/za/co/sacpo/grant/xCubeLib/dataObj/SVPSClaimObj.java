package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVPSClaimObj {
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
    public Item createItem(int pos1,int aId2,String GMNotes3,String GMLearnerName4,String GMPNumber5,String GMLearnerNumber6,
                           String GMLearnerId7, String GMClaimDate8, String GMClaimAmount9, String GMDaysWorked10, String GMLeave11,
                           String GMSSId12,String GMRegNo13) {

        return new Item(String.valueOf(pos1),aId2,GMNotes3,GMLearnerName4,GMPNumber5,GMLearnerNumber6,GMLearnerId7,GMClaimDate8,
                GMClaimAmount9,GMDaysWorked10,GMLeave11,GMSSId12,GMRegNo13);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GMNotes3;
        public final String GMLearnerName4;
        public final String GMPNumber5;
        public final String GMLearnerNumber6;
        public final String GMLearnerId7;
        public final String GMClaimDate8;
        public final String GMClaimAmount9;
        public final String GMDaysWorked10;
        public final String GMLeave11;
        public final String GMSSId12;
        public final String GMRegNo13;



        public Item(String aPos1, int aId2, String GMNotes3, String GMLearnerName4, String GMPNumber5, String GMLearnerNumber6,
                    String GMLearnerId7, String GMClaimDate8, String GMClaimAmount9, String GMDaysWorked10, String GMLeave11, String GMSSId12, String GMRegNo13) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMNotes3 = GMNotes3;
            this.GMLearnerName4 = GMLearnerName4;
            this.GMPNumber5 = GMPNumber5;
            this.GMLearnerNumber6 = GMLearnerNumber6;
            this.GMLearnerId7 = GMLearnerId7;
            this.GMClaimDate8 = GMClaimDate8;
            this.GMClaimAmount9 = GMClaimAmount9;
            this.GMDaysWorked10 = GMDaysWorked10;
            this.GMLeave11 = GMLeave11;
            this.GMSSId12 = GMSSId12;
            this.GMRegNo13 = GMRegNo13;


        }

        @Override
        public String toString() {
            return GMNotes3;
        }
    }
}