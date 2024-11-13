import java.awt.*;

public class Flower extends Shape {
    public Flower(int x,int y,int width,int height){
        super(x,y,width,height);
    }
    @Override
    public void paint(Graphics gr){
        Graphics2D w2 = (Graphics2D) gr;
        Image i = Toolkit.getDefaultToolkit().getImage("flower.png");
        w2.drawImage(i,getX(),getY(),getW(),getH(),this);
    }
}
