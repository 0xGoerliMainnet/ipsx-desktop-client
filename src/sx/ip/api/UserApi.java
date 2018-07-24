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

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 *
 * @author hygor
 */
public interface UserApi {
    
    public static String userApiUrl = "http://devapi.ip.sx:3000/api/users";
    
    public boolean authUser(String email, String password) throws UnirestException;
    
    public boolean loginUser(String credentials) throws UnirestException;
    
    public boolean logoutUser() throws UnirestException;
    
    public String loginUserFacebook(String token) throws UnirestException, Exception;
    
    public String changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) throws UnirestException;
    
    public boolean resetPassword(String email) throws UnirestException;
    
    public boolean addEthAddress(String customName, String ethAddress) throws UnirestException;
    
    public boolean userHasEthWallet() throws UnirestException;
    
}
