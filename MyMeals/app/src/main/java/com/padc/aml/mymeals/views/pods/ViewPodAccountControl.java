package com.padc.aml.mymeals.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.padc.aml.mymeals.events.DataEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 8/28/2016.
 */
public class ViewPodAccountControl extends FrameLayout {
    public ViewPodAccountControl(Context context) {
        super(context);
    }

    public ViewPodAccountControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodAccountControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void onEventMainThread(DataEvent.RefreshUserLoginStatusEvent event) {
        //refreshUserLoginStatus();
    }
}
