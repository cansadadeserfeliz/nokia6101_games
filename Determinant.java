import javax.microedition.lcdui.*;

public class Determinant extends Canvas implements CommandListener {
 
  private static final int WHITE = 0xFF << 16 | 0xFF << 8 | 0xFF;
  private static final int RED = 0xFF000000 | 0xc0 << 16 | 0x00 << 8 | 0x00;
  private static final int BLACK = ~WHITE;
  private static final int BLUE = Integer.parseInt("000000001111111111111111", 2);
  private static final int BLUE_DARK = Integer.parseInt("000000000000000011111111", 2);
  private static final int GREEN = Integer.parseInt("000000001111111100000000", 2);

  boolean det_is_null = true;  // true - определитель матрицы = 0
                                // false - определитель матрицы != 0
  int num = 0;                  // вводимое число
  String msg[] = { "A11", "A12", "A13",    
                   "A21", "A22", "A23",
                   "A31", "A32", "A33" };
  int arr[] = new int[9];       // массив значений матрицы

  private Command back = new Command("Back", Command.BACK, 1);
  private Command go = new Command("Go", Command.OK, 1); 
  private Command next = new Command("Next", Command.OK, 1); 

  private Display display = Display.getDisplay(Matrix.getInstance());

  int ind = 0;
  boolean is_neg = false;
  int det = -1;

  public Determinant() {
    super();
    addCommand(back);
    addCommand(go);
    addCommand(next);
    setCommandListener(this);
    display.setCurrent(this);
  }

  /* рисует белый отсекаемый пр€моугольник, эффективно стирающий все,
   * что было изображено в Canvas перед этим
   */
  protected void paintClipRect(Graphics g) {
    int clipX = g.getClipX();
    int clipY = g.getClipY();
    int clipH = g.getClipHeight();
    int clipW = g.getClipWidth();

    int color = g.getColor();
    g.setColor(WHITE);
    g.fillRect(clipX, clipY, clipH, clipW);
    g.setColor(color);
  }

  /* отображает внешний вид этого подкласса в Canvas */
  public void paint(Graphics g) {
    paintClipRect(g);  // очистить экран

    int width = getWidth();
    int height = getHeight();

    g.setColor(BLACK);
    g.setFont(Font.getDefaultFont());
    g.drawString("¬ведите значени€ матрицы: ", 3, 12, Graphics.LEFT | Graphics.BOTTOM);
    g.drawString(msg[0] + " || " + msg[1] + " || " + msg[2], 3, 25, Graphics.LEFT | Graphics.BOTTOM);
    g.drawString(msg[3] + " || " + msg[4] + " || " + msg[5], 3, 38, Graphics.LEFT | Graphics.BOTTOM);
    g.drawString(msg[6] + " || " + msg[7] + " || " + msg[8], 3, 51, Graphics.LEFT | Graphics.BOTTOM);

    g.setColor(RED);
    if(det == 0)
      g.drawString("Determinant is NULL", 3, 66, Graphics.LEFT | Graphics.BOTTOM);
    if(!det_is_null) {
      g.drawString("Determinant = " + det, 3, 66, Graphics.LEFT | Graphics.BOTTOM);
    }  

    g.drawString("Ќаберите * дл€ получени€", 3, 80, Graphics.LEFT | Graphics.BOTTOM);
    g.drawString("отрицательного значени€", 3, 92, Graphics.LEFT | Graphics.BOTTOM);
  }

  // ќпредел€ет, что обработка должна быть сделана в ответ на событие опускани€
  // клавиши, произошедшее в Canvas. Ётот метод подмен€ет тот же самый метод в Canvas.
  public void keyReleased(int keyCode) {
    switch(keyCode){
      case KEY_NUM1:  
      case KEY_NUM2:  
      case KEY_NUM3:  
      case KEY_NUM4: 
      case KEY_NUM5:  
      case KEY_NUM6:  
      case KEY_NUM7:  
      case KEY_NUM8: 
      case KEY_NUM9:  
      case KEY_NUM0:  
        num = num * 10 + keyCode - 48;
        if (is_neg == true) num = - num;
        is_neg = false;
        if (ind < 9) {
          arr[ind] = num;
          msg[ind] = "" + num;
        }
        repaint();
        break;
      case KEY_POUND:
        is_neg = true;
        repaint();
    }
  }

  public void commandAction(Command c, Displayable d) {
    if (c == back) Matrix.getInstance().display();
    if (c == go){
      det = arr[0]*arr[4]*arr[8] + arr[6]*arr[1]*arr[5] + arr[3]*arr[2]*arr[7] - arr[6]*arr[4]*arr[2] - arr[3]*arr[1]*arr[8] - arr[0]*arr[7]*arr[5];
      if(det == 0) det_is_null = true;
      else det_is_null = false;
      ind = 0;
      repaint();
    }
    if (c == next){
      ind++;
      num = 0;
      is_neg = false;
      repaint();
    }
  }

} // end of class 
