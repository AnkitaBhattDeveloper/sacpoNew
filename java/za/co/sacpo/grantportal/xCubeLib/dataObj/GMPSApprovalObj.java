package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GMPSApprovalObj {
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
    public Item createItem(int pos1,int aId2,String GMPSBlog3,String GMPSScheduleNo4,String GMPSGrantNumber5,String GMPSetaId6,String GMPSMonth7,
                           String GMPSYear8,String GMPSClaimAmount9,String GMPSProcessedBy10 ,String GMPSVerifiedBy11,String GMPSGrantManagerApproval12,
                           String GMPSProcessedByPaymaster13,String GMPSPayrollSchedule14,String GMPSAttendanceRegister15,
                           String GMPSEFTSheet16,String GMPSAuditReport17,String GMPSBlogPostCount18,String GMPSVerifierStatus19,String GMPSApprovalStatus20,String GMPSPaymasterStatus21) {

        return new Item(String.valueOf(pos1),aId2,GMPSBlog3,GMPSScheduleNo4,GMPSGrantNumber5,GMPSetaId6,GMPSMonth7,GMPSYear8,GMPSClaimAmount9,
                GMPSProcessedBy10,GMPSVerifiedBy11,GMPSGrantManagerApproval12,GMPSProcessedByPaymaster13,GMPSPayrollSchedule14,
                GMPSAttendanceRegister15,GMPSEFTSheet16,GMPSAuditReport17,GMPSBlogPostCount18,GMPSVerifierStatus19,GMPSApprovalStatus20,GMPSPaymasterStatus21);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GMPSBlog3;
        public final String GMPSScheduleNo4;
        public final String GMPSGrantNumber5;
        public final String GMPSetaId6;
        public final String GMPSMonth7;
        public final String GMPSYear8;
        public final String GMPSClaimAmount9;
        public final String GMPSProcessedBy10;
        public final String GMPSVerifiedBy11;
        public final String GMPSGrantManagerApproval12;
        public final String GMPSProcessedByPaymaster13;
        public final String GMPSPayrollSchedule14;
        public final String GMPSAttendanceRegister15;
        public final String GMPSEFTSheet16;
        public final String GMPSAuditReport17;
        public final String GMPSBlogPostCount18;
        public final String GMPSVerifierStatus19;
        public final String GMPSApprovalStatus20;
        public final String GMPSPaymasterStatus21;


        public Item(String aPos1, int aId2, String GMPSBlog3, String GMPSScheduleNo4, String GMPSGrantNumber5,
                    String GMPSetaId6, String GMPSMonth7, String GMPSYear8, String GMPSClaimAmount9, String GMPSProcessedBy10,
                    String GMPSVerifiedBy11, String GMPSGrantManagerApproval12, String GMPSProcessedByPaymaster13,
                    String GMPSPayrollSchedule14, String GMPSAttendanceRegister15, String GMPSEFTSheet16,
                    String GMPSAuditReport17,String GMPSBlogPostCount18,String GMPSVerifierStatus19,String GMPSApprovalStatus20,String GMPSPaymasterStatus21) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMPSBlog3 = GMPSBlog3;
            this.GMPSScheduleNo4 = GMPSScheduleNo4;
            this.GMPSGrantNumber5 = GMPSGrantNumber5;
            this.GMPSetaId6 = GMPSetaId6;
            this.GMPSMonth7 = GMPSMonth7;
            this.GMPSYear8 = GMPSYear8;
            this.GMPSClaimAmount9 = GMPSClaimAmount9;
            this.GMPSProcessedBy10 = GMPSProcessedBy10;
            this.GMPSVerifiedBy11 = GMPSVerifiedBy11;
            this.GMPSGrantManagerApproval12 = GMPSGrantManagerApproval12;
            this.GMPSProcessedByPaymaster13 = GMPSProcessedByPaymaster13;
            this.GMPSPayrollSchedule14 = GMPSPayrollSchedule14;
            this.GMPSAttendanceRegister15 = GMPSAttendanceRegister15;
            this.GMPSEFTSheet16 = GMPSEFTSheet16;
            this.GMPSAuditReport17 = GMPSAuditReport17;
            this.GMPSBlogPostCount18 = GMPSBlogPostCount18;
            this.GMPSVerifierStatus19 = GMPSVerifierStatus19;
            this.GMPSApprovalStatus20 = GMPSApprovalStatus20;
            this.GMPSPaymasterStatus21 = GMPSPaymasterStatus21;

        }

        @Override
        public String toString() {
            return GMPSBlog3;
        }
    }
}