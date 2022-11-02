package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SLeavesObj {
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
    public Item createItem(int pos1,int aId2, String aLeMonth3, String aLeFromDate4,String aLeToDate5, String aLeAnnual6, String aLeSick7,String aLeOPaid8,String aLeUnPaid9,String aLeSaStatus10, String aLeMotivation11,String aLeIsSupervisorComm12,String aLeIsDocument13,String aLeIsDocumentPath14,String aLeMotivationBtn15,String aLeEditBtn16,String aLeRemoveBtn17,String aLeSComment18) {
        return new Item(String.valueOf(pos1),aId2,aLeMonth3, aLeFromDate4,aLeToDate5, aLeAnnual6, aLeSick7,aLeOPaid8,aLeUnPaid9,aLeSaStatus10, aLeMotivation11,aLeIsSupervisorComm12,aLeIsDocument13,aLeIsDocumentPath14,aLeMotivationBtn15,aLeEditBtn16,aLeRemoveBtn17,aLeSComment18);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aLeMonth3;
        public final String aLeFromDate4;
        public final String aLeToDate5;
        public final String aLeAnnual6;
        public final String aLeSick7;

        public final String aLeOPaid8;
        public final String aLeUnPaid9;
        public final String aLeSaStatus10;
        public final String aLeMotivation11;
        public final String aLeIsSupervisorComm12;
        public final String aLeIsDocument13;
        public final String aLeIsDocumentPath14;
        public final String aLeMotivationBtn15;
        public final String aLeEditBtn16;
        public final String aLeRemoveBtn17;
        public final String aLeSComment18;
        public Item(String aPos1, int aId2, String aLeMonth3, String aLeFromDate4,String aLeToDate5, String aLeAnnual6, String aLeSick7,String aLeOPaid8,String aLeUnPaid9,String aLeSaStatus10, String aLeMotivation11,String aLeIsSupervisorComm12,String aLeIsDocument13,String aLeIsDocumentPath14,String aLeMotivationBtn15,String aLeEditBtn16,String aLeRemoveBtn17,String aLeSComment18) {
           this.aPos1 = aPos1;
           this.aId2 = aId2;
           this.aLeMonth3 = aLeMonth3;
           this.aLeFromDate4 = aLeFromDate4;
           this.aLeToDate5 = aLeToDate5;
           this.aLeAnnual6 = aLeAnnual6;
           this.aLeSick7 = aLeSick7;

            this.aLeOPaid8 = aLeOPaid8;
            this.aLeUnPaid9 = aLeUnPaid9;
            this.aLeSaStatus10 = aLeSaStatus10;
            this.aLeMotivation11 = aLeMotivation11;
            this.aLeIsSupervisorComm12 = aLeIsSupervisorComm12;
            this.aLeIsDocument13 = aLeIsDocument13;
            this.aLeIsDocumentPath14 = aLeIsDocumentPath14;

            this.aLeMotivationBtn15 = aLeMotivationBtn15;
            this.aLeEditBtn16 = aLeEditBtn16;
            this.aLeRemoveBtn17 = aLeRemoveBtn17;
            this.aLeSComment18 = aLeSComment18;
        }

        @Override
        public String toString() {
            return aLeMonth3;
        }
    }
}
