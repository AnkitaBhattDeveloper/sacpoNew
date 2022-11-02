package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MStuListObj {
    public  List<MStuListObj.Item> ITEMS = new ArrayList<MStuListObj.Item>();
    public  Map<Integer, MStuListObj.Item> ITEM_MAP = new HashMap<Integer, MStuListObj.Item>();
    public void addItem(MStuListObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MStuListObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MStuListObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MStuListObj.Item createItem(int pos1, int aId2,String notes3,String lname4,String lstatus5,String sdate6,String edate6,String seta7,String le8,String patt9,String catt10,String leave11,String lpending12,String pstipend13,String stpending14,String workx15,String workasl16,String reports18,String rpending19,String tp20,String doc21,String grantid22,String alertcount23,String isSPending24,String isWorkxPending25,String isWorkXassingPending26,String isRpending27,String isTpPending28) {
        return new MStuListObj.Item(String.valueOf(pos1),aId2,notes3,lname4,lstatus5,sdate6,edate6,seta7,le8,patt9,catt10,leave11,lpending12,pstipend13,stpending14,workx15,workasl16,reports18,rpending19,tp20,doc21,grantid22,alertcount23,isSPending24,isWorkxPending25,isWorkXassingPending26,isRpending27,isTpPending28);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String notes3;
        public final String lname4;
        public final String lstatus5;
        public final String sdate6;
        public final String edate6;
        public final String seta7;
        public final String le8;
        public final String patt9;
        public final String catt10;
        public final String leave11;
        public final String lpending12;
        public final String pstipend13;
        public final String stpending14;
        public final String workx15;
        public final String workasl16;
        public final String reports18;
        public final String rpending19;
        public final String tp20;
        public final String doc21;
        public final String grantid22;
        public final String alertcount23;
        public final String isSPending24;
        public final String isWorkxPending25;
        public final String isWorkXassingPending26;
        public final String isRpending27;
        public final String isTpPending28;

        public Item(String aPos1, int aId2, String notes3,String lname4,String lstatus5,String sdate6,String edate6,String seta7,String le8,String patt9,String catt10,String leave11,String lpending12,String pstipend13,String stpending14,String workx15,String workasl16,String reports18,String rpending19,String tp20,String doc21,String grantid22,String alertcount23,String isSPending24,String isWorkxPending25,String isWorkXassingPending26,String isRpending27,String isTpPending28) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.notes3=notes3;
            this.lname4=lname4;
            this.lstatus5=lstatus5;
            this.sdate6=sdate6;
            this.edate6=edate6;
            this.seta7=seta7;
            this.le8=le8;
            this.patt9=patt9;
            this.catt10=catt10;
            this.leave11=leave11;
            this.lpending12=lpending12;
            this.pstipend13=pstipend13;
            this.stpending14=stpending14;
            this.workx15=workx15;
            this.workasl16=workasl16;
            this.reports18=reports18;
            this.rpending19=rpending19;
            this.tp20=tp20;
            this.doc21=doc21;
            this.grantid22=grantid22;
            this.alertcount23=alertcount23;
            this.isSPending24=isSPending24;
            this.isWorkxPending25=isWorkxPending25;
            this.isWorkXassingPending26=isWorkXassingPending26;
            this.isRpending27=isRpending27;
            this.isTpPending28=isTpPending28;
        }

        @Override
        public String toString() {
            return lname4;
        }
    }
}
