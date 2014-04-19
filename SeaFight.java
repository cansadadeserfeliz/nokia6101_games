import javax.microedition.lcdui.*;
import java.util.Random;

public class SeaFight extends Canvas implements CommandListener {
 
  // Константа, которая представляет белый цвет.
  private static final int WHITE = 0xFF << 16 | 0xFF << 8 | 0xFF;
  private static final int RED = 0xFF000000 | 0xc0 << 16 | 0x00 << 8 | 0x00;
  private static final int BLACK = ~WHITE;
  private static final int BLUE = Integer.parseInt("000000001111111111111111", 2);
  private static final int BLUE_DARK = Integer.parseInt("000000000000000011111111", 2);
  private static final int GREEN = Integer.parseInt("000000001111111100000000", 2);
 
  int m_sea[][] = new int[10][10]; // исходное поле с кораблями
  /*{
  {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
  {0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
  {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
  {0, 1, 0, 0, 1, 1, 1, 0, 0, 0},
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
  {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
  {0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
  {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
  {0, 0, 0, 0, 1, 0, 0, 0, 0, 0}
  };
  */

  int count = 0; // = 20 заполненных ячеек
  boolean is_first = true;
  int move = 0;  // номер хода

  boolean show_all = false;

  int shotX = -1;     // координаты выстрела
  int shotY = -1;

  int combat[][] = new int[10][10]; // 1 - был произведен удар; 0 - нет

  private Command back = new Command("Back", Command.BACK, 1);
  private Command go = new Command("Go", Command.OK, 1); 
  private Command show = new Command("Show all", Command.SCREEN, 1); 
  private Display display = Display.getDisplay(Matrix.getInstance());

  public SeaFight() {
    super();
    addCommand(go);
    addCommand(back);
    addCommand(show);
    setCommandListener(this);
    display.setCurrent(this);

    ]// заполняем исходное поле кораблями
    
    Random rund = new Random();
    int x = 0, y = 0;
    int TT = 0;   // количество труб
                  // 0, 1, 2, 3 - "1"
                  // 4, 5, 6    - "2"
                  // 7, 8       - "3"
                  // 9          - "4"
    int way = 0;  // = 1 - down; = 0 - right

