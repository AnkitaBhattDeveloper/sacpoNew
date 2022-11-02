package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MBlogObj {
    public  List<Item> ITEMS = new ArrayList<Item>();
    public  Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<Item> ITEMS() {
        return this.ITEMS;
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(int pos1,int aId2,String blogHead3,String blogTxt4,String blogName5,String blogDate6,String group_id7) {
        return new Item(String.valueOf(pos1),aId2,blogHead3,blogTxt4,blogName5,blogDate6,group_id7);
    }



    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String blogHead3;
        public final String blogTxt4;
        public final String blogName5;
        public final String blogDate6;
        public final String group_id7;


        public Item(String aPos1,int aId2, String blogHead3, String blogTxt4,String blogName5,String blogDate6,String group_id7) {

            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.blogHead3= blogHead3;
            this.blogTxt4= blogTxt4;
            this.blogName5= blogName5;
            this.blogDate6= blogDate6;
            this.group_id7= group_id7;
        }

        @Override
        public String toString() {
            return blogHead3;
        }
    }
}
