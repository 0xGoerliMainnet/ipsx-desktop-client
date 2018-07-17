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
        
        return !jsonResponse.getBody().getObject().has("error");
    }

    @Override
    public boolean loginUser(String credentials) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.post(UserApi.userApiUrl + "/login")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("accept", "application/json")
            .field("credentials", credentials)
            .asJson();
        
        return !jsonResponse.getBody().getObject().has("error");
    }

    @Override
    public boolean logoutUser() throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loginUserFacebook(String accessToken) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean resetPassword(String email) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEthAddress(String customName, String ethAddress) throws UnirestException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
