package bt.pht.dao.parser.dom;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.domain.Catalog;
import by.htp.rent.domain.Equipment;

import static by.pht.rent.dao.parser.DataTypeTransformUnil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.sun.org.apache.xerces.internal.parser.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CatalogDataDomImp implements CatalogData {

	private static final String XML_FILE_PATH = "recources/rent_station.xml";

	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();
		// DOMParser parser = new DOMParser();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(XML_FILE_PATH);
			catalog.setEquipments(parseDocument(document));

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return catalog;
	}

	private List<Equipment> parseDocument(Document document){
		
		List<Equipment> list = new ArrayList<Equipment>();
		Element root = document.getDocumentElement();
		
		NodeList nodes = root.getElementsByTagName("equipment");
		Equipment equipment = null;
		
		for(int i=0; i<nodes.getLength(); i++) {
			equipment = new Equipment();
			Element currentnode = (Element)nodes.item(i);
			String id = currentnode.getAttribute("id");
			equipment.setId(convertId(id));
			
			Element element = getSingleChild(currentnode, "title");		
			String title = element.getTextContent();
			equipment.setTitle(title);
			
			element = getSingleChild(currentnode, "price");
			String price = element.getTextContent().trim();
			equipment.setPrice(convertPrice(price));
			
			element = getSingleChild(currentnode, "date");
			String date = element.getTextContent().trim();
			equipment.setDate(convertDate(date));
			
			list.add(equipment);
			
		}
		return list;
		
	}
	private Element getSingleChild(Element node, String name) {
		NodeList nodelist = node.getElementsByTagName(name);
		Element childelement = (Element)nodelist.item(0);
		
		return childelement;		
	}

}
