package by.htp.rent.dao.parser.sax;

import static by.pht.rent.dao.parser.DataTypeTransformUnil.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import by.htp.rent.domain.Equipment;
import by.pht.rent.dao.parser.CatalogTagName;

public class CatalogDataHandler extends DefaultHandler {

	private StringBuilder text;
	private Equipment equipment;
	private List<Equipment> equipments = new ArrayList<>();
	
	private static final char UNDERSCORE =  '_';
	private static final char DASH =  '-';
	private static final String ID = "id";
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		CatalogTagName tag = getTag(localName);
		switch (tag) {
		case EQUIPMENT:
			equipment = new Equipment();
			String id = attributes.getValue("id");
			equipment.setId(convertId(id));
			break;
		}
		text = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {	
		
		CatalogTagName tag = getTag(localName);
		switch (tag) {
		case EQUIPMENT:
			equipments.add(equipment);
			break;
		case TITLE:
			equipment.setTitle(text.toString().trim());
			break;
		case PRICE:
			String price = text.toString().trim();
			equipment.setPrice(convertPrice(price));
			break;
		case DATE:
				String date =  text.toString().trim();
				equipment.setDate(convertDate(date));
				break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text.append(ch, start, length);
		System.out.println("characters:" + text);
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
	
	private CatalogTagName getTag(String localName) {
		String tag = localName.toUpperCase().replace(DASH, UNDERSCORE);
		CatalogTagName tagElement = CatalogTagName.valueOf(tag);
		return tagElement;
		}

}