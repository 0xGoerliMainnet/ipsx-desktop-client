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

import com.google.gson.JsonSyntaxException;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.List;
import sx.ip.models.ETHWallet;
import sx.ip.models.TokenRequest;

/**
 * Interface responsible for describe the all user api methods
 */
public interface UserApi {

    /**
     * Base ulr for User API.
     */
    public static String userApiUrl = "http://devapi.ip.sx:3000/api/users";

    /**
     * Method responsible for do the login with email and password.
     *
     * @param email The instance of the current loader.
     * @param password The instance of the current stage.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean authUser(String email, String password) throws UnirestException, Exception;

    /**
     * Method responsible for do the login with credentials.
     *
     * @param credentials The instance of the current loader.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean loginUser(String credentials) throws UnirestException;

    /**
     * Method responsible for do the user logout.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean logoutUser() throws UnirestException;

    /**
     * Method responsible for do the login with facebook credentials.
     *
     * @param token The instance of the current loader.
     *
     * @throws UnirestException
     *
     * @throws Exception
     *
     * @return String
     */
    public String loginUserFacebook(String token) throws UnirestException, Exception;

    /**
     * Method responsible for the user password change.
     *
     * @param oldPassword The instance of the current loader.
     * @param newPassword The instance of the current stage.
     * @param newPasswordConfirmation The instance of the current application.
     *
     * @throws UnirestException
     *
     * @return String
     */
    public String changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) throws UnirestException;

    /**
     * Method responsible for send the password reset link.
     *
     * @param email The instance of the current loader.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean resetPassword(String email) throws UnirestException;

    /**
     * Method responsible for add a eth wallet address to the user account.
     *
     * @param customName The instance of the current loader.
     * @param ethAddress The instance of the current stage.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean addEthAddress(String customName, String ethAddress) throws UnirestException;

    /**
     * Method responsible for checking if the user has an active ETH wallet.
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean userHasEthWallet() throws UnirestException;

    /**
     * Method responsible of retrieving user's active ETH wallets.
     *
     * @throws UnirestException
     *
     * @return List<ETHWallet>
     */
    public List<ETHWallet> retrieveUsersETHWallets() throws UnirestException, JsonSyntaxException;

    /**
     * Method responsible of retrieving user's active ETH wallets.
     *
     * @throws UnirestException
     *
     * @return List<TokenRequest>
     */
    public List<TokenRequest> retrieveUserTokenRequests() throws UnirestException, JsonSyntaxException;

    /**
     * Method responsible for making token requests.
     *
     * @param userWallet
     *
     * @param amountRequested
     *
     * @throws UnirestException
     *
     * @return boolean
     */
    public boolean tokenRequest(ETHWallet userWallet, String amountRequested) throws UnirestException, JsonSyntaxException, Exception;

    /**
     * Method responsible for retrieving the user balance.
     *
     *
     * @throws UnirestException,JsonSyntaxException
     *
     * @return String
     */
    public String retrieveUserBalance() throws UnirestException, JsonSyntaxException;

}
