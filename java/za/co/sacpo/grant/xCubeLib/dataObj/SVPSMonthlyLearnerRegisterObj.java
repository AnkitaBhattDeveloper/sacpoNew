package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SVPSMonthlyLearnerRegisterObj {
    public List<SVPSMonthlyLearnerRegisterObj.Item> ITEMS = new ArrayList<SVPSMonthlyLearnerRegisterObj.Item>();
    public Map<Integer, SVPSMonthlyLearnerRegisterObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SVPSMonthlyLearnerRegisterObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SVPSMonthlyLearnerRegisterObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SVPSMonthlyLearnerRegisterObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SVPSMonthlyLearnerRegisterObj.Item createItem(int pos1, int aId2, String FMaDate3, String FMaDay4, String FMaLoginTime5, String FMaLogoutTime6, String FMaHoursWorked7,
                                                         String FMaLearnerComment8, String FMaAttStatus9, String FMaAttSupervisorStatus10, String FMaGrantAdminAttStatus11) {
        return new SVPSMonthlyLearnerRegisterObj.Item(String.valueOf(pos1),aId2,FMaDate3,FMaDay4,FMaLoginTime5,FMaLogoutTime6,
                FMaHoursWorked7,FMaLearnerComment8,FMaAttStatus9,FMaAttSupervisorStatus10,FMaGrantAdminAttStatus11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String FMaDate3;
        public final String FMaDay4;
        public final String FMaLoginTime5;
        public final String FMaLogoutTime6;
        public final String FMaHoursWorked7;
        public final String FMaLearnerComment8;
        public final String FMaAttStatus9;
        public final String FMaAttSupervisorStatus10;
        public final String FMaGrantAdminAttStatus11;



        public Item(String aPos1, int aId2, String FMaDate3, String FMaDay4, String FMaLoginTime5, String FMaLogoutTime6, String FMaHoursWorked7, 
                    String FMaLearnerComment8, String FMaAttStatus9,String FMaAttSupervisorStatus10, String FMaGrantAdminAttStatus11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMaDate3 = FMaDate3;
            this.FMaDay4 = FMaDay4;
            this.FMaLoginTime5 = FMaLoginTime5;
            this.FMaLogoutTime6 = FMaLogoutTime6;
            this.FMaHoursWorked7 = FMaHoursWorked7;
            this.FMaLearnerComment8 = FMaLearnerComment8;
            this.FMaAttStatus9 = FMaAttStatus9;
            this.FMaAttSupervisorStatus10 = FMaAttSupervisorStatus10;
            this.FMaGrantAdminAttStatus11 = FMaGrantAdminAttStatus11;

        }

        @Override
        public String toString() {
            return FMaDate3;
        }
    }

}
