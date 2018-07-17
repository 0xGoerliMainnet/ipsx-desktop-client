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

/**
 *
 * @author hygor
 */
public interface UserApi {
    
    public boolean authUser(String email, String password);
    
    public boolean loginUser(String credentials);
    
    public boolean logoutUser();
    
    public boolean loginUserFacebook(String accessToken);
    
    public boolean changePassword(String oldPassword, String newPassword);
    
    public boolean resetPassword(String email);
    
    public boolean addEthAddress(String customName, String ethAddress);
    
}
