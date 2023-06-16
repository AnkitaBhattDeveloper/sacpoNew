package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMObj {

    public List<SMObj.Item> ITEMS = new ArrayList<SMObj.Item>();
    public Map<Integer, SMObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SMObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<SMObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMObj.Item createItem(int pos1, int aId2, String chat_count3, String lead_emp4, String seta_id5,
                                 String smLearnerCount6, String seta_budge7, String admin_fee8,
                                 String smTranchesReceived9, String stipend_paid_out10, String cash_available11,String budge_year12,
                                 String project_expenses13, String note14,String CashAvailable15,String seta_manager_name16) {
        return new SMObj.Item(String.valueOf(pos1),aId2,chat_count3,lead_emp4,seta_id5,smLearnerCount6,seta_budge7,
                admin_fee8,smTranchesReceived9,stipend_paid_out10,cash_available11,budge_year12,project_expenses13,note14,CashAvailable15,seta_manager_name16);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String chat_count3;
        public final String lead_emp4;
        public final String seta_id5;
        public final String smLearnerCount6;
        public final String seta_budge7;
        public final String admin_fee8;
        public final String smTranchesReceived9;
        public final String stipend_paid_out10;
        public final String cash_available11;
        public final String budge_year12;
        public final String project_expenses13;
        public final String note14;
        public final String CashAvailable15;
        public final String seta_manager_name16;


        public Item(String aPos1, int aId2, String chat_count3, String lead_emp4, String seta_id5, String smLearnerCount6, String seta_budge7, String admin_fee8, String smTranchesReceived9, String stipend_paid_out10, String cash_available11,
                    String budge_year12,String project_expenses13,String note14,String CashAvailable15,String seta_manager_name16) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.chat_count3 = chat_count3;
            this.lead_emp4 = lead_emp4;
            this.seta_id5 = seta_id5;
            this.smLearnerCount6 = smLearnerCount6;
            this.seta_budge7 = seta_budge7;
            this.admin_fee8 = admin_fee8;
            this.smTranchesReceived9 = smTranchesReceived9;
            this.stipend_paid_out10 = stipend_paid_out10;
            this.cash_available11 = cash_available11;
            this.budge_year12 = budge_year12;
            this.project_expenses13 = project_expenses13;
            this.note14 = note14;
            this.CashAvailable15 = CashAvailable15;
            this.seta_manager_name16 = seta_manager_name16;
        }

        @Override
        public String toString() {
            return chat_count3;
        }
    }

}
