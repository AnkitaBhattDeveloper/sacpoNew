package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MSAttObj {

    public List<MSAttObj.Item> ITEMS = new ArrayList<MSAttObj.Item>();
    public Map<Integer, MSAttObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(MSAttObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MSAttObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MSAttObj.Item createItem(int pos1, int aId2, String month3,String year4,String daysWorked5,String annualLeave6,String sickLeave7,String oPL8,String uPL9,String yearMonth10,String student_id11) {
        return new MSAttObj.Item(String.valueOf(pos1),aId2,month3,year4,daysWorked5,annualLeave6,sickLeave7,oPL8,uPL9,yearMonth10,student_id11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String month3;
        public final String year4;
        public final String daysWorked5;
        public final String annualLeave6;
        public final String sickLeave7;
        public final String oPL8;
        public final String uPL9;
        public final String yearMonth10;
        public final String student_id11;


        public Item(String aPos1, int aId2,String month3,String year4,String daysWorked5,String annualLeave6,String sickLeave7,String oPL8,String uPL9,String yearMonth10,String student_id11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.month3=month3;
            this.year4=year4;
            this.daysWorked5=daysWorked5;
            this.annualLeave6=annualLeave6;
            this.sickLeave7=sickLeave7;
            this.oPL8=oPL8;
            this.uPL9=uPL9;
            this.yearMonth10=yearMonth10;
            this.student_id11=student_id11;
        }

        @Override
        public String toString() {
            return month3;
        }
    }
    }
