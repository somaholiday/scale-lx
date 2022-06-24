ANNWN_LX_DIR="/home/pi/annwn-lx"
JAVA_BIN="/opt/jdk1.8.0_333/bin/java"
ANNWN_LX_JAR="./target/annwn-lx-1.0.0-jar-with-dependencies.jar"

$JAVA_BIN -Duser.dir=$ANNWN_LX_DIR -cp $ANNWN_LX_JAR com.studioannwn.AnnwnLX
