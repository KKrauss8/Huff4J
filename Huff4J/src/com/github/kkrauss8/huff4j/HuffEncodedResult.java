package com.github.kkrauss8.huff4j;

/*
 * Stores the compressed data with
 * the huffman codes for each
 * character.
 */
public class HuffEncodedResult {

	private byte[] encodedData;
	private byte[] huffCodes; // Used to rebuild
	
	public HuffEncodedResult(byte[] encodedData, byte[] huffCodes) {
		this.encodedData = encodedData;
		this.huffCodes = huffCodes;
	}
	
	public byte[] getEncodedData() {
		return encodedData;
	}
	
	public byte[] getHuffCodes() {
		return huffCodes;
	}
	
}
