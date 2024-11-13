import java.awt.*;
public class Smoke extends Shape{
	public Smoke(int x,int y, int w, int h){
		super(x,y,w,h);
	}
	@Override
	public void paint(Graphics gr){
		Graphics2D w2 = (Graphics2D) gr;
		Image i = Toolkit.getDefaultToolkit().getImage("smoke.png");
		w2.drawImage(i,getX(),getY(),getW(),getH(),this);
	}
}
