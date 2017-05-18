package com.example.saurabh.blogapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jobScheduleButton;
    private Button btnStartJob;
    private Button btnCancelJobs;

    ComponentName myServiceComponent;
    MyJobService myJobService;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myJobService = (MyJobService) msg.obj;
            myJobService.setUICallback(MainActivity.this);
        }
    };

    // Second Example
    JobScheduler jobScheduler;
    private static final int MYJOBID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRes();

        myServiceComponent = new ComponentName(this, MyJobService.class);
        Intent myServiceIntent = new Intent(this, MyJobService.class);
        myServiceIntent.putExtra("messenger", new Messenger(myHandler));
        startService(myServiceIntent);

        //Second example
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

    }

    @Override
    public void onClick(View v) {
        if (v == jobScheduleButton) {
            JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
            builder.setRequiresCharging(true);
            //builder.setRequiresDeviceIdle(true);
            //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            myJobService.scheduleJob(builder.build());
        }
        if (v == btnStartJob) {
            ComponentName jobService =
                    new ComponentName(getPackageName(), My2ndJobService.class.getName());
            JobInfo jobInfo =
                    new JobInfo.Builder(MYJOBID, jobService).setPeriodic(10000).build();
    /*
     * setPeriodic(long intervalMillis)
     * Specify that this job should recur with the provided interval,
     * not more than once per period.
     */
            int jobId = jobScheduler.schedule(jobInfo);
            if (jobScheduler.schedule(jobInfo) > 0) {
                Toast.makeText(MainActivity.this, "Successfully scheduled job: "
                        + jobId, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "RESULT_FAILURE: "
                        + jobId, Toast.LENGTH_SHORT).show();
            }
        }
        if (v == btnCancelJobs) {
            List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
            String s = "";
            for(JobInfo j : allPendingJobs){
                int jId = j.getId();
                jobScheduler.cancel(jId);
                s += "jobScheduler.cancel(" + jId + " )";
            }
            Toast.makeText(MainActivity.this, s,
                    Toast.LENGTH_SHORT).show();

            //or
            //jobScheduler.cancelAll();
        }
    }

    private void initRes() {
        jobScheduleButton = (Button) findViewById(R.id.buttonScheduleJob);
        jobScheduleButton.setOnClickListener(MainActivity.this);
        btnStartJob = (Button) findViewById(R.id.startJob_btn);
        btnStartJob.setOnClickListener(MainActivity.this);
        btnCancelJobs = (Button) findViewById(R.id.cancelJobs_btn);
        btnCancelJobs.setOnClickListener(MainActivity.this);
    }

}
