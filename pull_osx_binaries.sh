curl -o lwjgl-2.9.0.zip "http://www.newdawnsoftware.com/jenkins/view/LWJGL/job/LWJGL/lastSuccessfulBuild/artifact/dist/lwjgl-2.9.0.zip"
unzip -o lwjgl-2.9.0.zip
mkdir -p lib
cp -a lwjgl-2.9.0/jar/*  lib
cp -a lwjgl-2.9.0/native/macosx/*  lib
rm -rf  lwjgl-2.9.0
rm lwjgl-2.9.0.zip
