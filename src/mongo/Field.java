package mongo;

/**
 * A template class that can represent any field with any value inside the document
 * Created by satyaalmasian on 18/03/2017.
 */
public class Field<T> {
    private String fieldName;
    private T value;
    public Field(String fieldName, T value)
    {
        this.fieldName=fieldName;
        if(value.getClass()==String.class)
        {
            this.value= (T) convertToUTF8(String.valueOf(value));//if it a text value we convert to utf-8 for readability in mongodb
        }
        this.value=value;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        return this.fieldName.equals(((Field)obj).fieldName);
    }
    /**
     * convert from internal Java String format -> UTF-8
     * @param s The Sring to be converted
     * @return A string
     */
    private String convertToUTF8(String s) {

        String out = null;
        if(s!=null && s!="") {
            try {
                out = new String(s.getBytes("UTF-8"), "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }
        return "";
    }
}

