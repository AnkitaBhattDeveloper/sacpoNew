package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MNAssignSObj {
    public List<MNAssignSObj.Item> ITEMS = new ArrayList<MNAssignSObj.Item>();
    public Map<Integer, MNAssignSObj.Item> ITEM_MAP = new HashMap<Integer, MNAssignSObj.Item>();
    public void addItem(MNAssignSObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MNAssignSObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MNAssignSObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MNAssignSObj.Item createItem(int pos1, int aId2, String mwXStu_User_id3, String mwX_student_name4, String mwX_reg_no5, String mwX_Name6,String mwX_university_name7,String mwX_Student_Emaill8) {
        return new MNAssignSObj.Item(String.valueOf(pos1),aId2,mwXStu_User_id3,mwX_student_name4,mwX_reg_no5, mwX_Name6,mwX_university_name7,mwX_Student_Emaill8);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mwXStu_User_id3;
        public final String mwX_student_name4;
        public final String mwX_reg_no5;
        public final String mwX_Name6;
        public final String mwX_university_name7;
        public final String mwX_Student_Emaill8;




        public Item(String aPos1, int aId2, String mwXStu_User_id3,String mwX_student_name4, String mwX_reg_no5, String mwX_Name6, String mwX_university_name7,String mwX_Student_Emaill8) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mwXStu_User_id3 = mwXStu_User_id3;
            this.mwX_student_name4 = mwX_student_name4;
            this.mwX_reg_no5 = mwX_reg_no5;
            this.mwX_Name6 = mwX_Name6;
            this.mwX_university_name7 = mwX_university_name7;
            this.mwX_Student_Emaill8 = mwX_Student_Emaill8;

        }



        @Override
        public String toString()
        {
            return mwXStu_User_id3;
        }
    }



}
