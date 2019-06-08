import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Metadata {
    private String Name;
    private String Autor;
    private int Date;
    private float Size;
    private String Descripcion;
    private int ID;
    private static int IDGlobal;
    private static Document document;


    public Metadata( String Nombre,String Autor,int AnoCreacion,int Tamano, String Descripcion){
        this.Name=Nombre;
        this.Autor=Autor;
        this.Date =AnoCreacion;
        this.Size=Tamano;
        this.Descripcion=Descripcion;
        this.ID=IDGlobal++;
    }

    /**
     * Carga el ID global guardado
     */
    static void Start(){
        File inputFile = new File("Metadata/input.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Root element :" + document.getRootElement().getName());
        Element classElement = document.getRootElement();

        IDGlobal=Integer.parseInt(classElement.getAttributeValue("ID"));
    }

    /**
     * Guarda el ID global
     */
    static void Close(){
        Element classElement = document.getRootElement();

        System.out.println("###"+IDGlobal);

        classElement.setAttribute("ID",IDGlobal+"");

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            //xmlOutput.output(document, System.out);
            xmlOutput.output(document, new FileWriter("Metadata/input.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update de SQL
     * @param Slots Espacios a modificar
     * @param SlotsValues Valores a colocar
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValues Valores para evaluar el where
     */
    static void Update(ArrayList<String> Slots,ArrayList<String> SlotsValues ,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
        Element classElement = document.getRootElement();
        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        for (Element student : studentList) {
            Element SlotActual;
            for (int i=0; i<SlotsWhere.size(); i++){
                if (student.getChild(SlotsWhere.get(i)).getText().equals(SlotsWhereValues.get(i))){
                    System.out.println("$$$"+student.getChild(SlotsWhere.get(i)).getText());
                    for (int j=0; j<Slots.size(); j++){
                        SlotActual=student.getChild(Slots.get(j));
                        SlotActual.setText(SlotsValues.get(j));
                    }
                    break;
                }
            }
        }
    }

    /**
     * Insert de SQL
     * @param Slots Espacios a colocar
     * @param SlotsValues Valores a colocar
     */
    static void Insert(ArrayList<String> Slots, ArrayList<String> SlotsValues){
        Element classElement = document.getRootElement();

        ArrayList<String> Aux;
        Aux=new ArrayList<>();
        Aux.add("ID");
        Aux.add("name");
        Aux.add("autor");
        Aux.add("date");
        Aux.add("size");
        Aux.add("description");

        Element Nuevo=new Element("image"+IDGlobal);

        Element tmp;
        for (int j=0; j<Aux.size(); j++){
            tmp=new Element(Aux.get(j));
            tmp.setText("null");
            Nuevo.addContent(tmp);
        }

        tmp=Nuevo.getChild("ID");
        tmp.setText(""+IDGlobal++);

        for (int j=0; j<Slots.size(); j++){
            tmp=Nuevo.getChild(Slots.get(j));
            tmp.setText(SlotsValues.get(j));
        }

        classElement.addContent(Nuevo);
    };

    /**
     * Delete de SQL
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValues Valores para evaluar el where
     */
    static void Delete(ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");


        for (int i=0;i<studentList.size();i++) {
            Element student=studentList.get(i);
            for (int j=0; j<SlotsWhere.size(); j++){
                //System.out.println("$$$"+student.getChild(SlotsWhere.get(j)).getText()+"$$$"+SlotsValues.get(j));
                if (student.getChild(SlotsWhere.get(j)).getText().equals(SlotsWhereValues.get(j))){
                    classElement.removeChild(student.getName());
                    i--;
                }
            }
        }

    }

    /**
     * Select de SQL con ALL
     */
    static void Select(){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        ArrayList<String> Slots=new ArrayList<String>();
        Slots.add("ID");
        Slots.add("name");
        Slots.add("autor");
        Slots.add("date");
        Slots.add("size");
        Slots.add("description");

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

    /**
     * Select de SQL
     * @param Slots Espacios a ver
     */
    static void Select(ArrayList<String> Slots){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");


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

    /**
     * Select de SQL
     * @param Slots Espacios a ver
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValues Valores para evaluar el where
     */
    static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots==null){
            Slots=new ArrayList<String>();
            Slots.add("ID");
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
                if (!student.getChild(SlotsWhere.get(i)).getText().equals(SlotsWhereValues.get(i))){
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

    /**
     * Select de SQL
     * @param Slots Espacios a ver
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValuesA Valores para evaluar mínimo en el where
     * @param SlotsWhereValuesB Valores para evaluar máximo en el where
     */
    static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValuesA,ArrayList<String> SlotsWhereValuesB){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots==null){
            Slots=new ArrayList<String>();
            Slots.add("ID");
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
            float value;
            float valueA;
            float valueB;

            for (int i=0; i<SlotsWhere.size(); i++){
                value=Float.parseFloat(student.getChild(SlotsWhere.get(i)).getText());
                valueA=Float.parseFloat(SlotsWhereValuesA.get(i));
                valueB=Float.parseFloat(SlotsWhereValuesB.get(i));
                if (valueA>value || value>valueB){
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
        String out="ID de la imagen: %s\n" +
                "Nombre de la imagen: %s\n" +
                "Autor: %s\n" +
                "Año de creación: %d\n" +
                "Tamaño (KB): %f\n" +
                "Descripción: %s\n" +
                "============================\n";
        out=String.format(out,ID,Name, Autor, Date, Size, Descripcion);
        System.out.println(out);
    }
}
