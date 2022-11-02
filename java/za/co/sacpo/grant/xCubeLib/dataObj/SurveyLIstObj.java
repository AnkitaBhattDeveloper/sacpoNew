package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyLIstObj {

    public List<SurveyLIstObj.Item> ITEMS = new ArrayList<SurveyLIstObj.Item>();
    public Map<Integer, SurveyLIstObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(SurveyLIstObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SurveyLIstObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SurveyLIstObj.Item createItem(int pos1, int aId2, String SurveyName3, String SurveyStatus4, String SurveyId5) {
        return new SurveyLIstObj.Item(String.valueOf(pos1),aId2,SurveyName3,SurveyStatus4,SurveyId5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String SurveyName3;
        public final String SurveyStatus4;
        public final String SurveyId5;


        public Item(String aPos1, int aId2, String SurveyName3, String SurveyStatus4, String SurveyId5) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SurveyName3 = SurveyName3;
            this.SurveyStatus4 = SurveyStatus4;
            this.SurveyId5 = SurveyId5;

        }

        @Override
        public String toString() {
            return SurveyName3;
        }
    }
    }
