package com.studioannwn;

import com.google.common.reflect.ClassPath;
import com.studioannwn.model.GridModel3D;

import heronarts.lx.LXEffect;
import heronarts.lx.LXPattern;
import heronarts.lx.blend.LightestBlend;
import heronarts.lx.studio.LXStudio;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import processing.core.PApplet;

public class Scale extends PApplet {

	static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "%3$s: %1$tc [%4$s] %5$s%6$s%n");
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
   * Adds logging to a file. The file name will be appended with a dash, date stamp, and
   * the extension ".log".
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

  private static final Logger logger = Logger.getLogger(Scale.class.getName());

  public static void main(String[] args) {
    PApplet.main(Scale.class.getName(), args);
  }

  private static final String LOG_FILENAME_PREFIX = "scale";

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
  private void registerAll(LXStudio lx) {
    List<Class<? extends LXPattern>> patterns = lx.getRegisteredPatterns();
    List<Class<? extends LXEffect>> effects = lx.getRegisteredEffects();
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
          if (!patterns.contains(p)) {
            lx.registerPattern(p);
            logger.info("Added pattern: " + p);
          }
        } else if (LXEffect.class.isAssignableFrom(c)) {
          Class<? extends LXEffect> e = c.asSubclass(LXEffect.class);
          if (!effects.contains(e)) {
            lx.registerEffect(e);
            logger.info("Added effect: " + e);
          }
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

    LXStudio.Flags flags = new LXStudio.Flags();
    flags.showFramerate = false;
    flags.isP3LX = true;
    flags.immutableModel = true;

    logger.info("Current renderer:" + sketchRenderer());
    logger.info("Current graphics:" + getGraphics());
    logger.info("Current graphics is GL:" + getGraphics().isGL());
    logger.info("Multithreaded hint: " + MULTITHREADED);
    logger.info("Multithreaded actually: " + (MULTITHREADED && !getGraphics().isGL()));

    ScaleLayout layout = new ScaleLayout();
    lx = new LXStudio(this, flags, layout.getModel());
    // lx = new LXStudio(this, flags, new GridModel3D());

    lx.ui.setResizable(RESIZABLE);
    lx.engine.output.enabled.setValue(ENABLE_ON_START);

    frameRate(GLOBAL_FRAME_RATE);
  }

  public void initialize(final LXStudio lx, LXStudio.UI ui) {
    // Add custom components or output drivers here
    // Register settings
    // lx.engine.registerComponent("scaleSettings", new Settings(lx, ui));

    // Common components
    // registry = new Registry(this, lx);

    // Register any patterns and effects LX doesn't recognize
    registerAll(lx);
    lx.registerBlend(LightestBlend.class);
  }

  public void onUIReady(LXStudio lx, LXStudio.UI ui) {
    ui.preview.addComponent(new ScaleVisualizer(lx));
  }

  public void draw() {
    // All is handled by LX Studio
  }

  // Configuration flags
  private final static boolean RESIZABLE = true;
  private final static boolean ENABLE_ON_START = false;
  private final static boolean MULTITHREADED = false;  // Disabled for anything GL
                                                       // Enable at your own risk!
                                                       // Could cause VM crashes.

  // Helpful global constants
  public final static float INCHES = 1.0f / 12.0f;
  public final static float IN = INCHES;
  public final static float FEET = 1.0f;
  public final static float FT = FEET;
  public final static float CM = IN / 2.54f;
  public final static float MM = CM * .1f;
  public final static float M = CM * 100;
  public final static float METER = M;

  public static final int LEDS_PER_UNIVERSE = 170;
}
