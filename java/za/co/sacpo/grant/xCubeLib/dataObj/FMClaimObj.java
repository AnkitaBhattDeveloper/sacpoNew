package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMClaimObj {
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
    public Item createItem(int pos1,String aId2,String claDate3,String claMonth4,String claDaysWorked5, String claAmount6,
                           String claLeave7,String claAbsent8,String claApprovedBy9, String claSupervisorAppDate10,
                           String claApprovedAmount11, String claApprovedByAdmin12,String claDateOfApproval13,
                           String claStudentId14,String claGrantId15,String claStatus16
            ,String claStuID17  ,String present_type17  ,String leave_type18
            ,String absent_type19 ,String month20,String year21){

        return new Item(String.valueOf(pos1),aId2,claDate3,claMonth4,claDaysWorked5,claAmount6,
                claLeave7,claAbsent8,claApprovedBy9,claSupervisorAppDate10,claApprovedAmount11,claApprovedByAdmin12,claDateOfApproval13,
                claStudentId14,claGrantId15,claStatus16,claStuID17
                ,present_type17 ,leave_type18 ,absent_type19,month20,year21);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String claDate3;
        public final String claMonth4;
        public final String claDaysWorked5;
        public final String claAmount6;
        public final String claLeave7;
        public final String claAbsent8;
        public final String claApprovedBy9;
        public final String claSupervisorAppDate10;
        public final String claApprovedAmount11;
        public final String claApprovedByAdmin12;
        public final String claDateOfApproval13;
        public final String claStudentId14;
        public final String claGrantId15;
        public final String claStatus16;
        public final String claStuID17;

        public final String present_type17;
        public final String leave_type18;
        public final String absent_type19;
        public final String month20;
        public final String year21;

        public Item(String aPos1, String aId2, String claDate3, String claMonth4, String claDaysWorked5,
                    String claAmount6, String claLeave7, String claAbsent8, String claApprovedBy9,
                    String claSupervisorAppDate10, String claApprovedAmount11, String claApprovedByAdmin12, String claDateOfApproval13,
                    String claStudentId14,String claGrantId15,String claStatus16
                ,String claStuID17 ,String present_type17
                ,String leave_type18,String absent_type19  ,String month20,String year21){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.claDate3= claDate3;
            this.claMonth4= claMonth4;
            this.claDaysWorked5= claDaysWorked5;
            this.claAmount6= claAmount6;
            this.claLeave7= claLeave7;
            this.claAbsent8= claAbsent8;
            this.claApprovedBy9=claApprovedBy9;
            this.claSupervisorAppDate10=claSupervisorAppDate10;
            this.claApprovedAmount11=claApprovedAmount11;
            this.claApprovedByAdmin12=claApprovedByAdmin12;
            this.claDateOfApproval13=claDateOfApproval13;
            this.claStudentId14=claStudentId14;
            this.claGrantId15=claGrantId15;
            this.claStatus16=claStatus16;
            this.claStuID17=claStuID17;
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

