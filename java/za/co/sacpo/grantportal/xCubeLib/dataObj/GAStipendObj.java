package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GAStipendObj {
    public List<GAStipendObj.Item> ITEMS = new ArrayList<GAStipendObj.Item>();
    public Map<String, GAStipendObj.Item> ITEM_MAP = new HashMap<String, GAStipendObj.Item>();
    public void addItem(GAStipendObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<GAStipendObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, GAStipendObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public GAStipendObj.Item createItem(int pos1, String aId2, String smStipDate3, String smStipMonth4, String smStipYear5, String smStipStipendAmount6,String studentID7,String grantID8) {

        return new GAStipendObj.Item(String.valueOf(pos1),aId2,smStipDate3,smStipMonth4,smStipYear5,smStipStipendAmount6,studentID7,grantID8);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String smStipDate3;
        public final String smStipMonth4;
        public final String smStipYear5;
        public final String smStipStipendAmount6;
        public final String studentID7;
        public final String grantID8;


        public Item(String aPos1, String aId2, String smStipDate3, String smStipMonth4, String smStipYear5,
                    String smStipStipendAmount6,String studentID7,String grantID8 ) {

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.smStipDate3= smStipDate3;
            this.smStipMonth4= smStipMonth4;
            this.smStipYear5= smStipYear5;
            this.smStipStipendAmount6= smStipStipendAmount6;
            this.studentID7= studentID7;
            this.grantID8= grantID8;

        }

        @Override
        public String toString() {
            return smStipDate3;
        }
    }
}

