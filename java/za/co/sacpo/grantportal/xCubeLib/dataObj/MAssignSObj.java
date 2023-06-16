package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MAssignSObj {

    public List<MAssignSObj.Item> ITEMS = new ArrayList<MAssignSObj.Item>();
    public Map<Integer, MAssignSObj.Item> ITEM_MAP = new HashMap<Integer, MAssignSObj.Item>();
    public void addItem(MAssignSObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MAssignSObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MAssignSObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MAssignSObj.Item createItem(int pos1, int aId2, String mSLearnerName3, String mSRegisNo4, String mwxStatus5, String mSUniversity6,String  mwXWorkstationName7,String  mwxStudentid8) {
        return new MAssignSObj.Item(String.valueOf(pos1),aId2,mSLearnerName3,mSRegisNo4,mwxStatus5,mSUniversity6,mwXWorkstationName7,mwxStudentid8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mSLearnerName3;
        public final String mSRegisNo4;
        public final String mwxStatus5;
        public final String mSUniversity6;
        public final String mwXWorkstationName7;
        public final String mwxStudentid8;



        public Item(String aPos1, int aId2, String mSLearnerName3, String mSRegisNo4, String mwxStatus5, String mSUniversity6,
                    String mwXWorkstationName7,String mwxStudentid8) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mSLearnerName3 = mSLearnerName3;
            this.mSRegisNo4 = mSRegisNo4;
            this.mwxStatus5 = mwxStatus5;
            this.mSUniversity6 = mSUniversity6;
            this.mwXWorkstationName7 = mwXWorkstationName7;
            this.mwxStudentid8 = mwxStudentid8;

        }


        @Override
        public String toString()
        {
            return mSLearnerName3;
        }
    }


}
