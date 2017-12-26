package bt.pht.dao.parser.Stax;

import static by.pht.rent.dao.parser.DataTypeTransformUnil.convertId;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.domain.Catalog;
import by.htp.rent.domain.Equipment;
import by.pht.rent.dao.parser.CatalogTagName;

public class CatalogDataStaxImpl implements CatalogData{

	private static final char UNDERSCORE =  '_';
	private static final char DASH =  '-';
	private static final String ID = "id";
	
	private static final String XML_FILE_PATH = "recources/rent_station.xml";
	
	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();
		XMLInputFactory xmlF = XMLInputFactory.newInstance();
		
		try {
			InputStream steam = new FileInputStream(XML_FILE_PATH);
			XMLStreamReader reader = xmlF.createXMLStreamReader(steam);
			List<Equipment> equipment = processReader(reader);
			catalog.setEquipments(equipment);
			
		} catch (XMLStreamException | FileNotFoundException e) {
				e.printStackTrace();
		}
		return catalog;
	}

	private List<Equipment> processReader(XMLStreamReader reader) {
		List<Equipment> equipments = new ArrayList<Equipment>();
		Equipment equipment = null;
		CatalogTagName tag = null;
		
		try {
			while(reader.hasNext()) {
				int type = reader.next();
				
				switch(type){
				
				case XMLStreamConstants.START_ELEMENT:
				
					tag = getTag(reader.getLocalName());
				
				switch (tag) {
				case EQUIPMENT:
					equipment = new Equipment();
					String id = reader.getAttributeValue(null, ID);
					equipment.setId(convertId(id));
					break;
				}
				break;
				
				case XMLStreamConstants.CHARACTERS:
					String text = reader.getText().trim();
					if (text.isEmpty()) {
						break;
					}
					switch (tag) {
					case TITLE:
						equipment.getTitle();
						break;
					case PRICE:
						equipment.getPrice();
						break;
					case DATE:
						equipment.getDate();
					break;
					}
					break;
					
				case XMLStreamConstants.END_ELEMENT:
					tag = getTag(reader.getLocalName());
					
					switch (tag) {
					case EQUIPMENT:
						equipments.add(equipment);
					break;
					}	
				}	
				break;
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}		
		return equipments;
	}

private CatalogTagName getTag(String localName) {
	String tag = localName.toUpperCase().replace(DASH, UNDERSCORE);
	CatalogTagName tagElement = CatalogTagName.valueOf(tag);
	return tagElement;
	}
}
