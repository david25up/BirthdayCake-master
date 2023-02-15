package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint coordPaint = new Paint();

    Paint green = new Paint();

    Paint pink = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 70.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;

    public static final float ballonRadius = 50.0f;

    private CakeModel cakeModel;

    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        cakeModel = new CakeModel();
        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFF132F7D);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        coordPaint.setColor(Color.RED);
        balloonPaint.setColor(Color.BLUE);
        balloonPaint.setStyle(Paint.Style.FILL);
        green.setColor(0xFF00ff08); //green
        green.setStyle(Paint.Style.FILL);
        pink.setColor(0xFFff00b3); //pink
        pink.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.WHITE);  //better than black default

    }

    public CakeModel getCakeModel() {
        return cakeModel;
    }


    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if (cakeModel.hasCandles) {
            canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);
        }
        if (cakeModel.isLit && cakeModel.hasCandles) {
            //draw the outer flame
            float flameCenterX = left + candleWidth/2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius/3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius/3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);

            //draw the wick

                float wickLeft = left + candleWidth / 2 - wickWidth / 2;
                float wickTop = bottom - wickHeight - candleHeight;
                canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
        }
    }

    /**
     * Draws a balloon at the expected x and y coordinates
     * Note: The balloon's center is placed at the center of the click and
     *       there cannot be more than one balloon on screen.
     */
    public void drawBalloon (Canvas canvas, float xCoord, float yCoord) {


        float xCenter = xCoord + ballonRadius;
        float yCenter = yCoord + ballonRadius;



        canvas.drawCircle(xCoord, yCoord, ballonRadius, balloonPaint); // Circle on Top

        canvas.drawLine(xCoord, (yCoord + (ballonRadius / 2) + 10.0f), xCoord, (yCoord + (ballonRadius * 2) + 30.0f), wickPaint);

        drawTriangle(canvas, balloonPaint, xCoord, yCoord, ballonRadius); // Bottom Triangle
    }

    /**
     * Helper method to draw the bottom triangle of the balloon.
     */
    public void drawTriangle(Canvas canvas, Paint paint, float x, float centerY, float length) {
        float halfLength = length / 2;
        float y = centerY + halfLength + 10.0f ;

        Path path = new Path();
        path.moveTo(x - halfLength, y); // Top Left
        path.lineTo(x, y + length); // Bottom Point
        path.lineTo(x + halfLength, y); // Top Right
        path.lineTo(x, y); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        canvas.drawText(cakeModel.xCoord + ", " + cakeModel.yCoord, 1300, 700, coordPaint);

        coordPaint.setTextSize(100);

        int candleAmount = cakeModel.candleCount+1;
        for (int i = 1; i < candleAmount; i++) {
            drawCandle(canvas, cakeLeft + ((i * cakeWidth) / (candleAmount))- candleWidth / candleAmount, cakeTop);
        }

        //Draws the balloons
        drawBalloon(canvas, cakeModel.xCoord, cakeModel.yCoord);

        drawBox(canvas, cakeModel.xCoord, cakeModel.yCoord, pink, green);
    }//onDraw

    public void drawBox(Canvas canvas, float xCord, float yCord, Paint pink, Paint green){
        //green boxes
        canvas.drawRect(xCord - 20, yCord - 10, xCord, yCord, green);// top left
        canvas.drawRect(xCord, yCord, xCord + 20, yCord + 10, green);//bottom right

        // pink
        canvas.drawRect(xCord - 20, yCord, xCord, yCord + 10, pink); // bottom left
        canvas.drawRect(xCord, yCord - 10, xCord + 20, yCord, pink);// top right

    }//drawbox

}//class CakeView

