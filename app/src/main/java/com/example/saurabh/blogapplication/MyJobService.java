package com.example.saurabh.blogapplication;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ee208840 on 16/5/17.
 */
//Require API Level 21
public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    // this is required method for JobService
    // This method is called when the scheduled job
    // is started
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "on start job...");
        Toast.makeText(this, "MyJobService.onStartJob()", Toast.LENGTH_SHORT).show();
          /*
      * True - if your service needs to process
     * the work (on a separate thread).
     * False - if there's no more work to be done for this job.
     */
        return false;
    }

    // this is required method for JobService
    // This method is called when the scheduled job
    // is stopped
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "on stop job....");
        Toast.makeText(this, "MyJobService.onStopJob()", Toast.LENGTH_SHORT).show();
        return false;
    }


    // This method is called when the service instance
    // is created
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " myJobService created....");
    }

    // This method is called when the service instance
    // is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "myJobService destroyed...");
    }

    MainActivity myMainActivity;

    public void setUICallback(MainActivity activity) {
        myMainActivity = activity;
    }

    // This method is called when the start command
    // is fired
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "myJobService onStartCommand called...");
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = 2;
        m.obj = this;
        try {
            callback.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
        return START_NOT_STICKY;

    }

    // Method that schedules the job
    public void scheduleJob(JobInfo build) {
        Log.d(TAG, "Scheduling job...");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(build);
    }
}
