package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GMLearnerListObj {

    public List<GMLearnerListObj.Item> ITEMS = new ArrayList<GMLearnerListObj.Item>();
    public Map<Integer, GMLearnerListObj.Item> ITEM_MAP = new HashMap<Integer, GMLearnerListObj.Item>();
    public void addItem(GMLearnerListObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GMLearnerListObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, GMLearnerListObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GMLearnerListObj.Item createItem(int pos1, int aId2, String PrReport3,
                                            String g_learner_name4, String g_learner_status5, String g_learnerEmail6,
                                            String gLearId7, String gCurrentStatus8, String g_emp_name9,
                                            String g_sdl_no10, String gsupervisor_name11, String gDuration12, String gSDate13
            , String gEDate14, String g_setaAllocation15, String gclaimed16, String g_unspent17
          , String g_student_id18, String g_stipend_id_19, String mentor_id20,String GMEmpId20) {
        return new GMLearnerListObj.Item(String.valueOf(pos1),aId2,PrReport3,
                g_learner_name4,g_learner_status5,g_learnerEmail6,gLearId7,gCurrentStatus8,
                g_emp_name9,g_sdl_no10,gsupervisor_name11,gDuration12,
                gSDate13,gEDate14,g_setaAllocation15,
                gclaimed16,g_unspent17,
        g_student_id18,g_stipend_id_19,
                mentor_id20  , GMEmpId20);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String PrReport3;
        public final String g_learner_name4;
        public final String g_learner_status5;
        public final String g_learnerEmail6;
        public final String gLearId7;
        public final String gCurrentStatus8;
        public final String g_emp_name9;
        public final String g_sdl_no10;
        public final String gsupervisor_name11;
        public final String gDuration12;
        public final String gSDate13;
        public final String gEDate14;
        public final String g_setaAllocation15;
        public final String gclaimed16;
        public final String g_unspent17;
        public final String g_student_id18;
        public final String g_stipend_id_19;
        public final String mentor_id20;
        public final String GMEmpId20;

        public Item(String aPos1, int aId2, String PrReport3, String g_learner_name4, String g_learner_status5, String g_learnerEmail6, String gLearId7, String gCurrentStatus8, String g_emp_name9,
                    String g_sdl_no10,String gsupervisor_name11
                ,String gDuration12,String gSDate13,String gEDate14,String g_setaAllocation15,String gclaimed16,String g_unspent17,String g_student_id18,String g_stipend_id_19,String mentor_id20,String GMEmpId20) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.PrReport3 = PrReport3;

            this.g_learner_name4 = g_learner_name4;
            this.g_learner_status5 = g_learner_status5;
            this.g_learnerEmail6 = g_learnerEmail6;
            this.gLearId7 = gLearId7;
            this.gCurrentStatus8 = gCurrentStatus8;

            this.g_emp_name9 = g_emp_name9;
            this.g_sdl_no10 = g_sdl_no10;
            this.gsupervisor_name11 = gsupervisor_name11;
            this.gDuration12 = gDuration12;
            this.gSDate13 = gSDate13;
            this.gEDate14 = gEDate14;
            this.g_setaAllocation15 = g_setaAllocation15;
            this.gclaimed16 = gclaimed16;
            this.g_unspent17 = g_unspent17;
            this.g_student_id18 = g_student_id18;
            this.g_stipend_id_19 = g_stipend_id_19;
            this.mentor_id20 = mentor_id20;
            this.GMEmpId20 = GMEmpId20;





        }

        @Override
        public String toString() {
            return PrReport3;
        }
    }
}


