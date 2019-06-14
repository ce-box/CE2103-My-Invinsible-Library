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
 * This class is responsible of the image metadata storage in MILIB Database
 * @author Juan Pablo
 * @author Esteban
 * @version alpha 3.5
 */
public class Metadata {

    // Networking configuration
    private static int IDGlobal;
    private static Document document;

    private static ArrayList<String> Aux;

    public static String getSelectList() {
        return SelectList;
    }

    private static String SelectList;

    private static String file_path="/home/esteban/Documentos/TEC/1S 2019/Algoritmos y estructuras de datos II/4. Proyectos/Proyecto #3/Source/MyInvensibleLibrary/Servidor MetaData/XML_Metadata/input.xml";

    public static void setFile_path(String file_path){
        Metadata.file_path = file_path;
    }

    public static void main(String[] args){
        Metadata.Start();
        ArrayList<String> L1=new ArrayList<>();
        L1.add("date");
        L1.add("ID");
        ArrayList<String> L4=new ArrayList<>();
        L4.add("7");
        ArrayList<String> L2=new ArrayList<>();
        L2.add("ID");
        ArrayList<String> L3=new ArrayList<>();
        L3.add("10");
        Metadata.Select(L1,L2,L3,L4);
        System.out.println(Metadata.verifySlotsRange(L1) );
        Metadata.Close();
    }

    /**
     * Toma una lista de slots y se asegura que existan en el xml
     * @param Slots
     * @return mensaje de error
     */
    public static String verifySlots(ArrayList<String> Slots){
        for (String slot:Slots){
            if(Aux.indexOf(slot)==-1){
                return "La columna \""+slot+"\" no existe";
            }
        }
        return "";
    }

    /**
     * Toma una lista de slots y se asegura que sean float en el xml
     * @param Slots
     * @return mensaje de error
     */
    public static String verifySlotsRange(ArrayList<String> Slots){
        for (String slot:Slots){
            if(!(slot.equals("ID") || slot.equals("size") || slot.equals("date"))){
                return "La columna \""+slot+"\" no es válida para un rango";
            }
        }
        return "";
    }

    /**
     * Toma una lista de slots y se asegura que no sean ID en el xml
     * @param Slots
     * @return mensaje de error
     */
    public static String verifySlotsNoID(ArrayList<String> Slots){
        for (String slot:Slots){
            if(slot=="ID"){
                return "ID no es una columna válida para modificar";
            }
        }
        return "";
    }

    /**
     * Carga el ID global guardado [BACK]
     */
    public static void Start(){
        Aux=new ArrayList<>();
        Aux.add("ID"); // ID
        Aux.add("name"); // name
        Aux.add("author"); // author
        Aux.add("date");// date
        Aux.add("size"); // size
        Aux.add("description");// description
        Aux.add("gallery");

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
    public static String Insert(ArrayList<String> Slots, ArrayList<String> SlotsValues){

        System.out.println("ENTRA");

        Element classElement = document.getRootElement();

        System.out.println("PASA ELEMENT");

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
        tmp.setText(""+IDGlobal);

        System.out.println("PASA el For 1 Va para el for 2");

        for (int j=0; j<Slots.size(); j++){

            System.out.println("FOR 2 "+j);

            tmp=Nuevo.getChild(Slots.get(j));
            tmp.setText(SlotsValues.get(j));
        }

        classElement.addContent(Nuevo);

        return ""+IDGlobal++;
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

        ArrayList<String> Slots=Aux;

        SelectList="";

        for (String slot: Slots) {
            SelectList+=slot+",";
            System.out.format("%-15s", slot);
        }
        SelectList = SelectList.substring(0, SelectList.length() - 1);
        System.out.println();


        for (Element student : studentList) {
            SelectList+="-";
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
                SelectList+=student.getChild(slot).getText()+",";
            }
            System.out.println();
            SelectList = SelectList.substring(0, SelectList.length() - 1);
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

        if (Slots.isEmpty()){
            Slots=Aux;
        }

        SelectList="";

        for (String slot: Slots) {
            SelectList+=slot+",";
            System.out.format("%-15s", slot);
        }
        SelectList = SelectList.substring(0, SelectList.length() - 1);
        System.out.println();


        for (Element student : studentList) {
            SelectList+="-";
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
                SelectList+=student.getChild(slot).getText()+",";
            }
            System.out.println();
            SelectList = SelectList.substring(0, SelectList.length() - 1);
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
            Slots=Aux;
        }

        SelectList="";

        for (String slot: Slots) {
            SelectList+=slot+",";
            System.out.format("%-15s", slot);
        }
        SelectList = SelectList.substring(0, SelectList.length() - 1);
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
            SelectList+="-";
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
                SelectList+=student.getChild(slot).getText()+",";
            }
            System.out.println();
            SelectList = SelectList.substring(0, SelectList.length() - 1);
        }
    }

    /**
     * Select de SQL
     * @param Slots Espacios a ver
     * @param SlotsWhere Espacios para evaluar el where
     * @param SlotsWhereValuesA Valores para evaluar mínimo en el where
     * @param SlotsWhereValuesB Valores para evaluar máximo en el where
     */
    public static void Select(ArrayList<String> Slots,ArrayList<String> SlotsWhere,ArrayList<String> SlotsWhereValuesA,ArrayList<String> SlotsWhereValuesB) {
        Element classElement = document.getRootElement();

        List<Element> studentList = classElement.getChildren();
        System.out.println("----------------------------");

        if (Slots == null) {
            Slots = Aux;
        }


        SelectList="";

        for (String slot: Slots) {
            SelectList+=slot+",";
            System.out.format("%-15s", slot);
        }
        SelectList = SelectList.substring(0, SelectList.length() - 1);
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

            if (!Where) continue;
            SelectList+="-";
            for (String slot : Slots) {
                System.out.format("%-15s", student.getChild(slot).getText());
                SelectList+=student.getChild(slot).getText()+",";
            }
            System.out.println();
            SelectList = SelectList.substring(0, SelectList.length() - 1);
        }
    }

}
