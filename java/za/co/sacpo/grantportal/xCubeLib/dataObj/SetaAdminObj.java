package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetaAdminObj {
    public List<SetaAdminObj.Item> ITEMS = new ArrayList<SetaAdminObj.Item>();
    public Map<Integer, SetaAdminObj.Item> ITEM_MAP = new HashMap<Integer, SetaAdminObj.Item>();
    public void addItem(SetaAdminObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SetaAdminObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SetaAdminObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SetaAdminObj.Item createItem(int pos1, int aId2, String GASetaName3, String GASetaID4, String GASetaBranch5, String GASetaBranchID6, String GASetaMAnagerName7,
                                         String GASetaManagerID8, String GAGrantName9, String GAGrantID10 , String GALeadEmployyerManager11, String GADuration12,
                                         String GAStartDate13, String GAEndDate14, String GATotalSeat15, String GAConfirmedStudent16, String GAUnconfirmedStudent17,
                                         String GAVacant18, String GASetaBudget19, String GAReceivedTranched20, String GAAdminFee21, String GAProjectedExpense22,
                                         String GAClaimed23, String GAUnspent24) {
        return new SetaAdminObj.Item(String.valueOf(pos1),aId2,GASetaName3,GASetaID4,GASetaBranch5,GASetaBranchID6,GASetaMAnagerName7,GASetaManagerID8,GAGrantName9,
                GAGrantID10,GALeadEmployyerManager11,GADuration12,GAStartDate13,GAEndDate14,GATotalSeat15,GAConfirmedStudent16,GAUnconfirmedStudent17,
                GAVacant18,GASetaBudget19,GAReceivedTranched20,GAAdminFee21,GAProjectedExpense22,GAClaimed23,GAUnspent24);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GASetaName3;
        public final String GASetaID4;
        public final String GASetaBranch5;
        public final String GASetaBranchID6;
        public final String GASetaMAnagerName7;
        public final String GASetaManagerID8;
        public final String GAGrantName9;
        public final String GAGrantID10;
        public final String GALeadEmployyerManager11;
        public final String GADuration12;
        public final String GAStartDate13;
        public final String GAEndDate14;
        public final String GATotalSeat15;
        public final String GAConfirmedStudent16;
        public final String GAUnconfirmedStudent17;
        public final String GAVacant18;
        public final String GASetaBudget19;
        public final String GAReceivedTranched20;
        public final String GAAdminFee21;
        public final String GAProjectedExpense22;
        public final String GAClaimed23;
        public final String GAUnspent24;

        public Item(String aPos1, int aId2, String GASetaName3, String GASetaID4, String GASetaBranch5, String GASetaBranchID6, String GASetaMAnagerName7, String GASetaManagerID8, String GAGrantName9, String GAGrantID10, String GALeadEmployyerManager11, String GADuration12, String GAStartDate13, String GAEndDate14, String GATotalSeat15, String GAConfirmedStudent16, String GAUnconfirmedStudent17, String GAVacant18, String GASetaBudget19, String GAReceivedTranched20, String GAAdminFee21, String GAProjectedExpense22, String GAClaimed23, String GAUnspent24) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GASetaName3 = GASetaName3;
            this.GASetaID4 = GASetaID4;
            this.GASetaBranch5 = GASetaBranch5;
            this.GASetaBranchID6 = GASetaBranchID6;
            this.GASetaMAnagerName7 = GASetaMAnagerName7;
            this.GASetaManagerID8 = GASetaManagerID8;
            this.GAGrantName9 = GAGrantName9;
            this.GAGrantID10 = GAGrantID10;
            this.GALeadEmployyerManager11 = GALeadEmployyerManager11;
            this.GADuration12 = GADuration12;
            this.GAStartDate13 = GAStartDate13;
            this.GAEndDate14 = GAEndDate14;
            this.GATotalSeat15 = GATotalSeat15;
            this.GAConfirmedStudent16 = GAConfirmedStudent16;
            this.GAUnconfirmedStudent17 = GAUnconfirmedStudent17;
            this.GAVacant18 = GAVacant18;
            this.GASetaBudget19 = GASetaBudget19;
            this.GAReceivedTranched20 = GAReceivedTranched20;
            this.GAAdminFee21 = GAAdminFee21;
            this.GAProjectedExpense22 = GAProjectedExpense22;
            this.GAClaimed23 = GAClaimed23;
            this.GAUnspent24 = GAUnspent24;
        }

        @Override
        public String toString() {
            return GASetaName3;
        }
    }
}
