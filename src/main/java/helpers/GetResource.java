package helpers;
import java.net.URLDecoder;

public class GetResource {

    static public String getFile(String image_name) {
        try {
            String path =  URLDecoder.decode(GetResource.class.getResource(image_name).getFile(), "UTF-8");
            return path;
        } catch (Exception e) {
            System.out.println(String.format("Error Getting Image: %s", image_name, GetResource.class.getResource(image_name)));
            e.printStackTrace();
        }
        return null;
    }
}
