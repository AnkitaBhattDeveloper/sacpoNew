package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMTranchesObj {

    public List<FMTranchesObj.Item> ITEMS = new ArrayList<FMTranchesObj.Item>();
    public Map<Integer, FMTranchesObj.Item> ITEM_MAP = new HashMap<Integer, FMTranchesObj.Item>();
    public void addItem(FMTranchesObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<FMTranchesObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, FMTranchesObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public FMTranchesObj.Item createItem(int pos1, int aId2, String GASetaName3, String GASetaID4, String SM_tranchesDetails5 ) {
        return new FMTranchesObj.Item(String.valueOf(pos1),aId2,GASetaName3,GASetaID4,SM_tranchesDetails5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GASetaName3;
        public final String GASetaID4;
        public final String SM_tranchesDetails5;



        public Item(String aPos1, int aId2, String GASetaName3, String GASetaID4, String SM_tranchesDetails5) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GASetaName3 = GASetaName3;
            this.GASetaID4 = GASetaID4;
            this.SM_tranchesDetails5 = SM_tranchesDetails5;

        }

        @Override
        public String toString() {
            return GASetaName3;
        }
    }
}



