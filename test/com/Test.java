package com;

public class Test {
	public static void main(String[] rags) {
		Test2 t = new Test2();
		try {
			t.aaa();
		} catch (Exception e) {
			System.out.println("01");
			e.printStackTrace();
		}
	}
}

class Test2 {
	public void aaa() throws Exception {
		try {
			int i = 1 / 0;
		} catch (Exception e) {
			throw new Exception();
		}
		System.out.println("0");
	}
}
