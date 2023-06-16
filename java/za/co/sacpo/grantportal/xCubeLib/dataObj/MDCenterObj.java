package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDCenterObj {

    public List<MDCenterObj.Item> ITEMS = new ArrayList<MDCenterObj.Item>();
    public Map<Integer, MDCenterObj.Item> ITEM_MAP = new HashMap<Integer, MDCenterObj.Item>();
    public void addItem(MDCenterObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MDCenterObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, MDCenterObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public MDCenterObj.Item createItem(int pos1,int aId2,String aYear3,String aMonth4,String aDownloadAttRegister5,String aIsDownloadAttRegister6,String aDownloadUnsignedClaimForm7,String aIsDownloadUnsignedClaimForm8,String aUploadSignedClaimForm9,String aDownloadSignedClaimForm10,String aIsDownloadSignedClaimForm11,String aMonthNumber12) {
        return new MDCenterObj.Item(String.valueOf(pos1),aId2,aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String aYear3;
        public final String aMonth4;
        public final String aDownloadAttRegister5;
        public final String aIsDownloadAttRegister6;
        public final String aDownloadUnsignedClaimForm7;
        public final String aIsDownloadUnsignedClaimForm8;
        public final String aUploadSignedClaimForm9;
        public final String aDownloadSignedClaimForm10;
        public final String aIsDownloadSignedClaimForm11;
        public final String aMonthNumber12;
        public Item(String aPos1,int aId2,String aYear3,String aMonth4,String aDownloadAttRegister5,String aIsDownloadAttRegister6,String aDownloadUnsignedClaimForm7,String aIsDownloadUnsignedClaimForm8,String aUploadSignedClaimForm9,String aDownloadSignedClaimForm10,String aIsDownloadSignedClaimForm11,String aMonthNumber12) {
            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.aYear3=aYear3;
            this.aMonth4=aMonth4;
            this.aDownloadAttRegister5=aDownloadAttRegister5;
            this.aIsDownloadAttRegister6=aIsDownloadAttRegister6;
            this.aDownloadUnsignedClaimForm7=aDownloadUnsignedClaimForm7;
            this.aIsDownloadUnsignedClaimForm8=aIsDownloadUnsignedClaimForm8;
            this.aUploadSignedClaimForm9=aUploadSignedClaimForm9;
            this.aDownloadSignedClaimForm10=aDownloadSignedClaimForm10;
            this.aIsDownloadSignedClaimForm11=aIsDownloadSignedClaimForm11;
            this.aMonthNumber12=aMonthNumber12;
//year3
            //month4
            //download_att_register5
            //is_download_att_register6
            //download_unsigned_claim_form7
            //is_download_unsigned_claim_form8
            //upload_signed_claim_form9
            //download_signed_claim_form10
            //is_download_signed_claim_form11

        }

        @Override
        public String toString() {
            return aYear3;
        }
    }
         }
