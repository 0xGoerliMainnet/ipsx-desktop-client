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
package sx.ip.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import org.json.JSONObject;
import sx.ip.controllers.NavController;
import sx.ip.models.ETHWallet;
import sx.ip.utils.CredentialType;
import sx.ip.utils.ProxyUtils;
import sx.ip.utils.SecurityHandle;

/**
 *
 * @author hygor
 */
public class UserApiImpl implements UserApi {

    /**
     * The ResourceBundle instance.
     */
    ResourceBundle rb = ProxyUtils.getBundle();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authUser(String email, String password) throws UnirestException, Exception {
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/auth")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("accept", "application/json")
                .field("email", email)
                .field("password", password)
                .asJson();

        if (!jsonResponse.getBody().getObject().has("error")) {
            if (jsonResponse.getBody().getObject().has("id")) {
                SecurityHandle sh = new SecurityHandle();
                Object encryptedPassword = sh.encryption(password);
                ProxyUtils.saveCredentials(encryptedPassword, CredentialType.BYTEARRAY);
                ProxyUtils.saveCredentials(email, CredentialType.STRING);
                NavController.accessToken = jsonResponse.getBody().getObject().getString("id");
                NavController.userId = jsonResponse.getBody().getObject().getInt("userId");
                return true;
            }
        } else {
            switch (jsonResponse.getBody().getObject().getJSONObject("error").getString("message")) {
                case "invalid user":
                    throw new Exception(rb.getString("key.main.alert.error.auth.message.1"));
                case "password is a required argument":
                    throw new Exception(rb.getString("key.main.alert.error.auth.message.2"));
                case "wrong password":
                    throw new Exception(rb.getString("key.main.alert.error.auth.message.3"));
                default:
                    throw new Exception(rb.getString("key.main.alert.error.unexpected.message") + jsonResponse.getBody().getObject().getJSONObject("error").getString("message"));
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loginUser(String credentials) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean logoutUser() throws UnirestException {
        if (NavController.accessToken != null) {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/logout")
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .queryString("access_token", NavController.accessToken)
                    .asJson();

            if (jsonResponse.getStatus() == 204 && jsonResponse.getBody() == null) {
                NavController.accessToken = null;
                NavController.userId = null;
                ProxyUtils.eraseCredentials();
                return true;
            }
        } else {
            ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, NavController.bundle.getString("key.main.alert.info.title"), null, NavController.bundle.getString("key.main.unauthorized"), null);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String loginUserFacebook(String token) throws UnirestException, Exception {
        String accessToken = "";
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/social/login/facebook")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("accept", "application/json")
                .field("token", token)
                .asJson();

        if (!jsonResponse.getBody().getObject().has("error")) {
            if (jsonResponse.getBody().getObject().has("id")) {
                accessToken = jsonResponse.getBody().getObject().getString("id");
                NavController.accessToken = accessToken;
                NavController.userId = jsonResponse.getBody().getObject().getInt("userId");
            }
        } else {
            throw new Exception(jsonResponse.getBody().getObject().getJSONObject("error").getString("message"));
        }
        return accessToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetPassword(String email) throws UnirestException {
        JSONObject options = new JSONObject();
        options.append("email", email);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/reset")
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(options)
                .asJson();

        if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray().getJSONObject(0).has("error")) {
            switch (jsonResponse.getBody().getObject().getJSONObject("error").getString("message")) {
                case "Cannot read property \'deleted_at\' of null":
                    throw new UnirestException(rb.getString("key.main.alert.error.resetpw.message"));
                default:
                    throw new UnirestException(rb.getString("key.main.alert.error.unexpected.message") + jsonResponse.getBody().getObject().getJSONObject("error").getString("message"));
            }
        }

        return jsonResponse.getStatus() == 204 && jsonResponse.getBody() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEthAddress(String customName, String ethAddress) throws UnirestException {
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("user_id", NavController.userId);
        bodyMap.put("address", ethAddress);
        bodyMap.put("alias", customName);
        JSONObject body = new JSONObject(bodyMap);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/{id}/eths")
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .queryString("access_token", NavController.accessToken)
                .routeParam("id", NavController.userId.toString())
                .body(body)
                .asJson();

        if (!jsonResponse.getBody().getArray().getJSONObject(0).has("error")) {
            if (jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .has("status")
                    && jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .getString("status")
                    .equals("active")) {
                return true;
            }
        } else {
            throw new UnirestException(rb.getString("key.main.alert.error.wallet.message") + jsonResponse.getBody().getArray().getJSONObject(0).get("error"));
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean userHasEthWallet() throws UnirestException {

        HttpResponse<JsonNode> jsonResponse = Unirest.get(UserApi.userApiUrl + "/{id}/eths")
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .queryString("access_token", NavController.accessToken)
                .routeParam("id", NavController.userId.toString())
                .asJson();

        if (jsonResponse.getBody().getArray().length() != 0 && !jsonResponse.getBody().getArray().getJSONObject(0).has("error")) {
            if (jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .has("status")
                    && jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .getString("status")
                    .equals("active")) {
                return true;
            }
        } else {
            if (jsonResponse.getBody().getArray().length() == 0) {
                return false;
            }
            throw new UnirestException(rb.getString("key.main.alert.error.wallet.message") + jsonResponse.getBody().getArray().getJSONObject(0).get("error"));
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ETHWallet> retrieveUsersETHWallets() throws UnirestException, JsonSyntaxException {

        Type type = new TypeToken<List<ETHWallet>>() {
        }.getType();
        ArrayList<ETHWallet> walletArray = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = Unirest.get(UserApi.userApiUrl + "/{id}/eths")
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .queryString("access_token", NavController.accessToken)
                    .routeParam("id", NavController.userId.toString())
                    .asJson();

            Gson g = new Gson();
            walletArray = g.fromJson(response.getBody().toString(), type);

            return walletArray;

        } catch (UnirestException | JsonSyntaxException e) {
            throw e;
        }
    }

    @Override
    public boolean tokenRequest(ETHWallet userWallet, String amountRequested) throws UnirestException, JsonSyntaxException, Exception {
        JSONObject body = new JSONObject();
        body.append("usereth_id", userWallet.getId());
        body.append("amount_requested", amountRequested);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/{id}/token_requests")
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .queryString("access_token", NavController.accessToken)
                .routeParam("id", NavController.userId.toString())
                .body(body)
                .asJson();

        if (jsonResponse.getStatus() == 200) {
            return true;
        } else {
            JSONObject errorMessageMap = (JSONObject) jsonResponse.getBody().getObject().get("error");
            throw new Exception("An Error has ocurred: " + "Status: "+ jsonResponse.getStatus() + ". " + errorMessageMap.get("message"));
        }

    }
}
