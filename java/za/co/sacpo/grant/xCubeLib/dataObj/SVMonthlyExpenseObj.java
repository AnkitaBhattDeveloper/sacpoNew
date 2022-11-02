package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVMonthlyExpenseObj {

    public List<SVMonthlyExpenseObj.Item> ITEMS = new ArrayList<SVMonthlyExpenseObj.Item>();
    public Map<Integer, SVMonthlyExpenseObj.Item> ITEM_MAP = new HashMap<Integer, SVMonthlyExpenseObj.Item>();
    public void addItem(SVMonthlyExpenseObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SVMonthlyExpenseObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SVMonthlyExpenseObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SVMonthlyExpenseObj.Item createItem(int pos1, int aId2, String SvLearnerName3,
                                               String SvPNumber4, String SvEmail5, String SvLearnerNo6,
                                               String SvMobileNo7, String SvClaimStipend9) {
        return new SVMonthlyExpenseObj.Item(String.valueOf(pos1),aId2,SvLearnerName3,SvPNumber4,SvEmail5,SvLearnerNo6,SvMobileNo7,
                SvClaimStipend9);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String SvLearnerName3;
        public final String SvPNumber4;
        public final String SvEmail5;
        public final String SvLearnerNo6;
        public final String SvMobileNo7;
        public final String SvClaimStipend9;


        public Item(String aPos1, int aId2, String SvLearnerName3, String SvPNumber4, String SvEmail5, String SvLearnerNo6,
                    String SvMobileNo7, String SvClaimStipend9) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SvLearnerName3 = SvLearnerName3;
            this.SvPNumber4 = SvPNumber4;
            this.SvEmail5 = SvEmail5;
            this.SvLearnerNo6 = SvLearnerNo6;
            this.SvMobileNo7 = SvMobileNo7;
            this.SvClaimStipend9 = SvClaimStipend9;






        }

        @Override
        public String toString() {
            return SvLearnerName3;
        }
    }
}


