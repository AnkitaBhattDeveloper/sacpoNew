package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyCommentObj {
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

    public Item createItem(int pos1, int aId2, String iv_userimg3, String userName4, String iv_get_imgPost5, String tv_commnt_body6, String txt_date7) {
        return new Item(String.valueOf(pos1), aId2, iv_userimg3, userName4, iv_get_imgPost5, tv_commnt_body6, txt_date7);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String iv_userimg3;
        public final String userName4;
        public final String iv_get_imgPost5;
        public final String tv_commnt_body6;
        public final String txt_date7;


        public String getGetImage6() {
            return iv_userimg3;
        }

        public String getImage() {
            return iv_get_imgPost5;
        }

        public Item(String aPos1, int aId2, String iv_userimg3, String userName4, String iv_get_imgPost5, String tv_commnt_body6, String txt_date7) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.iv_userimg3 = iv_userimg3;
            this.userName4 = userName4;
            this.iv_get_imgPost5 = iv_get_imgPost5;
            this.tv_commnt_body6 = tv_commnt_body6;
            this.txt_date7 = txt_date7;
        }

        @Override
        public String toString() {
            return iv_userimg3;
        }


    }
}
