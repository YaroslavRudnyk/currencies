package com.example.currencies.job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestWorker extends Worker {

  public static final String TAG = TestWorker.class.getSimpleName();

  public TestWorker(@NonNull Context context,
      @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull @Override public Result doWork() {
    Log.i(TAG, "DBG_JOB: test job success");
    Data.Builder builder = new Data.Builder();
    builder.putBoolean("IS_SUCCESS", true);
    return Result.success(builder.build());
  }
}
