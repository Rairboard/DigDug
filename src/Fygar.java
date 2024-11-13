import java.awt.*;

public class Fygar extends Shape{
    boolean left,right;
    private int speed;
    private int pointX, pointY;
    private Image image;
    public Fygar(int x, int y,int w,int h,int speed,int px,int py) {
        super(x,y,w,h);
        this.speed = speed;
        pointX = px;
        pointY = py;
        image = Toolkit.getDefaultToolkit().getImage("fygar.png");
    }
    public int getPointX(){
        return pointX;
    }
    public int getPointY(){
        return pointY;
    }
    public void goUp(int limit){
        if(getY()-speed>limit){
            setY(getY()-speed);
            pointY--;
        }
    }
    public void goDown(int limit){
        if(getY()+speed<limit){
            setY(getY()+speed);
            pointY++;
        }
    }
    public void goLeft(int limit){
        if(getX()-speed>limit){
            setX(getX()-speed);
            pointX--;
        }
    }
    public void goRight(int limit){
        if(getX()+speed<limit){
            setX(getX()+speed);
            pointX++;
        }
    }
    public void setImage(String s){
        image = Toolkit.getDefaultToolkit().getImage(s);
    }
    @Override
    public void paint(Graphics gr){
        Graphics2D w2 = (Graphics2D) gr;
        w2.drawImage(image,getX(),getY(),getW(),getH(),this);
    }
}
