package mit.bramtechs.imageslicer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slicer extends Thread {
	
	private final int index;
	private final BufferedImage image;
	private final File outputDirectory;
	private final File imageFile;
	private final SliceMode mode;
	private final Dimension size;
	
	public Slicer(int index, BufferedImage image, File imageFile, File outputDirectory, SliceMode mode, Dimension size) {
		this.index = index;
		this.image = image;
		this.outputDirectory = outputDirectory;
		this.imageFile = imageFile;
		this.mode = mode;
		this.size = size;
	}
	
	private void export(BufferedImage render) {
		String name = imageFile.getName().substring(0,imageFile.getName().indexOf('.'));
		String ext = imageFile.getName().substring(imageFile.getName().indexOf('.')+1);
		File output = new File(outputDirectory.getAbsoluteFile() + "/" + name + "_" + index + "." + ext);
		try {
			ImageIO.write(render, ext, output);
			System.out.println("Wrote slice to " + output.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		int w = size.width;
		int h = size.height;
		if (mode == SliceMode.Division) {
			w = image.getWidth() / size.width;
			h = image.getHeight() / size.height;
		}
		
		int x = index % (image.getWidth() / w);
		int y = index / (image.getHeight() / h);
		
		BufferedImage subImg = new BufferedImage(w,h,image.getType());
		Graphics2D g = subImg.createGraphics();
		g.drawImage(image,-x*w,-y*h,image.getWidth(),image.getHeight(),null);
		g.dispose();
		
		export(subImg);
	}
}
