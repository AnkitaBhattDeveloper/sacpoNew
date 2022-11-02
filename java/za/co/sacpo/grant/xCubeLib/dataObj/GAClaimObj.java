package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GAClaimObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,String aId2,String claDate3,String claMonth4,String claDaysWorked5, String paidLeaveCount6,
                           String unpaidLeaveCount7,String amount8,String studentStatus9, String mentorStatus10,
                           String adminStatus11, String studentStatusText12,String mentorStatusText13,String adminStatusText14,
                           String claimDownloadStatus15,String claimUploadStatus16,String isAttendanceApproval17){

        return new Item(String.valueOf(pos1),aId2,claDate3,claMonth4,claDaysWorked5,paidLeaveCount6,
                unpaidLeaveCount7,amount8,studentStatus9,mentorStatus10,adminStatus11,studentStatusText12,mentorStatusText13,
                adminStatusText14,claimDownloadStatus15,claimUploadStatus16,isAttendanceApproval17);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String claDate3;
        public final String claMonth4;
        public final String amount8;
        public final String claDaysWorked5;
        public final String paidLeaveCount6;
        public final String unpaidLeaveCount7;

        public final String studentStatus9;
        public final String mentorStatus10;
        public final String adminStatus11;
        public final String studentStatusText12;
        public final String mentorStatusText13;
        public final String adminStatusText14;
        public final String claimDownloadStatus15;
        public final String claimUploadStatus16;
        public final String isAttendanceApproval17;
        public Item(String aPos1, String aId2, String claDate3, String claMonth4, String claDaysWorked5,
                    String paidLeaveCount6, String unpaidLeaveCount7, String amount8, String studentStatus9,
                    String mentorStatus10, String adminStatus11, String studentStatusText12, String mentorStatusText13, String adminStatusText14,
                    String claimDownloadStatus15, String claimUploadStatus16,String  isAttendanceApproval17){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.claDate3= claDate3;
            this.claMonth4= claMonth4;
            this.claDaysWorked5= claDaysWorked5;
            this.paidLeaveCount6= paidLeaveCount6;
            this.unpaidLeaveCount7= unpaidLeaveCount7;
            this.amount8= amount8;
            this.studentStatus9=studentStatus9;
            this.mentorStatus10=mentorStatus10;
            this.adminStatus11=adminStatus11;
            this.studentStatusText12=studentStatusText12;
            this.mentorStatusText13=mentorStatusText13;
            this.adminStatusText14=adminStatusText14;
            this.claimDownloadStatus15=claimDownloadStatus15;
            this.claimUploadStatus16=claimUploadStatus16;
            this.isAttendanceApproval17=isAttendanceApproval17;
        }

        @Override
        public String toString() {
            return claDate3;
        }
    }
}

