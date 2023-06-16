package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GAProcessStipendClaimObj {
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
    public Item createItem(int pos1,String aId2,String GMaCYear3,String GMaCMonth4,String GMaCStipendProcess5,String GMaCActiveLearner6, String GMaCSubmit7, String GMaCApproved8,String GMaCBankApproval9,String grant_id10) {
        return new Item(String.valueOf(pos1),aId2,GMaCYear3,GMaCMonth4,GMaCStipendProcess5,GMaCActiveLearner6,GMaCSubmit7,GMaCApproved8,GMaCBankApproval9,grant_id10);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String GMaCYear3;
        public final String GMaCMonth4;
        public final String GMaCStipendProcess5;
        public final String GMaCActiveLearner6;
        public final String GMaCSubmit7;
        public final String GMaCApproved8;
        public final String GMaCBankApproval9;
        public final String grant_id10;


        public Item(String aPos1, String aId2, String GMaCYear3, String GMaCMonth4, String GMaCStipendProcess5, String GMaCActiveLearner6,String GMaCSubmit7,String GMaCApproved8,String GMaCBankApproval9,String grant_id10) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.GMaCYear3 = GMaCYear3;
            this.GMaCMonth4 = GMaCMonth4;
            this.GMaCStipendProcess5 = GMaCStipendProcess5;
            this.GMaCActiveLearner6 = GMaCActiveLearner6;
            this.GMaCSubmit7 = GMaCSubmit7;
            this.GMaCApproved8 = GMaCApproved8;
            this.GMaCBankApproval9 = GMaCBankApproval9;
            this.grant_id10 = grant_id10;

        }

        @Override
        public String toString() {
            return GMaCYear3;
        }
    }
}