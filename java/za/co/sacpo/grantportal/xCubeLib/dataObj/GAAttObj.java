package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class GAAttObj {
    public List<GAAttObj.Item> ITEMS = new ArrayList<GAAttObj.Item>();
    public Map<Integer, GAAttObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(GAAttObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GAAttObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GAAttObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GAAttObj.Item createItem(int pos1, int aId2, String mAsDate3, String mAsLoginTime4, String mAsLogoutTime5, String mAsSpendTime6, String mAsStudentAttendenceStatus7,
                                    String mAsMentorAttendanceStatus8, String mAsLeadAdminEmpAttStatus9,String mAsCordDifference10,String mDay11) {
        return new GAAttObj.Item(String.valueOf(pos1),aId2,mAsDate3,mAsLoginTime4,mAsLogoutTime5,mAsSpendTime6,mAsStudentAttendenceStatus7,mAsMentorAttendanceStatus8,mAsLeadAdminEmpAttStatus9,mAsCordDifference10,mDay11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mAsDate3;
        public final String mAsLoginTime4;
        public final String mAsLogoutTime5;
        public final String mAsSpendTime6;
        public final String mAsStudentAttendenceStatus7;
        public final String mAsMentorAttendanceStatus8;
        public final String mAsLeadAdminEmpAttStatus9;
        public final String mAsCordDifference10;
        public final String mDay11;


        public Item(String aPos1, int aId2, String mAsDate3, String mAsLoginTime4, String mAsLogoutTime5, String mAsSpendTime6, String mAsStudentAttendenceStatus7, String mAsMentorAttendanceStatus8, String mAsLeadAdminEmpAttStatus9, String mAsCordDifference10,String mDay11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mAsDate3 = mAsDate3;
            this.mAsLoginTime4 = mAsLoginTime4;
            this.mAsLogoutTime5 = mAsLogoutTime5;
            this.mAsSpendTime6 = mAsSpendTime6;
            this.mAsStudentAttendenceStatus7 = mAsStudentAttendenceStatus7;
            this.mAsMentorAttendanceStatus8 = mAsMentorAttendanceStatus8;
            this.mAsLeadAdminEmpAttStatus9 = mAsLeadAdminEmpAttStatus9;
            this.mAsCordDifference10 = mAsCordDifference10;
            this.mDay11 = mDay11;
        }

        @Override
        public String toString() {
            return mAsDate3;
        }
    }

}
