package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMAttObj {

    public List<SMAttObj.Item> ITEMS = new ArrayList<SMAttObj.Item>();
    public Map<Integer, SMAttObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SMAttObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMAttObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SMAttObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMAttObj.Item createItem(int pos1, int aId2, String smDate3, String smDay4, String smLoginTime5, String smLogoutTime6,
                                    String smHoursWorked7, String smStatus8) {
        return new SMAttObj.Item(String.valueOf(pos1),aId2,smDate3,smDay4,smLoginTime5,smLogoutTime6,smHoursWorked7,smStatus8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String smDate3;
        public final String smDay4;
        public final String smLoginTime5;
        public final String smLogoutTime6;
        public final String smHoursWorked7;
        public final String smStatus8;


        public Item(String aPos1, int aId2, String smDate3, String smDay4, String smLoginTime5, String smLogoutTime6, String smHoursWorked7, String smStatus8) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.smDate3 = smDate3;
            this.smDay4 = smDay4;
            this.smLoginTime5 = smLoginTime5;
            this.smLogoutTime6 = smLogoutTime6;
            this.smHoursWorked7 = smHoursWorked7;
            this.smStatus8 = smStatus8;
        }

        @Override
        public String toString() {
            return smDate3;
        }
    }

}
