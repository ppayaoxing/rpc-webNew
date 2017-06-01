package com.xw.rpc.client;

public class Test {

//	public static void main(String[] args) {
//		for(int i = 0;;i++){
//			System.out.println("---for 循环"+i);
//		}		
//	}
	

    public static void main(String[] args)  {
        MyClass myClass1 = new MyClass();
        MyClass myClass2 = new MyClass();
        System.out.println(myClass1.i);
        System.out.println(myClass1.j);
        System.out.println(myClass1.i);
        System.out.println(myClass1.j);
        System.out.println(myClass2.i);
        System.out.println(myClass2.j);
 
    }
}
 
class MyClass {
    public final double i = Math.random();
    public static double j = Math.random();

}
