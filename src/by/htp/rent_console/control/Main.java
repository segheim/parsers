package by.htp.rent_console.control;

import bt.pht.dao.parser.dom.CatalogDataDomImp;
import by.htp.rent.dao.CatalogData;
import by.htp.rent.dao.parser.sax.CatalogDataSaxImpl;
import by.htp.rent.domain.Catalog;

public class Main {

	public static void main(String[] args) {


		//CatalogData dao = new CatalogDataSaxImpl();
		CatalogData dao = new CatalogDataDomImp();
		Catalog catalog = dao.readCatalog();
		
		System.out.println(catalog);

	}

}