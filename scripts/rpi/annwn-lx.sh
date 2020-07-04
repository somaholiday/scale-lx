#!/bin/bash

ANNWN_LX_DIR=/home/pi/annwn-lx
JAVA_BIN=/home/pi/jdk1.8.0_231/bin/java
PROCESSING_DIR=/usr/local/lib/processing-3.5.3

$JAVA_BIN -Duser.dir=$ANNWN_LX_DIR -Djna.nosys=true -Djava.ext.dirs=$PROCESSING_DIR/java/lib/ext -Djava.library.path=$PROCESSING_DIR/core/library:/usr/java/packages/lib/arm:/lib:/usr/lib -cp $PROCESSING_DIR/core/library/jogl-all-natives-linux-amd64.jar:$PROCESSING_DIR/core/library/gluegen-rt.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-aarch64.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-i586.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-armv6hf.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-macosx-universal.jar:$PROCESSING_DIR/core/library/jogl-all.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-i586.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-windows-i586.jar:$PROCESSING_DIR/core/library/jogl-all-natives-windows-amd64.jar:$PROCESSING_DIR/core/library/jogl-all-natives-windows-i586.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-amd64.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-armv6hf.jar:$PROCESSING_DIR/core/library/jogl-all-natives-macosx-universal.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-windows-amd64.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-aarch64.jar:$PROCESSING_DIR/java/lib/rt.jar:$PROCESSING_DIR/lib/ant-launcher.jar:$PROCESSING_DIR/lib/ant.jar:$PROCESSING_DIR/lib/jna-platform.jar:$PROCESSING_DIR/lib/jna.jar:$PROCESSING_DIR/lib/pde.jar:$PROCESSING_DIR/core/library/core.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-aarch64.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-amd64.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-armv6hf.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-linux-i586.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-macosx-universal.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-windows-amd64.jar:$PROCESSING_DIR/core/library/gluegen-rt-natives-windows-i586.jar:$PROCESSING_DIR/core/library/gluegen-rt.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-aarch64.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-amd64.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-armv6hf.jar:$PROCESSING_DIR/core/library/jogl-all-natives-linux-i586.jar:$PROCESSING_DIR/core/library/jogl-all-natives-macosx-universal.jar:$PROCESSING_DIR/core/library/jogl-all-natives-windows-amd64.jar:$PROCESSING_DIR/core/library/jogl-all-natives-windows-i586.jar:$PROCESSING_DIR/core/library/jogl-all.jar:$PROCESSING_DIR/modes/java/mode/JavaMode.jar:$PROCESSING_DIR/modes/java/mode/antlr.jar:$PROCESSING_DIR/modes/java/mode/classpath-explorer-1.0.jar:$PROCESSING_DIR/modes/java/mode/com.ibm.icu.jar:$PROCESSING_DIR/modes/java/mode/jdi.jar:$PROCESSING_DIR/modes/java/mode/jdimodel.jar:$PROCESSING_DIR/modes/java/mode/jdtCompilerAdapter.jar:$PROCESSING_DIR/modes/java/mode/jsoup-1.7.1.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.core.contenttype.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.core.jobs.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.core.resources.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.core.runtime.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.equinox.common.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.equinox.preferences.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.jdt.core.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.osgi.jar:$PROCESSING_DIR/modes/java/mode/org.eclipse.text.jar:$PROCESSING_DIR/modes/java/mode/org.netbeans.swing.outline.jar:./lib/lxstudio-0.2.1-SNAPSHOT-jar-with-dependencies.jar:./lib/guava-28.0-jre.jar:./lib/commons-math3-3.6.1.jar:./target/annwn-lx-1.0.0.jar com.studioannwn.AnnwnLX