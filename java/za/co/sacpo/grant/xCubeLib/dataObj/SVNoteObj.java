package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVNoteObj {
    public List<SVNoteObj.Item> ITEMS = new ArrayList<SVNoteObj.Item>();
    public Map<Integer, SVNoteObj.Item> ITEM_MAP = new HashMap<Integer, SVNoteObj.Item>();
    public void addItem(SVNoteObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SVNoteObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<Integer, SVNoteObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;

    }
    public SVNoteObj.Item createItem(int pos1, int aId2, String description3, String add_date4,
                                     String  addby5, String  note_for6, String  cell_no7, String  note_from8, String  duration9, String  note_10) {

        return new SVNoteObj.Item(String.valueOf(pos1),aId2,description3,add_date4,addby5,note_for6,cell_no7,note_from8,duration9,note_10);
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



        public Item(String aPos1, int aId2,String description3,String add_date4,
                    String  addby5, String  note_for6,String cell_no7,String  note_from8,String duration9,String  note_10) {
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


        }

        @Override
        public String toString()
        {
            return description3;

        }
    }

}

