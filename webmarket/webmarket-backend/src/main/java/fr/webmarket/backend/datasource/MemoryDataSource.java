package fr.webmarket.backend.datasource;

import java.io.IOException;

import fr.webmarket.backend.marshalling.ImageToBase64Transformer;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemCatalog;
import fr.webmarket.backend.model.ItemTag;

public class MemoryDataSource {

	private static MemoryDataSource INSTANCE;

	private ItemCatalog catalog;

	private MemoryDataSource() {
		catalog = new ItemCatalog();
		initMockData();
	}

	public static MemoryDataSource getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MemoryDataSource();
		}
		return INSTANCE;
	}

	private void initMockData() {

		// Initialize default image
		String imagePath = "src/main/resources/frontend/static/default-image.png";
		String base64Image = null;
		try {
			base64Image = ImageToBase64Transformer.transform(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Initialize mock objects
		String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae ultrices quam. Sed eu ante at ipsum ultrices volutpat vel in nibh. Nullam at ipsum eu massa ultrices euismod. Proin ligula dolor, tincidunt eu viverra id, ultricies sit amet risus. Sed et eros vel ligula fermentum aliquam et at lorem.";

		ItemTag electroMenager = new ItemTag("Electroménager");
		ItemTag aspirateur = new ItemTag("Aspirateur");
		ItemTag laveVaisselle = new ItemTag("Lave-Vaisselle");
		ItemTag lecteurSalon = new ItemTag("Lecteur Salon");
		ItemTag hifi = new ItemTag("HiFi");
		ItemTag baladeur = new ItemTag("Baladeur");

		Item item1 = new Item("Lave-Vaisselle PV123", "Miele", description, 250);
		item1.getTags().add(electroMenager);
		item1.getTags().add(laveVaisselle);
		item1.setBase64Image(base64Image);

		Item item2 = new Item("Lave-Vaisselle", "Wirpool", description, 190);
		item2.getTags().add(electroMenager);
		item2.getTags().add(laveVaisselle);
		item2.setBase64Image(base64Image);

		Item item3 = new Item("iPod", "Apple", description, 340);
		item3.getTags().add(hifi);
		item3.getTags().add(baladeur);
		item3.setBase64Image(base64Image);

		Item item4 = new Item("Lecteur Blu-Ray", "Samsung", description, 190);
		item4.getTags().add(hifi);
		item4.getTags().add(lecteurSalon);
		item4.setBase64Image(base64Image);

		Item item5 = new Item("Aspirateur SupraPlus", "Dyson", description, 450);
		item5.getTags().add(aspirateur);
		item5.getTags().add(electroMenager);
		item5.setBase64Image(base64Image);

		Item item6 = new Item("Lave-Vaisselle PV567", "Miele", description, 280);
		item6.getTags().add(electroMenager);
		item6.getTags().add(laveVaisselle);
		item6.setBase64Image(base64Image);

		catalog.addItem(item1);
		catalog.addItem(item2);
		catalog.addItem(item3);
		catalog.addItem(item4);
		catalog.addItem(item5);
		catalog.addItem(item6);

		System.out.println(catalog.getItems());
	}

	public ItemCatalog getCatalog() {
		return catalog;
	}

}
