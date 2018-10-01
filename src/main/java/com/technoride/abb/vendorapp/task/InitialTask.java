package com.technoride.abb.vendorapp.task;

import javafx.concurrent.Task;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class InitialTask extends Task<List<CustomTask>>
{

    @Autowired
    private DatabaseConnectionCheckTask databaseConnectionCheckTask;
    @Override
    protected List<CustomTask> call() throws Exception {
        //System.out.println("Thread Called");
        List<CustomTask> customTasks = new ArrayList<>();
        customTasks.add(databaseConnectionCheckTask);
        long i=1;
        for (CustomTask customTask: customTasks)
        {

            if (customTask.finish())
            {
                long workdone = (100/customTasks.size()) * i;
                this.updateProgress(workdone,customTasks.size());
                this.updateMessage(customTask.message());

            }
            i++;
            Thread.sleep(5000);
        }
        return customTasks;
    }
}
