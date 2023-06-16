package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GAIssuesObj {

    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
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
    public Item createItem(int pos1,int aId2,String GAstatus3,String GATicetNo4,
                  String GAReportedBy5, String GAComments6,String GATitle7,String GAEmail8,
                  String GARolle9  ,String GAPariority10,String GAStart11,String GAResolve12) {
        return new Item(String.valueOf(pos1),aId2,GAstatus3,GATicetNo4,GAReportedBy5,GAComments6
       ,GATitle7 ,GAEmail8,GARolle9,GAPariority10,GAStart11,GAResolve12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String GAstatus3;
        public final String GATicetNo4;
        public final String GAReportedBy5;
        public final String GAComments6;
        public final String GATitle7;
        public final String GAEmail8;
        public final String GARolle9;
        public final String GAPariority10;
        public final String GAStart11;
        public final String GAResolve12;

        public Item(String aPos1, int aId2, String GAstatus3, String GATicetNo4, String GAReportedBy5,String GAComments6, String GATitle7,
                    String GAEmail8, String GARolle9, String GAPariority10, String GAStart11, String GAResolve12) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GAstatus3 = GAstatus3;
            this.GATicetNo4 = GATicetNo4;
            this.GAReportedBy5 = GAReportedBy5;
            this.GAComments6 = GAComments6;
            this.GATitle7 = GATitle7;
            this.GAEmail8 = GAEmail8;
            this.GARolle9 = GARolle9;
            this.GAPariority10 = GAPariority10;
            this.GAStart11 = GAStart11;
            this.GAResolve12 = GAResolve12;
          
        }

        @Override
        public String toString() {
            return GAstatus3;
            


        }
    }
}