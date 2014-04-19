import javax.microedition.lcdui.*;

class Questions {
  String question[];
  String answers[];
  int right_answer;

  Questions(String question[], String answers[], int right_answer){
    this.question = question;
    this.answers = answers;
    this.right_answer = right_answer;
  }

} // END OF class Questions


public class OLucky extends Canvas implements CommandListener {
 
  private static final int WHITE = 0xFF << 16 | 0xFF << 8 | 0xFF;
  private static final int RED = 0xFF000000 | 0xc0 << 16 | 0x00 << 8 | 0x00;
  private static final int BLACK = ~WHITE;
  private static final int BLUE = Integer.parseInt("000000001111111111111111", 2);
  private static final int BLUE_DARK = Integer.parseInt("000000000000000011111111", 2);
  private static final int GREEN = Integer.parseInt("000000001111111100000000", 2);
  private static final int YELLOW = Integer.parseInt("111111111111111100000000", 2);

  private Command back;
  private Command ok; 
  private Display display;

  boolean you_win = false;
  boolean you_lose = false;

  int quest_number = 0;
  int MAX_Q = 25;
  int on_air = 0;
  int money = 0;
  Questions q[] = new Questions[MAX_Q]; 

  public OLucky() {
    super();
    back = new Command("Back", Command.BACK, 2);
    ok = new Command("OK", Command.OK, 1);
    addCommand(back);
    addCommand(ok);
    display = Display.getDisplay(Matrix.getInstance());
    setCommandListener(this);
    display.setCurrent(this);

    String qstr[][] = new String[MAX_Q][4];
    String astr[][] = new String[MAX_Q][4];
    int rnum[] = new int[MAX_Q];
    int t = 0;

//               "***********************";

    qstr[t][0] = "Sasha _____ English.";
    qstr[t][1] = "";  
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "speak";
    astr[t][1] = "speaks";
    astr[t][2] = "does speaks";
    astr[t][3] = "do speak";
    rnum[t] = 1;

    t++;
    qstr[t][0] = "_____ some keys on";
    qstr[t][1] = "the desk.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "There is";
    astr[t][1] = "There are";
    astr[t][2] = "It is";
    astr[t][3] = "They are";
    rnum[t] = 1;

    t++;
    qstr[t][0] = "Ksenia _____ got";
    qstr[t][1] = "_____ brothers or";
    qstr[t][2] = "sisters.";
    qstr[t][3] = "";
    astr[t][0] = "hasn't/any";
    astr[t][1] = "hasn't/some";
    astr[t][2] = "has/any";
    astr[t][3] = "have/some";
    rnum[t] = 0;

    t++;
    qstr[t][0] = "Can I _____ you?";
    qstr[t][1] = "";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "am helping";
    astr[t][1] = "help";
    astr[t][2] = "to help";
    astr[t][3] = "helping";
    rnum[t] = 1;

//               "***********************";

    t++;
    qstr[t][0] = "He doesn't work _____";
    qstr[t][1] = "New Year's Day.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "in";
    astr[t][1] = "at";
    astr[t][2] = "on";
    astr[t][3] = "for";
    rnum[t] = 2;  

    t++;
    qstr[t][0] = "Oleg watches TV _____";
    qstr[t][1] = "he goes to sleep.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "after";
    astr[t][1] = "before";
    astr[t][2] = "for";
    astr[t][3] = "at";
    rnum[t] = 1;  

    t++;
    qstr[t][0] = "Can you _____ me how";
    qstr[t][1] = "to get to the station?";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "say";
    astr[t][1] = "say to";
    astr[t][2] = "tell to";
    astr[t][3] = "tell";
    rnum[t] = 3;  

//               "***********************";

    t++;
    qstr[t][0] = "_____ he stay at home";
    qstr[t][1] = "last night?";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "Do";
    astr[t][1] = "Did";
    astr[t][2] = "Does";
    astr[t][3] = "Doesn't";
    rnum[t] = 1;  

    t++;
    qstr[t][0] = "Kirill is _____ music.";
    qstr[t][1] = "";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "listening";
    astr[t][1] = "listen";
    astr[t][2] = "listening to";
    astr[t][3] = "listens to";
    rnum[t] = 2;  

    t++;
    qstr[t][0] = "What is he _____ ?";
    qstr[t][1] = "";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "goes to do";
    astr[t][1] = "going do";
    astr[t][2] = "going to do";
    astr[t][3] = "goes do";
    rnum[t] = 2;  

//               "***********************";

    t++;
    qstr[t][0] = "Cars are ____ expensive";
    qstr[t][1] = "_____ bicycles.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "-/than";
    astr[t][1] = "most/then";
    astr[t][2] = "more/than";
    astr[t][3] = "more/then";
    rnum[t] = 2;  

    t++;
    qstr[t][0] = "How _____ sugar do we";
    qstr[t][1] = "need?";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "many";
    astr[t][1] = "a lot of";
    astr[t][2] = "much";
    astr[t][3] = "lots of";
    rnum[t] = 2;  


//               "***********************";
    t++;
    qstr[t][0] = "Where _____ you at";
    qstr[t][1] = "six o'clock?";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "go";
    astr[t][1] = "went";
    astr[t][2] = "was";
    astr[t][3] = "were";
    rnum[t] = 3;  

    t++;
    qstr[t][0] = "Ivan and Katya _____";
    qstr[t][1] = "go out much.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "do";
    astr[t][1] = "doesn't";
    astr[t][2] = "don't";
    astr[t][3] = "does";
    rnum[t] = 2;  

    t++;
    qstr[t][0] = "Nikita _____ a green";
    qstr[t][1] = "sweater today.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "wears";
    astr[t][1] = "does wear";
    astr[t][2] = "is wearing";
    astr[t][3] = "wearing";
    rnum[t] = 2;  

//               "***********************";

    t++;
    qstr[t][0] = "He _____ breakfast";
    qstr[t][1] = "when I arrived.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "eating";
    astr[t][1] = "was having";
    astr[t][2] = "eats";
    astr[t][3] = "has";
    rnum[t] = 1;  

    t++;
    qstr[t][0] = "Misha went _____";
    qstr[t][1] = "the bridge.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "through";
    astr[t][1] = "out of";
    astr[t][2] = "under";
    astr[t][3] = "into";
    rnum[t] = 2;  

    t++;
    qstr[t][0] = "That building is";
    qstr[t][1] = "_____ in the world.";
    qstr[t][2] = "";
    qstr[t][3] = "";
    astr[t][0] = "most tall";
    astr[t][1] = "the tallest";
    astr[t][2] = "tallest";
    astr[t][3] = "the most tall";
    rnum[t] = 1;  

    t++;
    qstr[t][0] = "Tell me wich is _____ .";
    qstr[t][1] = "";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "more heavier";
    astr[t][1] = "most heavy";
    astr[t][2] = "more heaviest";
    astr[t][3] = "heavier";
    rnum[t] = 3; 

    t++;
    qstr[t][0] = "The horror film was";
    qstr[t][1] = "very _____ .";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "frightened";
    astr[t][1] = "frightening";
    astr[t][2] = "feared";
    astr[t][3] = "fearing";
    rnum[t] = 1; 

    t++;
    qstr[t][0] = "Alla doesn't try _____";
    qstr[t][1] = "as Vadim.";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "as hard";
    astr[t][1] = "so hard";
    astr[t][2] = "hard";
    astr[t][3] = "hardly";
    rnum[t] = 0; 

    t++;
    qstr[t][0] = "What would you _____";
    qstr[t][1] = "tomorrow?";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "like to do";
    astr[t][1] = "like do";
    astr[t][2] = "like doing";
    astr[t][3] = "liking do";
    rnum[t] = 0; 

    t++;
    qstr[t][0] = "Have you _____ ";
    qstr[t][1] = "to Paris.";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "just go";
    astr[t][1] = "just gone";
    astr[t][2] = "ever went";
    astr[t][3] = "ever been";
    rnum[t] = 3; 

    t++;
    qstr[t][0] = "I have _____ an avocado";
    qstr[t][1] = "before.";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "not eat";
    astr[t][1] = "ate";
    astr[t][2] = "not ate";
    astr[t][3] = "never eaten";
    rnum[t] = 3; 

    t++;
    qstr[t][0] = "There's _____ traffic";
    qstr[t][1] = "in Moscow.";
    qstr[t][2] = "";
    qstr[t][3] = "";
//               "***********************";
    astr[t][0] = "too much";
    astr[t][1] = "much";
    astr[t][2] = "too many";
    astr[t][3] = "many";
    rnum[t] = 0; 

    for (int i = 0; i < MAX_Q; i++)
          q[i] = new Questions(qstr[i], astr[i], rnum[i]);

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
    int str_num = 13;

    g.setColor(BLACK);
    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
    g.drawString("Âîïðîñ " + (quest_number + 1), 3, str_num, Graphics.LEFT | Graphics.BOTTOM);

    g.fillRect(75, 1, 200, 13);
    g.setColor(GREEN);
    g.drawString(money + "$", 78, str_num, Graphics.LEFT | Graphics.BOTTOM);

    str_num += 17;

    g.setColor(BLACK);
    g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
    for (int i = 0; i < (q[quest_number].question).length; i++) {
      if (q[quest_number].question[i] == "") break;
      g.drawString(q[quest_number].question[i], 3, str_num, Graphics.LEFT | Graphics.BOTTOM);
      str_num += 13;
    }

    str_num += 5;
    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
    g.setColor(BLUE_DARK);
    for (int i = 0; i < (q[quest_number].answers).length; i++) {
      if (i == on_air) g.setColor(RED);
      g.drawString(q[quest_number].answers[i], 3, str_num, Graphics.LEFT | Graphics.BOTTOM);
      str_num += 13;
      g.setColor(BLUE_DARK);
    }        

    if (you_win) {
      g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
      g.setColor(BLACK);
      // paintClipRect(g);
      g.fillRect(g.getClipX(), g.getClipY(), 700, 700);
      g.setColor(YELLOW);
      g.drawString("You Win :))", 28, 40, Graphics.LEFT | Graphics.BOTTOM);
      g.setColor(GREEN);
      g.drawString(money + " $", 28, 60, Graphics.LEFT | Graphics.BOTTOM);
      }
    if (you_lose) {
      g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
      g.setColor(BLACK);
      // paintClipRect(g);
      g.fillRect(g.getClipX(), g.getClipY(), 700, 700);
      g.setColor(RED);
      g.drawString("You Lose :((", 25, 40, Graphics.LEFT | Graphics.BOTTOM);
    }
  }

  // Определяет, что обработка должна быть сделана в ответ на событие опускания
  // клавиши, произошедшее в Canvas. Этот метод подменяет тот же самый метод в Canvas.
  public void keyReleased(int keyCode) {
    switch(keyCode){
      case UP: 
      case KEY_NUM2:
      case -1:
        if (on_air == 0) on_air = 3;
        else on_air--;
        repaint();
        break;
      case DOWN:
      case KEY_NUM8:
      case -2:
        if (on_air == 3) on_air = 0;
        else on_air++;
        repaint();
        break;
    } // end of switch
    repaint();
  }

  public void commandAction(Command c, Displayable d) {
    if (c == back) Matrix.getInstance().display(); 
    if (c == ok) {
      if (on_air != q[quest_number].right_answer) {
        if (!you_win) {
          repaint();
          you_lose = true;
          quest_number = 0;
          }
        }
      }
      on_air = 0;
      if (quest_number < MAX_Q - 1) {
        quest_number++;
        if (!you_win) money += 100;
      }
      else { 
        you_win = true;
        quest_number = 0;
      }
      repaint();
  }

} // end of class
