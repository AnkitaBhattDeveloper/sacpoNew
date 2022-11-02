package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MWorkXObj {
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
    public Item createItem(int pos1,int aId2,String mWEmpName3,String mWWorkstation4,String mWStuent5, String mWLatitude6,String mWLongitude7,String mWbtnEdit8,String mWbtnRemove9) {
        return new Item(String.valueOf(pos1),aId2,mWEmpName3,mWWorkstation4,mWStuent5,mWLatitude6,mWLongitude7,mWbtnEdit8,mWbtnRemove9);
    }
        public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mWEmpName3;
        public final String mWWorkstation4;
        public final String mWStuent5;
        public final String mWLatitude6;
        public final String mWLongitude7;
        public final String mWbtnEdit8;
        public final String mWbtnRemove9;

        public Item(String aPos1, int aId2, String mWEmpName3, String mWWorkstation4, String mWStuent5, String mWLatitude6, String mWLongitude7, String mWbtnEdit8, String mWbtnRemove9) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mWEmpName3 = mWEmpName3;
            this.mWWorkstation4 = mWWorkstation4;
            this.mWStuent5 = mWStuent5;
            this.mWLatitude6 = mWLatitude6;
            this.mWLongitude7 = mWLongitude7;
            this.mWbtnEdit8 = mWbtnEdit8;
            this.mWbtnRemove9 = mWbtnRemove9;
        }

        @Override
        public String toString()
        {
            return mWEmpName3;
        }
    }
}
