module org.example.atpprojectpartc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires java.rmi;
    requires ATP.Project.PartB.Modular;
    requires javafx.media;

    opens org.example.atpprojectpartc to javafx.fxml;
    exports org.example.atpprojectpartc;
    exports org.example.atpprojectpartc.View;
    opens org.example.atpprojectpartc.View to javafx.fxml;
    exports org.example.atpprojectpartc.View.Drawers;
    opens org.example.atpprojectpartc.View.Drawers to javafx.fxml;
}