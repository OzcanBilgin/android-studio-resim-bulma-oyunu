package com.example.user.resimbulma2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * Created by user on 4.11.2016.
 */

public class MemoryButton extends Button {
    protected int row;
    protected int column;
    protected int frontDrawableId;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;


    public MemoryButton(Context context, int r, int c, int frontImageDrawableId){
        super(context);

        row = r;
        column = c;

        frontDrawableId = frontImageDrawableId;
        front = context.getDrawable(frontImageDrawableId); // fID BG dizi hali dizi çağirir
        back = context.getDrawable(R.drawable.icon);

        setBackground(back);

        GridLayout.LayoutParams tempParams =  new GridLayout.LayoutParams(GridLayout.spec(r),
                GridLayout.spec(c)); // griD layout satir sutun numaralarini verir

        tempParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        tempParams.height = GridLayout.LayoutParams.WRAP_CONTENT;

        setLayoutParams(tempParams);


    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontDrawableId() {
        return frontDrawableId;
    }

    public void flip(){
        if (isMatched())
            return;

        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        }
        else{
            setBackground(front);
            isFlipped = true;
        }

    }
}
