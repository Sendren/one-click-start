import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        // 入口函数里调用Application的静态方法launch，之后会自动调用start方法
        Application.launch(View.class);
    }
}
