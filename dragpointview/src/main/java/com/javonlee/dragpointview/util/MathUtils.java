package com.javonlee.dragpointview.util;

import android.content.Context;
import android.graphics.PointF;

/**
 * Created by Administrator on 2017/7/15.
 */
public class MathUtils {

    /**
     * Get the point of intersection between circle and line.
     *
     * @param radius The circle radius.
     * @param lineK The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(float cx,float cy, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if(lineK != null){

            radian= (float) Math.atan(lineK);
            xOffset = (float) (Math.cos(radian) * radius);
            yOffset = (float) (Math.sin(radian) * radius);
        }else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(cx + xOffset, cy + yOffset);
        points[1] = new PointF(cx - xOffset, cy - yOffset);

        return points;
    }

    /**
     *  Gets the distance between two points.
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double getDistance(PointF p1,PointF p2){
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
