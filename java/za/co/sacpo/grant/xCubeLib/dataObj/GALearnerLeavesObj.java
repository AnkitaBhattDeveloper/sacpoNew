package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GALearnerLeavesObj {

    public List<GALearnerLeavesObj.Item> ITEMS = new ArrayList<GALearnerLeavesObj.Item>();
    public Map<Integer, GALearnerLeavesObj.Item> ITEM_MAP = new HashMap<Integer, GALearnerLeavesObj.Item>();
    public void addItem(GALearnerLeavesObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GALearnerLeavesObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GALearnerLeavesObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GALearnerLeavesObj.Item createItem(int pos1, int aId2, String aLeDate3, String aLeType4, String aLeStatus5, String aLeDay6,String aLeLearner7, String aLeLearnerId8) {
        return new GALearnerLeavesObj.Item(String.valueOf(pos1),aId2,aLeDate3,aLeType4,aLeStatus5,aLeDay6,aLeLearner7, aLeLearnerId8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aLeDate3;
        public final String aLeType4;
        public final String aLeStatus5;
        public final String aLeDay6;
        public final String aLeLearner7;
        public final String aLeLearnerId8;


        public Item(String aPos1, int aId2, String aLeDate3, String aLeType4,String aLeStatus5, String aLeDay6,String aLeLearner7, String aLeLearnerId8) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.aLeDate3 = aLeDate3;
            this.aLeType4 = aLeType4;
            this.aLeStatus5 = aLeStatus5;
            this.aLeDay6 = aLeDay6;
            this.aLeLearner7 = aLeLearner7;
            this.aLeLearnerId8 = aLeLearnerId8;
        }

        @Override
        public String toString() {
            return aLeDate3;
        }
    }
}

