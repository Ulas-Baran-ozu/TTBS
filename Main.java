import view.LoginView;
import controller.LoginController;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}
