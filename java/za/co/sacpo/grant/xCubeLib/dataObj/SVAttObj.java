package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SVAttObj {
    public List<SVAttObj.Item> ITEMS = new ArrayList<SVAttObj.Item>();
    public Map<Integer, SVAttObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SVAttObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SVAttObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SVAttObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SVAttObj.Item createItem(int pos1, int aId2, String FMaDate3, String FMaDay4, String FMaLoginTime5, String FMaLogoutTime6, String FMaHoursWorked7,
                                    String FMaLearnerComment8, String FMaAttStatus9) {
        return new SVAttObj.Item(String.valueOf(pos1),aId2,FMaDate3,FMaDay4,FMaLoginTime5,FMaLogoutTime6,FMaHoursWorked7,FMaLearnerComment8,FMaAttStatus9);
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



        public Item(String aPos1, int aId2, String FMaDate3, String FMaDay4, String FMaLoginTime5, String FMaLogoutTime6, String FMaHoursWorked7, String FMaLearnerComment8, String FMaAttStatus9) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMaDate3 = FMaDate3;
            this.FMaDay4 = FMaDay4;
            this.FMaLoginTime5 = FMaLoginTime5;
            this.FMaLogoutTime6 = FMaLogoutTime6;
            this.FMaHoursWorked7 = FMaHoursWorked7;
            this.FMaLearnerComment8 = FMaLearnerComment8;
            this.FMaAttStatus9 = FMaAttStatus9;

        }

        @Override
        public String toString() {
            return FMaDate3;
        }
    }

}
