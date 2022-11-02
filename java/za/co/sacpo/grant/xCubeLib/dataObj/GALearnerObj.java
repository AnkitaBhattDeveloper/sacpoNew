package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GALearnerObj {

    public List<GALearnerObj.Item> ITEMS = new ArrayList<GALearnerObj.Item>();
    public Map<Integer, GALearnerObj.Item> ITEM_MAP = new HashMap<Integer, GALearnerObj.Item>();
    public void addItem(GALearnerObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GALearnerObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GALearnerObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GALearnerObj.Item createItem(int pos1, int aId2,String Chat14,String Notes15, String mSLearnerId3, String mSLearnerName4, String mSLeaderEmployer5, String mSWorks6, String mSGrant7,String mSDuration8,String mSStartDate9,String mSEndDate10,String mSWorkedDays11, String mSLeaveDays12,String Student_id13,String PrivateReport) {
        return new GALearnerObj.Item(String.valueOf(pos1),aId2,Chat14,Notes15,mSLearnerId3,mSLearnerName4,mSLeaderEmployer5,mSWorks6,mSGrant7,mSDuration8,mSStartDate9,mSEndDate10,mSWorkedDays11,mSLeaveDays12,Student_id13,PrivateReport);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String Chat14;
        public final String PrivateReport;
        public final String Notes15;
        public final String mSLearnerId3;
        public final String mSLearnerName4;
        public final String mSLeaderEmployer5;
        public final String mSWorks6;
        public final String mSGrant7;
        public final String mSDuration8;
        public final String mSStartDate9;
        public final String mSEndDate10;
        public final String mSWorkedDays11;
        public final String mSLeaveDays12;
        public final String Student_id13;

        public Item(String aPos1, int aId2,String Chat14,String mSLearnerId3, String Notes15, String mSLearnerName4, String mSLeaderEmployer5, String mSWorks6, String mSGrant7, String mSDuration8, String mSStartDate9, String mSEndDate10,String mSWorkedDays11, String mSLeaveDays12,String Student_id13,String PrivateReport) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.Chat14 = Chat14;
            this.Notes15 = Notes15;
            this.mSLearnerId3 = mSLearnerId3;
            this.mSLearnerName4 = mSLearnerName4;
            this.mSLeaderEmployer5 = mSLeaderEmployer5;
            this.mSWorks6 = mSWorks6;
            this.mSGrant7 = mSGrant7;
            this.mSDuration8 = mSDuration8;
            this.mSStartDate9 = mSStartDate9;
            this.mSEndDate10 = mSEndDate10;
            this.mSWorkedDays11 = mSWorkedDays11;
            this.mSLeaveDays12 = mSLeaveDays12;
            this.Student_id13 = Student_id13;
            this.PrivateReport = PrivateReport;
        }

        @Override
        public String toString() {
            return mSLearnerId3;
        }
    }
}
