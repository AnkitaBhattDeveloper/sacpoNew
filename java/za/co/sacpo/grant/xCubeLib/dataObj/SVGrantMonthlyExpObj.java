package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVGrantMonthlyExpObj {
    public List<SVGrantMonthlyExpObj.Item> ITEMS = new ArrayList<SVGrantMonthlyExpObj.Item>();
    public Map<Integer, SVGrantMonthlyExpObj.Item> ITEM_MAP = new HashMap<Integer, SVGrantMonthlyExpObj.Item>();
    public void addItem(SVGrantMonthlyExpObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<SVGrantMonthlyExpObj.Item> getITEMS(){
        return this.ITEMS;
    }

    public Map<Integer, SVGrantMonthlyExpObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SVGrantMonthlyExpObj.Item createItem(int pos1, int aId2, String SVYear3, String SVMonth4 , String SVTotalExp5,
                                                String SVGrant_id6,String SVMonthNo7) {
        return new SVGrantMonthlyExpObj.Item(String.valueOf(pos1),aId2,SVYear3,SVMonth4,SVTotalExp5,SVGrant_id6,SVMonthNo7);
    }


    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String SVYear3;
        public final String SVMonth4;
        public final String SVTotalExp5;
        public final String SVGrant_id6;
        public final String SVMonthNo7;


        public Item(String aPos1, int aId2, String SVYear3, String SVMonth4,String SVTotalExp5,String SVGrant_id6,String SVMonthNo7) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SVYear3 = SVYear3;
            this.SVMonth4 = SVMonth4;
            this.SVTotalExp5 = SVTotalExp5;
            this.SVGrant_id6 = SVGrant_id6;
            this.SVMonthNo7 = SVMonthNo7;

        }

        @Override
        public String toString() {
            return SVYear3;
        }
    }
}

