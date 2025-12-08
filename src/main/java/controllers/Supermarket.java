package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Aisle;
import models.FloorArea;
import models.GoodItem;
import models.Shelf;
import utils.MyLinkedList;

import java.io.*;

public class Supermarket {
    private MyLinkedList<FloorArea> floorAreas;
    private File file;

    public Supermarket() {
        floorAreas = new MyLinkedList<>();
        file = new File("supermarket_save.xml");
    }

    public FloorArea addFloorArea(String title, int level) {
        if (title.isEmpty() || level == -1) return null;

        for (FloorArea floorArea : floorAreas) {
            if (floorArea.getTitle().equals(title) && floorArea.getLevel() == level) {
                return null;
            }
        }

        FloorArea newArea = new FloorArea(title, level);
        floorAreas.add(newArea);

        return newArea;
    }

    public MyLinkedList<FloorArea> getFloorAreas() {
        return floorAreas;
    }

    public FloorArea getFloorArea(int index) {
        return floorAreas.get(index);
    }

    public FloorArea getFloorArea(String name) {
        for (int i = 0; i < floorAreas.size(); i++) {
            if (floorAreas.get(i).getTitle().equals(name)) {
                return floorAreas.get(i);
            }
        }

        return null;
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(this);
        os.close();
    }

    public Supermarket load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[] {
                Supermarket.class,
                FloorArea.class,
                Aisle.class,
                Shelf.class,
                GoodItem.class };

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));

        Supermarket supermarket = (Supermarket) in.readObject();
        in.close();

        return supermarket;
    }

    public void clear() {
        floorAreas.clear();

        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
