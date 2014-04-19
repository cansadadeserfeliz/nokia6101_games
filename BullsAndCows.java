import javax.microedition.lcdui.*;
import java.util.Random;

public class BullsAndCows extends Canvas implements CommandListener {
 
  // Константа, которая представляет белый цвет.
  private static final int WHITE = 0xFF << 16 | 0xFF << 8 | 0xFF;
  private static final int RED = 0xFF000000 | 0xc0 << 16 | 0x00 << 8 | 0x00;
  private static final int BLACK = ~WHITE;

  int mass[] = {0, 0, 0, 0};
  String msg[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
  int ent_mass[] = {0, 0, 0, 0};
  int ent_ind = 0;
  int msg_ind = 0;
  boolean you_win = false;
  boolean you_lose = false;

  private Command back = new Command("Back", Command.BACK, 1);
  private Command go = new Command("Go", Command.SCREEN, 1); 
  private Display display = Display.getDisplay(Matrix.getInstance());

  public BullsAndCows() {
    super();
    addCommand(back);
    addCommand(go);
    setCommandListener(this);
    display.setCurrent(this);

    Random rund = new Random();
    mass[0] = Math.abs(rund.nextInt() % 10);
    do {
      mass[1] = Math.abs(rund.nextInt() % 10);
    }
    while(mass[1] == mass[0]);
    do {
      mass[2] = Math.abs(rund.nextInt() % 10);
    }
    while(mass[2] == mass[0] || mass[2] == mass[1]);
    do {
      mass[3] = Math.abs(rund.nextInt() % 10);
    }
    while(mass[3] == mass[0] || mass[3] == mass[1] || mass[3] == mass[2]);
  }
   

  /* рисует белый отсекаемый прямоугольник, эффективно стирающий все,
   * что было изображено в Canvas перед этим
   */
  protected void paintClipRect(Graphics g) {
    int clipX = g.getClipX();
    int clipY = g.getClipY();
    int clipH = g.getClipHeight();
    int clipW = g.getClipWidth();

    int color = g.getColor();
    g.setColor(WHITE);
    g.fillRect(clipX, clipY, 700, 700);
    g.setColor(color);
  }

  /* отображает внешний вид этого подкласса в Canvas */
  public void paint(Graphics g) {
    paintClipRect(g);  // очистить экран

    int width = getWidth();
    int height = getHeight();

    g.setColor(BLACK);
    g.setFont(Font.getDefaultFont());
    g.drawString("Enter:   " + ent_mass[0] + " " + ent_mass[1] + " " + ent_mass[2] + " " + ent_mass[3], 5, 14, Graphics.LEFT | Graphics.BOTTOM);
    g.drawLine(5, 15, width - 20, 15);

    int x = 30;
    for (int i = 0; i<7; i++) {
      g.drawString(msg[i], 5, x, Graphics.LEFT | Graphics.BOTTOM);
      x += 12;
    }
    x = 30;
    for (int i = 7; i<14; i++) {
      g.drawString(msg[i], 65, x, Graphics.LEFT | Graphics.BOTTOM);
      x += 12;
    }

    g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));
    g.setColor(RED);
    if (you_win) {
      paintClipRect(g);
      g.drawString("You Win :))", 30, 40, Graphics.LEFT | Graphics.BOTTOM);
      }
    if (you_lose) {
      paintClipRect(g);  // очистить экран
      g.drawString("You Lose :((", 30, 40, Graphics.LEFT | Graphics.BOTTOM);
      g.drawString(mass[0] + " " + mass[1] + " " + mass[2] + " " + mass[3], 35, 70, Graphics.LEFT | Graphics.BOTTOM);
    }
  }

  // Определяет, что обработка должна быть сделана в ответ на событие опускания
  // клавиши, произошедшее в Canvas. Этот метод подменяет тот же самый метод в Canvas.
  public void keyReleased(int keyCode) {
    printKeyEventInfo(keyCode);
  }


  protected void printKeyEventInfo(int keyCode) {
    //System.out.println(getKeyName(keyCode));
    //System.out.println(keyCode);
    //int k = getKeyName(keyCode);
    switch(keyCode){
      case 49:  //1
      case 50:  //2
      case 51:  //3
      case 52:  //4
      case 53:  //5
      case 54:  //6
      case 55:  //7
      case 56:  //8
      case 57:  //9
      case 48:  //0
        if (ent_ind < 4) {
          ent_mass[ent_ind] = keyCode - 48;
          ent_ind++;        
        }
        break;
    } // end of switch
    repaint();
  }

  public void commandAction(Command c, Displayable d) {
    if (c == back) Matrix.getInstance().display(); 
    if (c == go) {
      ent_ind = 0;

      for (int i = 0; i < 4; i++)
        msg[msg_ind] += ent_mass[i] + " ";

      for (int i = 0; i < 4; i++)
        if (ent_mass[i] == mass[i]) msg[msg_ind] += "Б";

      for (int i = 0; i < 4; i++) 
        for (int j = 0; j < 4; j++) {
          if (ent_mass[i] == mass[j])
            if (ent_mass[i] != mass[i]) msg[msg_ind] += "К";
        }

      boolean b = true;
      for (int i = 0; i < 4; i++)
        if (ent_mass[i] != mass[i]) b = false;
      if(b) you_win = true;
      
      for (int i = 0; i < 4; i++)
        ent_mass[i] = 0;

      msg_ind++;
      if (msg_ind > 14) you_lose = true;
      repaint();
    }
  }

} // end of class DrawDemo
