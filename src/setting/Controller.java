package setting;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @author (c) 2016, Satya(Shideh) Almasian, almasian@stud.uni-heidelberg.de
 */

//Class for extra methods that are used in other classes.
public class Controller {

    /**
     * Validates whether a text is in a date format or not
     * @param format input string
     * @param value input string
     * @return output boolean
     */
    public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            return false;
        }
        return date != null;
    }

    /**
     * convert from internal Java String format -> UTF-8
     * @param s The Sring to be converted
     * @return A string
     */
    public static String convertToUTF8(String s) {

        String out = null;
        if(s!=null && s!="") {
            try {
                out = new String(s.getBytes("UTF-8"), "UTF-8");
                // out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }
        return "";
    }
    /**
     * helper function to split the text of the files
     * @param content The Sring to be converted
     * @return A string
     */
    public static String splitText(String content) {
        String out = "";
        if (content.split(": ").length == 2) {
            out = content.split(": ")[1];
        } else if (content.split(": ").length > 2) {
            out = content.split(": ")[1];
            String s[] = content.split(": ");
            for (int i = 2; i < s.length; i++) {
                out += " : ";
                out += s[i];
            }
        }
        return out;
    }
    /**
     * helper function to return an abslout url
     * @param url the url the has the domain name
     * @param relative the relative url
     * @return A string of new url
     */
    public static String absURL(String url,String relative)
    {
        String newUrl="";
        try
        {
            URL url2 = new URL(url);
            String baseUrl = url2.getProtocol() + "://" + url2.getHost();
            newUrl=baseUrl+relative;
        }
        catch (MalformedURLException e)
        {
            System.out.println("Error during the conversion of relative url :"+url);
            newUrl=relative;
        }
        return  newUrl;
    }
    /**
     * helper function fix some characters
     * @param link the url to be fixed
     * @return A string of new url
     */
    public static String correctURL(String link)
    {
        if(link.contains("&"))//params produces 400 error
        {
            link=link.substring(0,link.indexOf("&"));
        }
        if(link.contains("%27s"))//this code produces 400 error
        {
            link=link.replace("%27s","");
        }
        if(link.contains("%27t"))//this code produces 400 error
        {
            link=link.replace("%27t","");
        }
        if (link.contains("%C3%A9"))//this code produces 400 error
        {
            link=link.replace("%C3%A9","é");
        }
        if (link.contains("%C3%B6"))//this code produces 400 error
        {
            link=link.replace("%C3%B6","ö");
        }
        if(link.contains("_(physics)"))
        {
            link=link.replace("_(physics)","");
        }
        if(link.contains("_(geology)"))
        {
            link=link.replace("_(geology)","");
        }
        if(link.contains("_(deformation)"))
        {
            link=link.replace("_(deformation)","");
        }
        if(link.contains("_(petrology)"))
        {
            link=link.replace("_(petrology)","");
        }
        //                            if (link.contains("%C3%B8"))//this code produces 400 error
//                            {
//                                link=link.replace("%C3%B8","ø");
//                            }

        return  link;
    }
}
