package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMSummaryClaimObj {
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
    public Item createItem(int pos1,int aId2,String FMScLearnerName3,String FMScLearnerId4,String FMScRegistration_No5,String FMScClaimAmount6,String FMScClaimDate7,
                           String FMScBankName8,String FMScBranchCode9,String FMScAccount_No10 ,String FMScAccountHolderName11,String FMScSalaryManagerStatus12,
                           String FMSclblGrantManagerStatus13,
                           String FMScGrantAdminStatus14,String FMSStipend_id15,String FMDownloadeClaimUnSignedForm16,
                           String FMDownloadeClaimUnSignedForm17 ,String FMApprivalStipend18,String FMSupervisor_claim_approval19) {

        return new Item(String.valueOf(pos1),aId2,FMScLearnerName3,FMScLearnerId4,FMScRegistration_No5,FMScClaimAmount6,FMScClaimDate7,FMScBankName8,FMScBranchCode9,FMScAccount_No10,FMScAccountHolderName11,FMScSalaryManagerStatus12,FMSclblGrantManagerStatus13,
                FMScGrantAdminStatus14,FMSStipend_id15,FMDownloadeClaimUnSignedForm16,FMDownloadeClaimUnSignedForm17,FMApprivalStipend18,FMSupervisor_claim_approval19);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String FMScLearnerName3;

        public int getaId2() {
            return aId2;
        }
        public final String FMScLearnerId4;
        public final String FMScRegistration_No5;
        public final String FMScClaimAmount6;
        public final String FMScClaimDate7;
        public final String FMScBankName8;
        public final String FMScBranchCode9;
        public final String FMScAccount_No10;
        public final String FMScAccountHolderName11;
        public final String FMScSalaryManagerStatus12;
        public final String FMSclblGrantManagerStatus13;
        public final String FMScGrantAdminStatus14;
        public final String FMSStipend_id15;
        public final String FMDownloadeClaimUnSignedForm16;
        public final String FMDownloadeClaimSignedForm17;
        public final String FMApprivalStipend18;
        public final String FMSupervisor_claim_approval19;




        public Item(String aPos1, int aId2, String FMScLearnerName3, String FMScLearnerId4, String FMScRegistration_No5, String FMScClaimAmount6, String FMScClaimDate7, String FMScBankName8, String FMScBranchCode9, String FMScAccount_No10, String FMScAccountHolderName11, String FMScSalaryManagerStatus12, String FMSclblGrantManagerStatus13,
                    String FMScGrantAdminStatus14 , String FMSStipend_id15,
                    String FMDownloadeClaimUnSignedForm16 ,String FMDownloadeClaimSignedForm17,String FMApprivalStipend18,String FMSupervisor_claim_approval19) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMScLearnerName3 = FMScLearnerName3;
            this.FMScLearnerId4 = FMScLearnerId4;
            this.FMScRegistration_No5 = FMScRegistration_No5;
            this.FMScClaimAmount6 = FMScClaimAmount6;
            this.FMScClaimDate7 = FMScClaimDate7;
            this.FMScBankName8 = FMScBankName8;
            this.FMScBranchCode9 = FMScBranchCode9;
            this.FMScAccount_No10 = FMScAccount_No10;
            this.FMScAccountHolderName11 = FMScAccountHolderName11;
            this.FMScSalaryManagerStatus12 = FMScSalaryManagerStatus12;
            this.FMSclblGrantManagerStatus13 = FMSclblGrantManagerStatus13;
            this.FMScGrantAdminStatus14 = FMScGrantAdminStatus14;
            this.FMSStipend_id15 = FMSStipend_id15;
            this.FMDownloadeClaimUnSignedForm16 = FMDownloadeClaimUnSignedForm16;
            this.FMDownloadeClaimSignedForm17 = FMDownloadeClaimSignedForm17;
            this.FMApprivalStipend18 = FMApprivalStipend18;
            this.FMSupervisor_claim_approval19 = FMSupervisor_claim_approval19;

        }
        @Override
        public String toString() {
            return FMScLearnerName3;
        }
    }
}