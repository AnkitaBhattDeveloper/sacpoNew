package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GASummaryObj {
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
    public Item createItem(int pos1,int aId2,String GAScLearnerName3,String GAScLearnerId4,String GAScRegistration_No5,String GAScClaimAmount6,
                           String GAScClaimDate7,String GAScSalaryManagerClaimApproval12,String GASclblGrantManagerClaimApproval13,String GAScLearnerClaimStatus14,
                           String GAScSupervisorClaimApproval15,String GAScAmtPaidToDate16,String GAScDaysWorked17,String GAScLeave18,String GAScDetailsChanged19,String GAScBankName20,String GAScBankBranch21,
                           String GAScBankBranchCode22,String GAScAccountNumber23,
                           String GAScAccountHolderName24,String GAScshow_proof_of_banking25,
                           String GASc_proof_of_banking26
            ,String stipend_id,String stipend_month,
                           String stipend_year,String GASc_VerifiedBankDetails27
            ,String GASc_approve_attendance28,String GASc_verify_stipend29
            ,String GAUploadClaimForm30 ,String GADownloadeClaimUnSignedForm31,String GADownloadeClaimSignedForm32,String grant_id_33,String date_34    ) {

        return new Item(String.valueOf(pos1),aId2,GAScLearnerName3,GAScLearnerId4,GAScRegistration_No5,GAScClaimAmount6,GAScClaimDate7,GAScSalaryManagerClaimApproval12,GASclblGrantManagerClaimApproval13,GAScLearnerClaimStatus14,
                GAScSupervisorClaimApproval15,GAScAmtPaidToDate16,GAScDaysWorked17,GAScLeave18,GAScDetailsChanged19,GAScBankName20 , GAScBankBranch21,GAScBankBranchCode22,
                GAScAccountNumber23,GAScAccountHolderName24,GAScshow_proof_of_banking25,GASc_proof_of_banking26,stipend_id,stipend_month,stipend_year,
                GASc_VerifiedBankDetails27,
                GASc_approve_attendance28,
                GASc_verify_stipend29,GAUploadClaimForm30,GADownloadeClaimUnSignedForm31,GADownloadeClaimSignedForm32,grant_id_33,date_34);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GAScLearnerName3;
        public final String GAScLearnerId4;
        public final String GAScRegistration_No5;
        public final String GAScClaimAmount6;
        public final String GAScClaimDate7;
        public final String GAScSalaryManagerClaimApproval12;
        public final String GASclblGrantManagerClaimApproval13;
        public final String GAScLearnerClaimStatus14;
        public final String GAScSupervisorClaimApproval15;
        public final String GAScAmtPaidToDate16;
        public final String GAScDaysWorked17;
        public final String GAScLeave18;
        public final String GAScDetailsChanged19;
        public final String GAScBankName20;
        public final String GAScBankBranch21;
        public final String GAScBankBranchCode22;
        public final String GAScAccountNumber23;
        public final String GAScAccountHolderName24;
        public final String GAScshow_proof_of_banking25;
        public final String GASc_proof_of_banking26;
        public final String date_34;



        public final String stipend_id;
        public final String stipend_month;
        public final String stipend_year;
        public final String GASc_VerifiedBankDetails27;
        public final String GASc_approve_attendance28;
        public final String GASc_verify_stipend29;
        public final String GAUploadClaimForm30;
        public final String GADownloadeClaimUnSignedForm31;
        public final String GADownloadeClaimSignedForm32;
        public final String grant_id_33;
/*    String GAUploadClaimForm30 = "1";
                            String GADownloadeClaimUnSignedForm31 = "1";
                            String GADownloadeClaimSignedForm32 = "1";
*/
public String getStipend_id() {
    return stipend_id;
}
        public Item(String aPos1, int aId2, String GAScLearnerName3, String GAScLearnerId4, String GAScRegistration_No5, String GAScClaimAmount6, String GAScClaimDate7, String GAScSalaryManagerClaimApproval12, String GASclblGrantManagerClaimApproval13, String GAScLearnerClaimStatus14,
                    String GAScSupervisorClaimApproval15,String GAScAmtPaidToDate16,String GAScDaysWorked17,String GAScLeave18,String GAScDetailsChanged19,String GAScBankName20,String GAScBankBranch21,
                    String GAScBankBranchCode22,String GAScAccountNumber23,String GAScAccountHolderName24, String GAScshow_proof_of_banking25, String GASc_proof_of_banking26,
                    String stipend_id,String stipend_month
                   ,String stipend_year,String GASc_VerifiedBankDetails27,
                    String GASc_approve_attendance28
                ,String GASc_verify_stipend29,String GAUploadClaimForm30
                ,String GADownloadeClaimUnSignedForm31 ,String GADownloadeClaimSignedForm32,String grant_id_33,String date_34) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GAScLearnerName3 = GAScLearnerName3;
            this.GAScLearnerId4 = GAScLearnerId4;
            this.GAScRegistration_No5 = GAScRegistration_No5;
            this.GAScClaimAmount6 = GAScClaimAmount6;
            this.GAScClaimDate7 = GAScClaimDate7;
            this.GAScSalaryManagerClaimApproval12 = GAScSalaryManagerClaimApproval12;
            this.GASclblGrantManagerClaimApproval13 = GASclblGrantManagerClaimApproval13;
            this.GAScLearnerClaimStatus14 = GAScLearnerClaimStatus14;
            this.GAScSupervisorClaimApproval15 = GAScSupervisorClaimApproval15;
            this.GAScAmtPaidToDate16 = GAScAmtPaidToDate16;
            this.GAScDaysWorked17 = GAScDaysWorked17;
            this.GAScLeave18 = GAScLeave18;
            this.GAScDetailsChanged19 = GAScDetailsChanged19;
            this.GAScBankName20 = GAScBankName20;
            this.GAScBankBranch21 = GAScBankBranch21;
            this.GAScBankBranchCode22 = GAScBankBranchCode22;
            this.GAScAccountNumber23 = GAScAccountNumber23;
            this.GAScAccountHolderName24 = GAScAccountHolderName24;
            this.GAScshow_proof_of_banking25 = GAScshow_proof_of_banking25;
            this.GASc_proof_of_banking26 = GASc_proof_of_banking26;
            this.stipend_id = stipend_id;
            this.stipend_month = stipend_month;
            this.stipend_year = stipend_year;
            this.GASc_VerifiedBankDetails27 = GASc_VerifiedBankDetails27;
            this.GASc_approve_attendance28 = GASc_approve_attendance28;
            this.GASc_verify_stipend29 = GASc_verify_stipend29;
            this.GAUploadClaimForm30 = GAUploadClaimForm30;
            this.GADownloadeClaimUnSignedForm31 = GADownloadeClaimUnSignedForm31;
            this.GADownloadeClaimSignedForm32 = GADownloadeClaimSignedForm32;
            this.grant_id_33 = grant_id_33;
            this.date_34 = date_34;

        }
        @Override
        public String toString() {
            return GAScLearnerName3;
        }
    }
}