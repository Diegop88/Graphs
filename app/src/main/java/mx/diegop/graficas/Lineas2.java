package mx.diegop.graficas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego A. Ramírez Vásquez on 17/07/2015.
 *
 */
public class Lineas2 extends View {
    AllData allData;
    Paint pBack;
    private static float MYPADDING = 15;
    private static float TOP, BOTTOM, LEFT, RIGHT;

    public Lineas2(Context context) {
        this(context, null);
    }

    public Lineas2(Context context, AttributeSet attrs) {
        super(context, attrs);

        pBack = new Paint();
        pBack.setColor(Color.BLACK);
        pBack.setStrokeWidth(4);

        allData = new AllData();
    }

    public void addDatos(List<Integer> datos) {
        allData.addLine(datos);
    }

    public void setDataComplete() {
        allData.initValues();
        removeCallbacks(animator);
        post(animator);
    }

    public void limpiar() {
        allData.clearData();
    }

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            allData.update();
            if(allData.incomplete())
                postDelayed(this, 1);
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getHeight() - getPaddingBottom(), pBack);
        canvas.drawLine(getPaddingLeft(), getHeight() - getPaddingBottom(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), pBack);
        allData.drawSelf(canvas);
    }

    private class AllData {
        private List<Lines> lines;
        float divisionHeight, divisionWidth, pX, avanceX, realHeight;
        int maxValue, maxItems;

        public AllData(){
            lines = new ArrayList<>();
        }

        public void addLine(List<Integer> datos) {
            maxItems(datos);
            maxValue(datos);
            lines.add(new Lines(datos));
        }

        private void maxItems(List<Integer> datos) {
            if(datos.size() > maxItems)
                maxItems = datos.size();
        }

        private void maxValue(List<Integer> datos) {
            for(int sint : datos){
                if(sint > maxValue)
                    maxValue = sint;
            }
        }

        public void initValues() {
            maxItems--;

            TOP = getPaddingTop() + MYPADDING;
            BOTTOM = getHeight() - getPaddingBottom() - MYPADDING;
            LEFT = getPaddingLeft() + MYPADDING;
            RIGHT = getWidth() - getPaddingRight() - MYPADDING;

            realHeight = BOTTOM - TOP;
            divisionHeight = realHeight / maxValue;

            float realWidth = RIGHT - LEFT;
            divisionWidth = realWidth / maxItems;
            avanceX = divisionWidth / 50;

            pX = LEFT;

            for(Lines line: lines){
                line.initValues();
                pX = LEFT;
            }
        }

        private float obtainPosY(int value) {
            return (BOTTOM - (divisionHeight * value));
        }

        private float obtainPosXini() {
            return pX;
        }

        private float obtainPosXfin() {
            return pX + divisionWidth;
        }

        public void drawSelf(Canvas canvas) {
            for(Lines line : lines){
                line.drawSelf(canvas);
            }
        }

        public void clearData() {
            lines.clear();
            maxValue = 0;
            maxItems = 0;
        }

        public void update() {
            for(Lines line : lines){
                line.update();
            }
        }

        public boolean incomplete() {
            boolean complete = true;
            for(Lines line : lines){
                complete &= line.incomplete();
            }
            return true;
        }

        private class Lines {
            private List<Line> lines = new ArrayList<>();

            public Lines(List<Integer> mPuntos) {
                for (int i = 0; i < (mPuntos.size() - 1); i++) {
                    lines.add(new Line(mPuntos.get(i), mPuntos.get(i + 1)));
                }
            }

            public void initValues(){
                for(Line line : lines){
                    line.initValues();
                    pX += divisionWidth;
                }
            }

            public void update() {
                for (Line line : lines) {
                    if (line.complete())
                        continue;
                    else {
                        line.actualizar();
                        return;
                    }
                }
            }

            public void drawSelf(Canvas canvas) {
                for (Line line : lines)
                    line.drawSelf(canvas);
            }

            public boolean incomplete() {
                boolean complete = true;
                for (Line line : lines)
                    complete &= line.complete();
                return !complete;
            }

            private class Line {
                private int ini, fin;
                private float posXini, posXfin, posYini, posYfin, m;
                private float posXact, posYact;
                private Paint paint;

                public Line(int pIni, int pFin) {
                    ini = pIni;
                    fin = pFin;

                    paint = new Paint();
                    paint.setStrokeWidth(3);
                    paint.setColor(Color.RED);
                    paint.setAntiAlias(true);
                }

                private void initValues() {
                    posXini = obtainPosXini();
                    posXfin = obtainPosXfin();
                    posYini = obtainPosY(ini);
                    posYfin = obtainPosY(fin);
                    m = (posYfin - posYini) / (posXfin - posXini);
                    posXact = posXini;
                    posYact = posYini;
                }

                public void actualizar() {
                    posXact += avanceX;
                    posYact = (m * (posXact - posXini)) + posYini;
                }

                public void drawSelf(Canvas canvas) {
                    canvas.drawLine(posXini, posYini, posXact, posYact, paint);
                    if(complete())
                        canvas.drawCircle(posXfin, posYfin, 6, paint);
                }

                public boolean complete() {
                    if (posXact > posXfin) {
                        posXact = posXfin;
                        posYact = posYfin;
                    }

                    return posXact == posXfin;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, obtenDim(heightMeasureSpec));
    }

    private int obtenDim(int measureSpec){
        int MINVAL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
        int dimension = MeasureSpec.getSize(measureSpec);
        int modo = MeasureSpec.getMode(measureSpec);
        if (modo == MeasureSpec.EXACTLY){
            return dimension;
        } else if (modo == MeasureSpec.AT_MOST){
            return Math.min(dimension, MINVAL);
        } else {
            return MINVAL;
        }
    }
}