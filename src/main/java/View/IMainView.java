package View;

import javafx.stage.Stage;

import Model.Reason;

public interface IMainView {
    void signUp();
    void SearchProfile();
    void Update();
    void Delete();
    void Login();
    void viewMyProfile();
    void goToWebsite();
    void showProfile(String username);
    void Quit();
    void setStage(Stage stage);
    void showAlert(String showalert);
    public void attention();
}
