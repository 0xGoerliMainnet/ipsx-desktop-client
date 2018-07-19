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

import com.jfoenix.controls.JFXButton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.client.ApiException;
import io.swagger.client.api.CountriesApi;
import io.swagger.client.model.InlineResponse200;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sx.ip.IPSXDesktopClient;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author caio
 */
public class FXMLLoginController extends NavController implements Initializable {

    /**
     * The login with email button instance.
     */
    @FXML
    private JFXButton btnLoginEmail;

    /**
     * The login with Facebook button instance.
     */
    @FXML
    private JFXButton btnLoginFacebook;

    /**
     * The login info pane instance.
     */
    @FXML
    private AnchorPane loginInfoPane;

    /**
     * The close button instance.
     */
    @FXML
    private JFXButton btnClose;
    /**
     * The WebView for Facebook login.
     */
    @FXML
    private WebView webviewFacebook;

    /**
     * The WebView for Facebook login.
     */
    @FXML
    private Label loginErrorLabel;

    /**
     * The WebEngine of the WebView for Facebook login.
     */
    private WebEngine webEn;

    /**
     * The Facebook code required for token retrieval.
     */
    private String facebookCode;

    /**
     * The Facebook access token for the API.
     */
    private String facebookAccessToken;

    /**
     * The main anchor pane instance.
     */
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * Method resposible for handling the close action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }

    /**
     * Method resposible for the transition to the login with email screen
     * action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void loginWithEmailAction(ActionEvent event) throws IOException {
        CountriesApi apiInstance = new CountriesApi();
        String where = ""; // String | Criteria to match model instances
        try {
            InlineResponse200 result = apiInstance.countriesCount(where);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CountriesApi#countriesCount");
            e.printStackTrace();
        }
    }

    @FXML
    private void loginWithFacebookAction(ActionEvent ae) throws IOException {
        this.webviewFacebook.setVisible(true);
        this.webviewFacebook.setDisable(false);
        this.webEn.load("https://www.facebook.com/v3.0/dialog/oauth?"
                + "client_id=--"
                + "&redirect_uri=https://www.facebook.com/connect/login_success.html");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.webEn = this.webviewFacebook.getEngine();
        this.webEn.load(null);
        this.webviewFacebook.setVisible(false);
        this.webviewFacebook.setDisable(true);

        this.webEn.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> observable, State oldValue, State newValue) -> {
            if (newValue == State.SUCCEEDED) {
//                System.out.println(this.webEn.getLocation());
                if (this.webEn.getLocation().contains("https://www.facebook.com/connect/login_success.html?code=")) {
                    String[] code = this.webEn.getLocation().split("code=");
                    this.facebookCode = code[1];
                    try {
                        HttpResponse<JsonNode> tokenResponse = Unirest.get("https://graph.facebook.com/v3.0/oauth/access_token?")
                                .queryString("client_id", "--")
                                .queryString("redirect_uri", "https://www.facebook.com/connect/login_success.html")
                                .queryString("client_secret", "--")
                                .queryString("code", this.facebookCode)
                                .asJson();
                        if (tokenResponse.getBody().getObject().has("error")) {
                            //facebook api error msg in frontend
                            this.loginErrorLabel.setVisible(true);
                            String errorMessage = tokenResponse.getBody().getObject().getJSONObject("error").getString("message");
                            this.loginErrorLabel.setText(errorMessage);

                        } else if (tokenResponse.getBody().getObject().has("access_token")) {

                            this.facebookAccessToken = tokenResponse.getBody().getObject().get("access_token").toString();
                            HttpResponse<JsonNode> apiResponse = Unirest.post("--")
                                    .header("Content-Type", "application/x-www-form-urlencoded")
                                    .header("Accept", "application/json")
                                    .field("token", this.facebookAccessToken)
                                    .asJson();
                            if (apiResponse.getBody().getObject().has("error")) {
                                //IPSX error msg in frontend
                                this.loginErrorLabel.setVisible(true);
                                String error = apiResponse.getBody().getArray().getJSONObject(0).getJSONObject("error").getString("message");
                                this.loginErrorLabel.setText(error);
                            } else {
                                try {
                                    Unirest.shutdown();
                                    //LOAD POST LOGIN SCREEN
                                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
                                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                                    NavControllerHandle.navigateTo(loader, stage, app);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                    } catch (UnirestException e) {
                        System.out.println(e.getStackTrace());
                    }

                    this.webEn.load(null);
                    this.webviewFacebook.setVisible(false);
                    this.webviewFacebook.setDisable(true);
                }
            }
        });
    }

}
