package br.brilhante.gustavo.giphysearch.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import br.brilhante.gustavo.giphysearch.R;


public class GustavoActionBar extends RelativeLayout {

    LayoutInflater inflater = null;

    RelativeLayout backButtonTouchArea, rightButtonTouchArea, middleButtonTouchArea;

    ImageButton leftButton, rightButton;

    ImageButton middleButton;

    String text = "";

    Activity activity;

    boolean leftClickable = false;
    boolean rightClickable = false;
    boolean middleClickable = false;

    boolean backButtonEnable = false;
    boolean rightButtonEnable = false;
    boolean middleButtonEnable = false;

    Drawable rightButtonIcon;

    ActionBarListener listener;

    int pastIndexSelected ,indexSelected;

    public static final int LEFT_SELECTED = 0;
    public static final int MIDDLE_SELECTED = 1;
    public static final int RIGHT_SELECTED = 2;


    public GustavoActionBar(Context context) {
        super(context);
        initViews();
        if(context instanceof Activity){
            if(backButtonEnable)enableBackButton((Activity) context);
        }
    }

    public GustavoActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GustavoActionBar, 0, 0);
        try{
            text = typedArray.getString(R.styleable.GustavoActionBar_text);
            backButtonEnable = typedArray.getBoolean(R.styleable.GustavoActionBar_backEnabled, false);
            rightButtonEnable = typedArray.getBoolean(R.styleable.GustavoActionBar_rightEnabled, false);
            middleButtonEnable = typedArray.getBoolean(R.styleable.GustavoActionBar_middleEnabled, false);
            rightButtonIcon = typedArray.getDrawable(R.styleable.GustavoActionBar_rightButtonIcon);
        }finally {
            if (!isInEditMode()) {
                initViews();
                if(context instanceof Activity){
                    if(backButtonEnable)enableBackButton((Activity) context);
                    if(rightButtonEnable)enableRightButton((Activity) context);
                    if(middleButtonEnable)enableMiddleButton((Activity) context);
                    if(rightButtonIcon!=null)setRightButtonIcon(rightButtonIcon);
                }
            }
        }
    }

    public GustavoActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initViews(){

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_custom_actionbar, this, true);

        backButtonTouchArea = (RelativeLayout) findViewById(R.id.backButtonTouchArea);
        rightButtonTouchArea = (RelativeLayout) findViewById(R.id.rightButtonTouchArea);
        middleButtonTouchArea = (RelativeLayout) findViewById(R.id.middleButtonTouchArea);
        leftButton = (ImageButton) findViewById(R.id.backButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        middleButton = (ImageButton) findViewById(R.id.middleButton);


        setSelected(LEFT_SELECTED);
    }

    public void enableRightButton(Activity activity){
        rightButton.setVisibility(View.VISIBLE);
        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in);
        rightButton.startAnimation(scaleIn);
        rightClickable = true;
        this.activity = activity;
        rightButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return rightAnimation(event);
            }
        });
        rightButtonTouchArea.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return rightAnimation(event);
            }
        });
    }

    public void enableMiddleButton(Activity activity){
        middleButton.setVisibility(View.VISIBLE);
        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in);
        middleButton.startAnimation(scaleIn);
        middleClickable = true;
        this.activity = activity;
        middleButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return middleAnimation(event);
            }
        });
        middleButtonTouchArea.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return middleAnimation(event);
            }
        });
    }

    public void enableBackButton(Activity activity){
        leftButton.setVisibility(View.VISIBLE);
        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in);
        leftButton.startAnimation(scaleIn);
        leftClickable = true;
        this.activity = activity;
        leftButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return leftAnimation(event);
            }
        });
        backButtonTouchArea.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return leftAnimation(event);
            }
        });
    }


    public void disableBackButton(){
        leftButton.setVisibility(View.GONE);
        leftClickable = false;
    }

    public boolean leftAnimation(MotionEvent event){
        if(leftClickable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Animation shrinkInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in);
                    leftButton.startAnimation(shrinkInAnimation);
                    return true;
                case MotionEvent.ACTION_UP:
                    Animation ShrinkOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out);
                    ShrinkOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setSelected(LEFT_SELECTED);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(listener!=null && pastIndexSelected!=indexSelected){
                                listener.onLeftButtonClick(leftButton, pastIndexSelected, indexSelected);
                            }
                            /*else if(activity!=null) {
                                activity.onBackPressed();
                            }*/
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    leftButton.startAnimation(ShrinkOutAnimation);


                    return true;
            }
        }
        return false;
    }

    public boolean rightAnimation(MotionEvent event){
        if(rightClickable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Animation shrinkInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in);
                    rightButton.startAnimation(shrinkInAnimation);
                    return true;
                case MotionEvent.ACTION_UP:
                    Animation shrinkOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out);
                    shrinkOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setSelected(RIGHT_SELECTED);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(listener!=null && pastIndexSelected!=indexSelected) {
                                listener.onRightButtonClick(rightButton, pastIndexSelected, indexSelected);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    rightButton.startAnimation(shrinkOutAnimation);


                    return true;
            }
        }
        return false;
    }


    public boolean middleAnimation(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Animation shakeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in);
                middleButton.startAnimation(shakeOutAnimation);
                return true;
            case MotionEvent.ACTION_UP:
                Animation shakeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out);
                shakeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        setSelected(MIDDLE_SELECTED);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(listener!=null && pastIndexSelected!=indexSelected) {
                            listener.onMiddleTextClick(middleButton, pastIndexSelected, indexSelected);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                middleButton.startAnimation(shakeInAnimation);

        }
        return true;
    }

    public void setSelected(int indexSelected){
        pastIndexSelected = this.indexSelected;
        this.indexSelected = indexSelected;
        switch (indexSelected){
            case LEFT_SELECTED:
                setImageDrawable(leftButton,R.drawable.star_purple);
                setImageDrawable(middleButton,R.drawable.frame_white);
                setImageDrawable(rightButton,R.drawable.search_white);
                break;
            case MIDDLE_SELECTED:
                setImageDrawable(leftButton,R.drawable.star_white);
                setImageDrawable(middleButton,R.drawable.frame_purple);
                setImageDrawable(rightButton,R.drawable.search_white);
                break;
            case RIGHT_SELECTED:
                setImageDrawable(leftButton,R.drawable.star_white);
                setImageDrawable(middleButton,R.drawable.frame_white);
                setImageDrawable(rightButton,R.drawable.search_purple);
                break;
        }
    }

    public boolean isMiddleButtonEnable() {
        return middleButtonEnable;
    }

    public void setImageDrawable(ImageButton button, int drawableId){
        button.setBackground(getContext().getDrawable(drawableId));
    }

    //deve ser passado um id do drawable ex: R.drawable.icon
    public void setRightButtonIcon(Drawable drawable){
        rightButton.setBackground( drawable );
    }

    //deve ser passado um id do drawable ex: R.drawable.icon
    public void setRightButtonIcon(int id){
        rightButton.setBackground( ((Context)activity).getResources().getDrawable(id) );
    }


    public void setListener(ActionBarListener listener) {
        this.listener = listener;
    }

    public interface ActionBarListener{
        void onRightButtonClick(ImageButton button, int lastIndex, int index);
        void onLeftButtonClick(ImageButton button, int lastIndex, int index);
        void onMiddleTextClick(ImageView button, int lastIndex, int index);
    }



}
