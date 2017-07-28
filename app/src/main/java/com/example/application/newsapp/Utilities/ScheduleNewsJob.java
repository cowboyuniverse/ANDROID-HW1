package com.example.application.newsapp.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by cowboyuniverse on 7/26/17.
 */

//     This will schedule a background job from Firebase's JobDispatcher.

//     HW4 TODO     5) 10pts: Using Firebase's JobDispatcher, modify your app so that it loads new news information every minute.
public class ScheduleNewsJob {
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class).setTag(NEWS_JOB_TAG).setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER).setRecurring(true).setTrigger(Trigger.executionWindow(360, 360 + 60))
                .setReplaceCurrent(true).build();
        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}




//    Reference from marcs code

//    private static final int SCHEDULE_INTERVAL_MINUTES = 360;
//    private static final int SYNC_FLEXTIME_SECONDS = 60;
//            .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
//                    SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))