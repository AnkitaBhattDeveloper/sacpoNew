package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFormsObj {
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
    public Item createItem(int pos1,int aId2,String sFname3,String sFid11,String sFlearner4, String sFhostEmp5,
                           String sFleadEmp6,String sFcomplition7) {
        return new Item(String.valueOf(pos1),aId2,sFname3,sFid11, sFlearner4,sFhostEmp5,sFleadEmp6,sFcomplition7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String sFname3;
        public final String sFid11;
        public final String sFlearner4;
        public final String sFhostEmp5;
        public final String sFleadEmp6;
        public final String sFcomplition7;


        public Item(String aPos1, int aId2, String sFname3,String sFid11, String sFlearner4, String sFhostEmp5, String sFleadEmp6, String sFcomplition7) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.sFname3 = sFname3;
            this.sFid11 = sFid11;
            this.sFlearner4 = sFlearner4;
            this.sFhostEmp5 = sFhostEmp5;
            this.sFleadEmp6 = sFleadEmp6;
            this.sFcomplition7 = sFcomplition7;

        }

        @Override
        public String toString() {
            return sFname3;
        }
    }
}
