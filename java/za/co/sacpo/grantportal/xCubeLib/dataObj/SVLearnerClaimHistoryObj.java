package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVLearnerClaimHistoryObj {
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
    public Item createItem(int pos1,int aId2,String GMMonth3,String GMYear4,String GMStipendClaim5) {

        return new Item(String.valueOf(pos1),aId2,GMMonth3,GMYear4,GMStipendClaim5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GMMonth3;
        public final String GMYear4;
        public final String GMStipendClaim5;



        public Item(String aPos1, int aId2, String GMMonth3, String GMYear4, String GMStipendClaim5) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMMonth3 = GMMonth3;
            this.GMYear4 = GMYear4;
            this.GMStipendClaim5 = GMStipendClaim5;


        }

        @Override
        public String toString() {
            return GMMonth3;
        }
    }
}