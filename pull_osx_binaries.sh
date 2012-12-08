curl -o lwjgl2.8.5.zip https://nodeload.github.com/LWJGL/lwjgl/zip/lwjgl2.8.5
unzip lwjgl2.8.5.zip
mkdir -p lib
cp -ar lwjgl-lwjgl2.8.5/libs/macosx/* lib
rm -rf  lwjgl-lwjgl2.8.5
rm lwjgl2.8.5.zip
