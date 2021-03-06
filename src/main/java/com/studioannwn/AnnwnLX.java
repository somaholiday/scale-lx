package com.studioannwn;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.common.reflect.ClassPath;

import com.studioannwn.output.ladybug.LadybugLayout;
import heronarts.lx.LX;
import heronarts.lx.LXPlugin;
import heronarts.lx.effect.LXEffect;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.studio.LXStudio;
import processing.core.PApplet;

public class AnnwnLX extends PApplet implements LXPlugin {

  // Configuration flags
  private final static boolean RESIZABLE = true;
  private final static boolean ENABLE_ON_START = true;
  private final static boolean MULTITHREADED = false; // Disabled for anything GL
                                                      // Enable at your own risk!
                                                      // Could cause VM crashes.

  public final static boolean DEBUG = true;

  static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "%3$s: %1$tc [%4$s] %5$s%6$s%n");
  }

  /**
   * Set the main logging level here.
   *
   * @param level the new logging level
   */
  public static void setLogLevel(Level level) {
    // Change the logging level here
    Logger root = Logger.getLogger("");
    root.setLevel(level);
    for (Handler h : root.getHandlers()) {
      h.setLevel(level);
    }
  }

  /**
   * Adds logging to a file. The file name will be appended with a dash, date
   * stamp, and the extension ".log".
   *
   * @param prefix prefix of the log file name
   * @throws IOException if there was an error opening the file.
   */
  public static void addLogFileHandler(String prefix) throws IOException {
    String suffix = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    Logger root = Logger.getLogger("");
    Handler h = new FileHandler(prefix + "-" + suffix + ".log");
    h.setFormatter(new SimpleFormatter());
    root.addHandler(h);
  }

  private static final Logger logger = Logger.getLogger(AnnwnLX.class.getName());
  private static final String LOG_FILENAME_PREFIX = AnnwnLX.class.getSimpleName();

  public static void main(String[] args) {
    PApplet.main(AnnwnLX.class.getName(), args);
  }

  // Reference to top-level LX instance
  private heronarts.lx.studio.LXStudio lx;

  public static PApplet pApplet;
  public static final int GLOBAL_FRAME_RATE = 40;

  @Override
  public void settings() {
    size(1024, 600, P3D);
  }

  /**
   * Registers all patterns and effects that LX doesn't already have registered.
   * This check is important because LX just adds to a list.
   *
   * @param lx the LX environment
   */
  private void registerAll(LX lx) {
    //List<Class<? extends LXPattern>> patterns = lx.registry.mutablePatterns; //getRegisteredPatterns();
    //List<Class<? extends LXEffect>> effects = lx.getRegisteredEffects();
    final String parentPackage = getClass().getPackage().getName();

    try {
      ClassPath classPath = ClassPath.from(getClass().getClassLoader());
      for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
        // Limit to this package and sub-packages
        if (!classInfo.getPackageName().startsWith(parentPackage)) {
          continue;
        }
        Class<?> c = classInfo.load();
        if (Modifier.isAbstract(c.getModifiers())) {
          continue;
        }
        if (LXPattern.class.isAssignableFrom(c)) {
          Class<? extends LXPattern> p = c.asSubclass(LXPattern.class);
          //if (!patterns.contains(p)) {
            lx.registry.addPattern(p);
            logger.info("Added pattern: " + p);
          //}
        } else if (LXEffect.class.isAssignableFrom(c)) {
          Class<? extends LXEffect> e = c.asSubclass(LXEffect.class);
          //if (!effects.contains(e)) {
            lx.registry.addEffect(e);
            logger.info("Added effect: " + e);
          //}
        }
      }
    } catch (IOException ex) {
      logger.log(Level.WARNING, "Error finding pattern and effect classes", ex);
    }
  }

  @Override
  public void setup() {
    // Processing setup, constructs the window and the LX instance
    pApplet = this;

    try {
      addLogFileHandler(LOG_FILENAME_PREFIX);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Error creating log file: " + LOG_FILENAME_PREFIX, ex);
    }

    LXStudio.Flags flags = new LXStudio.Flags(this);
    flags.showFramerate = false;
    flags.isP3LX = true;
    flags.immutableModel = true;
    flags.useGLPointCloud = false; // Uncomment to run on Raspberry Pi

    logger.info("Current renderer:" + sketchRenderer());
    logger.info("Current graphics:" + getGraphics());
    logger.info("Current graphics is GL:" + getGraphics().isGL());
    logger.info("Multithreaded hint: " + MULTITHREADED);
    logger.info("Multithreaded actually: " + (MULTITHREADED && !getGraphics().isGL()));

    LadybugLayout layout = new LadybugLayout();
    lx = new LXStudio(this, flags, layout.getModel());
    layout.addOutputs(lx);

    lx.ui.setResizable(RESIZABLE);
    lx.engine.output.enabled.setValue(ENABLE_ON_START);

    frameRate(GLOBAL_FRAME_RATE);
  }

  @Override
  public void initialize(LX lx) {
    registerAll(lx);
  }

  public void onUIReady(LXStudio lx, LXStudio.UI ui) {
//    ui.preview.addComponent(new ScaleVisualizer(lx));

    new UIOutputControls(lx, ui).setExpanded(true).addToContainer(ui.leftPane.global);
  }

  public void draw() {
    // All is handled by LX Studio
  }
}
