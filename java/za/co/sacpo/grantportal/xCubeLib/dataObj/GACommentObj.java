package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GACommentObj {
    public  List<GACommentObj.Item> ITEMS = new ArrayList<GACommentObj.Item>();
    public  Map<Integer, GACommentObj.Item> ITEM_MAP = new HashMap<Integer, GACommentObj.Item>();
    public void addItem(GACommentObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GACommentObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GACommentObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GACommentObj.Item createItem(int pos1, int aId2, String msfLearnerName3, String msfReportsCounts4, String msfSupervisorComments5, String msfWeekendDate6) {
        return new GACommentObj.Item(String.valueOf(pos1),aId2,msfLearnerName3,msfReportsCounts4,msfSupervisorComments5,msfWeekendDate6);
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
