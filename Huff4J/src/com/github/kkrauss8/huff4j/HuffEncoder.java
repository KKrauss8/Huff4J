package com.github.kkrauss8.huff4j;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.BitSet;

import java.io.ByteArrayOutputStream;

/*
 * Implementation of the generic Huffman encoding
 * algorithm which uses variable-length coding.
 */
public class HuffEncoder {
	
	private static final char NULL_CHAR = '\0'; // Null character used to indicate interior tree node

	private Map<Character, HuffCode> huffCodeMap;
	private Map<Character, Integer> frequencyMap;
	private HuffNode root;
	
	public HuffEncoder() {
		huffCodeMap = new HashMap<Character, HuffCode>();
		frequencyMap = new HashMap<Character, Integer>();
		root = null;
	} 
	

	private void createFrequencyMap(String text) {
		for (char character : text.toCharArray()) {
			Integer frequency = frequencyMap.get(character);
			frequencyMap.put(character, frequency == null ? 1 : frequency + 1);
		}
	}
	
	
	private void createHuffmanTree() {
		Queue<HuffNode> queue = new PriorityQueue<>();
		
		// Create Leaf Nodes
		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			HuffNode node = new HuffNode(entry.getKey(), entry.getValue(), null, null);
			
			queue.add(node);
		}

		// Create Interior Nodes
		while (queue.size() > 1) {
			HuffNode left = queue.poll();
			HuffNode right = queue.poll();

			int weight = left.getWeight() + right.getWeight();
			HuffNode parent = new HuffNode(NULL_CHAR, weight, left, right);

			queue.add(parent);
		}

		root = queue.poll();
	}
	
	
	private void createHuffmanCodes(HuffNode node, HuffCode code) {
		if (node.getElement() != NULL_CHAR) {
			huffCodeMap.put(node.getElement(), code);
		} else {
			createHuffmanCodes(node.getLeftChild(), code.writeBit(false));
			createHuffmanCodes(node.getRightChild(), code.writeBit(true));
		}
	}
	
	
	// Helps create the compressed result
	private byte[] createEncodedResultFromInput(String text) {
		BitSet output = new BitSet();
		int outputLength = 0;
		
		for (char character : text.toCharArray()) {
			HuffCode huffCode = huffCodeMap.get(character);
			BitSet codeData = huffCode.getHuffCode();
			int codeLength = huffCode.getCodeLength();

			for (int i = 0; i < codeLength; i++) {
				output.set(outputLength++, codeData.get(i));
			}

		}
		
		return output.toByteArray();
	}
	
	
	// Used to rebuild tree during decoding process.
	private byte[] frequencyMappingsIntoByteArray() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			char character = entry.getKey();
			int frequency = entry.getValue();
			
			output.write(character);

			for (int i = 0; i < 4; i++) {
				output.write(frequency);
				frequency >>= 8;
			}
		}
		
		return output.toByteArray();
	}
	
	
	public byte[] encode(String text) {
		if (text == null) {
			throw new IllegalArgumentException("String cannot be null.");
		}

		if (text.isEmpty()) {
			throw new IllegalArgumentException("String cannot be empty");
		}

		if (text.isBlank()) {
			throw new IllegalArgumentException("String contains no alphanumeric characters");
		}
		
		createFrequencyMap(text);
		createHuffmanTree();
		createHuffmanCodes(root, new HuffCode());
		
		return createEncodedResultFromInput(text);
	}

	
	public byte[] getCharFrequencyData() {
		return frequencyMappingsIntoByteArray();
	}
	
}