  for (TT = 0; TT < 10; TT++){

    w1: while(true){
      // все корабли расставлены
 
      rund = new Random();
      x = Math.abs(rund.nextInt() % 10);
      y = Math.abs(rund.nextInt() % 10);

      // если координаты x, y не заняты 
      if(m_sea[y][x] != 1) { 
 
        // если вокруг нее имеются другие точки
        if(y-1 >= 0){
          if(m_sea[y-1][x] == 1) continue w1; 
          if(x-1 >= 0) 
            if(m_sea[y-1][x-1] == 1) continue w1; 
          if(x+1 < 10)
            if(m_sea[y-1][x+1] == 1) continue w1; 
        }
        if(y+1 < 10){ 
          if(m_sea[y+1][x] == 1) continue w1; 
          if(x-1 >= 0) 
            if(m_sea[y+1][x-1] == 1) continue w1; 
          if(x+1 < 10)
            if(m_sea[y+1][x+1] == 1) continue w1; 
        }
        if(x-1 >= 0) 
          if(m_sea[y][x-1] == 1) continue w1; 
        if(x+1 < 10)
          if(m_sea[y][x+1] == 1) continue w1; 

        // ставим точку с координатами x, y
        m_sea[y][x] = 1; 

        // если 1-трубный - в начало цикла for
        if(TT == 0 || TT == 1 || TT == 2 || TT == 3) {
          break w1;
        }

        // выбираем направление формирования корабля
        way = rund.nextInt();
        if (way >= 0) way = 1; // down
        else way = 0;          // right

        // если вниз
        if (way == 1) {
          if(y+1 >= 10) {
            m_sea[y][x] = 0;  // удаляем точку
            continue w1;      // в начало цикла
          }

          if(is_next(y+1, x, true)) {
            m_sea[y][x] = 0;  // удаляем точку
            continue w1;      // в начало цикла
          }

          m_sea[y+1][x] = 1;  // ставим точку с координатами x, y+1

          // если 2-трубный - в начало цикла for
          if(TT == 4 || TT == 5 || TT == 6) {
            break w1;
          }

         if(y+2 >= 10) {
            m_sea[y][x] = 0;   // удаляем точку [y]  [x]
            m_sea[y+1][x] = 0; // удаляем точку [y+1][x]
            continue w1;       // в начало цикла
          }

          if(is_next(y+2, x, true)) {
            m_sea[y][x] = 0;   // удаляем точку [y]  [x]
            m_sea[y+1][x] = 0; // удаляем точку [y+1][x]
            continue w1;       // в начало цикла
          }

          m_sea[y+2][x] = 1;  // ставим точку с координатами x, y+2

          // если 3-трубный - в начало цикла
          if(TT == 7 || TT == 8) {
            break w1;
          }

         if(y+3 >= 10) {
            m_sea[y][x] = 0;   // удаляем точку [y]  [x]
            m_sea[y+1][x] = 0; // удаляем точку [y+1][x]
            m_sea[y+2][x] = 0; // удаляем точку [y+2][x]
            continue w1;       // в начало цикла
          }

          if(is_next(y+3, x, true)) {
            m_sea[y][x] = 0;   // удаляем точку [y]  [x]
            m_sea[y+1][x] = 0; // удаляем точку [y+1][x]
            m_sea[y+2][x] = 0; // удаляем точку [y+2][x]
            continue w1;       // в начало цикла
          }

          m_sea[y+3][x] = 1;  // ставим точку с координатами x, y+3
          break w1;
        } // END если вниз

        // если вправо
        else {
          if(x+1 >= 10) {
            m_sea[y][x] = 0;  // удаляем точку
            continue w1;      // в начало цикла
          }

          if(is_next(y, x+1, false)) {
            m_sea[y][x] = 0;  // удаляем точку
            continue w1;      // в начало цикла
          }

          m_sea[y][x+1] = 1;  // ставим точку с координатами x+1, y

          // если 2-трубный - в начало цикла
          if(TT == 4 || TT == 5 || TT == 6) {
            break;
          }

         if(x+2 >= 10) {
            m_sea[y][x] = 0;   // удаляем точку [y][x]
            m_sea[y][x+1] = 0; // удаляем точку [y][x+1]
            continue w1;       // в начало цикла
          }

          if(is_next(y, x+2, false)) {
            m_sea[y][x] = 0;   // удаляем точку [y][x]
            m_sea[y][x+1] = 0; // удаляем точку [y][x+1]
            continue w1;       // в начало цикла
          }

          m_sea[y][x+2] = 1;  // ставим точку с координатами x+2, y

          // если 3-трубный - в начало цикла
          if(TT == 7 || TT == 8) {
            break w1;
          }

         if(x+3 >= 10) {
            m_sea[y][x] = 0;   // удаляем точку [y][x]
            m_sea[y][x+1] = 0; // удаляем точку [y][x+1]
            m_sea[y][x+2] = 0; // удаляем точку [y][x+2]
            continue w1;       // в начало цикла
          }

          if(is_next(y, x+3, false)) {
            m_sea[y][x] = 0;   // удаляем точку [y][x]
            m_sea[y][x+1] = 0; // удаляем точку [y][x+1]
            m_sea[y][x+2] = 0; // удаляем точку [y][x+2]
            continue w1;       // в начало цикла
          }

          m_sea[y][x+3] = 1;  // ставим точку с координатами x+3, y
          break w1;
        } // END если вправо

      } // END if(m_sea[y][x] != 1)
    } // END while
  } // END for TT

  } // END function SeaFight(){}


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
    g.fillRect(clipX, clipY, clipH, clipW);
    g.setColor(color);
  }

  /* отображает внешний вид этого подкласса в Canvas */
  public void paint(Graphics g) {
    paintClipRect(g);  // очистить экран

    int width = getWidth() - 5;
    int height = getHeight() - 20;

    int dx = width / 10;     // разбиение по x
    int dy = height / 10;    // разбиение по y

    g.setColor(BLACK);

    g.setColor(BLUE_DARK);
    // ставим метку в ту ктетку в которую был произведен удар
    for (int i = 0; i < 10; i++)
      for (int j = 0; j < 10; j++)
        if (combat[i][j] == 1)
          g.fillRect(i*dx + 5, j*dy + 20, dx, dy); 
//          g.fillRect(i*dx + dx/2 - dx/10 + 5, j*dy + dy/2 - dy/10 + 20, dx/5, dy/5);

     g.setColor(RED);
    // закрашиваем клетку если произошло попадание
    for (int i = 0; i < 10; i++)
      for (int j = 0; j < 10; j++)
        if (combat[i][j] == 1 && m_sea[i][j] == 1) {
          g.fillRect(i*dx + 5, j*dy + 20, dx, dy);
        }

    // !!! если нажата Show_all кнопка показываем все спрятанные корабли
    if(show_all) {
      for (int i = 0; i < 10; i++)
        for (int j = 0; j < 10; j++)
          if (m_sea[i][j] == 1) {
            g.fillRect(i*dx + 5, j*dy + 20, dx, dy);
          }
    } // END if(show_all)

    g.setColor(BLACK);

    // lines
    for (int i = 0; i <= 10; i++)                    // вертикальные
      g.drawLine(i*dx + 5, 20, i*dx + 5, 500);
    for (int i = 0; i <= 10; i++)                    // горизонтальные
      g.drawLine(5, i*dy + 20, 500, i*dy + 20);

    g.setFont(Font.getDefaultFont());
    g.drawString("Enter:", 1, 1, Graphics.LEFT | Graphics.TOP);
    

    // подписи к осям
    g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL));
    for (int i = 0; i < 10; i++) 
      g.drawString("" + i, i*dx + dx/2 + 5, 20, Graphics.HCENTER | Graphics.BOTTOM);
    for (int i = 0; i < 10; i++)
      g.drawString("" + i, 0, i*dy + dy/2 + 24, Graphics.LEFT | Graphics.BOTTOM); 

    g.setFont(Font.getDefaultFont());

    if (shotX == -1 && shotY == -1) {
      g.drawString("X :: Y", 35, 1, Graphics.LEFT | Graphics.TOP);
    }

    if (shotX != -1 && shotY == -1) {
      g.drawString(shotX + " :: Y", 35, 1, Graphics.LEFT | Graphics.TOP);
    }

    if (shotX != -1 && shotY != -1) 
      g.drawString(shotX + " :: " + shotY, 35, 1, Graphics.LEFT | Graphics.TOP);

    g.setColor(RED);
    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
    g.drawString("Move # "+ move, 65, 1, Graphics.LEFT | Graphics.TOP);
    
