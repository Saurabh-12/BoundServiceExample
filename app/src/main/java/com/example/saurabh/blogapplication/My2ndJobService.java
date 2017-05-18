package com.example.saurabh.blogapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

/**
 * Created by ee208840 on 16/5/17.
 */

public class My2ndJobService extends JobService{

    public My2ndJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(this, "My2ndJobService.onStartJob()", Toast.LENGTH_SHORT).show();
  /*
   * True - if your service needs to process
   * the work (on a separate thread).
   * False - if there's no more work to be done for this job.
   */
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Toast.makeText(this, "My2ndJobService.onStopJob()", Toast.LENGTH_SHORT).show();

        return false;
    }
}
