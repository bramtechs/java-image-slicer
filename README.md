# java-image-slicer

> Not tested enough for production use! Proceed with caution!

Java Image Slicer is a **multi-threaded** image slicer that slices images in chunks according to a given dimension and mode.

## Usage
```bash
imgslice [INPUT FILE PATH] [OUTPUT FOLDER PATH] -a [CHUNK SIZE X] [CHUNK SIZE Y]
imgslice [INPUT FILE PATH] [OUTPUT FOLDER PATH] -d [CHUNK AMOUNT X] [CHUNK AMOUNT Y]
```
This program contains two 'modes':
- absolute mode (-a)
    - Splices the input image in images of x by y pixels in size.
    - *An image of 64x32 pixels in size with command ```-a 16 8``` will create 16 new images of each 16x8 pixels in size.*
- division mode (-d) 
    - Splices the input image in x by y 'pieces' or images.
    - *An image of 64x32 pixels in size with command `-d 2 4` will create 8 new images of each 32x8 pixels in size.*

**After installing you can try out the provided example**

```bash
# in ~/Downloads/java-image-slicer
imgslice ./test/lotus.jpg ./test/out -a 64 64 
imgslice ./test/test.png ./test/out -d 3 1 
```
*[lotus image](https://pixabay.com/nl/photos/lotus-bloem-bloom-bloesem-978659/) by jennyzhh2008 - Pixabay License - Free for commercial use*

## Quick install
The following script assumes you have installed:
- java (>= 11)
- git

```bash
cd ~/Downloads && \
git clone https://github.com/bramtechs/java-image-slicer && \
cd ~/Downloads/java-image-slicer && \
chown +x install.sh && \
sudo ./install.sh
```

The jar will be located at ```/opt/java-image-slicer/java-image-slicer.jar``` and it's symlink will be created at ```/usr/bin/imgslice```.

## Uninstall
To uninstall the program, go to the cloned repo and run:
```bash
chmod +x uninstall.sh
sudo ./uninstall
```

## Notice
Running any of my programs is at your own risk. I am not responsible for turning your computer into a nuclear power plant.