import java.util.Arrays;


public class KeypadLock {
	private int[] secretCode;
	public KeypadLock (int[] code) {
		secretCode = new int[code.length];
		for (int i = 0; i < code.length; i++) {
			secretCode[i] = code[i];
		}
	}
	
	public boolean checkCode (int[] code) {
		return Arrays.equals(secretCode,code);
	}
	
	public void printString ()	{
		for (int i: secretCode) {
			System.out.print(i + " ");
		}
	}
	
	public static void main (String[] args)	{
		int[] mykey = {1,2,3,4};
		KeypadLock myLock = new KeypadLock(mykey);
		mykey[0] = 0;
		
		int[] entered = {1,2,3,4};
		if (myLock.checkCode(entered)) System.out.print("good");
		myLock.printString();
	}
}
