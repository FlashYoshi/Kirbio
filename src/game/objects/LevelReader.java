package game.objects;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Titouan Vervack
 */
public class LevelReader {

    public LevelReader(World world, File path) {
        try {
            Document document = new SAXBuilder().build(path);
            Element rootNode = document.getRootElement();
            List blocks = rootNode.getChildren("block");
            List flags = rootNode.getChildren("flag");

            //Blokken
            for (int i = 0; i < blocks.size(); i++) {
                Element el = (Element) blocks.get(i);
                int x = Integer.valueOf(el.getAttributeValue("x")).intValue();
                int y = Integer.valueOf(el.getAttributeValue("y")).intValue();
                int width = Integer.valueOf(el.getAttributeValue("width")).intValue();
                int height = Integer.valueOf(el.getAttributeValue("height")).intValue();
                boolean visible = Boolean.valueOf(el.getAttributeValue("visible")).booleanValue();
                world.addBlock(x, y, width, height, visible);
            }
            
            //Vlaggen
            for (int i = 0; i < flags.size(); i++) {
                Element el = (Element) flags.get(i);
                int x = Integer.valueOf(el.getAttributeValue("x")).intValue();
                int y = Integer.valueOf(el.getAttributeValue("y")).intValue();
                boolean visible = Boolean.valueOf(el.getAttributeValue("visible")).booleanValue();
                world.addFlag(x, y, visible);
            }
        } catch (IOException ioexep) {
            System.out.println("IO @ Parser");
        } catch (JDOMException jdomexep) {
            System.out.println("JDOM @ Parser");
        }
    }
}