package cn.onekit;

/**
 * Created by BryantCurry on 2016/12/29.
 */

public class SIZE {
    private int strwidth;
    private int strheight;
    public SIZE(int strwidth,int strheight){
        this.strheight = strheight;
        this.strwidth = strwidth;
    }
    public String result(){

        return  strwidth+","+strheight;
    }
}
