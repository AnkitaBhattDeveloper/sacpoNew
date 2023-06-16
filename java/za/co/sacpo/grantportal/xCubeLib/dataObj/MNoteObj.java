package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MNoteObj {
    public List<MNoteObj.Item> ITEMS = new ArrayList<MNoteObj.Item>();
    public Map<Integer, MNoteObj.Item> ITEM_MAP = new HashMap<Integer, MNoteObj.Item>();
    public void addItem(MNoteObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<MNoteObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer,MNoteObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;

    }
    public MNoteObj.Item createItem(int pos1, int aId2, String description3, String add_date4,
                                    String addby5, String note_for6, String cell_no7, String note_from8, String duration9, String note_10, String note_11, String grant_id11, String user_id12) {

        return new MNoteObj.Item(String.valueOf(pos1),aId2,description3,add_date4,addby5,note_for6,cell_no7,note_from8,duration9,
                note_10,note_11,grant_id11,user_id12);
    }

    public static class Item {
        public final String aPos1;
        public final int aId2;
        public final String description3;
        public final String add_date4;
        public final String addby5;
        public final String note_for6;
        public final String cell_no7;
        public final String note_from8;
        public final String duration9;
        public final String note_10;
        public final String note_11;
        public final String grant_id11;
        public final String user_id12;



        public Item(String aPos1, int aId2,String description3,String add_date4,
                    String  addby5, String  note_for6,String cell_no7,String  note_from8,String duration9,String  note_10,String note_11,String  grant_id11,String  user_id12) {
            this.aPos1 = aPos1;
            this.aId2 = aId2;
            this.description3 = description3;
            this.add_date4 = add_date4;
            this.addby5 = addby5;
            this.note_for6 = note_for6;
            this.cell_no7 = cell_no7;
            this.note_from8 = note_from8;
            this.duration9 = duration9;
            this.note_10 = note_10;
            this.note_11 = note_11;
            this.grant_id11 = grant_id11;
            this.user_id12 = user_id12;


        }

        @Override
        public String toString()
        {
            return description3;

        }
    }

}

