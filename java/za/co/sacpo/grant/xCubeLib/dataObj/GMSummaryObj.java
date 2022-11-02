package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GMSummaryObj {
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
    public Item createItem(int pos1,int aId2,String FMScLearnerName3,String FMScLearnerId4,String FMScRegistration_No5,String FMScClaimAmount6,
                           String FMScClaimDate7,String FMScSalaryManagerStatus12,String FMSclblGrantManagerStatus13,
                           String FMScGrantAdminStatus14 ,String FMScapprove_stipend_link_status15,String FMScs_m_s_id16,
                           String FMDownloadeClaimUnSignedForm16,String FMDownloadeClaimSignedForm17,String FMApprivalStipend18) {

        return new Item(String.valueOf(pos1),aId2,FMScLearnerName3,FMScLearnerId4,FMScRegistration_No5,FMScClaimAmount6,FMScClaimDate7,FMScSalaryManagerStatus12,
                FMSclblGrantManagerStatus13,FMScGrantAdminStatus14,FMScapprove_stipend_link_status15,
                FMScs_m_s_id16, FMDownloadeClaimUnSignedForm16,FMDownloadeClaimSignedForm17,FMApprivalStipend18);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String FMScLearnerName3;
        public final String FMScLearnerId4;
        public final String FMScRegistration_No5;
        public final String FMScClaimAmount6;
        public final String FMScClaimDate7;

        public final String FMScSalaryManagerStatus12;
        public final String FMSclblGrantManagerStatus13;
        public final String FMScGrantAdminStatus14;
        public final String FMScapprove_stipend_link_status15;
        public final String FMScs_m_s_id16;
        public final String FMDownloadeClaimUnSignedForm16;
        public final String FMDownloadeClaimSignedForm17;

        public String getFMScs_m_s_id16() {
            return FMScs_m_s_id16;
        }

        public final String FMApprivalStipend18;

        public Item(String aPos1, int aId2, String FMScLearnerName3, String FMScLearnerId4, String FMScRegistration_No5, String FMScClaimAmount6, String FMScClaimDate7,
                    String FMScSalaryManagerStatus12, String FMSclblGrantManagerStatus13, String FMScGrantAdminStatus14, String FMScapprove_stipend_link_status15,
                    String FMScs_m_s_id16, String FMDownloadeClaimUnSignedForm16, String FMDownloadeClaimSignedForm17 ,String FMApprivalStipend18) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMScLearnerName3 = FMScLearnerName3;
            this.FMScLearnerId4 = FMScLearnerId4;
            this.FMScRegistration_No5 = FMScRegistration_No5;
            this.FMScClaimAmount6 = FMScClaimAmount6;
            this.FMScClaimDate7 = FMScClaimDate7;
            this.FMScSalaryManagerStatus12 = FMScSalaryManagerStatus12;
            this.FMSclblGrantManagerStatus13 = FMSclblGrantManagerStatus13;
            this.FMScGrantAdminStatus14 = FMScGrantAdminStatus14;
            this.FMScapprove_stipend_link_status15 = FMScapprove_stipend_link_status15;
            this.FMScs_m_s_id16 = FMScs_m_s_id16;
            this.FMDownloadeClaimUnSignedForm16 = FMDownloadeClaimUnSignedForm16;
            this.FMDownloadeClaimSignedForm17 = FMDownloadeClaimSignedForm17;
            this.FMApprivalStipend18 = FMApprivalStipend18;

        }
            @Override
            public String toString() {
            return FMScLearnerName3;

        }
    }
}