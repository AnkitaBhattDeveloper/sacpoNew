package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMSummaryObj {

    public List<SMSummaryObj.Item> ITEMS = new ArrayList<SMSummaryObj.Item>();
    public Map<Integer, SMSummaryObj.Item> ITEM_MAP = new HashMap<Integer, SMSummaryObj.Item>();
    public void addItem(SMSummaryObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMSummaryObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMSummaryObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMSummaryObj.Item createItem(int pos1, int aId2, String FMScLearnerName3, String FMScLearnerId4, String FMScRegistration_No5, String FMScClaimAmount6,
                                        String FMScClaimDate7, String FMScSalaryManagerStatus12, String FMSclblGrantManagerStatus13, String FMScGrantAdminStatus14, String SMDownloadeClaimUnSignedForm15
            , String SMDownloadeClaimSignedForm16,String SMStipendId17,String grant_id18,String SMSupervisorApprovalStatus19) {

        return new SMSummaryObj.Item(String.valueOf(pos1),aId2,FMScLearnerName3
                ,FMScLearnerId4,FMScRegistration_No5,FMScClaimAmount6,FMScClaimDate7,FMScSalaryManagerStatus12,FMSclblGrantManagerStatus13,FMScGrantAdminStatus14,SMDownloadeClaimUnSignedForm15,
                SMDownloadeClaimSignedForm16,SMStipendId17,grant_id18,SMSupervisorApprovalStatus19);
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
        public final String SMDownloadeClaimUnSignedForm15;
        public final String SMDownloadeClaimSignedForm16;
        public final String SMStipendId17;
        public final String grant_id18;
        public final String SMSupervisorApprovalStatus19;


        public Item(String aPos1, int aId2, String FMScLearnerName3, String FMScLearnerId4, String FMScRegistration_No5, String FMScClaimAmount6, String FMScClaimDate7, String FMScSalaryManagerStatus12, String FMSclblGrantManagerStatus13, String FMScGrantAdminStatus14,String SMDownloadeClaimUnSignedForm15
                ,String SMDownloadeClaimSignedForm16 ,String SMStipendId17,String grant_id18,String SMSupervisorApprovalStatus19) {
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
            this.SMDownloadeClaimUnSignedForm15 = SMDownloadeClaimUnSignedForm15;
            this.SMDownloadeClaimSignedForm16 = SMDownloadeClaimSignedForm16;
            this.SMStipendId17 = SMStipendId17;
            this.grant_id18 = grant_id18;
            this.SMSupervisorApprovalStatus19 = SMSupervisorApprovalStatus19;

          }
            @Override
        public String toString() {
            return FMScLearnerName3;
          }
        }
     }
