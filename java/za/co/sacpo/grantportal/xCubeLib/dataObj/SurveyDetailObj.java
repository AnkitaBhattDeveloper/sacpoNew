package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyDetailObj {

    public  List<SurveyDetailItem> SurveyDetailItemS = new ArrayList<SurveyDetailItem>();
    public Map<String, SurveyDetailItem> SurveyDetailItem_MAP = new HashMap<String, SurveyDetailItem>();
    public void addSurveyDetailItem(SurveyDetailItem SurveyDetailItem) {
        this.SurveyDetailItemS.add(SurveyDetailItem);
        this.SurveyDetailItem_MAP.put(SurveyDetailItem.aId2, SurveyDetailItem);
    }
    public List<SurveyDetailItem> getSurveyDetailItemS(){
        return this.SurveyDetailItemS;
    }
    public Map<String, SurveyDetailItem> getSurveyDetailItem_MAP(){
        return this.SurveyDetailItem_MAP;
    }
    public SurveyDetailItem createSurveyDetailItem(String pos1,String aId2,String SQuesType3,String SQues_Ans4,String SOption5,String SQuestion6) {
        return new SurveyDetailItem(String.valueOf(pos1),aId2,SQuesType3,SQues_Ans4,SOption5,SQuestion6);
    }



    public static class SurveyDetailItem {
        public final String aPos1;
        public final String aId2;
        public final String SQuesType3;
        public final String SQues_Ans4;
        public final String SOption5;
        public final String SQuestion6;


      


        public SurveyDetailItem(String aPos1, String aId2, String SQuesType3, String SQues_Ans4,String SOption5,String SQuestion6) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.SQuesType3 = SQuesType3;
            this.SQues_Ans4 = SQues_Ans4;
            this.SOption5 = SOption5;
            this.SQuestion6 = SQuestion6;

           


        }

        @Override
        public String toString() {
            return SQues_Ans4;
        }
    }
}


