package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVBViewPostObj {
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
    public Item createItem(int pos1,int aId2,String sVpbBlogTopics3,String sVpbBlogPostTitle4,String sVpbApprovalPending5) {
        return new Item(String.valueOf(pos1),aId2,sVpbBlogTopics3,sVpbBlogPostTitle4,sVpbApprovalPending5);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String sVpbBlogTopics3;
        public final String sVpbBlogPostTitle4;
        public final String sVpbApprovalPending5;



        public Item(String aPos1, int aId2, String sVpbBlogTopics3, String sVpbBlogPostTitle4,String sVpbApprovalPending5) {
           this.aPos1 = aPos1;
           this.aId2 = aId2;
           this.sVpbBlogTopics3 = sVpbBlogTopics3;
           this.sVpbBlogPostTitle4 = sVpbBlogPostTitle4;
           this.sVpbApprovalPending5 = sVpbApprovalPending5;

        }

        @Override
        public String toString() {
            return sVpbBlogTopics3;
        }
    }
}
