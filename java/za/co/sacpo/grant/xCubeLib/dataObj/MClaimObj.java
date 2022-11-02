package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MClaimObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
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
    public Item createItem(int pos1,String aId2,String claMonthVal3,String claYear4,String claMonth5, String claAmount6,String claOutRange7,String claDaysWorked8,String claLeave9, String claDCFLink10,String clasDCFLinkStatus11, String claSDCFLink12,String claSDCFLinkStatus13){

        return new Item(String.valueOf(pos1),aId2,claMonthVal3,claYear4,claMonth5,claAmount6,claOutRange7,claDaysWorked8,claLeave9,claDCFLink10,clasDCFLinkStatus11,claSDCFLink12,claSDCFLinkStatus13);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String claMonthVal3;
        public final String claYear4;
        public final String claMonth5;
        public final String claAmount6;
        public final String claOutRange7;
        public final String claDaysWorked8;
        public final String claLeave9;
        public final String claDCFLink10;
        public final String clasDCFLinkStatus11;
        public final String claSDCFLink12;
        public final String claSDCFLinkStatus13;


        public Item(String aPos1, String aId2, String claMonthVal3, String claYear4, String claMonth5,
                    String claAmount6, String claOutRange7, String claDaysWorked8, String claLeave9,
                    String claDCFLink10, String clasDCFLinkStatus11, String claSDCFLink12, String claSDCFLinkStatus13){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.claMonthVal3= claMonthVal3;
            this.claYear4= claYear4;
            this.claMonth5= claMonth5;
            this.claAmount6= claAmount6;
            this.claOutRange7= claOutRange7;
            this.claDaysWorked8= claDaysWorked8;
            this.claLeave9=claLeave9;
            this.claDCFLink10=claDCFLink10;
            this.clasDCFLinkStatus11=clasDCFLinkStatus11;
            this.claSDCFLink12=claSDCFLink12;
            this.claSDCFLinkStatus13=claSDCFLinkStatus13;
        }

        public String getMonthField_18() {
            return claMonthVal3;
        }
        @Override
        public String toString() {
            return claMonthVal3;
        }
    }
}
