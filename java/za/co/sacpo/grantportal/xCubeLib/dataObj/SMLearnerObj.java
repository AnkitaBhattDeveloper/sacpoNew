package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMLearnerObj {

    public List<SMLearnerObj.Item> ITEMS = new ArrayList<SMLearnerObj.Item>();
    public Map<Integer, SMLearnerObj.Item> ITEM_MAP = new HashMap<Integer, SMLearnerObj.Item>();
    public void addItem(SMLearnerObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMLearnerObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMLearnerObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMLearnerObj.Item createItem(int pos1, int aId2, String lblLearnerName3, String lblLearnerEmail4, String lblLearnerCellNo5,
                                        String lblLearnerId6, String lblCurrentStatus7, String lblEmployerName8, String lblSDL_Number9,
                                        String lblMentorName10, String lblDuration11, String lblStartDate12,String lblEndDate13,
                                        String lblSetaAllocation14,String lblClaimed15,String lblUnspent16,String prReport17,String GMEmpId18,String GMStuId19) {
        return new SMLearnerObj.Item(String.valueOf(pos1),aId2,lblLearnerName3,lblLearnerEmail4,lblLearnerCellNo5,lblLearnerId6,
                lblCurrentStatus7,lblEmployerName8,lblSDL_Number9,lblMentorName10,lblDuration11,lblStartDate12,lblEndDate13,
                lblSetaAllocation14,lblClaimed15,lblUnspent16,prReport17,GMEmpId18,GMStuId19);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String lblLearnerName3;
        public final String lblLearnerEmail4;
        public final String lblLearnerCellNo5;
        public final String lblLearnerId6;
        public final String lblCurrentStatus7;
        public final String lblEmployerName8;
        public final String lblSDL_Number9;
        public final String lblMentorName10;
        public final String lblDuration11;
        public final String lblStartDate12;
        public final String lblEndDate13;
        public final String lblSetaAllocation14;
        public final String lblClaimed15;
        public final String lblUnspent16;
        public final String prReport17;
        public final String GMEmpId18;
        public final String GMStuId19;

        public Item(String aPos1, int aId2, String lblLearnerName3, String lblLearnerEmail4, String lblLearnerCellNo5, String lblLearnerId6,
                    String lblCurrentStatus7, String lblEmployerName8, String lblSDL_Number9, String lblMentorName10, String lblDuration11,
                    String lblStartDate12, String lblEndDate13, String lblSetaAllocation14, String lblClaimed15, String lblUnspent16,
                    String prReport17,String GMEmpId18,String GMStuId19) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.lblLearnerName3 = lblLearnerName3;
            this.lblLearnerEmail4 = lblLearnerEmail4;
            this.lblLearnerCellNo5 = lblLearnerCellNo5;
            this.lblLearnerId6 = lblLearnerId6;
            this.lblCurrentStatus7 = lblCurrentStatus7;
            this.lblEmployerName8 = lblEmployerName8;
            this.lblSDL_Number9 = lblSDL_Number9;
            this.lblMentorName10 = lblMentorName10;
            this.lblDuration11 = lblDuration11;
            this.lblStartDate12 = lblStartDate12;
            this.lblEndDate13 = lblEndDate13;
            this.lblSetaAllocation14 = lblSetaAllocation14;
            this.lblClaimed15 = lblClaimed15;
            this.lblUnspent16 = lblUnspent16;
            this.prReport17 = prReport17;
            this.GMEmpId18 = GMEmpId18;
            this.GMStuId19 = GMStuId19;
        }

        @Override
        public String toString() {
            return lblLearnerName3;
        }
    }
}

