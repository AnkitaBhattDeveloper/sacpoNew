package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLeavesObj {

    public List<MLeavesObj.Item> ITEMS = new ArrayList<MLeavesObj.Item>();
    public Map<Integer, MLeavesObj.Item> ITEM_MAP = new HashMap<Integer, MLeavesObj.Item>();
    public void addItem(MLeavesObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MLeavesObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MLeavesObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MLeavesObj.Item createItem(int pos1, int aId2, String month3,String fromDate4,String toDate5,String annualLeave6,String sickLeave7,String oPaidLeave8,String uPLeave9,String notesLeave10,String attIds11) {
        return new MLeavesObj.Item(String.valueOf(pos1),aId2,month3,fromDate4,toDate5,annualLeave6,sickLeave7,oPaidLeave8,uPLeave9,notesLeave10,attIds11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String month3;
        public final String fromDate4;
        public final String toDate5;
        public final String annualLeave6;
        public final String sickLeave7;
        public final String oPaidLeave8;
        public final String uPLeave9;
        public final String notesLeave10;
        public final String attIds11;


        public Item(String aPos1, int aId2,String month3,String fromDate4,String toDate5,String annualLeave6,String sickLeave7,String oPaidLeave8,String uPLeave9,String notesLeave10,String attIds11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.month3=month3;
            this.fromDate4=fromDate4;
            this.toDate5=toDate5;
            this.annualLeave6=annualLeave6;
            this.sickLeave7=sickLeave7;
            this.oPaidLeave8=oPaidLeave8;
            this.uPLeave9=uPLeave9;
            this.notesLeave10=notesLeave10;
            this.attIds11=attIds11;
        }

        @Override
        public String toString() {
            return month3;
        }
    }
}


