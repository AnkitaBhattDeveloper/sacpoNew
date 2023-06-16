package za.co.sacpo.grantportal.xCubeLib.dataObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormsObj {
    public List<Item> ITEMS = new ArrayList<Item>();
    public Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public void addItem(Item item) {
        this.ITEMS.add(item);
        this.ITEM_MAP.put(item.aFormId, item);
    }
    public List<Item> getITEMS(){
        return this.ITEMS;
    }
    public Map<String, Item> getITEM_MAP(){
        return this.ITEM_MAP;
    }
    public Item createItem(String aFormId,String aFormUrl) {
        return new Item(aFormId,aFormUrl);
    }
    public String getFormURLByID(String formId){
        String Url="";
        Map<String, Item> params = this.getITEM_MAP();
        for (Map.Entry<String, Item> i : params.entrySet()) {
            String key = i.getKey();
            Item Value = i.getValue();
            String ValueFormId = Value.getFormId();
            if(ValueFormId.equals(formId)) {
                Url = Value.getFormUrl();
            }
        }
        return Url;
    }
    public static class Item {
        public final String aFormId;
        public final String aFormUrl;

        public Item(String aFormId,String aFormUrl) {
            this.aFormId= aFormId;
            this.aFormUrl= aFormUrl;
        }

        @Override
        public String toString() {
            return aFormUrl;
        }
        public String getFormId() {
            return aFormId;
        }
        public String getFormUrl() {
            return aFormUrl;
        }
    }
}
