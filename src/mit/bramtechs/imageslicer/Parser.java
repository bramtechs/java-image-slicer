package mit.bramtechs.imageslicer;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		Dimension size = new Dimension(width,height);
		
		switch (mode) {
		case Absolute:
			pack(inputFile, outputFolder,mode, size);
			break;
		case Division:
			pack(inputFile, outputFolder,mode, size);
			break;
		default:
			throw new ImageSlicerException("Unknown mode " + args[2] + ", pass -a or -d");
		}
	}
		
	private int getTotalSections(BufferedImage img, Dimension size, SliceMode mode) {
		int w = size.width;
		int h = size.height;
		if (mode == SliceMode.Absolute) {
			w = img.getWidth()/size.width;
			h = img.getHeight()/size.height;
		}
		return w*h;
	}
	
	private void pack(File imageFile, File outputDir,SliceMode mode, Dimension size) {
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int amount = getTotalSections(image, size,mode);

		// start the threads
		Thread[] threads = new Thread[amount];
		for (int i = 0; i < amount; i++) {
			threads[i] = new Slicer(i,image,imageFile,outputDir,mode,size);
			threads[i].start();
		}
		
		// wait for threads to complete
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws ImageSlicerException {
		new Parser(args);
	}
}
