package fr.webmarket.backend.marshalling;

import java.io.File;
import java.io.IOException;

import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

public class MarshallingUtils {

	private static final BaseEncoding BASE64_ENCODING = BaseEncoding.base64();

	public static String transformImageToBase64(String path) throws IOException {

		byte[] image = Files.toByteArray(new File(path));
		return BASE64_ENCODING.encode(image);
	}
}
