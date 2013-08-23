#!/bin/sh

# everything is relative to this script
cd $(dirname "$0")

VERSION="0.12.4"
REMOTE_ZIP="http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt/$VERSION/sbt.zip"
DEPS=".deps/sbt"
TEMP="$DEPS/temp"
ZIP="$TEMP/sbt-launch-$VERSION.zip"
JAR="$DEPS/sbt-launch-$VERSION.jar"

# download launcher
if [ ! -f $JAR ]; then
  echo "[INFO] Downloading sbt launcher binary"
  mkdir -p $TEMP
  curl -o $ZIP $REMOTE_ZIP
  unzip $ZIP -d $TEMP
  mv $TEMP/sbt/bin/sbt-launch.jar $JAR
  rm -rf $TEMP
fi

java \
  -Dsbt.version=$VERSION \
  -Dsbt.global.base=$DEPS \
  -Dsbt.boot.directory=$DEPS/boot \
  -Xmx1G \
  -Xms1G \
  -XX:MaxPermSize=512M \
  -jar $JAR "$@"
