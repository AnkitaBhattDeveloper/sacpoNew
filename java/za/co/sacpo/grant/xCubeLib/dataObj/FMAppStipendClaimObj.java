package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMAppStipendClaimObj {
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
    public Item createItem(int pos1,String aId2,String FMaCYear3,String FMaCMonth4,String FMaCGrantManagerStatus5,String FMaCStipendProcess6,
                           String FMaCNoOfActiveLearner7,String FMaCSubmitted8,String FMaCApprove9,String grant_id10) {
        return new Item(String.valueOf(pos1),aId2,FMaCYear3,FMaCMonth4,FMaCGrantManagerStatus5,FMaCStipendProcess6,FMaCNoOfActiveLearner7,
                FMaCSubmitted8,FMaCApprove9,grant_id10 );
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String FMaCYear3;
        public final String FMaCMonth4;
        public final String FMaCGrantManagerStatus5;
        public final String FMaCStipendProcess6;
        public final String FMaCNoOfActiveLearner7;
        public final String FMaCSubmitted8;
        public final String FMaCApprove9;
        public final String grant_id10;


        public Item(String aPos1, String aId2, String FMaCYear3, String FMaCMonth4, String FMaCGrantManagerStatus5, String FMaCStipendProcess6,
                    String FMaCNoOfActiveLearner7, String FMaCSubmitted8, String FMaCApprove9, String grant_id10) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMaCYear3 = FMaCYear3;
            this.FMaCMonth4 = FMaCMonth4;
            this.FMaCGrantManagerStatus5 = FMaCGrantManagerStatus5;
            this.FMaCStipendProcess6 = FMaCStipendProcess6;
            this.FMaCNoOfActiveLearner7 = FMaCNoOfActiveLearner7;
            this.FMaCSubmitted8 = FMaCSubmitted8;
            this.FMaCApprove9 = FMaCApprove9;
            this.grant_id10 = grant_id10;

        }

        @Override
        public String toString() {
            return FMaCYear3;
        }
    }
}