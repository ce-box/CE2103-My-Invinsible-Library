package main.com.tec.MILIB_DB.domain;

// Libraries
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

/**
 *
 */
public class Metadata {

    private static int IDGlobal;
    private static Document document;
    private static String file_path;

    public static ArrayList<ArrayList<String>> getSelectList() {
        return SelectList;
    }

    private static ArrayList<ArrayList<String>> SelectList;

    public static void setFile_path(String file_path){
        Metadata.file_path = file_path;
    }

    /**
     * Carga el ID global guardado [BACK]
     */
    public static void Start(){
        SelectList=new ArrayList<>();

        // Se debe colocar el path completo segun la maquina
        File inputFile = new File(file_path);
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
     * Guarda el ID global [COMMIT]
     */
    public static void Close(){
        Element classElement = document.getRootElement();

        System.out.println("###"+IDGlobal);

        classElement.setAttribute("ID",IDGlobal+"");

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            //xmlOutput.output(document, System.out);
            xmlOutput.output(document, new FileWriter(file_path));
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
    public static void Update(ArrayList<String> Slots,ArrayList<String> SlotsValues ,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
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
    public static void Insert(ArrayList<String> Slots, ArrayList<String> SlotsValues){

        System.out.println("ENTRA");

        Element classElement = document.getRootElement();

        System.out.println("PASA ELEMENT");

        ArrayList<String> Aux;
        Aux=new ArrayList<>();
        Aux.add("ID"); // ID
        Aux.add("name"); // name
        Aux.add("author"); // author
        Aux.add("date");// date
        Aux.add("size"); // size
        Aux.add("description");// description

        System.out.println("PASA LA LISTA");

        Element Nuevo=new Element("image"+IDGlobal);

        System.out.println("CREA EL ELEMENT");

        Element tmp;
        for (int j=0; j<Aux.size(); j++){

            System.out.println("FOR 1 "+j);

            tmp=new Element(Aux.get(j));
            tmp.setText("null");
            Nuevo.addContent(tmp);
        }

        tmp=Nuevo.getChild("ID");
        tmp.setText(""+IDGlobal++);

        System.out.println("PASA el For 1 Va para el for 2");

        for (int j=0; j<Slots.size(); j++){

            System.out.println("FOR 2 "+j);

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
    public static void Delete(ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
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
    public static void Select(){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        ArrayList<String> Slots=new ArrayList<String>();
        Slots.add("ID");
        Slots.add("name");
        Slots.add("author");
        Slots.add("date");
        Slots.add("size");
        Slots.add("description");

        ArrayList<String> tmp= new ArrayList<>();

        for (String slot: Slots) {
            tmp.add(slot);
            System.out.format("%-15s", slot);
        }
        SelectList.add(tmp);
        System.out.println();


        for (Element student : studentList) {
            tmp= new ArrayList<>();
            for (String slot : Slots) {
                tmp.add(slot);
                System.out.format("%-15s", student.getChild(slot).getText());
            }
            SelectList.add(tmp);
            System.out.println();
        }
    }

    /**
     * Update de SQL
     * @param Slots Espacios a ver
     */
    public static void Select(ArrayList<String> Slots){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        ArrayList<String> tmp= new ArrayList<>();

        for (String slot: Slots) {
            tmp.add(slot);
            System.out.format("%-15s", slot);
        }
        SelectList.add(tmp);
        System.out.println();


        for (Element student : studentList) {
            tmp= new ArrayList<>();
            for (String slot : Slots) {
                tmp.add(slot);
                System.out.format("%-15s", student.getChild(slot).getText());
            }
            SelectList.add(tmp);
            System.out.println();
        }
    }

    /**
     * Update de SQL
     * @param Slots Espacios a ver
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValues Valores para evaluar el where
     */
    public static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValues){
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots.isEmpty()){
            Slots=new ArrayList<String>();
            Slots.add("ID");
            Slots.add("name");
            Slots.add("author");
            Slots.add("date");
            Slots.add("size");
            Slots.add("description");
        }

        ArrayList<String> tmp= new ArrayList<>();

        for (String slot: Slots) {
            tmp.add(slot);
            System.out.format("%-15s", slot);
        }
        SelectList.add(tmp);
        System.out.println();

        for (Element student : studentList) {
            boolean Where=true;

            for (int i=0; i<SlotsWhere.size(); i++){
                if (!student.getChild(SlotsWhere.get(i)).getText().equals(SlotsWhereValues.get(i))){
                    Where=false;
                    break;
                }
            }

            tmp=new ArrayList<>();

            if (!Where) continue;
            for (String slot : Slots) {
                tmp.add(slot);
                System.out.format("%-15s", student.getChild(slot).getText());
            }
            SelectList.add(tmp);
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
    static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValuesA,ArrayList<String> SlotsWhereValuesB) {
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots == null) {
            Slots = new ArrayList<String>();
            Slots.add("ID");
            Slots.add("name");
            Slots.add("autor");
            Slots.add("date");
            Slots.add("size");
            Slots.add("description");
        }

        ArrayList<String> tmp= new ArrayList<>();

        for (String slot: Slots) {
            tmp.add(slot);
            System.out.format("%-15s", slot);
        }
        SelectList.add(tmp);
        System.out.println();

        for (Element student : studentList) {
            boolean Where = true;
            float value;
            float valueA;
            float valueB;

            for (int i = 0; i < SlotsWhere.size(); i++) {
                value = Float.parseFloat(student.getChild(SlotsWhere.get(i)).getText());
                valueA = Float.parseFloat(SlotsWhereValuesA.get(i));
                valueB = Float.parseFloat(SlotsWhereValuesB.get(i));
                if (valueA > value || value > valueB) {
                    Where = false;
                    break;
                }
            }

            tmp=new ArrayList<>();

            if (!Where) continue;
            for (String slot : Slots) {
                tmp.add(slot);
                System.out.format("%-15s", student.getChild(slot).getText());
            }
            SelectList.add(tmp);
            System.out.println();
        }
    }

}
