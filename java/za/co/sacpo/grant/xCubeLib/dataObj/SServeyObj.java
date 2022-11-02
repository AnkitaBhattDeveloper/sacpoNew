package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SServeyObj {
    public List<SServeyObj.Item> ITEMS = new ArrayList<SServeyObj.Item>();
    public Map<Integer, SServeyObj.Item> ITEM_MAP = new HashMap<Integer, SServeyObj.Item>();
    public void addItem(SServeyObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SServeyObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SServeyObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
   
       }
    public SServeyObj.Item createItem(int pos1, int aId2, String p_t_expiry3, String p_t_id4, String poll_title5, String poll_status6) {
   
    return new SServeyObj.Item(String.valueOf(pos1),aId2,p_t_expiry3,p_t_id4,poll_title5,poll_status6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String p_t_expiry3;
        public final String p_t_id4;
        public final String poll_title5;
        public final String poll_status6;


        public Item(String aPos1, int aId2, String p_t_expiry3, String p_t_id4, String poll_title5, String poll_status6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.p_t_expiry3 = p_t_expiry3;
            this.p_t_id4 = p_t_id4;
            this.poll_title5 = poll_title5;
            this.poll_status6 = poll_status6;

        }

        @Override
        public String toString()
        {
            return p_t_expiry3;

        }
    }

}
