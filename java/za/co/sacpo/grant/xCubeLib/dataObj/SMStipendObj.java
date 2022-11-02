package za.co.sacpo.grant.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMStipendObj {
    public List<SMStipendObj.Item> ITEMS = new ArrayList<SMStipendObj.Item>();
    public Map<String, SMStipendObj.Item> ITEM_MAP = new HashMap<String, SMStipendObj.Item>();
    public void addItem(SMStipendObj.Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aId2, item);
    }
    public List<SMStipendObj.Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, SMStipendObj.Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public SMStipendObj.Item createItem(int pos1, String aId2, String smStipDate3, String smStipMonth4, String smStipYear5, String smStipStipendAmount6) {

        return new SMStipendObj.Item(String.valueOf(pos1),aId2,smStipDate3,smStipMonth4,smStipYear5,smStipStipendAmount6);
    }

    public static class Item {
        public final String aPos1;
        public final String aId2;
        public final String smStipDate3;
        public final String smStipMonth4;
        public final String smStipYear5;
        public final String smStipStipendAmount6;


        public Item(String aPos1, String aId2, String smStipDate3, String smStipMonth4, String smStipYear5,
                    String smStipStipendAmount6) {

            this.aId2 = aId2;
            this.aPos1 = aPos1;
            this.smStipDate3= smStipDate3;
            this.smStipMonth4= smStipMonth4;
            this.smStipYear5= smStipYear5;
            this.smStipStipendAmount6= smStipStipendAmount6;

        }

        @Override
        public String toString() {
            return smStipDate3;
        }
    }
}

