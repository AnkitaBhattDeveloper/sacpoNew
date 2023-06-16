package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrantManagerObj {
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
    public Item createItem(int pos1,int aId2,String GMSetaName3,String GMSetaID4,String GMSetaBranch5,String GMSetaBranchID6,String GMSetaMAnagerName7,
                           String GMSetaManagerID8,String GMGrantName9,String GMGrantID10 ,String GMLeadEmployyerManager11,String GMDuration12,
                           String GMStartDate13,String GMEndDate14,String GMTotalSeat15,String GMConfirmedStudent16,String GMUnconfirmedStudent17,
                           String GMVacant18,String GMSetaBudget19,String GMReceivedTranched20,String GMAdminFee21,String GMProjectedExpense22,
                           String GMClaimed23,String GMUnspent24,String GMNotes01,String GM_name26) {
        return new Item(String.valueOf(pos1),aId2,GMSetaName3,GMSetaID4,GMSetaBranch5,GMSetaBranchID6,GMSetaMAnagerName7,GMSetaManagerID8,GMGrantName9,
                GMGrantID10,GMLeadEmployyerManager11,GMDuration12,GMStartDate13,GMEndDate14,GMTotalSeat15,GMConfirmedStudent16,GMUnconfirmedStudent17,
                GMVacant18,GMSetaBudget19,GMReceivedTranched20,GMAdminFee21,GMProjectedExpense22,GMClaimed23,GMUnspent24,GMNotes01,GM_name26);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GMSetaName3;
        public final String GMSetaID4;
        public final String GMSetaBranch5;
        public final String GMSetaBranchID6;
        public final String GMSetaMAnagerName7;
        public final String GMSetaManagerID8;
        public final String GMGrantName9;
        public final String GMGrantID10;
        public final String GMLeadEmployyerManager11;
        public final String GMDuration12;
        public final String GMStartDate13;
        public final String GMEndDate14;
        public final String GMTotalSeat15;
        public final String GMConfirmedStudent16;
        public final String GMUnconfirmedStudent17;
        public final String GMVacant18;
        public final String GMSetaBudget19;
        public final String GMReceivedTranched20;
        public final String GMAdminFee21;
        public final String GMProjectedExpense22;
        public final String GMClaimed23;
        public final String GMUnspent24;
        public final String GMNotes01;
        public final String GM_name26;

        public Item(String aPos1, int aId2, String GMSetaName3, String GMSetaID4, String GMSetaBranch5, String GMSetaBranchID6, String GMSetaMAnagerName7, String GMSetaManagerID8, String GMGrantName9, String GMGrantID10, String GMLeadEmployyerManager11, String GMDuration12, String GMStartDate13, String GMEndDate14, String GMTotalSeat15, String GMConfirmedStudent16, String GMUnconfirmedStudent17, String GMVacant18, String GMSetaBudget19, String GMReceivedTranched20,
                    String GMAdminFee21, String GMProjectedExpense22, String GMClaimed23, String GMUnspent24, String GMNotes01, String GM_name26) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMSetaName3 = GMSetaName3;
            this.GMSetaID4 = GMSetaID4;
            this.GMSetaBranch5 = GMSetaBranch5;
            this.GMSetaBranchID6 = GMSetaBranchID6;
            this.GMSetaMAnagerName7 = GMSetaMAnagerName7;
            this.GMSetaManagerID8 = GMSetaManagerID8;
            this.GMGrantName9 = GMGrantName9;
            this.GMGrantID10 = GMGrantID10;
            this.GMLeadEmployyerManager11 = GMLeadEmployyerManager11;
            this.GMDuration12 = GMDuration12;
            this.GMStartDate13 = GMStartDate13;
            this.GMEndDate14 = GMEndDate14;
            this.GMTotalSeat15 = GMTotalSeat15;
            this.GMConfirmedStudent16 = GMConfirmedStudent16;
            this.GMUnconfirmedStudent17 = GMUnconfirmedStudent17;
            this.GMVacant18 = GMVacant18;
            this.GMSetaBudget19 = GMSetaBudget19;
            this.GMReceivedTranched20 = GMReceivedTranched20;
            this.GMAdminFee21 = GMAdminFee21;
            this.GMProjectedExpense22 = GMProjectedExpense22;
            this.GMClaimed23 = GMClaimed23;
            this.GMUnspent24 = GMUnspent24;
            this.GMNotes01 = GMNotes01;
            this.GM_name26 = GM_name26;
        }

        @Override
        public String toString() {
            return GMSetaName3;
        }
    }
}