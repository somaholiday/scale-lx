package com.studioannwn;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.common.reflect.ClassPath;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.system.SystemInfo;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import com.pi4j.util.ConsoleColor;

import com.studioannwn.output.ladybug.LadybugLayout;
import heronarts.lx.LX;
import heronarts.lx.LXPlugin;
import heronarts.lx.mixer.LXChannel;
import heronarts.lx.effect.LXEffect;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.studio.LXStudio;
import processing.core.PApplet;

public class AnnwnLX extends PApplet implements LXPlugin {
  
  public static GpioController gpio;

  // Configuration flags
  private final static boolean RESIZABLE = true;
  private final static boolean ENABLE_ON_START = true;
  private final static boolean MULTITHREADED = false; // Disabled for anything GL
                                                      // Enable at your own risk!
                                                      // Could cause VM crashes.

  public final static boolean DEBUG = false;

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
    
    setupButtons();

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
  
  private void setupButtons() {
    // create Pi4J console wrapper/helper
    // (This is a utility class to abstract some of the boilerplate code)
    final Console console = new Console();

    // print program title/header
    console.title("<-- The Pi4J Project -->", "GPIO Listen (All Pins) Example");

    // create GPIO controller
    gpio = GpioFactory.getInstance();

    // by default we will use gpio pin PULL-DOWN
    PinPullResistance pull = PinPullResistance.PULL_DOWN;

    // prompt user to wait
    console.println(" ... please wait; provisioning GPIO pins with resistance [" + pull + "]");

    // create pins collection to store provisioned pin instances
    List<GpioPinDigitalInput> provisionedPins = new ArrayList<>();
    Pin[] pins;

    // get a collection of raw pins based on the board type (model)
    SystemInfo.BoardType board;
    try {
       board = SystemInfo.getBoardType();
    } catch (Exception ex) {
       System.err.println(ex.getMessage());
       return;
    } 
    
    if(board == SystemInfo.BoardType.RaspberryPi_ComputeModule ||
       board == SystemInfo.BoardType.RaspberryPi_ComputeModule3 ||
       board == SystemInfo.BoardType.RaspberryPi_ComputeModule3_Plus) {
        // get all pins for compute module 1 & 3 (CM4 uses 'RaspiPin' pin provider)
        pins = RCMPin.allPins();
    }
    else {
        // get exclusive set of pins based on RaspberryPi model (board type)
        pins = RaspiPin.allPins(board);
    }


    // provision GPIO input pins with its internal pull resistor set
    for (Pin pin : pins) {
        try {
            GpioPinDigitalInput provisionedPin = gpio.provisionDigitalInputPin(pin, pull);
            provisionedPins.add(provisionedPin);

            // un-export the provisioned GPIO pins when program exits
            provisionedPin.setShutdownOptions(true);
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    // prompt user that we are ready
    console.println(" ... GPIO pins provisioned and ready for use.");
    console.emptyLine();

    // --------------------------------
    // EVENT-BASED GPIO PIN MONITORING
    // --------------------------------

    // create and register gpio pin listeners for event pins
    gpio.addListener(new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            // display pin state on console
            console.println(" --> GPIO PIN STATE CHANGE (EVENT): " + event.getPin() + " = " +
            ConsoleColor.conditional(
              event.getState().isHigh(), // conditional expression
              ConsoleColor.GREEN,        // positive conditional color
              ConsoleColor.RED,          // negative conditional color
              event.getState()));        // text to display
            
            if (event.getState().isHigh()) {  
              LXChannel channel = lx.engine.mixer.getDefaultChannel();
              console.println(channel.label.getString());
              channel.goRandomPattern();
            }
        }
    }, provisionedPins.toArray(new GpioPinDigitalInput[0]));
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        console.println("SHUTTING DOWN GPIO");
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
      }
    });
  }
}
