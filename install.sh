#!/bin/bash
if [ "$EUID" -ne 0 ]
  then echo "Sudo permissions required!"
  exit
fi

mkdir -p /opt/java-image-slicer && \
./gradlew jar && \
cp build/libs/java-image-slicer.jar /opt/java-image-slicer && \
cp imgslice /opt/java-image-slicer && \

chmod +x /opt/java-image-slicer/imgslice && \

ln -s /opt/java-image-slicer/imgslice /usr/bin/imgslice && \
echo "imgslicer installed!"
