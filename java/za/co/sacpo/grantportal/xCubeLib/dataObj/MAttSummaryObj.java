package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MAttSummaryObj {

    public List<MAttSummaryObj.Item> ITEMS = new ArrayList<MAttSummaryObj.Item>();
    public Map<Integer, MAttSummaryObj.Item> ITEM_MAP = new HashMap<>();
    public void addItem(MAttSummaryObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MAttSummaryObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MAttSummaryObj.Item createItem(int pos1, int aId2, String mVADate3, String mVADay4, String mVATime5
            , String lblLogoutTime6, String lblLoginTime7, String mcord_diff8, String attt_id9, String approval_status10, String remark11) {
        return new MAttSummaryObj.Item(String.valueOf(pos1),aId2,mVADate3
                ,mVADay4,mVATime5  ,lblLogoutTime6,lblLoginTime7,mcord_diff8,attt_id9,approval_status10,remark11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mVADate3;
        public final String mVADay4;
        public final String mVATime5;
        public final String lblLogoutTime6;
        public final String lblLoginTime7;
        public final String mcord_diff8;
        public final String attt_id9;
        public final String approval_status10;
        public final String remark11;



        public Item(String aPos1, int aId2, String mVADate3, String mVADay4, String mVATime5,String lblLogoutTime6,String lblLoginTime7,String mcord_diff8,String attt_id9,String approval_status10,String remark11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mVADate3 = mVADate3;
            this.mVADay4 = mVADay4;
            this.mVATime5 = mVATime5;
            this.lblLogoutTime6 = lblLogoutTime6;
            this.lblLoginTime7 = lblLoginTime7;
            this.mcord_diff8 = mcord_diff8;
            this.attt_id9 = attt_id9;
            this.approval_status10 = approval_status10;
            this.remark11 = remark11;

        }

        @Override
        public String toString() {
            return mVADate3;
        }
    }
    }
