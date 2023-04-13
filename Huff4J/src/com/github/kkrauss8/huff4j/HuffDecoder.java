package com.github.kkrauss8.huff4j;

import java.util.Map;
import java.util.HashMap;

public class HuffDecoder {

	private Map<Character, Integer> frequencyMap;
	private Map<Charcater, HuffCode> codeMap;

	public HuffDecoder() {
		frequencyMap = new HashMap<>();
		codeMap = new HashMap<>();
	}

	private void rebuildFrequencyMap() {

	}

	private void rebuildHuffmanTree() {

	}

	public String decode(byte[] encodedData, byte[] charFrequencyData) {

	}
	
}
