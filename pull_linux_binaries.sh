wget -O lwjgl-2.8.5.zip "http://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.8.5/lwjgl-2.8.5.zip?r=&ts=1357241147&use_mirror=iweb"
unzip -o lwjgl-2.8.5.zip
mkdir -p lib
#cp -a lwjgl-2.8.5/jar/*  lib
cp -a lwjgl-2.8.5/native/linux/*  lib
rm -rf  lwjgl-2.8.5
rm lwjgl-2.8.5.zip
