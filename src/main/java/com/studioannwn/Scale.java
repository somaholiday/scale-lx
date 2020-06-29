package com.studioannwn;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.common.reflect.ClassPath;
import com.studioannwn.model.discopussy.DiscoPussyConfig;
import com.studioannwn.model.discopussy.DiscoPussyModel;
import com.studioannwn.output.ScaleLayout;

import com.studioannwn.output.pixlite.PixLite;
import com.studioannwn.output.pixlite.PixLite16;
import com.studioannwn.output.pixlite.PixLite4;
import com.studioannwn.ui.DiscoPussyVisualizer;
import heronarts.lx.LXEffect;
import heronarts.lx.LXPattern;
import heronarts.lx.blend.LightestBlend;
import heronarts.lx.output.LXOutput;
import heronarts.lx.studio.LXStudio;
import processing.core.PApplet;

public class Scale extends PApplet {

  // Configuration flags
  private final static boolean RESIZABLE = true;
  private final static boolean ENABLE_ON_START = true;
  private final static boolean MULTITHREADED = false;  // Disabled for anything GL
                                                       // Enable at your own risk!
                                                       // Could cause VM crashes.

  public final static boolean DEBUG = false;

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

  public ScaleLayout layout;

  public static PApplet pApplet;
  public static final int GLOBAL_FRAME_RATE = 40;

  public final HashMap<String, PixLite> pixlites = new HashMap<String, PixLite>();

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
    flags.useGLPointCloud = false; // Uncomment to run on Raspberry Pi

    logger.info("Current renderer:" + sketchRenderer());
    logger.info("Current graphics:" + getGraphics());
    logger.info("Current graphics is GL:" + getGraphics().isGL());
    logger.info("Multithreaded hint: " + MULTITHREADED);
    logger.info("Multithreaded actually: " + (MULTITHREADED && !getGraphics().isGL()));

    DiscoPussyModel model = new DiscoPussyModel(new DiscoPussyConfig());
    lx = new LXStudio(this, flags, model);

//    layout = new ScaleLayout();
//    lx = new LXStudio(this, flags, layout.getModel());
//    layout.addOutputs(lx);
    // lx.setModel(layout.getModel());
    // lx.setModel(new GridModel3D());

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

    for (DiscoPussyModel.Tentacle tentacle : DiscoPussyModel.getTentacles()) {
      for (DiscoPussyModel.Dataline dataline : tentacle.getDatalines()) {
        String ipAddress = dataline.getIpAddress();

        if (!pixlites.containsKey(ipAddress)) {
          PixLite pixlite = new PixLite16(lx, ipAddress);
          pixlites.put(ipAddress, pixlite);
          lx.addOutput(pixlite);
          pixlite.enabled.setValue(true);
        }

        PixLite pixlite = pixlites.get(ipAddress);
        pixlite.addOutput(dataline.getChannel(), dataline.getPoints());
      }
    }

    for (DiscoPussyModel.Dataline dataline : DiscoPussyModel.getBar().getDatalines()) {
      String ipAddress = dataline.getIpAddress();

      if (!pixlites.containsKey(ipAddress)) {
        PixLite pixlite = new PixLite4(lx, ipAddress);
        pixlites.put(ipAddress, pixlite);
        lx.addOutput(pixlite);
        pixlite.enabled.setValue(true);
      }

      PixLite pixlite = pixlites.get(ipAddress);
      pixlite.addOutput(dataline.getChannel(), dataline.getPoints());
    }

    // print used pixlite channels
    System.out.println();
    System.out.println("-- Setup pixlites ----------------------------");
    pixlites.forEach((ipAddress, pixlite) -> {
      System.out.println(ipAddress + " - " + pixlite.children.size() + " datalines");
      for (LXOutput output : pixlite.children) {
        System.out.println(" -> Channel " + ((PixLite.PixLiteOutput) output).getOutputIndex());
      }
      System.out.println("----------------------------------------------");
    });
    System.out.println();
  }

  public void onUIReady(LXStudio lx, LXStudio.UI ui) {
//    ui.preview.addComponent(new ScaleVisualizer(lx));
    ui.preview.addComponent(new DiscoPussyVisualizer(lx));

    new UIOutputControls(lx, ui).setExpanded(true).addToContainer(ui.leftPane.global);
  }

  public void draw() {
    // All is handled by LX Studio
  }
}
