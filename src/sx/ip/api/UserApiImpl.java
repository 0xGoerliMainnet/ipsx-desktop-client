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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.HashMap;
import javafx.scene.control.Alert;
import org.json.JSONObject;
import sx.ip.controllers.NavController;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author hygor
 */
public class UserApiImpl implements UserApi{

    @Override
    public boolean authUser(String email, String password) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/auth")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("accept", "application/json")
            .field("email", email)
            .field("password", password)
            .asJson();
        
        if(!jsonResponse.getBody().getObject().has("error")){
            if(jsonResponse.getBody().getObject().has("id")){
                NavController.accessToken = jsonResponse.getBody().getObject().getString("id");
                NavController.userId = jsonResponse.getBody().getObject().getInt("userId");
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean loginUser(String credentials) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean logoutUser() throws UnirestException {
        if(NavController.accessToken != null){
            HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/logout")
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .queryString("access_token", NavController.accessToken)
                .asJson();

            if(jsonResponse.getStatus() == 204 && jsonResponse.getBody() == null){
                NavController.accessToken = null;
                NavController.userId = null;
                return true;
            }
        }else{
            ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, NavController.bundle.getString("key.main.alert.info.title"), null, NavController.bundle.getString("key.main.unauthorized"), null);
        }
        
        return false;
    }

    @Override
    public String loginUserFacebook(String token) throws UnirestException {
        String accessToken = "";
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/social/login/facebook")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("accept", "application/json")
            .field("token", token)
            .asJson();
        
        if(!jsonResponse.getBody().getObject().has("error")){
            if(jsonResponse.getBody().getObject().has("id")){
                accessToken = jsonResponse.getBody().getObject().getString("id");
                NavController.accessToken = accessToken;
                NavController.userId = jsonResponse.getBody().getObject().getInt("userId");
            }
        }
        return accessToken;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean resetPassword(String email) throws UnirestException {
        JSONObject options = new JSONObject();
        options.append("email", email);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/reset")
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .body(options)            
            .asJson();
        
        return jsonResponse.getStatus() == 204 && jsonResponse.getBody() == null;
    }

    @Override
    public boolean addEthAddress(String customName, String ethAddress) throws UnirestException {
        //TODO: Verify accessToken
        HashMap<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("user_id", NavController.userId);
        bodyMap.put("address", ethAddress);
        bodyMap.put("alias", customName);
        JSONObject body = new JSONObject(bodyMap);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl+"/{id}/eths")
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .queryString("access_token", NavController.accessToken)
            .routeParam("id", NavController.userId.toString())
            .body(body)
            .asJson();
        
        if(!jsonResponse.getBody().getArray().getJSONObject(0).has("error")){
            if(jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .has("status")
                    && 
               jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .getString("status")
                    .equals("active")){
                    return true;
            }
        }else{
            throw new UnirestException("Error in userAPI: Cannot create user's wallet, " + jsonResponse.getBody().getArray().getJSONObject(0).get("error"));
        }
        return false;   
    }

    @Override
    public boolean userHasEthWallet() throws UnirestException {
        
        HttpResponse<JsonNode> jsonResponse = Unirest.get(UserApi.userApiUrl+"/{id}/eths")
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .queryString("access_token", NavController.accessToken)
            .routeParam("id", NavController.userId.toString())
            .asJson();
        
        if(!jsonResponse.getBody().getArray().getJSONObject(0).has("error")){
            if(jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .has("status")
                    &&
               jsonResponse.getBody()
                    .getArray()
                    .getJSONObject(0)
                    .getString("status")
                    .equals("active")){
                    return true;
            }
        }else{
            throw new UnirestException("Error in userAPI: Cannot retrieve user's wallet, " + jsonResponse.getBody().getArray().getJSONObject(0).get("error"));
        }
        return false;
    } 
}
