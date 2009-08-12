package edu.cornell.dendro.corina.util.labels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;

import edu.cornell.dendro.corina.util.Base64;

public class LabBarcode extends Barcode128 {

	/**
	 * An enum representing the type of this barcode
	 * 
	 * @author Lucas Madar
	 */
	public static enum Type {
		SAMPLE('S'),
		BOX('B'),
		SERIES('Z');
		
		/** The character we prepend to the UUID */
		private char charVal;
		
		Type(char c) {
			charVal = c;
		}
		
		public char getPrefix() {
			return charVal;
		}
		
		public static Type valueOf(char c) {
			for(Type type : values()) {
				if(type.charVal == c)
					return type;
			}
			
			throw new IllegalArgumentException("Invalid type");
		}
	}
	
	/** The font we use to label the barcode */
	private static Font barcodeFont = FontFactory.getFont(FontFactory.COURIER);

	/**
	 * Construct a new Lab Barcode representing a given UUID and UUID type
	 * 
	 * @param uuidType One of LabBarcode.Type
	 * @param uuid The UUID to encode
	 */
	public LabBarcode(Type uuidType, UUID uuid) {
		super();
				
		setCode(encode(uuidType, uuid));
		setAltText(uuidType + " " + uuid.toString());
		setCodeType(Barcode.CODE128);
		setFont(barcodeFont.getBaseFont());
	}

	/**
	 * Encode a UUID and Type into base64
	 * @param uuidType
	 * @param uuid
	 * @returns a string representing the base64 uuid
	 */
	public static String encode(Type uuidType, UUID uuid) {
		// convert the uuid into a raw byte array
		ByteArrayOutputStream bbis = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bbis);
		
		String b64enc;
		try {
			dos.writeByte((byte) uuidType.getPrefix());
			dos.writeByte((byte) ':');
			
			// start with high bits... is this proper?
			dos.writeLong(uuid.getMostSignificantBits());
			dos.writeLong(uuid.getLeastSignificantBits());
			
			b64enc = Base64.encodeBytes(bbis.toByteArray());
		} catch (IOException e) {
			// this should never happen, since it's a ByteArrayOutputStream
			throw new RuntimeException(e);
		}
		
		return b64enc;
	}
	
	/**
	 * Silly class for a return value when decoding lab barcodes
	 * @author Lucas Madar
	 */
	public static class DecodedBarcode {
		public Type uuidType;
		public UUID uuid;
	}
	
	/**
	 * Decodes an encoded barcode
	 * 
	 * @param b64enc
	 * @return A decoded barcode structure
	 */
	public static DecodedBarcode decode(String b64enc) {
		byte[] bytes;
		
		try {
			bytes = Base64.decode(b64enc);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid barcode value (not base64)");
		}
		
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			char type = (char) dis.readByte();
			char colon = (char) dis.readByte();
			long msb = dis.readLong();
			long lsb = dis.readLong();
			
			if(colon != ':')
				throw new IllegalArgumentException("Barcode is not a Corina barcode (invalid format)");
			
			DecodedBarcode barcode = new DecodedBarcode();
			
			// load uuid
			barcode.uuid = new UUID(msb, lsb);
			try {
				barcode.uuidType = Type.valueOf(type);
			} catch (IllegalArgumentException iae) {
				throw new IllegalArgumentException("Invalid barcode type: " + type);
			}
			
			return barcode;
		} catch (IOException e) {
			throw new IllegalArgumentException("Barcode is not a Corina barcode");
		}
	}
}
