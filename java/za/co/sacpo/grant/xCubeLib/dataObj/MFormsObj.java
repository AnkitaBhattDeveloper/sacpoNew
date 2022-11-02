package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MFormsObj {
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
    public Item createItem(int pos1,int aId2,String mFname3,String mFlearner4, String mFhostEmp5,
                           String mFleadEmp6,String mFcomplition7,String mFbtnDownloadUnsignedForm8,String mFbtnUploadSignedForm9,String mFbtnDownloadSignedForm10,String mFid11) {
        return new Item(String.valueOf(pos1),aId2,mFname3,mFlearner4,mFhostEmp5,mFleadEmp6,mFcomplition7,mFbtnDownloadUnsignedForm8,mFbtnUploadSignedForm9,mFbtnDownloadSignedForm10,mFid11);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String mFname3;
        public final String mFlearner4;
        public final String mFhostEmp5;
        public final String mFleadEmp6;
        public final String mFcomplition7;
        public final String mFbtnDownloadUnsignedForm8;
        public final String mFbtnUploadSignedForm9;
        public final String mFbtnDownloadSignedForm10;
        public final String mFid11;

        public Item(String aPos1, int aId2, String mFname3, String mFlearner4, String mFhostEmp5, String mFleadEmp6, String mFcomplition7, String mFbtnDownloadUnsignedForm8, String mFbtnUploadSignedForm9, String mFbtnDownloadSignedForm10, String mFid11) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.mFname3 = mFname3;
            this.mFlearner4 = mFlearner4;
            this.mFhostEmp5 = mFhostEmp5;
            this.mFleadEmp6 = mFleadEmp6;
            this.mFcomplition7 = mFcomplition7;
            this.mFbtnDownloadUnsignedForm8 = mFbtnDownloadUnsignedForm8;
            this.mFbtnUploadSignedForm9 = mFbtnUploadSignedForm9;
            this.mFbtnDownloadSignedForm10 = mFbtnDownloadSignedForm10;
            this.mFid11 = mFid11;
        }

        @Override
        public String toString() {
            return mFname3;
        }
    }
}
