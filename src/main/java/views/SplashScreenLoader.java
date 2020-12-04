package views;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Media;
import models.MediaCategory;
import views.NYTComboBox.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class SplashScreenLoader{
    public SplashScreenLoader () throws IOException {
        initPreLoader();
    }
    public void initPreLoader() throws IOException {

       // if(checkIfFilesExist())
           // showMainStage();

            Task<Boolean> task = defineTask();
            //getSplashMessageBox();
            
            runApiTask(task);

    }
    private Task<Boolean> defineTask() {

        return new Task<>() {
            @Override public Boolean call() throws InterruptedException, ParseException, IOException {
                return  (NYTComboBox.callReadApiFile().size()>0?true:false);
            }
        };
    }
    private void runApiTask(Task<Boolean> task) {

        task.setOnRunning((e) -> {
           // progressBar.progressProperty().bind(task.progressProperty());
            taskListener(task);
            System.out.println("Please wait here");
            //setSceneAndStage();
        });//alert box
        taskOnSuccessORFailure(task);
    }
    private void taskOnSuccessORFailure(Task<Boolean> task) {

//        task.setOnSucceeded((e) -> showMainStage());
        task.setOnSucceeded((e) -> System.out.println("Success"));
        task.setOnFailed((e) -> System.out.println("Failure"));
//        task.setOnFailed((e) -> { createNoApiDataStage(); });
        new Thread(task).start();
    }
    private void taskListener(Task<Boolean> task) {

        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Close my box");
                //setUnBindProperties();
                //.closebox
            }
        });
    }



}
