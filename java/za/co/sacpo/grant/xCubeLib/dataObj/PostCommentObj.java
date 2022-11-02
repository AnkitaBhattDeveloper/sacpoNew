package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostCommentObj {
    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<Integer, Item> ITEM_MAP = new HashMap<Integer, Item>();

    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<Item> getITEMS() {
        return this.ITEMS;
    }

    public Map<Integer, Item> getITEM_MAP() {
        return this.ITEM_MAP;
    }

    public Item createItem(int pos1, int aId2, String iv_userimg3, String tv_title4, String tv_time5, String txt_date6, String txt_getComment7, String iv_getUserImg8, String tv_head_user9) {
        return new Item(String.valueOf(pos1), aId2, iv_userimg3, tv_title4, tv_time5, txt_date6,txt_getComment7,iv_getUserImg8,tv_head_user9);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String iv_userimg3;
        public final String tv_title4;
        public final String tv_time5;

        public final String txt_date6;
        public final String txt_getComment7;
        public final String iv_getUserImg8;
        public final String tv_head_user9;


        public String getGetImage6() {
            return iv_userimg3;
        }

        public Item(String aPos1, int aId2, String iv_userimg3, String tv_title4, String tv_time5, String txt_date6,String txt_getComment7,
                    String iv_getUserImg8,String tv_head_user9) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.iv_userimg3 = iv_userimg3;
            this.tv_title4 = tv_title4;
            this.tv_time5 = tv_time5;
            this.txt_date6 = txt_date6;
            this.txt_getComment7 = txt_getComment7;
            this.iv_getUserImg8 = iv_getUserImg8;
            this.tv_head_user9 = tv_head_user9;
        }

        @Override
        public String toString() {
            return iv_userimg3;
        }


    }
}
