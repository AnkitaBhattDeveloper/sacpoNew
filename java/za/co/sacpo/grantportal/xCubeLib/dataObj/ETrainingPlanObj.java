package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ETrainingPlanObj {

    public List<ETrainingPlanObj.Item> ITEMS = new ArrayList<ETrainingPlanObj.Item>();
    public Map<Integer, ETrainingPlanObj.Item> ITEM_MAP = new HashMap<Integer, ETrainingPlanObj.Item>();
    public void addItem(ETrainingPlanObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<ETrainingPlanObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, ETrainingPlanObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public ETrainingPlanObj.Item createItem(int pos1,int aId2,String atitle3,String aTPlan4,String aIsFile5,String aIsEdit6) {
        return new ETrainingPlanObj.Item(String.valueOf(pos1),aId2,atitle3,aTPlan4,aIsFile5,aIsEdit6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String atitle3;
        public final String aTPlan4;
        public final String aIsFile5;
        public final String aIsEdit6;


        public Item(String aPos1,int aId2,String atitle3,String aTPlan4,String aIsFile5 , String aIsEdit6) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.atitle3=atitle3;
            this.aTPlan4=aTPlan4;
            this.aIsFile5=aIsFile5;
            this.aIsEdit6=aIsEdit6;
        }

        @Override
        public String toString() {
            return atitle3;
        }
    }
}
