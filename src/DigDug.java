import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DigDug extends JPanel implements KeyListener,Runnable{
    Hero hero;
    ArrayList<Pooka> pooka;
    ArrayList<Fygar> fygar;
    ArrayList<Rock> rock;
    ArrayList<Flower> flower;
    ArrayList<PumpBody> body;
    Image bodyImage;
    int[][] hook;
    private boolean playing;
    private final int block_size = 24;
    private int lives;
    private int WIDTH,HEIGHT;
    private int row, col;
    private int[] heroPosition = new int[2];
    //  Column and rows
    private int[][] monster;
    private int[][] m;
    private int[][] rockPos;
    boolean[] key = new boolean[5];
    int[] dir = new int[2];

    public DigDug(int N) {
        reset(N);
        setSize(WIDTH, HEIGHT);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        new Thread(this).start();
    }
    public void reset(int N){
        lives = 0;
        playing = true;
        row = N;
        col = N * 2;
        HEIGHT = block_size * (row + 2);
        WIDTH = block_size * (col + 2);
        hook = new int[row][col];
        m = new int[row][col];
        monster = new int[row][col];
        rockPos = new int[row][col];
        int sr = row/5-1;
        int sc = (int)(Math.random() * col/4);
        heroPosition[0] = sc;
        heroPosition[1] = sr;
        hero = new Hero(heroPosition[0] * block_size + block_size,heroPosition[1] * block_size + block_size,block_size,block_size,block_size);
        pooka = new ArrayList<>();
        fygar = new ArrayList<>();
        rock = new ArrayList<>();
        flower = new ArrayList<>();
        body = new ArrayList<>();
        while (pooka.size() < (int)(Math.random() * 5)+2){
            int r = (int)(Math.random() * row);
            int c = (int)(Math.random() * col);
            if(monster[r][c]!=1 && r>=row/5){
                pooka.add(new Pooka(c*block_size + block_size,r*block_size+block_size,block_size,block_size,block_size,c,r));
                monster[r][c] = 1;
                int in = (int)(Math.random() * col/2)+1;
                int a = c - in;
                int b = c + in;
                for (int col = a; col <= b ; col++) {
                    if(isValidMove(col,r)){
                        m[r][col] = 1;
                    }
                }
            }
        }
        while(fygar.size()<(int)(Math.random()*5)+2){
            int r = (int)(Math.random() * row);
            int c = (int)(Math.random() * col);
            if(monster[r][c]!=1 && r >= row/5){
                fygar.add(new Fygar(c*block_size + block_size,r*block_size+block_size,block_size,block_size,block_size,c,r));
                monster[r][c] = 1;
                int in = (int)(Math.random() * col/2)+1;
                int a = c - in;
                int b = c + in;
                for (int col = a; col <= b ; col++) {
                    if(isValidMove(col,r)){
                        m[r][col] = 1;
                    }
                }
            }
        }
        ArrayList<int[]> loc = new ArrayList<>();
        int numRock = (int)(Math.random() * 20)+5;
        int numFlower = (int)(Math.random() * 10)+1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(m[i][j]==1 && monster[i][j]!=1 && i!=heroPosition[1] && j!=heroPosition[0]&& rock.size() < numRock){
                    loc.add(new int[]{i,j});
                }
            }
        }
        for (int i = 0; i < numRock && loc.size() > 0; i++) {
            int in = (int)(Math.random() * loc.size());
            int[] point = loc.remove(in);
            int r = point[0];
            int c = point[1];
            rockPos[r][c] = 1;
            rock.add(new Rock(c*block_size + block_size,r*block_size+block_size,block_size,block_size,block_size,c,r));
        }
        for(int i = 0;i < numFlower;i++){
            int r = row/5-1;
            int c = (int)(Math.random() * col);
            if(r!=heroPosition[1]||c!=heroPosition[0]){
                flower.add(new Flower(c * block_size + block_size,r * block_size + block_size,block_size,block_size));
            }
        }
    }
    @Override
    public void paint(Graphics window){
        if(playing){
            System.out.println("Running");
            Graphics2D g2 = (Graphics2D) window;
            Image img1 = Toolkit.getDefaultToolkit().getImage("background.jpg");
            g2.drawImage(img1, 0, 0, WIDTH, HEIGHT, this);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(m[i][j] == 1){
                        window.setColor(Color.BLACK);
                        window.fillRect(j * block_size + block_size,i * block_size + block_size,block_size,block_size);
                    }else{
                        if(i<row/5){
                            Color blue = new Color(26, 26, 237);
                            window.setColor(blue);
                            window.fillRect(j*block_size + block_size,i * block_size + block_size,block_size,block_size);
                        }
                        else if(i < row/5*2){
                            window.setColor(Color.ORANGE);
                            window.fillRect(j*block_size + block_size,i * block_size + block_size,block_size,block_size);
                        }
                        else if(i < row/5*3){
                            Color orange = new Color(255, 153, 51);
                            window.setColor(orange);
                            window.fillRect(j*block_size + block_size,i * block_size + block_size,block_size,block_size);
                        }
                        else if(i < row/5*4){
                            window.setColor(Color.RED);
                            window.fillRect(j*block_size + block_size,i * block_size + block_size,block_size,block_size);
                        }
                        else{
                            Color brown = new Color(107, 31, 1);
                            window.setColor(brown);
                            window.fillRect(j*block_size + block_size,i * block_size + block_size,block_size,block_size);
                        }
                    }
                }
            }
            window.setColor(Color.ORANGE);
            window.drawString(lives + " Flowers Dug Up! Gained " + lives + " Lives$$$",WIDTH/28,HEIGHT/14);
            window.setColor(Color.ORANGE);
            window.drawString(lives + " Lives Left!!!",WIDTH/28,HEIGHT/10);
            hero.paint(window);
            for (int i = 0; i < flower.size(); i++) {
                if(flower.get(i).intersects(hero)){
                    lives++;
                    flower.remove(i);
                    i--;
                }else{
                    flower.get(i).paint(window);
                }
            }
            for(int i=0;i<pooka.size();i++) {
                if(rockPos[pooka.get(i).getPointY()][pooka.get(i).getPointX()]==1|| hook[pooka.get(i).getPointY()][pooka.get(i).getPointX()]==1){
                    pooka.remove(i);
                    i--;
                    continue;
                }
                pooka.get(i).paint(window);
            }
            for(int i=0;i<fygar.size();i++){
                if(rockPos[fygar.get(i).getPointY()][fygar.get(i).getPointX()]==1|| hook[fygar.get(i).getPointY()][fygar.get(i).getPointX()]==1){
                    fygar.remove(i);
                    i--;
                    continue;
                }
                fygar.get(i).paint(window);
            }
            for(Rock r : rock){
                r.paint(window);
            }
            for(PumpBody pb : body){
                pb.paint(window);
            }
            for(Pooka p : pooka){
                if (p.intersects(hero)) {
                   if(lives>0){
                       lives--;
                       reset(row);
                   }else{
                       window.setColor(Color.ORANGE);
                       window.setFont(new Font("Ravie",Font.BOLD,20));
                       window.drawString("YOU DIED!!! YOURE CADEN!",WIDTH/3,HEIGHT/6);
                       playing = false;
                       return;
                   }
                }
            }
            for(Fygar f : fygar){
                if (f.intersects(hero)) {
                    if(lives>0){
                        lives--;
                        reset(row);
                    }else{
                        window.setColor(Color.ORANGE);
                        window.setFont(new Font("Ravie",Font.BOLD,20));
                        window.drawString("YOU DIED!!! YOURE CADEN!",WIDTH/3,HEIGHT/6);
                        playing = false;
                        return;
                    }
                }
            }
            if(fygar.isEmpty()&&pooka.isEmpty()){
                window.setColor(Color.ORANGE);
                window.setFont(new Font("Ravie",Font.BOLD,15));
                window.drawString("SWEPT ALL MONSTERS!!! 212$$$!",WIDTH/3,HEIGHT/6);
                playing = false;
                return;
            }
            if(key[0]){
                key[0] = false;
            }
            else if(key[1]){
                key[1] = false;
            }
            else if(key[2]){
                key[2] = false;
            }
            else if(key[3]){
                key[3] = false;
            }
            else if(key[4]){
                key[4] = false;
                body = new ArrayList<>();
                hook = new int[row][col];
            }
        }
    }
    public void cornerHero(int targetX,int targetY){
        for(Pooka p :pooka){
//            if((int)(Math.random() * 4)==0){
//                continue;
//            }
            int px = p.getPointX(), py = p.getPointY();
            if(px < targetX && isValidMove(px+1,py) && monster[py][px+1]!=1 && rockPos[py][px+1]!=1){
                p.goRight(WIDTH);
                monster[py][px+1] = 1;
                monster[py][px] = 0;
                p.setImage("pooka_RIGHT.png");
                // right
            }
            else if(px > targetX && isValidMove(px-1,py) && monster[py][px-1]!=1 && rockPos[py][px-1]!=1){
                p.goLeft(0);
                monster[py][px-1] = 1;
                monster[py][px] = 0;
                p.setImage("pooka_LEFT.png");
                // left
            }
            else if(py < targetY&& isValidMove(px,py+1) && monster[py+1][px]!=1 && rockPos[py+1][px]!=1){
                p.goDown(HEIGHT);
                monster[py+1][px] = 1;
                monster[py][px] = 0;
                // down
            }
            else if(py > targetY && isValidMove(px,py-1) && monster[py-1][px]!=1 && rockPos[py-1][px]!=1){
                p.goUp(0);
                monster[py-1][px] = 1;
                monster[py][px] = 0;
                // up
            }
        }
        for(Fygar f :fygar){
            if((int)(Math.random() * 4)==0){
                continue;
            }
            int px = f.getPointX(), py = f.getPointY();
            if(px < targetX && isValidMove(px+1,py) && monster[py][px+1]!=1 && rockPos[py][px+1]!=1){
                f.goRight(WIDTH);
                monster[py][px+1] = 1;
                monster[py][px] = 0;
                f.setImage("fygar_RIGHT.png");
                // right
            }
            else if(px > targetX && isValidMove(px-1,py) && monster[py][px-1]!=1 && rockPos[py][px-1]!=1){
                f.goLeft(0);
                monster[py][px-1] = 1;
                monster[py][px] = 0;
                f.setImage("fygar_LEFT.png");
                // left
            }
            else if(py < targetY&& isValidMove(px,py+1) && monster[py+1][px]!=1 && rockPos[py+1][px]!=1){
                f.goDown(HEIGHT);
                monster[py+1][px] = 1;
                monster[py][px] = 0;
                // down
            }
            else if(py > targetY && isValidMove(px,py-1) && monster[py-1][px]!=1 && rockPos[py-1][px]!=1){
                f.goUp(0);
                monster[py-1][px] = 1;
                monster[py][px] = 0;
                // up
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int x = heroPosition[0];
        int y = heroPosition[1];
        System.out.println("keying");
        switch (keyCode) {
            case KeyEvent.VK_UP:
                y--;
                if (isValidMove(x, y)) {
                    hero.goUp(0);
                    key[0] = true;
                    Image i = Toolkit.getDefaultToolkit().getImage("hero_UP.png");
                    hero.setImage(i);
                }
                break;
            case KeyEvent.VK_DOWN:
                y++;
                if (isValidMove(x, y)) {
                    hero.goDown(HEIGHT);
                    key[1] = true;
                    Image i = Toolkit.getDefaultToolkit().getImage("hero_DOWN.png");
                    hero.setImage(i);
                }
                break;
            case KeyEvent.VK_LEFT:
                x--;
                if (isValidMove(x, y)) {
                    hero.goLeft(0);
                    Image i = Toolkit.getDefaultToolkit().getImage("hero_LEFT.png");
                    hero.setImage(i);
                    key[2] = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                x++;
                if (isValidMove(x, y)) {
                    hero.goRight(WIDTH);
                    Image i = Toolkit.getDefaultToolkit().getImage("hero_RIGHT.png");
                    hero.setImage(i);
                    key[3] = true;
                }
                break;
            case KeyEvent.VK_W:
                dir = new int[]{0,-1};
                key[4] = true;
                bodyImage = Toolkit.getDefaultToolkit().getImage("rope_VERTICAL.png");
                break;
            case KeyEvent.VK_A:
                dir = new int[]{-1,0};
                key[4] = true;
                bodyImage = Toolkit.getDefaultToolkit().getImage("rope_HORIZONTAL.png");
                break;
            case KeyEvent.VK_D:
                dir = new int[]{1,0};
                key[4] = true;
                bodyImage = Toolkit.getDefaultToolkit().getImage("rope_HORIZONTAL.png");
                break;
            case KeyEvent.VK_S:
                dir = new int[]{0,1};
                key[4] = true;
                bodyImage = Toolkit.getDefaultToolkit().getImage("rope_VERTICAL.png");
                break;
        }
        if (isValidMove(x, y)) {
            heroPosition[0] = x;
            heroPosition[1] = y;
            if(heroPosition[1]>=row/5){
                m[y][x] = 1;
            }
            if(!key[4]){
                cornerHero(heroPosition[0], heroPosition[1]); // Move the monster toward the hero position with 75% random chance
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(monster[i][j]==1){
                        if(i>=row/5){
                            m[i][j] = 1;
                        }
                    }
                }
            }
            updateRockPosition();
            if(key[4]){
                body = new ArrayList<>();
                int hx = heroPosition[0];
                int hy = heroPosition[1];
                while (isValidMove(hx, hy)&&(m[hy][hx] == 1|| hy < row/5)&& rockPos[hy][hx]!=1) {
                    if(!(hx==heroPosition[0] && hy == heroPosition[1])){
                        hook[hy][hx] = 1;
                        body.add(new PumpBody(hx * block_size + block_size,hy * block_size + block_size,block_size,block_size,hx,hy));
                        body.get(body.size()-1).setImage(bodyImage);
                    }
                    if(monster[hy][hx]==1){
                        hook[hy][hx] = 1;
                        body.add(new PumpBody(hx * block_size + block_size,hy * block_size + block_size,block_size,block_size,hx,hy));
                        body.get(body.size()-1).setImage(bodyImage);
                    }
                    hx+=dir[0];
                    hy+=dir[1];
                }
            }
        }
    }
    public void updateRockPosition(){
        for(Rock r : rock){
            int px = r.getPointX();
            int py = r.getPointY();
            if(isValidMove(px,py+1) && m[py+1][px]==1 && !(py+1==heroPosition[1] && px == heroPosition[0])){
                rockPos[py+1][px] = 1;
                rockPos[py][px] = 0;
                r.fall(HEIGHT);
            }
        }
    }
    //x = col and y = row
    private boolean isValidMove(int x, int y) {
        return y >= row/5-1 && x > -1 && x < col;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
        try
        {

            double interval = Math.pow(10,9) / 30;
            double nextDrawTime = System.nanoTime() + interval;
            while( playing )
            {
                double remain = nextDrawTime - System.nanoTime();
                remain/=Math.pow(10,6);
                if(remain>0){
                    Thread.sleep( (long)remain );
                }
                nextDrawTime += interval;
                repaint();
            }
        }
        catch( Exception e )
        {
        }
    }
}
