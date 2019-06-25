package helpers;
import java.io.File;

public class GetResource {

    static public String getFile(String image_name) {
        try {
            String path = new File(GetResource.class.getResource(image_name).toURI()).getAbsolutePath();
            return path;
        } catch (Exception e) {
            System.out.println(String.format("Error Getting Image: %s", image_name, GetResource.class.getResource(image_name)));
            e.printStackTrace();
        }
        return null;
    }
}
