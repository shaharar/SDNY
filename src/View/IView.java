package View;

import javafx.stage.Stage;

import Model.Reason;

public interface IView {
    void signUp();
    void Search();
    void Update();
    void Delete();
    void Login();
    void viewMyProfile();
    void goToWebsite();
    void showProfile(String username);
    void Quit();
    void setStage(Stage stage);
    void textFieldUpdate();
    void textFielProfile(String usernametosearch);
    void showAlert(String showalert);
    public void attention();
}
