package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SNewClaimObj {

    public List<SNewClaimObj.Item> ITEMS = new ArrayList<SNewClaimObj.Item>();
    public Map<String, SNewClaimObj.Item> ITEM_MAP = new HashMap<String, SNewClaimObj.Item>();
    public void addItem(SNewClaimObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SNewClaimObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, SNewClaimObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SNewClaimObj.Item createItem(int pos1, String aId2, String claimYear3,String claimMonth4,String date5,String claimAmount6,String supervisorStatus7,String grantAdminStatus8,String isPendingSupervisorStatus9,String isPendingGrantAdminStatus10,String downloadUSCF11,String isDownloadUSCF12,String isDownloadSCF13,String downloadSCF14,String isUSCF15){

        return new SNewClaimObj.Item(String.valueOf(pos1),aId2,claimYear3,claimMonth4,date5,claimAmount6,supervisorStatus7,grantAdminStatus8,isPendingSupervisorStatus9,isPendingGrantAdminStatus10,downloadUSCF11,isDownloadUSCF12,isDownloadSCF13,downloadSCF14,isUSCF15);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String claimYear3;
        public final String claimMonth4;
        public final String date5;
        public final String claimAmount6;
        public final String supervisorStatus7;
        public final String grantAdminStatus8;
        public final String isPendingSupervisorStatus9;
        public final String isPendingGrantAdminStatus10;
        public final String downloadUSCF11;
        public final String isDownloadUSCF12;
        public final String isDownloadSCF13;
        public final String downloadSCF14;
        public final String isUSCF15;

        public Item(String aPos1, String aId2, String claimYear3,String claimMonth4,String date5,String claimAmount6,String supervisorStatus7,String grantAdminStatus8,String isPendingSupervisorStatus9,String isPendingGrantAdminStatus10,String downloadUSCF11,String isDownloadUSCF12,String isDownloadSCF13,String downloadSCF14,String isUSCF15){

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.claimYear3	=claimYear3;
            this.claimMonth4=claimMonth4;
            this.date5=date5;
            this.claimAmount6=claimAmount6;
            this.supervisorStatus7=supervisorStatus7;
            this.grantAdminStatus8=grantAdminStatus8;
            this.isPendingSupervisorStatus9=isPendingSupervisorStatus9;
            this.isPendingGrantAdminStatus10=isPendingGrantAdminStatus10;
            this.downloadUSCF11=downloadUSCF11;
            this.isDownloadUSCF12=isDownloadUSCF12;
            this.isDownloadSCF13=isDownloadSCF13;
            this.downloadSCF14=downloadSCF14;
            this.isUSCF15=isUSCF15;
        }

        @Override
        public String toString() {
           return claimAmount6;
        }
    }
}


