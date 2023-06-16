package za.co.sacpo.grantportal.xCubeLib.dataObj;


        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class SupervisorCommentObj {
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

    public Item createItem(int pos1, int aId2, String txtFeedback_ga3, String txt_Uname4, String txtDate5, String getImage6) {
        return new Item(String.valueOf(pos1), aId2, txtFeedback_ga3, txt_Uname4, txtDate5, getImage6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String txtFeedback_ga3;
        public final String txt_Uname4;
        public final String txtDate5;
        public final String getImage6;


        public Item(String aPos1, int aId2, String txtFeedback_ga3, String txt_Uname4, String txtDate5, String getImage6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.txtFeedback_ga3 = txtFeedback_ga3;
            this.txt_Uname4 = txt_Uname4;
            this.txtDate5 = txtDate5;
            this.getImage6 = getImage6;
        }

        @Override
        public String toString() {
            return txtFeedback_ga3;
        }
    }
}
