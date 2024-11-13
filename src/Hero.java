import java.awt.*;

public class Hero extends Shape{
    int speed;
    Image img;
    public Hero(int x, int y,int w,int h, int sp) {
        super(x,y,w,h);
        this.speed = sp;
        img = Toolkit.getDefaultToolkit().getImage("hero_LEFT.png");
    }
    public void goUp(int limit){
        if(getY()-speed>limit){
            setY(getY()-speed);
        }
    }
    public void goDown(int limit){
        if(getY()+speed<limit){
            setY(getY()+speed);
        }
    }
    public void goLeft(int limit){
        if(getX()-speed>limit){
            setX(getX()-speed);
        }
    }
    public void goRight(int limit){
        if(getX()+speed<limit){
            setX(getX()+speed);
        }
    }
    public void setImage(Image i){
        img = i;
    }
    @Override
    public void paint(Graphics gr){
        Graphics2D w2 = (Graphics2D) gr;
        w2.drawImage(img,getX(),getY(),getW(),getH(),this);
    }
}