/*
    if (count == 20) {
      g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));
      g.drawString("You Win!!!", 50, 50, Graphics.LEFT | Graphics.BOTTOM);
    }
*/   
  }

  // Определяет, что обработка должна быть сделана в ответ на событие опускания
  // клавиши, произошедшее в Canvas. Этот метод подменяет тот же самый метод в Canvas.
  public void keyReleased(int keyCode) {
    printKeyEventInfo(keyCode);
  }


  protected void printKeyEventInfo(int keyCode) {
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
        if (is_first){
          shotX = keyCode - 48;
          is_first = false;
        }
        else {
          shotY = keyCode - 48;
          is_first = false;
        }
        repaint();
        break;
    } // end of switch
  }


  boolean is_next (int y, int x, boolean down) {
    if (down) {
      if (y+1 >= 10) return true;
      if (m_sea[y+1][x] == 1) return true;
      if (x-1 >= 0) if (m_sea[y+1][x-1] == 1) return true;
      if (x+1 < 10) if (m_sea[y+1][x+1] == 1) return true;
    }
    else {  // движение вправо
      if (x+1 >= 10) return true;
      if (m_sea[y][x+1] == 1) return true;
      if (y-1 >= 0) if (m_sea[y-1][x+1] == 1) return true;
      if (y+1 < 10) if (m_sea[y+1][x+1] == 1) return true;
    }
    return false;
  }



  public void commandAction(Command c, Displayable d) {
    if (c == back) Matrix.getInstance().display();
    if (c == go) {
      is_first = true;
      if (shotX != -1 && shotY != -1) {
        combat[shotY][shotX] = 1;
      }
      shotX = shotY = -1;
      move++;
      repaint();
    }
    if (c == show) { show_all = true; repaint(); }
  }

} // end of class Sea-fight
