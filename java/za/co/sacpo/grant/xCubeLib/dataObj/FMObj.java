package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMObj {
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
    public Item createItem(int pos1,int aId2,String FMLeadEmp3,String FMSetaGrantId4,String FMLearner5,String FMTotalAllocation6,String FMAdminFee7,
                           String FMTranchesReceived8,String FMStipendPaidout9,String FMCashAvailable10 ,String FMBudgetYear11,String FMProjectExpense12,String FMNote13,String FMNAME18) {

        return new Item(String.valueOf(pos1),aId2,FMLeadEmp3,FMSetaGrantId4,FMLearner5,FMTotalAllocation6,FMAdminFee7,FMTranchesReceived8,FMStipendPaidout9,
                FMCashAvailable10,FMBudgetYear11,FMProjectExpense12,FMNote13,FMNAME18);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String FMLeadEmp3;
        public final String FMSetaGrantId4;
        public final String FMLearner5;
        public final String FMTotalAllocation6;
        public final String FMAdminFee7;
        public final String FMTranchesReceived8;
        public final String FMStipendPaidout9;
        public final String FMCashAvailable10;
        public final String FMBudgetYear11;
        public final String FMProjectExpense12;
        public final String FMNote13;
        public final String FMNAME18;


        public Item(String aPos1, int aId2, String FMLeadEmp3, String FMSetaGrantId4, String FMLearner5, String FMTotalAllocation6, String FMAdminFee7, String FMTranchesReceived8, String FMStipendPaidout9, String FMCashAvailable10, String FMBudgetYear11
                , String FMProjectExpense12 , String FMNote13, String FMNAME18) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMLeadEmp3 = FMLeadEmp3;
            this.FMSetaGrantId4 = FMSetaGrantId4;
            this.FMLearner5 = FMLearner5;
            this.FMTotalAllocation6 = FMTotalAllocation6;
            this.FMAdminFee7 = FMAdminFee7;
            this.FMTranchesReceived8 = FMTranchesReceived8;
            this.FMStipendPaidout9 = FMStipendPaidout9;
            this.FMCashAvailable10 = FMCashAvailable10;
            this.FMBudgetYear11 = FMBudgetYear11;
            this.FMProjectExpense12 = FMProjectExpense12;
            this.FMNote13 = FMNote13;
            this.FMNAME18 = FMNAME18;

        }

        @Override
        public String toString() {
            return FMLeadEmp3;
        }


    }
}