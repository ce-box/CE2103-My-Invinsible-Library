import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RaidController {
    private  List<String> listaId = new ArrayList<String>();
    private  List<String> listaImagenes = new ArrayList<String>();
    public   Raid5 raid5=new Raid5();

    //###################################################################################################################


    public void writeCommit(String imagen,String id){
        listaImagenes.add(imagen);
        listaId.add(id);
        System.out.println("YA SE INSERTO ALGO SIN COMMIT");
        System.out.println("ESTE ES EL ARRAY DE IMAGENES"+Arrays.toString(listaImagenes.toArray()));
        System.out.println("ESTE ES EL ARRAY DE ID"+Arrays.toString(listaId.toArray()));
        // adds 1 at 0 index
    }


    public  void recuperrar(String id) throws IOException, ClassNotFoundException {
        String Imagen1=id+"-1.png";
        String Imagen2=id+"-2.png";
        String Imagen3=id+"-3.png";
        String [] imagenesOrdenas={Imagen1,Imagen2,Imagen2};
        
        byte[][] informacionDisponible=this.raid5.obtenerInfromacionDisponible(id);
        System.out.println("EL size es"+informacionDisponible[0].length);
        System.out.println("EL size es"+informacionDisponible[1].length);
        System.out.println("EL size es"+informacionDisponible[2].length);
        int numero=raid5.dameElSizeDelArray(id,informacionDisponible[0].length,informacionDisponible[1].length,informacionDisponible[2].length);
        System.out.println("el numero es "+ numero);
        byte[][]infromacionDisponibleOrdenado=this.raid5.cualEsmasGrande(informacionDisponible[0],informacionDisponible[1],informacionDisponible[2]);
        byte[] ParteRecuperado=recuperacion(infromacionDisponibleOrdenado[2],infromacionDisponibleOrdenado[1],infromacionDisponibleOrdenado[0],numero);
        ByteArrayInputStream bis = new ByteArrayInputStream(ParteRecuperado);
        BufferedImage recuperado = ImageIO.read(bis);
        BufferedImage[]OtrasImagenes=(this.raid5.obtenerImagenesRestantes(id));

        if(!raid5.buscar(Imagen1)){
            System.out.println("La que  es la 1");
            BufferedImage bImage2 = raid5.DameImagenEspecifica(Imagen2);
            BufferedImage bImage3 = raid5.DameImagenEspecifica(Imagen3);
            ImageIO.write(recuperado, "png", new File(Imagen1) );
            BufferedImage joined = new BufferedImage(bImage2.getWidth()*3,bImage2.getHeight(), bImage2.getType());
            Graphics2D graph = joined.createGraphics();
            graph.drawImage(recuperado, 0, 0,null);
            graph.drawImage(bImage2, joined.getWidth()/3, 0,null);
            graph.drawImage(bImage3, joined.getWidth()/3*2, 0,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(joined, "png", baos);
            raid5.borrar(id);
            this.Write(Base64.encode(baos.toByteArray()),id);

        }

        if(!raid5.buscar(Imagen2)){
            System.out.println("La que  es la 1");
            BufferedImage bImage1 = raid5.DameImagenEspecifica(Imagen1);
            BufferedImage bImage3 = raid5.DameImagenEspecifica(Imagen3);
            ImageIO.write(recuperado, "png", new File(Imagen2) );
            BufferedImage joined = new BufferedImage(bImage1.getWidth()*3,bImage1.getHeight(), bImage1.getType());
            Graphics2D graph = joined.createGraphics();
            graph.drawImage(bImage1, 0, 0,null);
            graph.drawImage(recuperado, joined.getWidth()/3, 0,null);
            graph.drawImage(bImage3, joined.getWidth()/3*2, 0,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(joined, "png", baos);
            raid5.borrar(id);
            this.Write(Base64.encode(baos.toByteArray()),id);
            System.out.println("La que  es la 2");
            ImageIO.write(recuperado, "png", new File(Imagen2) );
        }
        if(!raid5.buscar(Imagen3)){
            System.out.println("La que  es la 1");
            BufferedImage bImage1 = raid5.DameImagenEspecifica(Imagen1);
            BufferedImage bImage2 = raid5.DameImagenEspecifica(Imagen2);
            ImageIO.write(recuperado, "png", new File(Imagen3) );
            BufferedImage joined = new BufferedImage(bImage1.getWidth()*3,bImage1.getHeight(), bImage1.getType());
            Graphics2D graph = joined.createGraphics();
            graph.drawImage(bImage1, 0, 0,null);
            graph.drawImage(bImage2, joined.getWidth()/3, 0,null);
            graph.drawImage(recuperado, joined.getWidth()/3*2, 0,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(joined, "png", baos);
            raid5.borrar(id);
            this.Write(Base64.encode(baos.toByteArray()),id);
            System.out.println("La que  es la 3");
            ImageIO.write(recuperado, "png", new File(Imagen3) );
        }

        System.out.println("EL size del recuperado es"+ParteRecuperado.length);
    }
    //###################################################################################################################

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

    //###################################################################################################################

    public String seek(String id) throws IOException {
        return raid5.obtenerImagen(id);
    }

    //###################################################################################################################

    public void delete(String idParaEliminar){
        this.raid5.borrar(idParaEliminar);
//        int indiceDeImagenAEliminar=listaId.indexOf(idParaEliminar);
//        listaImagenes.set(indiceDeImagenAEliminar,"0");
//        System.out.println("YA SE ELIMINAR ALGO SIN COMMIT");
//        System.out.println("ESTE ES EL ARRAY DE IMAGENES"+Arrays.toString(listaImagenes.toArray()));
//        System.out.println("ESTE ES EL ARRAY DE ID"+Arrays.toString(listaId.toArray()));
    }

    //###################################################################################################################

    public void Write(String imagenBase64,String id) throws IOException {
        byte [] arrayDeImagen= Base64.decode(imagenBase64);
        ByteArrayInputStream bis = new ByteArrayInputStream(arrayDeImagen);
        BufferedImage image = ImageIO.read(bis);
        BufferedImage primeraParte = image.getSubimage(0, 0, (image.getWidth()/3),image.getHeight());
        BufferedImage segundaParte = image.getSubimage(image.getWidth()/3, 0, image.getWidth()/3, image.getHeight());
        BufferedImage terceraParte = image.getSubimage(image.getWidth()/3*2, 0, image.getWidth()/3, image.getHeight());
        BufferedImage partesDeLaImagen[]={primeraParte,segundaParte,terceraParte};
        ByteArrayOutputStream contenedor1 = new ByteArrayOutputStream();
        ImageIO.write(primeraParte, "png", contenedor1);

        ByteArrayOutputStream contenedor2 = new ByteArrayOutputStream();
        ImageIO.write(segundaParte, "png", contenedor2);
        System.out.println("el size de contendor2 es"+contenedor2.toByteArray().length);

        ByteArrayOutputStream contenedor3 = new ByteArrayOutputStream();
        ImageIO.write(terceraParte, "png", contenedor3);
        byte[][]arraysOrdenados=cualEsmasGrande(contenedor1.toByteArray(),contenedor2.toByteArray(),contenedor3.toByteArray());
        byte [] paridad=calcularParidad(arraysOrdenados[0],arraysOrdenados[1],arraysOrdenados[2]);
        System.out.println("el size de paridad es"+paridad.length);
        this.raid5.GuardarInfromacion(partesDeLaImagen,id,Base64.encode(paridad));


}

//###################################################################################################################

public String[] serializacion(String imagen){
        int size=imagen.length();
        String[] Respuesta = new String[4];
        String binario1=imagen.substring(0,size/3);
        String binario2=imagen.substring(size/3,size/3*2);
        String binario3=imagen.substring(size/3*2,size);
        byte[] bloque1=Base64.decode(binario1);
        byte[] bloque2=Base64.decode(binario2);
        byte[] bloque3=Base64.decode(binario3);
        byte[] paridad=calcularParidad(bloque1,bloque2,bloque3);
        String paridadString=Base64.encode(paridad);
        Respuesta[0]=binario1;
        Respuesta[1]=binario2;
        Respuesta[2]=binario3;
        Respuesta[3]=paridadString;
        return Respuesta;

}
//###################################################################################################################
    public void commit() throws IOException {
        Iterator<String> idActual = listaId.iterator();
        Iterator<String> imagenActual = listaImagenes.iterator();
        while(idActual.hasNext()){
            String id=idActual.next();
            String imagen=imagenActual.next();
            System.out.println("ESTA ES LA IMAGEN "+imagen+"ESTE ES EL ID"+id);
            if(imagen.equals("0")){
               //this.delete(id);
            }
            else{
                String[] data=serializacion(imagen);
                //raid5.GuardarInfromacion(data,id);
                //System.out.println("este es el data"+Arrays.toString(listaImagenes.toArray()));

                //this.write(imagen,data);
            }
        }
    }
    //###################################################################################################################

    public static byte [] XOR(byte array1[],byte array2[]){
        byte[] array2Lleno=rellenar(array1,array2);
        byte[] array_3 = new byte[array1.length];
        for(int i = 0; i < array1.length; i++)
        {
            array_3[i]= (byte) (array1[i] ^ array2Lleno[i]);
        }
        return array_3;
    }
    //###################################################################################################################

    //ESTA FUNCION LO QUE HACE ES RECUPERAR UN BYTE ARRAY A PARTIR DE SU PARIDAD Y OTROS DOS ARRAYS
    public static  byte[] recuperacion(byte[] arrayX,byte[]arrayZ,byte paridad[],int sizeDelArrayParaRecuperar){
        byte[] temp=XOR(paridad,arrayZ);
        byte[]arrayRecuperado = XOR(temp,arrayX);
        return Arrays.copyOfRange(arrayRecuperado, 0, sizeDelArrayParaRecuperar);
    }
    //###################################################################################################################

    //ESTA FUNCION LO QUE HACE ES COMPARAR DOS ARRAY PARA VER SI SON IGUALES
    public static void CompararArray(byte[] bloque1, byte[] bloque2){
        boolean Funciono=true;
        for(int i = 0; i < bloque1.length; i++)
        {
            if(bloque1[i]!=bloque2[i]){
                Funciono=false;
            }
        }
        System.out.println("EL VALOR DE LA PRUEBA ES "+Funciono);
    }

    //###################################################################################################################


    public static byte[] calcularParidad( byte[] bloque1,byte[] bloque2,byte [] bloque3){
        byte[] temp=XOR(bloque1,bloque2);//AQUI LO QUE HACEMOS ES HACER UN XOR ENTRE LOS DOS PRIMEROS ARRAYS
        byte[] paridad=XOR(temp,bloque3);//CON EL RESULTADO LE HACEMOS UNA XOR AL RESTANTE PARA OBTENER EL QUE NOS FALTA
        return paridad;
    }
    //###################################################################################################################



    public  static byte[]rellenar( byte[] bloque1,byte[] bloqueaRellenar){
        byte[]arraycompleto = new byte[bloque1.length];
        int sizeInicial=bloqueaRellenar.length;
        //AQUI LO QUE HACEMOS ES RELLENAR EL NUEVO ARRAY CON LOS ELEMENTOS DEL VIEJO
        for(int i = 0; i < sizeInicial; i++)
        {
            arraycompleto[i]= bloqueaRellenar[i];
        }
        //AQUI LO QUE HACEMOS ES PONER LOS CAMPOS RESTANTES CON LOS DEL PRIMER ARRAY
        for (int i=bloque1.length-1;i>=sizeInicial;i--) {
           // System.out.println("No son del mismo size");
            arraycompleto[i] = 0 ;
        }
        return arraycompleto;
    }
    //###################################################################################################################


}
