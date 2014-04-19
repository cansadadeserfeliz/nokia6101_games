import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class Matrix extends MIDlet implements CommandListener {
  private Command exit = new Command("Exit", Command.EXIT, 1);
  private static String demos[] = {
    "OLucky",
    "BullsAndCows",
    "SeaFight",
    "Determinant",
    "IMatrix"
  };
  private static Matrix instance = null;
  private List mainMenu = new List ("Select demo", Choice.IMPLICIT, demos, null);
 
  public Matrix() {
    super();
    instance = this;
  }

  public static Matrix getInstance() {
    return instance;
  }

  public void destroyApp(boolean destroy) {
  }

  public void pauseApp() {
  }

  void quit() {
    destroyApp(true);
    notifyDestroyed();
  }

  public void startApp() {
    Display display;
    mainMenu.addCommand(exit);
    mainMenu.setCommandListener(this);
    display = Display.getDisplay(this);
    display.setCurrent(mainMenu);
  }

  public void display() {
    Display.getDisplay(this).setCurrent(mainMenu);
  }

  public void commandAction(Command c, Displayable d) {
    Displayable displayable = null;
    if (c == List.SELECT_COMMAND) {
      int index = mainMenu.getSelectedIndex();
      try {
        displayable = (Displayable)Class.forName(demos[index]).newInstance();
        if (displayable == null) return;
        Display display = Display.getDisplay(this);
        display.setCurrent(displayable);
      }
      catch (Exception e) {
        System.out.println("Got Exception here!!!");
        e.printStackTrace();
        return;
      }
    }
    else if (c == exit) quit();
  }

}
