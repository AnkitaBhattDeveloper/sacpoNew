package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAttObj {
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
    public Item createItem(int pos1,int aId2,String aLiDate3,String aLiTime4,String aLoDate5, String aLoTime6,String aTimeSpent7,String aStatus8,
                           String aDay9,String aStatusShort10,String aViewComments11,String aMonth_year12) {
        return new Item(String.valueOf(pos1),aId2,aLiDate3,aLiTime4,aLoDate5,aLoTime6,aTimeSpent7,aStatus8,aDay9,aStatusShort10,aViewComments11,aMonth_year12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aLiDate3;
        public final String aLiTime4;
        public final String aLoDate5;
        public final String aLoTime6;
        public final String aTimeSpent7;
        public final String aStatus8;
        public final String aDay9;
        public final String aStatusShort10;
        public final String aViewComments11;
        public final String aMonth_year12;

        public Item(String aPos1,int aId2, String aLiDate3, String aLiTime4,String aLoDate5,String aLoTime6,String aTimeSpent7,String aStatus8,
                    String aDay9,String aStatusShort10,String aViewComments11,String aMonth_year12) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aLiDate3= aLiDate3;
            this.aLiTime4= aLiTime4;
            this.aLoDate5= aLoDate5;
            this.aLoTime6= aLoTime6;
            this.aTimeSpent7= aTimeSpent7;
            this.aStatus8= aStatus8;
            this.aDay9=aDay9;
            this.aStatusShort10=aStatusShort10;
            this.aViewComments11=aViewComments11;
            this.aMonth_year12=aMonth_year12;
        }

        @Override
        public String toString() {
            return aLiDate3;
        }
    }
}
