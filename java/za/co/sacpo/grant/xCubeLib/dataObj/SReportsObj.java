package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SReportsObj {
    public  List<SReportsObj.Item> ITEMS = new ArrayList<SReportsObj.Item>();
    public  Map<Integer, SReportsObj.Item> ITEM_MAP = new HashMap<Integer, SReportsObj.Item>();
    public void addItem(SReportsObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SReportsObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SReportsObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SReportsObj.Item createItem(int pos1, int aId2, String msfReportTitle3, String msfYear4, String msupervisorStatus5, String mReportN6,String misSupervisorApproved7,String month8) {
        return new SReportsObj.Item(String.valueOf(pos1),aId2,msfReportTitle3,msfYear4,msupervisorStatus5,mReportN6,misSupervisorApproved7,month8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String msfReportTitle3;
        public final String msfYear4;
        public final String msupervisorStatus5;
        public final String mReportN6;
        public final String misSupervisorApproved7;
        public final String month8;

        public Item(String aPos1, int aId2, String msfReportTitle3,String msfYear4, String msupervisorStatus5, String mReportN6, String misSupervisorApproved7, String month8) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.msfReportTitle3 = msfReportTitle3;
            this.msfYear4 = msfYear4;
            this.msupervisorStatus5 = msupervisorStatus5;
            this.mReportN6 = mReportN6;
            this.misSupervisorApproved7 =  misSupervisorApproved7;
            this.month8 =  month8;
        }

        @Override
        public String toString() {
            return msfReportTitle3;
        }
    }
}
