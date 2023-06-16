package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MStudentObj {
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
    public Item createItem(int pos1,int aId2,String mSLearnerName3,String mSRegisNo4,String mSLearnerId5, String mSUniversity6,String mSDuration7,String mSStartDate8,String mSEndDate9,String mSWorkedDay10,String mSLeaveDay11,String mSbtnDownloadLearnerCV12,String mSbtnApproveAttendance13, String mSbtnDownloadClaimForm14, String mSbtnApproved15) {
        return new Item(String.valueOf(pos1),aId2,mSLearnerName3,mSRegisNo4,mSLearnerId5,mSUniversity6,mSDuration7,mSStartDate8,mSEndDate9,mSWorkedDay10,mSLeaveDay11,mSbtnDownloadLearnerCV12,mSbtnApproveAttendance13,mSbtnDownloadClaimForm14,mSbtnApproved15);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mSLearnerName3;
        public final String mSRegisNo4;
        public final String mSLearnerId5;
        public final String mSUniversity6;
        public final String mSDuration7;
        public final String mSStartDate8;
        public final String mSEndDate9;
        public final String mSWorkedDay10;
        public final String mSLeaveDay11;
        public final String mSbtnDownloadLearnerCV12;
        public final String mSbtnApproveAttendance13;
        public final String mSbtnDownloadClaimForm14;
        public final String mSbtnApproved15;


        public Item(String aPos1, int aId2, String mSLearnerName3, String mSRegisNo4, String mSLearnerId5, String mSUniversity6, String mSDuration7, String mSStartDate8, String mSEndDate9, String mSWorkedDay10, String mSLeaveDay11, String mSbtnDownloadLearnerCV12, String mSbtnApproveAttendance13, String mSbtnDownloadClaimForm14, String mSbtnApproved15) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mSLearnerName3 = mSLearnerName3;
            this.mSRegisNo4 = mSRegisNo4;
            this.mSLearnerId5 = mSLearnerId5;
            this.mSUniversity6 = mSUniversity6;
            this.mSDuration7 = mSDuration7;
            this.mSStartDate8 = mSStartDate8;
            this.mSEndDate9 = mSEndDate9;
            this.mSWorkedDay10 = mSWorkedDay10;
            this.mSLeaveDay11 = mSLeaveDay11;
            this.mSbtnDownloadLearnerCV12 = mSbtnDownloadLearnerCV12;
            this.mSbtnApproveAttendance13 = mSbtnApproveAttendance13;
            this.mSbtnDownloadClaimForm14 = mSbtnDownloadClaimForm14;
            this.mSbtnApproved15 = mSbtnApproved15;
        }

        @Override
        public String toString() {
            return mSLearnerName3;
        }
    }
}
