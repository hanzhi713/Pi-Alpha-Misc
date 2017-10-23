package music;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MusicXMLParser {
    public static void main(String[] args) throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("C:\\Users\\asus\\Desktop\\IdeaProjects\\Robot\\src\\music\\summer.xml"));
        //获取文档根节点
        Element root = document.getRootElement();
        //输出根标签的名字
        System.out.println(root.getName());

        Element score = root.element("part");
        Map<Integer, Entry<int[], int[]>> mapHigh = new HashMap<>();
        Map<Integer, Entry<int[], int[]>> mapLow = new HashMap<>();
        for (Element e: score.elements()){
            System.out.println(e.getName());
            System.out.println(e.attribute("number").getValue());
        }
    }
}
