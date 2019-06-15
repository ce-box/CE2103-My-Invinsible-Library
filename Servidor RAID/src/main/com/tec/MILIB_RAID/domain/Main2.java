package main.com.tec.MILIB_RAID.domain;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws IOException {
        String nombre = "/raiz/feo/loca/12-1.png";
        String id = "";
        boolean inicio = false;
        for (int x = nombre.length() - 1; x >= 0; x--){
            if (nombre.charAt(x) == '/') {
                inicio = false;
            }
            if (inicio) {
                id = id + nombre.charAt(x);
            }
            if (nombre.charAt(x) == '-') {
                inicio = true;
            }
        }
        String idReal="";
        for (int x=id.length()-1;x>=0;x--)
            idReal = idReal + id.charAt(x);
        System.out.println(idReal);

}
}
