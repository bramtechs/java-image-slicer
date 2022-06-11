#!/bin/bash
if [ "$EUID" -ne 0 ]
  then echo "Sudo permissions required!"
  exit
fi

rm -rf /opt/java-image-slicer && \
rm /usr/bin/imgslice && \
echo "imgslicer uninstalled!"
