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
package sx.ip.models;

import java.util.Date;




/**
 * Class that will hold Token Requests information.
 */
public class TokenRequest {
    
    private String address;
    private String updated_at;
    private Integer user_id;
    private Integer usereth_id;
    private Integer verified;
    private String alias;
    private String response;
    private String tx_id;
    private String amount_requested;
    private String amount_received;
    private Date created_at;
    private Integer id;
    private String status;
    private String network;

    /**
    * Create a <code>TokenRequest</code> that will hold all the token request information.
    *
    * @param address
    *            The wallet address
    * @param updated_at
    *            The last update date
    * @param user_id
    *            The user id
    * @param usereth_id
    * 
    * @param verified
    *            If the wallet is verified
    * @param alias
    *            The wallet alias
    * @param response
    * 
    * @param tx_id
    * 
    * @param amount_requested
    * 
    * @param amount_received
    * 
    * @param created_at
    *            The wallet creation date
    * @param id
    *            The wallet id
    * @param status
    *            The wallet status
    * @param network
    *            The wallet network
    */

    public TokenRequest(String address, String updated_at, Integer user_id, Integer usereth_id, Integer verified, String alias, String response, String tx_id, String amount_requested, String amount_received, Date created_at, Integer id, String status, String network) {
        this.address = address;
        this.updated_at = updated_at;
        this.user_id = user_id;
        this.usereth_id = usereth_id;
        this.verified = verified;
        this.alias = alias;
        this.response = response;
        this.tx_id = tx_id;
        this.amount_requested = amount_requested;
        this.amount_received = amount_received;
        this.created_at = created_at;
        this.id = id;
        this.status = status;
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Integer getUsereth_id() {
        return usereth_id;
    }

    public void setUsereth_id(Integer usereth_id) {
        this.usereth_id = usereth_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public String getAmount_requested() {
        return amount_requested;
    }

    public void setAmount_requested(String amount_requested) {
        this.amount_requested = amount_requested;
    }

    public String getAmount_received() {
        return amount_received;
    }

    public void setAmount_received(String amount_received) {
        this.amount_received = amount_received;
    }
        
    /**
     * @return the wallet address
     */
    @Override
    public String toString() {
        return status;
    }
    
}
