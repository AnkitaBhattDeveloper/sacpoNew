package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDAttObj {
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
    public Item createItem(int pos1,int aId2,String aMonth3,String aYear4,String aCount5,String aALeaveCount6,String aSLeaveCount7,String aOPaidLeaveCount8,String aUPaidLeaveCount9,String aSupervisorStatus10,String aSupervisorComment11,String aDownloadReg12,String aDownloadLink13,String aCommentLink14) {
        return new Item(String.valueOf(pos1),aId2,aMonth3,aYear4,aCount5,aALeaveCount6, aSLeaveCount7, aOPaidLeaveCount8, aUPaidLeaveCount9, aSupervisorStatus10, aSupervisorComment11, aDownloadReg12,aDownloadLink13,aCommentLink14);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aMonth3;
        public final String aYear4;
        public final String aCount5;
        public final String aALeaveCount6;
        public final String aSLeaveCount7;
        public final String aOPaidLeaveCount8;
        public final String aUPaidLeaveCount9;
        public final String aSupervisorStatus10;
        public final String aSupervisorComment11;
        public final String aDownloadReg12;
        public final String aDownloadLink13;
        public final String aCommentLink14;

        public Item(String aPos1, int aId2, String aMonth3, String aYear4,String aCount5,String aALeaveCount6,String aSLeaveCount7,String aOPaidLeaveCount8,String aUPaidLeaveCount9,String aSupervisorStatus10,String aSupervisorComment11,String aDownloadReg12,String aDownloadLink13,String aCommentLink14) {
           this.aPos1 = aPos1;
           this.aId2 = aId2;
           this.aMonth3 = aMonth3;
           this.aYear4 = aYear4;
           this.aCount5 = aCount5;
           this.aALeaveCount6=aALeaveCount6;
           this.aSLeaveCount7=aSLeaveCount7;
           this.aOPaidLeaveCount8=aOPaidLeaveCount8;
           this.aUPaidLeaveCount9=aUPaidLeaveCount9;
           this.aSupervisorStatus10=aSupervisorStatus10;
           this.aSupervisorComment11=aSupervisorComment11;
           this.aDownloadReg12=aDownloadReg12;
            this.aDownloadLink13=aDownloadLink13;
            this.aCommentLink14=aCommentLink14;
        }

        @Override
        public String toString() {
            return aMonth3;
        }
    }
}