package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StuFeedbackObj {
    public  List<StuFeedbackObj.Item> ITEMS = new ArrayList<StuFeedbackObj.Item>();
    public  Map<Integer, StuFeedbackObj.Item> ITEM_MAP = new HashMap<Integer, StuFeedbackObj.Item>();
    public void addItem(StuFeedbackObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<StuFeedbackObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, StuFeedbackObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public StuFeedbackObj.Item createItem(int pos1, int aId2, String msfLearnerName3, String msfReportsCounts4, String msfSupervisorComments5, String msfWeekendDate6) {
        return new StuFeedbackObj.Item(String.valueOf(pos1),aId2,msfLearnerName3,msfReportsCounts4,msfSupervisorComments5,msfWeekendDate6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String msfLearnerName3;
        public final String msfReportsCounts4;
        public final String msfSupervisorComments5;
        public final String msfWeekendDate6;

        public Item(String aPos1, int aId2, String msfLearnerName3,String msfReportsCounts4, String msfSupervisorComments5, String msfWeekendDate6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.msfLearnerName3 = msfLearnerName3;
            this.msfReportsCounts4 = msfReportsCounts4;
            this.msfSupervisorComments5 = msfSupervisorComments5;
            this.msfWeekendDate6 = msfWeekendDate6;

        }

        @Override
        public String toString() {
            return msfLearnerName3;
        }
    }
}
