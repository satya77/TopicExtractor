package mongo;

import java.util.LinkedList;
import java.util.List;

/**
 * Comibination of fields that make a document
 * Created by satyaalmasian on 18/03/2017.
 */
public class DocumentItem {
    private List<Field> items;// it has a list of fields that contain different fields a document can hold
    {
        items=new LinkedList<>();
    }
    public DocumentItem()
    {

    }
    public void  addField(Field field)
    {
        items.add(field);
    }

    public void removeField(Field field)
    {
        items.remove(field);
    }
    public List<Field> getItem() {
        return items;
    }
    public String getQuery(String fieldName)
    {
        for(Field p:items) {
            if (p.getFieldName() == fieldName) {
                return String.valueOf(p.getValue());
            }
        }
        return null;
    }

    public void setItem(List<Field> item) {
        this.items = item;
    }


}
