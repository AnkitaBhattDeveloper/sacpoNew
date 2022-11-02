package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyDetailListObj {

    public  List<SurveyDetailListItem> SurveyDetailListItemS = new ArrayList<SurveyDetailListItem>();
    public Map<Integer, SurveyDetailListItem> SurveyDetailListItem_MAP = new HashMap<>();
    public void addSurveyDetailListItem(SurveyDetailListItem SurveyDetailListItem) {
        this.SurveyDetailListItemS.add(SurveyDetailListItem);
        this.SurveyDetailListItem_MAP.put(SurveyDetailListItem.aId2, SurveyDetailListItem);
    }
    public List<SurveyDetailListItem> getSurveyDetailListItemS(){
        return this.SurveyDetailListItemS;
    }
    public Map<Integer, SurveyDetailListItem> getSurveyDetailListItem_MAP(){
        return this.SurveyDetailListItem_MAP;
    }
    public SurveyDetailListItem createSurveyDetailListItem(String pos1,int aId2,String SurveyName3,String S_Month4,String S_Year5,String S_Month_num6) {
        return new SurveyDetailListItem(String.valueOf(pos1),aId2,SurveyName3,S_Month4,S_Year5,S_Month_num6);
    }



    public static class SurveyDetailListItem {
        public final String aPos1;
        public final int aId2;
        public final String SurveyName3;
        public final String S_Month4;
        public final String S_Year5;
        public final String S_Month_num6;




        public SurveyDetailListItem(String aPos1, int aId2, String SurveyName3, String S_Month4,String S_Year5,String S_Month_num6) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SurveyName3 = SurveyName3;
            this.S_Month4 = S_Month4;
            this.S_Year5 = S_Year5;
            this.S_Month_num6 = S_Month_num6;



        }

        @Override
        public String toString() {
            return S_Month4;
        }
    }
}


