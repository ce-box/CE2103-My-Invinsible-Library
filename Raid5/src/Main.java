import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public  static byte[][] cualEsmasGrande(byte[]array1,byte[]array2,byte[]array3){
        byte[][]arraysOrdenados=new byte[3][];
        int size1=array1.length;
        int size2=array2.length;
        int size3=array3.length;

        if(size1>=size2&&size1>=size3){
            arraysOrdenados[0]=array1;
            if(size2>size3){
                arraysOrdenados[1]=array2;
                arraysOrdenados[2]=array3;
            }
            else{
                arraysOrdenados[2]=array2;
                arraysOrdenados[1]=array3;
            }
        }
        if(size2>=size1&&size2>=size3){
            arraysOrdenados[0]=array2;
            if(size1>size3){
                arraysOrdenados[1]=array1;
                arraysOrdenados[2]=array3;
            }
            else{
                arraysOrdenados[2]=array1;
                arraysOrdenados[1]=array3;
            }
        }
        if(size3>=size1&&size3>=size2){
            arraysOrdenados[0]=array3;
            if(size1>size2){
                arraysOrdenados[1]=array1;
                arraysOrdenados[2]=array2;
            }
            else{
                arraysOrdenados[2]=array1;
                arraysOrdenados[1]=array2;
            }
        }


        return arraysOrdenados;
    }
    public static void main(String[] args) throws IOException {
//split
        BufferedImage image = ImageIO.read(new File("/home/reds/Descargas/Tux.png"));

        System.out.println("Original Image Dimension: "+image.getWidth()+"x"+image.getHeight());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
//        String encodedImage = Base64.encode(baos.toByteArray());
//        byte [] data=Base64.decode(encodedImage);
//        String infromacion=Base64.encode(data);
        RaidController controlador=new RaidController();
        System.out.println(controlador.seek("Tux"));
        byte [] data = Base64.decode(controlador.seek("Tux"));
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "png", new File("output.png") );

//        System.out.println("el resultado del scanner es"+controlador.raid5.scanearDiscos());
//        System.out.println("el resultado de la busqueda  es"+controlador.raid5.buscar("Tux-1"));
//        System.out.println("el resultado de la busqueda  es"+controlador.raid5.buscar("Tux-2"));
//        System.out.println("el resultado de la busqueda  es"+controlador.raid5.buscar("Tux-3"));
//        System.out.println("el resultado de la busqueda  es"+controlador.raid5.buscar("Tux-P"));


        //controlador.meterImagen(infromacion,"Tux");
//        if(infromacion.equals(encodedImage)){
//            System.out.println("las imagenes si son iguales perro");
//        }
//        File croppedFile1 = new File("/home/reds/Descargas/primeraParte.png");
//        File croppedFile2 = new File("/home/reds/Descargas/segundaParte.png");
//        File croppedFile3 = new File("/home/reds/Descargas/terceraParte.png");
//        BufferedImage parte1 = ImageIO.read(croppedFile1);
//        BufferedImage parte2 = ImageIO.read(croppedFile2);
//        BufferedImage parte3 =ImageIO.read(croppedFile3);
//
//        ByteArrayOutputStream contenedor1 = new ByteArrayOutputStream();
//        ImageIO.write(parte1, "png", contenedor1);
//
//        ByteArrayOutputStream contenedor2 = new ByteArrayOutputStream();
//        ImageIO.write(parte2, "png", contenedor2);
//
//        ByteArrayOutputStream contenedor3 = new ByteArrayOutputStream();
//        ImageIO.write(parte3, "png", contenedor3);
//
//        System.out.println("el size del array 1 es "+contenedor1.toByteArray().length);
//        System.out.println("el size del array 2 es "+contenedor2.toByteArray().length);
//        System.out.println("el size del array 3 es "+contenedor3.toByteArray().length);
//        byte[][]arraysOrdenados=cualEsmasGrande(contenedor1.toByteArray(),contenedor2.toByteArray(),contenedor3.toByteArray());
//
//        System.out.println("el size del array  1 es "+arraysOrdenados[0].length);
//        System.out.println("el size del array 2 es "+arraysOrdenados[1].length);
//        System.out.println("el size del array 3 es "+arraysOrdenados[2].length);


//
//        //Get the cropped image
//        //ASI ES COMO PARTIMOS LA IMAGEN EN 3 PARTES
//
        BufferedImage primeraParte = image.getSubimage(0, 0, (image.getWidth()/3),image.getHeight());
        BufferedImage segundaParte = image.getSubimage(image.getWidth()/3, 0, image.getWidth()/3, image.getHeight());
        BufferedImage terceraParte = image.getSubimage(image.getWidth()/3*2, 0, image.getWidth()/3, image.getHeight());

        //Create a file to stream the out buffered image to
        File croppedFile1 = new File("/home/reds/Descargas/primeraParte.png");
        File croppedFile2 = new File("/home/reds/Descargas/segundaParte.png");
        File croppedFile3 = new File("/home/reds/Descargas/terceraParte.png");
//
//        //Write the cropped file
//        ImageIO.write(primeraParte, "png", croppedFile1);
//        ImageIO.write(segundaParte, "png", croppedFile2);
//        ImageIO.write(terceraParte, "png", croppedFile3);
//
//
//        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//        //join
//        BufferedImage image1 = ImageIO.read(new File(croppedFile1.getAbsolutePath()));
//        BufferedImage image2 = ImageIO.read(new File(croppedFile2.getAbsolutePath()));
//        BufferedImage image3 = ImageIO.read(new File(croppedFile3.getAbsolutePath()));
//        BufferedImage joined = new BufferedImage(image1.getWidth()*3,image.getHeight(), image1.getType());
//
//
//        Graphics2D graph = joined.createGraphics();
//        graph.drawImage(image1, 0, 0,null);
//        graph.drawImage(image2, joined.getWidth()/3, 0,null);
//        graph.drawImage(image3, joined.getWidth()/3*2, 0,null);
//
//        File joinedFile = new File("/home/reds/Documentos/Tux.png");
//        ImageIO.write(joined, "png", joinedFile);
       // #####################
        //controlador.delete("aleluya");
//        Raid5 test=new Raid5();
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println(s);
//        String input = "Android gave new life to Java";
//        boolean isFound = input.indexOf("gavesa") !=-1? true: false;
//        System.out.println("el valor de is found es "+isFound);
//        String data[]={"Hola","como","Estas","Paridad"};
//       //test.borrar("perrito");
//        test.borrar("pajarito");
//        //test.GuardarInfromacion(data,"pajarito");
//        //test.borrar("1+");
//        System.out.println("El valor es "+ test.buscar("pajaritsoao"));
//        System.out.println("la imagen es "+test.obtenerImagen("perrito"));
//        File[] contents = test.Disco1.listFiles();
//       // System.out.println(contents[1]);
//        String container = "aBcDeFg";
//        String content = "dE";
//        //test.borrar("perrito");
////        System.out.println("estsos son los archivos del disco 1 " +Arrays.toString(contents));
////        String camino =test.Disco1.getAbsolutePath();
////        System.out.println("Este es el camino"+ camino);
////        test.crearArchivo(camino,"ESTA ES LA INFROMACION INTERNA","Prueba");
    }
    public void dividirImagen(){

    }
}
