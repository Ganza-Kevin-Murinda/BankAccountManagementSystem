module com.bankacc.bankaccessmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.bankacc.bankaccessmanagementsystem to javafx.fxml;
    exports com.bankacc.bankaccessmanagementsystem;
}