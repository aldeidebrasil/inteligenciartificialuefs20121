package algoritmogenetico;

import java.util.BitSet;

public class Testes {

	public static void main(String args[]){
		BitSet bits1 = new BitSet();
		BitSet bits2 = new BitSet();
		// set some bits
		for(int i=0; i<bits1.size(); i++) {
		if((i%2) == 0) bits1.set(i);
		if((i%5) != 0) bits2.set(i);
		}
		System.out.println("Initial pattern in bits1: ");
		System.out.println(bits1);
		System.out.println("\\nInitial pattern in bits2: ");
		System.out.println(bits2);
		// AND bits
		bits2.and(bits1);
		System.out.println("\\nbits2 AND bits1: ");
		System.out.println(bits2);
		// OR bits
		bits2.or(bits1);
		System.out.println("\\nbits2 OR bits1: ");
		System.out.println(bits2);
		// XOR bits
		bits2.xor(bits1);
		System.out.println("\\nbits2 XOR bits1: ");
		System.out.println(bits2);
		} 
	
}
