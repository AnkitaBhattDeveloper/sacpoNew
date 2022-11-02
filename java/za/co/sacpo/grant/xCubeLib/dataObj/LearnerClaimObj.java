package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearnerClaimObj {


    public List<LearnerClaimObj.Item> ITEMS = new ArrayList<LearnerClaimObj.Item>();
    public Map<String, LearnerClaimObj.Item> ITEM_MAP = new HashMap<String, LearnerClaimObj.Item>();
    public void addItem(LearnerClaimObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<LearnerClaimObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, LearnerClaimObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public LearnerClaimObj.Item createItem(int pos1, String aId2, String claDate3, String claMonth4, String cla_ammount5, String dayswork6,
                                      String leavecount7, String absentcount8, String m_status9, String m_date10,
                                      String m_ammount_status11, String admin_approve_ammount12, String admin_approve_date13,
                                           String claStudentId14,String claGrantId15,String claStatus16, String present_type17, String leave_type18, String absent_type19, String month20,String year21 ){

        return new LearnerClaimObj.Item(String.valueOf(pos1),aId2,claDate3,claMonth4,cla_ammount5,dayswork6,
                leavecount7,absentcount8,m_status9,m_date10,m_ammount_status11,admin_approve_ammount12,admin_approve_date13,
                claStudentId14,claGrantId15,claStatus16 ,present_type17,leave_type18,absent_type19,month20 ,year21);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String claDate3;
        public final String claMonth4;
        public final String cla_ammount5;
        public final String dayswork6;
        public final String leavecount7;
        public final String absentcount8;
        public final String m_status9;
        public final String m_date10;
        public final String m_ammount_status11;
        public final String admin_approve_ammount12;
        public final String admin_approve_date13;

        public final String claStudentId14;
        public final String claGrantId15;
        public final String claStatus16;
        public final String present_type17;
        public final String leave_type18;
        public final String absent_type19;
        public final String month20;
        public final String year21;

            public Item(String aPos1, String aId2, String claDate3, String claMonth4, String cla_ammount5,
                    String dayswork6, String leavecount7, String absentcount8, String m_status9,
                    String m_date10, String m_ammount_status11, String admin_approve_ammount12, String admin_approve_date13,
                    String claStudentId14, String claGrantId15, String claStatus16 , String present_type17 , String leave_type18
                    , String absent_type19  , String month20 , String year21  ){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.claDate3= claDate3;
            this.claMonth4= claMonth4;
            this.cla_ammount5= cla_ammount5;
            this.dayswork6= dayswork6;
            this.leavecount7= leavecount7;
            this.absentcount8= absentcount8;
            this.m_status9=m_status9;
            this.m_date10=m_date10;
            this.m_ammount_status11=m_ammount_status11;
            this.admin_approve_ammount12=admin_approve_ammount12;
            this.admin_approve_date13=admin_approve_date13;
            this.claStudentId14=claStudentId14;
            this.claGrantId15=claGrantId15;
            this.claStatus16=claStatus16;
            this.present_type17=present_type17;
            this.leave_type18=leave_type18;
            this.absent_type19=absent_type19;
            this.month20=month20;
            this.year21=year21;


        }

        @Override
        public String toString() {
            return claDate3;
        }
    }
}



