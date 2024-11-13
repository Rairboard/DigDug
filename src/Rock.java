import java.awt.*;

public class Rock extends Shape {
    private int fallRate;
    private int pointX, pointY;
    public Rock(int x,int y,int width,int height,int fallRate,int px, int py){
        super(x,y,width,height);
        this.fallRate = fallRate;
        pointX = px;
        pointY = py;
    }
    public int getPointX(){
        return pointX;
    }
    public int getPointY(){
        return pointY;
    }
    public void fall(int limit){
        if(getY()+fallRate < limit){
            setY(getY() + fallRate);
            pointY++;
        }
    }
    @Override
    public void paint(Graphics gr){
        Graphics2D w2 = (Graphics2D) gr;
        Image i = Toolkit.getDefaultToolkit().getImage("rock.png");
        w2.drawImage(i,getX(),getY(),getW(),getH(),this);
    }
}
