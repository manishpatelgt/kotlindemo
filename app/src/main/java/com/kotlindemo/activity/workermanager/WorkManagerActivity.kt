package com.kotlindemo.activity.workermanager

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.*
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.extensions.toast
import kotlinx.android.synthetic.main.activity_workmanager.*
import kotlinx.android.synthetic.main.activity_workmanager.toolbar
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Patel on 5/23/2019.
 */
//https://github.com/jarroyoesp/Worker-Manager-Example
//https://proandroiddev.com/exploring-the-stable-android-jetpack-workmanager-82819d5d7c34
//https://android--code.blogspot.com/2019/02/android-kotlin-workmanager-constraints.html
//https://developer.android.com/topic/libraries/architecture/workmanager/basics

class WorkManagerActivity : ParentActivity() {

    private var mViewModel: WorkerViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workmanager)
        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        configView()
    }

    /**
     * CONFIG VIEW
     */
    private fun configView() {

        // Worker Unique Power&Internet
        activity_main_button_worker_unique.setOnClickListener {
            initWorkerUniqueWithPowerAndConnectivity()
        }

        // Periodic worker
        activity_main_button_worker_periodic.setOnClickListener {
            initPeriodicWorker()
        }

        // Periodic worker
        activity_main_button_worker_with_param.setOnClickListener {
            initWorkerUniqueWithParamenters()
        }

        // Periodic worker
        activity_main_button_worker_view_model.setOnClickListener {
            configViewModel()
            initWorkerViewModel()
        }

        //Download Image worker
        activity_main_button_worker_download.setOnClickListener {
            initDownloadWorker()
        }
    }

    private fun initDownloadWorker() {

        // Create a Constraints object that defines when the task should run
        val downloadConstraints = Constraints.Builder()
            // Device need to charging for the WorkRequest to run.
            .setRequiresCharging(true)
            // Any working network connection is required for this work.
            .setRequiredNetworkType(NetworkType.CONNECTED)
            //.setRequiresBatteryNotLow(true)
            // Many other constraints are available, see the
            // Constraints.Builder reference
            .build()

        // Define the input data for work manager
        val data = Data.Builder()
        data.putString(
            DownloadImageWorker.ARG_EXTRA_PARAM,
            "https://www.freeimageslive.com/galleries/buildings/structures/pics/canal100-0416.jpg"
        )

        // Create an one time work request
        val downloadImageWork = OneTimeWorkRequest
            .Builder(DownloadImageWorker::class.java)
            .setInputData(data.build())
            .setConstraints(downloadConstraints)
            .build()

        // Enqueue the work
        WorkManager.getInstance().enqueue((downloadImageWork))

        // Get the work status using live data
        WorkManager.getInstance().getWorkInfoByIdLiveData(downloadImageWork.id).observe(this, Observer { workInfo ->

            // Toast the work state
            toast(workInfo.state.name)

            if (workInfo != null) {

                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    // Show the work state in text view
                    showWorkerStatus("Download enqueued.")
                } else if (workInfo.state == WorkInfo.State.BLOCKED) {
                    showWorkerStatus("Download blocked.")
                } else if (workInfo.state == WorkInfo.State.RUNNING) {
                    showWorkerStatus("Download running.")
                }
            }

            // When work finished
            if (workInfo != null && workInfo.state.isFinished) {

                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    showWorkerStatus("Download successful.")

                    // Get the output data
                    val successOutputData = workInfo.outputData
                    val uriText = successOutputData.getString(DownloadImageWorker.OUTPUT_DATA_PARAM)

                    // If uri is not null then show it
                    uriText?.apply {
                        // If download finished successfully then show the downloaded image in image view
                        imageView.setImageURI(Uri.parse(uriText))
                        showWorkerStatus(uriText)
                    }
                } else if (workInfo.state == WorkInfo.State.FAILED) {
                    showWorkerStatus("Failed to download.")
                } else if (workInfo.state == WorkInfo.State.CANCELLED) {
                    showWorkerStatus("Work request cancelled.")
                }
            }
        })
    }

    /**
     * In this example this work was called when the device is connected to Internet and
     * is charging. If you click the button and this constraints are not satisfied then the works will not be
     * launched, but when connectivity and charging will be available, the workmanager will be in charge to
     * launch it. This work will not be lost.
     */
    private fun initWorkerUniqueWithPowerAndConnectivity() {
        // optionally, add constraints like power, network availability
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerTest = OneTimeWorkRequestBuilder<WorkerExample>()
            .setConstraints(constraints).build()

        // Now, enqueue your work
        WorkManager.getInstance().beginUniqueWork(WorkerExample.TAG, ExistingWorkPolicy.REPLACE, workerTest)
    }

    /**
     * PERIODIC TASK
     * W/WM-WorkSpec: Interval duration lesser than minimum allowed value; Changed to 900000
     * If we want to cancel a worker we can add a Tag and cancel it by its Tag
     */
    private fun initPeriodicWorker() {
        val mWorkManager = WorkManager.getInstance()
        mWorkManager?.cancelAllWorkByTag(WorkerExample.TAG)

        val periodicBuilder = PeriodicWorkRequest.Builder(WorkerExample::class.java, 15, TimeUnit.MINUTES)
        val myWork = periodicBuilder.addTag(WorkerExample.TAG).build()
        mWorkManager?.enqueue(myWork)
    }

    /**
     * To send parameter to a Work:
     *    val data = Data.Builder()     *
     *   //Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
     *   data.putString(WorkerExample.ARG_EXTRA_PARAM, "your string param")
     *
     * And in the worker to get the data:
     *   val param =  inputData.getString(ARG_EXTRA_PARAM)
     */
    private fun initWorkerUniqueWithParamenters() {
        val data = Data.Builder()

        //Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
        data.putString(WorkerExample.ARG_EXTRA_PARAM, "initWorkerUniqueWithParamenters")

        //Set Input Data
        val workerTest = OneTimeWorkRequestBuilder<WorkerExample>()
            .setInputData(data.build())
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        // Now, enqueue your work
        WorkManager.getInstance().enqueue(workerTest)
    }

    private fun configViewModel() {
        // Get the ViewModel
        mViewModel = ViewModelProviders.of(this).get(WorkerViewModel::class.java)
        // Show work status
        mViewModel!!.getOutputWorkInfo().observe(this, observer)
    }

    private fun initWorkerViewModel() {
        showWorkerStatus("Init")
        mViewModel!!.initWorker()
    }

    private val observer = Observer<List<WorkInfo>> { state ->
        state?.let {
            if (it == null || it.isEmpty()) {
                showWorkerStatus("Empty")
            } else {
                // We only care about the one output status.
                // Every continuation has only one worker tagged TAG_OUTPUT
                val workInfo = it.get(0)

                when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> {
                        showWorkerStatus("ENQUEUED")
                    }

                    WorkInfo.State.RUNNING -> {
                        showWorkerStatus("RUNNING")
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        val successOutputData = workInfo.outputData
                        val firstValue = successOutputData.getString(WorkerExample.OUTPUT_DATA_PARAM1)
                        val secondValue = successOutputData.getInt(WorkerExample.OUTPUT_DATA_PARAM2, -1)

                        showWorkerStatus("SUCCEEDED: Output $firstValue - $secondValue")
                    }
                }
            }
        }

    }

    private fun showWorkerStatus(message: String) {
        activity_main_tv_status.text = message
    }
}