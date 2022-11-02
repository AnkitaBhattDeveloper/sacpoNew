package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMLeaveObj {

    public List<SMLeaveObj.Item> ITEMS = new ArrayList<SMLeaveObj.Item>();
    public Map<Integer, SMLeaveObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SMLeaveObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMLeaveObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMLeaveObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMLeaveObj.Item createItem(int pos1, int aId2, String smLearnerName3, String smLearnerId4, String smLeaveDate5, String smLeaveDay6,
                                      String smLeaveType7, String smLeaveReason8,String smStatus9) {
        return new SMLeaveObj.Item(String.valueOf(pos1),aId2,smLearnerName3,smLearnerId4,smLeaveDate5,smLeaveDay6,smLeaveType7,smLeaveReason8,smStatus9);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String smLearnerName3;
        public final String smLearnerId4;
        public final String smLeaveDate5;
        public final String smLeaveDay6;
        public final String smLeaveType7;
        public final String smLeaveReason8;
        public final String smStatus9;


        public Item(String aPos1, int aId2, String smLearnerName3, String smLearnerId4, String smLeaveDate5, String smLeaveDay6, String smLeaveType7, String smLeaveReason8,String smStatus9) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.smLearnerName3 = smLearnerName3;
            this.smLearnerId4 = smLearnerId4;
            this.smLeaveDate5 = smLeaveDate5;
            this.smLeaveDay6 = smLeaveDay6;
            this.smLeaveType7 = smLeaveType7;
            this.smLeaveReason8 = smLeaveReason8;
            this.smStatus9 = smStatus9;
        }

        @Override
        public String toString() {
            return smLearnerName3;
        }
    }

}
