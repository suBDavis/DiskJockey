package net.redspin.Diskjockey;
import net.redspin.Diskjockey.DBwrangler;

public class Driver {
	public static void main(String[] args){
		String[] elems = DBwrangler.getDB().getNext();
		System.out.println(elems[0] + " " + elems[1]);
	}

}
