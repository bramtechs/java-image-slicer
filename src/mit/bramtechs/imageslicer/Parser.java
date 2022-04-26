package mit.bramtechs.imageslicer;

import java.io.File;

public class Parser {

	// ex: "./test/lotus.jpg","./test/out","-p","64","64"
	public Parser(String[] args) throws ImageSlicerException {
		switch (args.length) {
			case 0:
				System.out.println("No input file specified");
				return;
			case 1:
				System.out.println("No output folder specified");
				return;
			case 2:
				System.out.println("No slice mode specified (ABSOLUTE mode (-a) or DIVISION mode (-d))");
				return;
			case 3:
				System.out.println("No width of subimage or amount OR horizontal sections specified.");
				return;
			case 4:
				System.out.println("No height of subimage or amount OR vertical sections specified.");
				return;
			case 5:
				break;
			default:
				System.out.println("Too many arguments specified!");
				return;
		}
		
		File inputFile = new File(args[0]);
		File outputFolder = new File(args[1]);
		
		if (!inputFile.exists()) {
			throw new ImageSlicerException("Input path " + inputFile.getAbsolutePath() + " does not exist.");
		}
		if (!inputFile.isFile()) {
			throw new ImageSlicerException("Input path " + inputFile.getAbsolutePath() + " is not a file.");
		}
		
		// TODO check image format
		
		if (!outputFolder.exists()) {
			System.out.println("Creating folder" + outputFolder.getAbsolutePath() + "...");
			if (outputFolder.mkdir() == false) {
				throw new ImageSlicerException("Could not create folder " + outputFolder.getAbsolutePath() + " (too deep?)");
			}
			if (!outputFolder.isDirectory()) {
				throw new ImageSlicerException("Output path " + outputFolder.getAbsolutePath() + " is not a directory.");
			}
		}
		
		SliceMode mode = null;
		if (args[2].equalsIgnoreCase("-a")) {
			mode = SliceMode.Absolute;
		}
		else if (args[2].equalsIgnoreCase("-d")) {
			mode = SliceMode.Division;
		}else {
			throw new ImageSlicerException("Unknown mode " + args[2] + ", pass -a or -d");
		}
		
		int width = Integer.parseInt(args[3]);
		int height = Integer.parseInt(args[4]);
		
		System.out.println("Slicing " + args[0] + " to " + args[1] + " as ("+width+" x "+height+", " + mode +")...");
	}

	public static void main(String[] args) throws ImageSlicerException {
		new Parser(args);
	}
}
