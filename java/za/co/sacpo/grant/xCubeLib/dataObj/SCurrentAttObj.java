package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCurrentAttObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int aId2,String aDate3,String aDay4,String aSignIn5,String aSignOut6,String aHoursWorked7,String aAttendanceStatus8,String aDistanceFromWorkstation9,String aCommentLink10,String aSignInColor11,String aSignOutColor12,String aCommentVal13) {
        return new Item(String.valueOf(pos1),aId2,aDate3,aDay4,aSignIn5,aSignOut6,aHoursWorked7,aAttendanceStatus8,aDistanceFromWorkstation9,aCommentLink10,aSignInColor11,aSignOutColor12,aCommentVal13);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aDate3;
        public final String aDay4;
        public final String aSignIn5;
        public final String aSignOut6;
        public final String aHoursWorked7;
        public final String aAttendanceStatus8;
        public final String aDistanceFromWorkstation9;
        public final String aCommentLink10;
        public final String aSignInColor11;
        public final String aSignOutColor12;
        public final String aCommentVal13;

        public Item(String aPos1,int aId2,String aDate3,String aDay4,String aSignIn5,String aSignOut6,String aHoursWorked7,String aAttendanceStatus8,String aDistanceFromWorkstation9,String aCommentLink10,String aSignInColor11,String aSignOutColor12,String aCommentVal13) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aDate3=aDate3;
            this.aDay4=aDay4;
            this.aSignIn5=aSignIn5;
            this.aSignOut6=aSignOut6;
            this.aHoursWorked7=aHoursWorked7;
            this.aAttendanceStatus8=aAttendanceStatus8;
            this.aDistanceFromWorkstation9=aDistanceFromWorkstation9;
            this.aCommentLink10=aCommentLink10;
            this.aSignInColor11=aSignInColor11;
            this.aSignOutColor12=aSignOutColor12;
            this.aCommentVal13=aCommentVal13;
        }

        @Override
        public String toString() {
            return aDate3;
        }
    }
}
