package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import myUber.CLUI;

class CLUITest {

	@Test
	void stringToCommand() {
		List<String> result = new ArrayList<String>();
		result.add("addCustomer");
		result.add("Jean");
		result.add("Pierre");
		assertEquals( result, CLUI.stringToCommand("addCustomer Jean Pierre"));
	}
	
	@Test
	void init() {
		File file = new File("words.txt");
		String texte = CLUI.loadFile(file);
		List<String> commands = CLUI.separateLine(texte);
		System.out.println(commands);
		assertEquals(commands.get(0), "aaa");
		assertEquals(commands.get(1), "b");
		assertEquals(commands.get(2), "cc");
		
	}

}
