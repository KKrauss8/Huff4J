package com.github.kkrauss8.huff4j;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;

import java.io.ByteArrayOutputStream;

/*
 * Implementation of the generic Huffman encoding
 * algorithm which uses variable-length coding.
 */
public class HuffEncoder {
	
	private static final char NCHAR = '\0'; // Null character used to indicate internal tree node
	private Map<Character, byte[]> codes;
	
	
	public HuffEncoder() {} // Constructor doesn't need to do anything. Still included anyway
	

	// Step 1
	private Map<Character, Integer> createFrequencyMap(String text) {
		Map<Character, Integer> map = new HashMap<>();
		
		for (char character : text.toCharArray()) {
			Integer frequency = map.get(character);
			map.put(character, frequency == null ? 1 : frequency + 1);
		}
		
		return map;
	}
	
	
	// Step 2
	private HuffNode createHuffmanTree(Map<Character, Integer> map) {
		Queue<HuffNode> queue = new PriorityQueue<>();
		
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			HuffNode node = new HuffNode(entry.getKey(), entry.getValue(), null, null);
			queue.add(node);
		}
		
		while (queue.size() > 1) {
			HuffNode left = queue.poll();
			HuffNode right = queue.poll();
			
			int weight = left.getWeight() + right.getWeight();
			HuffNode parent = new HuffNode(NCHAR, weight, left, right);
			
			queue.add(parent);
		}
		
		return queue.poll();
	}
	
	
	// Step 3
	private void createHuffmanCodes(HuffNode node, HuffCode code) {
		if (node.isLeaf()) {
			codes.put(node.getElement(), code.getHuffCode());
		}
		
		createHuffmanCodes(node.getLeftChild(), code.writeBit(false));
		createHuffmanCodes(node.getRightChild(), code.writeBit(true));
	}
	
	
	// Helps create the compressed result
	private byte[] createEncodedResultFromInput(String text) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		for (char character : text.toCharArray()) {
			byte[] bytes = codes.get(character);
			output.writeBytes(bytes);
		}
		
		return output.toByteArray();
	}
	
	
	// Creates and formats the character/huffman code association data
	// TODO: Formatting needs to be done
	private byte[] createTreeCodeData() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		for (Map.Entry<Character, byte[]> entry : codes.entrySet()) {
			char character = entry.getKey();
			byte[] code = entry.getValue();
			
			output.write(character);
			output.writeBytes(code);
		}
		
		return output.toByteArray();
	}
	
	
	
	public HuffEncodedResult encode(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Argument cannot be null.");
		}
		
		Map<Character, Integer> map = createFrequencyMap(text);
		HuffNode root = createHuffmanTree(map);
		createHuffmanCodes(root, new HuffCode());
		
		byte[] result = createEncodedResultFromInput(text);
		byte[] codes = createTreeCodeData();
		
		return new HuffEncodedResult(result, codes);
	}
	
}
