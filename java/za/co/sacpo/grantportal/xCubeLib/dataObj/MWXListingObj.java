package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MWXListingObj {
    public List<MWXListingObj.Item> ITEMS = new ArrayList<MWXListingObj.Item>();
    public Map<Integer, MWXListingObj.Item> ITEM_MAP = new HashMap<Integer, MWXListingObj.Item>();

    public void addItem(MWXListingObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }

    public List<MWXListingObj.Item> getITEMS() {
        return this.ITEMS;
    }

    public Map<Integer, MWXListingObj.Item> getITEM_MAP() {
        return this.ITEM_MAP;
    }

    public MWXListingObj.Item createItem(int pos1, int aId2, String mwXStudentName3, String mwXGrantName4, String mwXStuRegis_no5, String mwXWorkstationName6) {
        return new MWXListingObj.Item(String.valueOf(pos1), aId2, mwXStudentName3, mwXGrantName4, mwXStuRegis_no5, mwXWorkstationName6);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mwXStudentName3;
        public final String mwXGrantName4;
        public final String mwXStuRegis_no5;
        public final String mwXWorkstationName6;

        public Item(String aPos1, int aId2, String mwXStudentName3, String mwXGrantName4, String mwXStuRegis_no5, String mwXWorkstationName6) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mwXStudentName3 = mwXStudentName3;
            this.mwXGrantName4 = mwXGrantName4;
            this.mwXStuRegis_no5 = mwXStuRegis_no5;
            this.mwXWorkstationName6 = mwXWorkstationName6;

        }

        @Override
        public String toString() {
            return mwXStudentName3;
        }
    }
}




