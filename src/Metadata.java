import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Metadata {
    private String Nombre;
    private String Autor;
    private int AnoCreacion;
    private float Tamano;
    private String Descripcion;


    public Metadata( String Nombre,String Autor,int AnoCreacion,int Tamano, String Descripcion){
        this.Nombre=Nombre;
        this.Autor=Autor;
        this.AnoCreacion=AnoCreacion;
        this.Tamano=Tamano;
        this.Descripcion=Descripcion;
    }

    public static void Select(ArrayList<String> Slots){
        File inputFile = new File("Metadata/input.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Root element :" + document.getRootElement().getName());
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots==null){
            Slots=new ArrayList<String>();
            Slots.add("name");
            Slots.add("autor");
            Slots.add("date");
            Slots.add("size");
            Slots.add("description");
        }

        for (String slot: Slots) {
            System.out.format("%-15s", slot);
        }
        System.out.println();

        for (Element student : studentList) {
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
            }
            System.out.println();
        }
    }

    public static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsValues){
        File inputFile = new File("Metadata/input.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Root element :" + document.getRootElement().getName());
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots==null){
            Slots=new ArrayList<String>();
            Slots.add("name");
            Slots.add("autor");
            Slots.add("date");
            Slots.add("size");
            Slots.add("description");
        }

        for (String slot: Slots) {
            System.out.format("%-15s", slot);
        }
        System.out.println();

        for (Element student : studentList) {
            boolean Where=true;

            for (int i=0; i<SlotsWhere.size(); i++){
                if (!student.getChild(SlotsWhere.get(i)).getText().equals(SlotsValues.get(i))){
                    Where=false;
                    break;
                }
            }

            if (!Where) continue;
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
            }

            System.out.println();
        }
    }


    public void print(){
        String out="Nombre de la imagen: %s\n" +
                "Autor: %s\n" +
                "Año de creación: %d\n" +
                "Tamaño (KB): %f\n" +
                "Descripción: %s\n" +
                "============================\n";
        out=String.format(out,Nombre, Autor, AnoCreacion, Tamano, Descripcion);
        System.out.println(out);
    }
}
