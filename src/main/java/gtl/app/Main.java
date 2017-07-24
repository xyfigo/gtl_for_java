package gtl.app;/**
 * Created by ZhenwenHe on 2017/4/3.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.BitSet;

public class Main extends Application {

    public static void main(String[] args) {
        int n = 8;
        java.util.BitSet bitSet=new BitSet(n+1);
        for(int i=0;i<n;++i){
            bitSet.set(i,i%2==0?true:false);
        }
        System.out.println(bitSet.length());
        for(int i=0;i<bitSet.length();++i){
            System.out.print(bitSet.get(i)==true?1:0);
        }
        System.out.println(" "+bitSet.toString());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane rootPane = new StackPane();
        Scene s = new Scene(rootPane, 400, 300);

        Button btn = new Button("Test");
        btn.setOnAction((e) ->
                {
                    System.out.println("Test");
                }
        );

        rootPane.getChildren().add(btn);


        primaryStage.setTitle("Test");
        primaryStage.setScene(s);

        primaryStage.show();
    }
}
