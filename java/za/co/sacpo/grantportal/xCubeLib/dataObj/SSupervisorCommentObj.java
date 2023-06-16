package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSupervisorCommentObj {


    public List<SSupervisorCommentObj.Item> ITEMS = new ArrayList<SSupervisorCommentObj.Item>();
    public Map<Integer, SSupervisorCommentObj.Item> ITEM_MAP = new HashMap<Integer, SSupervisorCommentObj.Item>();

    public void addItem(SSupervisorCommentObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<SSupervisorCommentObj.Item> getITEMS() {
        return this.ITEMS;
    }

    public Map<Integer, SSupervisorCommentObj.Item> getITEM_MAP() {
        return this.ITEM_MAP;
    }

    public SSupervisorCommentObj.Item createItem(int pos1, int aId2, String lblRef3, String getUserName4, String getlblDate5) {
        return new SSupervisorCommentObj.Item(String.valueOf(pos1), aId2, lblRef3, getUserName4, getlblDate5);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String lblRef3;
        public final String getUserName4;
        public final String getlblDate5;



        public Item(String aPos1, int aId2, String lblRef3, String getUserName4, String getlblDate5) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.lblRef3 = lblRef3;
            this.getUserName4 = getUserName4;
            this.getlblDate5 = getlblDate5;

        }

        @Override
        public String toString() {
            return lblRef3;
        }
    }
}

