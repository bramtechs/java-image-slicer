package mit.bramtechs.imageslicer.tests;

import mit.bramtechs.imageslicer.ImageSlicerException;
import mit.bramtechs.imageslicer.Parser;

public class Tester {

	public static void main(String[] args) throws ImageSlicerException {
		new Parser(new String[]{"./test/lotus.jpg","./test/out","-a","64","64"});
		new Parser(new String[]{"./test/test.png","./test/out","-d","3","1"});
	}

}
