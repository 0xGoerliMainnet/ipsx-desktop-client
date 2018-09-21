/*
 * Copyright 2018 IP Exchange : https://ip.sx/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sx.ip.controllers;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import org.slf4j.LoggerFactory;
import sx.ip.api.CountryApi;
import sx.ip.api.CountryApiImpl;
import static sx.ip.controllers.NavController.bundle;
import sx.ip.models.Country;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLProxySummaryController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLProxySummaryController.class);
    
    @FXML
    JFXComboBox comboCountry;

    @FXML
    ProgressBar progressBar;



    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadCountryCombo();
        } catch (IOException ex) {
            Logger.getLogger(FXMLProxySummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Method resposible for loading the country combobox.
     *
     */
    private void loadCountryCombo() throws IOException {
        Task task = new Task<List<Country>>() {
            @Override
            protected List<Country> call() throws Exception {
                CountryApi api = new CountryApiImpl();
                    return api.retrieveCountries();
            }
        };
        task.setOnSucceeded((Event ev) -> {
            ArrayList<Country> countryArray = (ArrayList<Country>) task.getValue();
            this.comboCountry.getItems().addAll(countryArray);
            this.comboCountry.setDisable(false);
            progressBar.setVisible(false);
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.auth.title"), null, task.getException().getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), task.getException(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());

        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, e);
                ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.title"), null, e.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), e, null);
            }
        });
        progressBar.setVisible(true);
        thread.run();

    }
}
