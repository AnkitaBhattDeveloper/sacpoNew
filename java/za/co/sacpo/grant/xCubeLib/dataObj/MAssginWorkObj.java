package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MAssginWorkObj {
    public List<MAssginWorkObj.Item> ITEMS = new ArrayList<MAssginWorkObj.Item>();
    public Map<Integer, MAssginWorkObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(MAssginWorkObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MAssginWorkObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MAssginWorkObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MAssginWorkObj.Item createItem(int pos1, int aId2, String MaLearnerName3, String MaEmail4, String MaCellNo5, String MaGrantName6, String MaStartDate7,
                                          String MaEndDate8, String MaBgColor9,String MabtnName10,String mWorkstaionid11,String mLinkedStudentId12) {
        return new MAssginWorkObj.Item(String.valueOf(pos1),aId2,MaLearnerName3,MaEmail4,MaCellNo5,MaGrantName6,MaStartDate7,MaEndDate8,MaBgColor9,MabtnName10,mWorkstaionid11,
                mLinkedStudentId12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String MaLearnerName3;
        public final String MaEmail4;
        public final String MaCellNo5;
        public final String MaGrantName6;
        public final String MaStartDate7;
        public final String MaEndDate8;
        public final String MaBgColor9;
        public final String MabtnName10;
        public final String mWorkstaionid11;
        public final String mLinkedStudentId12;



        public Item(String aPos1, int aId2, String MaLearnerName3, String MaEmail4, String MaCellNo5, String MaGrantName6, String MaStartDate7, String MaEndDate8, String MaBgColor9,String MabtnName10,
                    String mWorkstaionid11,String mLinkedStudentId12) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.MaLearnerName3 = MaLearnerName3;
            this.MaEmail4 = MaEmail4;
            this.MaCellNo5 = MaCellNo5;
            this.MaGrantName6 = MaGrantName6;
            this.MaStartDate7 = MaStartDate7;
            this.MaEndDate8 = MaEndDate8;
            this.MaBgColor9 = MaBgColor9;
            this.MabtnName10 = MabtnName10;
            this.mWorkstaionid11 = mWorkstaionid11;
            this.mLinkedStudentId12 = mLinkedStudentId12;

        }

        @Override
        public String toString() {
            return MaLearnerName3;
        }
    }

}
