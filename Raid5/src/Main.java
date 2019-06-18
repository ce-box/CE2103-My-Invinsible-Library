import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        RaidController controlador=new RaidController();
        System.out.println( controlador.raid5.DameUnDiscoQueNoEstaVacio());
        BufferedImage image2 = ImageIO.read(new File("/home/reds/Descargas/Tux.png"));
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ImageIO.write(image2, "png", baos2);
        String encodedImage2 = Base64.encode(baos2.toByteArray());
        //controlador.delete("2#");
         //controlador.WriteCommit(encodedImage2,"2#","Sahid");
         //controlador.rollback("Sahid");
        // controlador.WriteCommit(encodedImage2,"3#","Sahid");
        //controlador.rollback("Sahid");
         //controlador.WriteCommit(encodedImage2,"4#","Sahid");
         controlador.deleteCommmit("3#","Sahid");

//        controlador.deleteCommmit("3#");
        controlador.commit("Sahid");



    }

}
