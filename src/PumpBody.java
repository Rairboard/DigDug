import java.awt.*;
public class PumpBody extends Shape{
    Image image;
    int pointX,pointY;
    public PumpBody(int x,int y, int w, int h,int px,int py){
        super(x,y,w,h);
        image = Toolkit.getDefaultToolkit().getImage("rope_HORIZONTAL.png");
        pointX = px;
        pointY = py;
    }
    public int getPointX(){
        return pointX;
    }
    public int getPointY(){
        return pointY;
    }
    public void setImage(Image img){
        image= img;
    }
    @Override
    public void paint(Graphics gr){
        Graphics2D w2 = (Graphics2D) gr;
        w2.drawImage(image,getX(),getY(),getW(),getH(),this);
    }
}
