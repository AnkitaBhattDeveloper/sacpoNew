package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GASuperStuObj {

    public List<GASuperStuObj.Item> ITEMS = new ArrayList<GASuperStuObj.Item>();
    public Map<Integer, GASuperStuObj.Item> ITEM_MAP = new HashMap<Integer, GASuperStuObj.Item>();
    public void addItem(GASuperStuObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GASuperStuObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GASuperStuObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GASuperStuObj.Item createItem(int pos1, int aId2,String PrReport3, String Chat4, String learner5, String supervisor6, String attCount7, String leaveCount8,String stipendapproval9,String mentorId10,String student_id11) {
        return new GASuperStuObj.Item(String.valueOf(pos1),aId2,PrReport3,Chat4,learner5,supervisor6,attCount7,leaveCount8,stipendapproval9,mentorId10,student_id11);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String PrReport3;
        public final String Chat4;
        public final String learner5;
        public final String supervisor6;
        public final String attCount7;
        public final String leaveCount8;
        public final String stipendapproval9;
        public final String mentorId10;
        public final String student_id11;


        public Item(String aPos1, int aId2, String PrReport3, String Chat4, String learner5, String supervisor6, String attCount7, String leaveCount8, String stipendapproval9, String mentorId10,String student_id11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.PrReport3 = PrReport3;

            this.Chat4 = Chat4;
            this.learner5 = learner5;
            this.supervisor6 = supervisor6;
            this.attCount7 = attCount7;
            this.leaveCount8 = leaveCount8;

            this.stipendapproval9 = stipendapproval9;
            this.mentorId10 = mentorId10;
            this.student_id11 = student_id11;

        }

        @Override
        public String toString() {
            return Chat4;
        }
    }
}



