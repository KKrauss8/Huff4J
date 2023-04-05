package com.github.kkrauss8.huff4j;

import java.util.BitSet;

public class HuffCode {

	private BitSet code;
	private int codeLength;
	
	public HuffCode() {
		this(new BitSet(), 0);
	}
	
	private HuffCode(BitSet code, int codeLength) {
		this.code = code;
		this.codeLength = codeLength;
	}
	
	public HuffCode writeBit(boolean bit) {
		BitSet newCode = (BitSet) code.clone();
		newCode.set(codeLength++, bit);
		
		return new HuffCode(newCode, codeLength);
	}
	
	public byte[] getHuffCode() {
		return code.toByteArray();
	}
	
	public int getCodeLength() {
		return codeLength;
	}
	
}
