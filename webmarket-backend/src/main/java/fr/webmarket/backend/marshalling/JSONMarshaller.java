package fr.webmarket.backend.marshalling;

import org.codehaus.jackson.map.ObjectMapper;

public class JSONMarshaller {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getDefaultMapper() {
		return mapper;
	}

}
