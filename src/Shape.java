import java.awt.*;

/*
 *	Directions:
 *
 *		Write the intersects method below.
 *
 *		Then move on to Block
 */

abstract class Shape extends Canvas
{
    private int x, y, w, h; //these are instance variables

    //this is the constructor for a Block
    public Shape(int ex, int wy, int wd, int ht)
    {
        x =ex;
        y = wy;
        w=wd;
        h=ht;
    }

    // All Blocks will have all of these set and get methods
    public int getX( ){ return this.x; }

    public int getY( ){ return this.y; }
    public int getW(){ return this.w; }
    public int getH(){ return this.h; }
    public void setY( int wy ){ y = wy; }
    public void setX( int ex ){ x = ex; }
    public boolean intersects( Shape other )
    {
        Rectangle thisB = new Rectangle(this.x, this.y,this.w,this.h);
        Rectangle otherB = new Rectangle(other.getX(),other.getY(),other.getW(),other.getH());
        return (thisB.intersects(otherB));
    }
}