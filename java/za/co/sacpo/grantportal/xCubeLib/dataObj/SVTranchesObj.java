package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVTranchesObj {
    public List<SVTranchesObj.Item> ITEMS = new ArrayList<SVTranchesObj.Item>();
    public Map<Integer, SVTranchesObj.Item> ITEM_MAP = new HashMap<Integer, SVTranchesObj.Item>();
    public void addItem(SVTranchesObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<SVTranchesObj.Item> getITEMS(){
        return this.ITEMS;
    }

    public Map<Integer, SVTranchesObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SVTranchesObj.Item createItem(int pos1, int aId2, String GASetaName3, String GASetaID4 , String GATrancheRecieveStatus5, String GA_Grantid6) {
        return new SVTranchesObj.Item(String.valueOf(pos1),aId2,GASetaName3,GASetaID4,GATrancheRecieveStatus5,GA_Grantid6);
    }


    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GASetaName3;
        public final String GASetaID4;
        public final String GATrancheRecieveStatus5;
        public final String GA_Grantid6;


        public Item(String aPos1, int aId2, String GASetaName3, String GASetaID4,String GATrancheRecieveStatus5,String GA_Grantid6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GASetaName3 = GASetaName3;
            this.GASetaID4 = GASetaID4;
            this.GATrancheRecieveStatus5 = GATrancheRecieveStatus5;
            this.GA_Grantid6 = GA_Grantid6;

        }

        @Override
        public String toString() {
            return GASetaName3;
        }
    }
}

