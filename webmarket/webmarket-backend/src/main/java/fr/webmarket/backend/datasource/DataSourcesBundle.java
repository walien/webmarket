package fr.webmarket.backend.datasource;

import fr.webmarket.backend.datasource.impl.MemoryDataSource;

public class DataSourcesBundle {

	public static DataSource getDefaultDataSource() {
		return MemoryDataSource.getInstance();
	}

}
