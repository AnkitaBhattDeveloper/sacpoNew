package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyObj {

    public  List<SurveyItem> SurveyItemS = new ArrayList<SurveyItem>();
    public Map<String, SurveyItem> SurveyItem_MAP = new HashMap<String, SurveyItem>();
    public void addSurveyItem(SurveyItem SurveyItem) {
        this.SurveyItemS.add(SurveyItem);
        this.SurveyItem_MAP.put(SurveyItem.aId2, SurveyItem);
    }
    public List<SurveyItem> getSurveyItemS(){
        return this.SurveyItemS;
    }
    public Map<String, SurveyItem> getSurveyItem_MAP(){
        return this.SurveyItem_MAP;
    }
    public SurveyItem createSurveyItem(String pos1,String aId2,String SQuesType3,String SSUrveyId4,String SOption5,String SQuestion6,String SANS_T_TYPE7,String SANS_TYPE_ID8,String SANS_TYPE_ID9,String SANS_TYPE_ID10,String SANS_TYPE_ID11,String SANS_TYPE_ID12,String SANS_TYPE_ID13,String SANS_TYPE_ID14,String SANS_TYPE_ID15,
                                       String SANS_TYPE_ID16,String SANS_TYPE_ID17) {
        return new SurveyItem(String.valueOf(pos1),aId2,SQuesType3,SSUrveyId4,SOption5,SQuestion6,SANS_T_TYPE7,SANS_TYPE_ID8,SANS_TYPE_ID9,SANS_TYPE_ID10,SANS_TYPE_ID11,SANS_TYPE_ID12,SANS_TYPE_ID13,SANS_TYPE_ID14,
                SANS_TYPE_ID15,SANS_TYPE_ID16,SANS_TYPE_ID17);
    }



    public static class SurveyItem {
        public final String aPos1;
        public final String aId2;
        public final String SQuesType3;
        public final String SSUrveyId4;
        public final String SOption5;
        public final String SQuestion6;

        public final String SANS_T_TYPE7;
        public final String SANS_TYPE_ID8;
        public final String SANS_TYPE_ID9;
        public final String SANS_TYPE_ID10;
        public final String SANS_TYPE_ID11;
        public final String SANS_TYPE_ID12;
        public final String SANS_TYPE_ID13;
        public final String SANS_TYPE_ID14;
        public final String SANS_TYPE_ID15;
        public final String SANS_TYPE_ID16;
        public final String SANS_TYPE_ID17;


        public SurveyItem(String aPos1, String aId2, String SQuesType3, String SSUrveyId4,String SOption5,String SQuestion6,String SANS_T_TYPE7,
                          String SANS_TYPE_ID8,String SANS_TYPE_ID9,String SANS_TYPE_ID10,String SANS_TYPE_ID11,String SANS_TYPE_ID12,
                          String SANS_TYPE_ID13,String SANS_TYPE_ID14,String SANS_TYPE_ID15,String SANS_TYPE_ID16,String SANS_TYPE_ID17) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SQuesType3 = SQuesType3;
            this.SSUrveyId4 = SSUrveyId4;
            this.SOption5 = SOption5;
            this.SQuestion6 = SQuestion6;
            this.SANS_T_TYPE7 = SANS_T_TYPE7;
            this.SANS_TYPE_ID8 = SANS_TYPE_ID8;
            this.SANS_TYPE_ID9 = SANS_TYPE_ID9;
            this.SANS_TYPE_ID10 = SANS_TYPE_ID10;
            this.SANS_TYPE_ID11 = SANS_TYPE_ID11;
            this.SANS_TYPE_ID12 = SANS_TYPE_ID12;
            this.SANS_TYPE_ID13 = SANS_TYPE_ID13;
            this.SANS_TYPE_ID14 = SANS_TYPE_ID14;
            this.SANS_TYPE_ID15 = SANS_TYPE_ID15;
            this.SANS_TYPE_ID16 = SANS_TYPE_ID16;
            this.SANS_TYPE_ID17 = SANS_TYPE_ID17;


        }

        @Override
        public String toString() {
            return SSUrveyId4;
        }
    }
}


