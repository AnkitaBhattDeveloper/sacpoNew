package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMLearnerListObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int aId2,String FMNotes3,String FMLearnerName4,String FMLearnerId5,String FMLearnerEmail6,String FMLearnerCellNo7,
                           String FMCurrentStatus8,String FMEmployeeName9,String FMSdlNo10 ,String FMSupervisorName11,String FMDuration12,
                           String FMStartDate13,String FMEndDate14,String FMSetaAllocation15,String FMClaimed16,String FMUnspet17,
                           String FMMentor18,String FMGrantId19,String FMEmpId20) {

        return new Item(String.valueOf(pos1),aId2,FMNotes3,FMLearnerName4,FMLearnerId5,FMLearnerEmail6,FMLearnerCellNo7,FMCurrentStatus8,FMEmployeeName9,
                FMSdlNo10,FMSupervisorName11,FMDuration12,FMStartDate13,FMEndDate14,
                FMSetaAllocation15,FMClaimed16,FMUnspet17,FMMentor18,FMGrantId19,FMEmpId20);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String FMNotes3;
        public final String FMLearnerName4;
        public final String FMLearnerId5;
        public final String FMLearnerEmail6;
        public final String FMLearnerCellNo7;
        public final String FMCurrentStatus8;
        public final String FMEmployeeName9;
        public final String FMSdlNo10;
        public final String FMSupervisorName11;
        public final String FMDuration12;
        public final String FMStartDate13;
        public final String FMEndDate14;
        public final String FMSetaAllocation15;
        public final String FMClaimed16;
        public final String FMUnspet17;
        public final String FMMentor18;
        public final String FMGrantId19;
        public final String FMEmpId20;


        public Item(String aPos1, int aId2, String FMNotes3, String FMLearnerName4, String FMLearnerId5, String FMLearnerEmail6,
                    String FMLearnerCellNo7, String FMCurrentStatus8, String FMEmployeeName9, String FMSdlNo10, String FMSupervisorName11,
                    String FMDuration12,String FMStartDate13,String FMEndDate14,String FMSetaAllocation15,String FMClaimed16,String FMUnspet17,String FMMentor18,String FMGrantId19,String FMEmpId20) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.FMNotes3 = FMNotes3;
            this.FMLearnerName4 = FMLearnerName4;
            this.FMLearnerId5 = FMLearnerId5;
            this.FMLearnerEmail6 = FMLearnerEmail6;
            this.FMLearnerCellNo7 = FMLearnerCellNo7;
            this.FMCurrentStatus8 = FMCurrentStatus8;
            this.FMEmployeeName9 = FMEmployeeName9;
            this.FMSdlNo10 = FMSdlNo10;
            this.FMSupervisorName11 = FMSupervisorName11;
            this.FMDuration12 = FMDuration12;
            this.FMStartDate13 = FMStartDate13;
            this.FMEndDate14 = FMEndDate14;
            this.FMSetaAllocation15 = FMSetaAllocation15;
            this.FMClaimed16 = FMClaimed16;
            this.FMUnspet17 = FMUnspet17;
            this.FMMentor18 = FMMentor18;
            this.FMGrantId19 = FMGrantId19;
            this.FMEmpId20 = FMEmpId20;

        }

        @Override
        public String toString() {
            return FMNotes3;
        }
    }
}