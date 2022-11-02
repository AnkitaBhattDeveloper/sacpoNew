package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCurrentAttObj {

    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(String pos1,String aId2,String aDate3,String aDay4,String aDisCord5,String aLearnerStatus6, String aLearnerStatusId7, String aSignInHours8, String aSignInMin9, String aSignInSec10, String aSignOutHours11, String aSignOutMin12, String aSignOutSec13, String aSignInTime14, String aSignOutTime15, String aHoursWorked16, String aLearnerComment17 ) {
        return new Item(String.valueOf(pos1),aId2,aDate3,aDay4,aDisCord5,aLearnerStatus6,aLearnerStatusId7,aSignInHours8,aSignInMin9,aSignInSec10,aSignOutHours11,aSignOutMin12,aSignOutSec13,aSignInTime14,aSignOutTime15,aHoursWorked16,aLearnerComment17);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String aDate3;
        public final String aDay4;
        public final String aDisCord5;
        public final String aLearnerStatus6;
        public final String aLearnerStatusId7;
        public final String aSignInHours8;
        public final String aSignInMin9;
        public final String aSignInSec10;
        public final String aSignOutHours11;
        public final String aSignOutMin12;
        public final String aSignOutSec13;
        public final String aSignInTime14;
        public final String aSignOutTime15;
        public final String aHoursWorked16;
        public final String aLearnerComment17;
  

        public Item(String aPos1,String aId2, String aDate3, String aDay4,String aDisCord5,String aLearnerStatus6, String aLearnerStatusId7, String aSignInHours8, String aSignInMin9, String aSignInSec10, String aSignOutHours11, String aSignOutMin12, String aSignOutSec13, String aSignInTime14, String aSignOutTime15, String aHoursWorked16, String aLearnerComment17 ) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aDate3= aDate3;
            this.aDay4= aDay4;
            this.aDisCord5= aDisCord5;
            this.aLearnerStatus6=aLearnerStatus6;
            this.aLearnerStatusId7=aLearnerStatusId7;
            this.aSignInHours8=aSignInHours8;
            this.aSignInMin9=aSignInMin9;
            this.aSignInSec10=aSignInSec10;
            this.aSignOutHours11=aSignOutHours11;
            this.aSignOutMin12=aSignOutMin12;
            this.aSignOutSec13=aSignOutSec13;
            this.aSignInTime14=aSignInTime14;
            this.aSignOutTime15=aSignOutTime15;
            this.aHoursWorked16=aHoursWorked16;
            this.aLearnerComment17=aLearnerComment17;

        }

        @Override
        public String toString() {
            return aDate3;
        }
    }
}



