package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLeavesListObj {

    public List<MLeavesListObj.Item> ITEMS = new ArrayList<MLeavesListObj.Item>();
    public Map<Integer, MLeavesListObj.Item> ITEM_MAP = new HashMap<Integer, MLeavesListObj.Item>();
    public void addItem(MLeavesListObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MLeavesListObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MLeavesListObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MLeavesListObj.Item createItem(int pos1, int aId2, String tal3,String tsl4,String topl5,String tul6,String pal7,String psl8,String popl9,String pul10) {
        return new MLeavesListObj.Item(String.valueOf(pos1),aId2,tal3,tsl4,topl5,tul6,pal7,psl8,popl9,pul10);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String tal3;
        public final String tsl4;
        public final String topl5;
        public final String tul6;
        public final String pal7;
        public final String psl8;
        public final String popl9;
        public final String pul10;


        public Item(String aPos1, int aId2, String tal3,String tsl4,String topl5,String tul6,String pal7,String psl8,String popl9,String pul10) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.tal3 = tal3;
            this.tsl4 = tsl4;
            this.topl5 = topl5;
            this.tul6 = tul6;
            this.pal7 = pal7;
            this.psl8 = psl8;
            this.popl9 = popl9;
            this.pul10 = pul10;
        }

        @Override
        public String toString() {
            return tal3;
        }
    }
}


