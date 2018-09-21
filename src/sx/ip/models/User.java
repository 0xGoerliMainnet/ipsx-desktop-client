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
public class User {

    private Integer id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String telegram;
    private String country_id;
    private String email;
    private String valid;
    private String email_token;
    private String kyc_status;
    private String kyc_msg;
    private String ip;
    private String remember_token;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private Date self_deleted_at;
    private String kyc_msg_text;
    private String ballance;
    private String proxy_test;
    private String self_deleted_at_confirmation;
    private String force_kyc;
    private String referral_id;
    private String referral_code;
    private String avatar;
    private String source;
    private String social_name;
    private String social_id;
    private String social_token;
    private Date social_date;
    private Integer intention_company;
    private Integer intention_provider;

    /**
     * Create a <code>User</code> that will hold all the user information.
     */
    public User(Integer id, String first_name, String middle_name, String last_name, String telegram, String country_id, String email, String valid, String email_token, String kyc_status, String kyc_msg, String ip, String remember_token, Date created_at, Date updated_at, Date deleted_at, Date self_deleted_at, String kyc_msg_text, String ballance, String proxy_test, String self_deleted_at_confirmation, String force_kyc, String referral_id, String referral_code, String avatar, String source, String social_name, String social_id, String social_token, Date social_date, Integer intention_company, Integer intention_provider) {
        this.id = id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.telegram = telegram;
        this.country_id = country_id;
        this.email = email;
        this.valid = valid;
        this.email_token = email_token;
        this.kyc_status = kyc_status;
        this.kyc_msg = kyc_msg;
        this.ip = ip;
        this.remember_token = remember_token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.self_deleted_at = self_deleted_at;
        this.kyc_msg_text = kyc_msg_text;
        this.ballance = ballance;
        this.proxy_test = proxy_test;
        this.self_deleted_at_confirmation = self_deleted_at_confirmation;
        this.force_kyc = force_kyc;
        this.referral_id = referral_id;
        this.referral_code = referral_code;
        this.avatar = avatar;
        this.source = source;
        this.social_name = social_name;
        this.social_id = social_id;
        this.social_token = social_token;
        this.social_date = social_date;
        this.intention_company = intention_company;
        this.intention_provider = intention_provider;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getEmail_token() {
        return email_token;
    }

    public void setEmail_token(String email_token) {
        this.email_token = email_token;
    }

    public String getKyc_status() {
        return kyc_status;
    }

    public void setKyc_status(String kyc_status) {
        this.kyc_status = kyc_status;
    }

    public String getKyc_msg() {
        return kyc_msg;
    }

    public void setKyc_msg(String kyc_msg) {
        this.kyc_msg = kyc_msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Date getSelf_deleted_at() {
        return self_deleted_at;
    }

    public void setSelf_deleted_at(Date self_deleted_at) {
        this.self_deleted_at = self_deleted_at;
    }

    public String getKyc_msg_text() {
        return kyc_msg_text;
    }

    public void setKyc_msg_text(String kyc_msg_text) {
        this.kyc_msg_text = kyc_msg_text;
    }

    public String getBallance() {
        return ballance;
    }

    public void setBallance(String ballance) {
        this.ballance = ballance;
    }

    public String getProxy_test() {
        return proxy_test;
    }

    public void setProxy_test(String proxy_test) {
        this.proxy_test = proxy_test;
    }

    public String getSelf_deleted_at_confirmation() {
        return self_deleted_at_confirmation;
    }

    public void setSelf_deleted_at_confirmation(String self_deleted_at_confirmation) {
        this.self_deleted_at_confirmation = self_deleted_at_confirmation;
    }

    public String getForce_kyc() {
        return force_kyc;
    }

    public void setForce_kyc(String force_kyc) {
        this.force_kyc = force_kyc;
    }

    public String getReferral_id() {
        return referral_id;
    }

    public void setReferral_id(String referral_id) {
        this.referral_id = referral_id;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSocial_name() {
        return social_name;
    }

    public void setSocial_name(String social_name) {
        this.social_name = social_name;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getSocial_token() {
        return social_token;
    }

    public void setSocial_token(String social_token) {
        this.social_token = social_token;
    }

    public Date getSocial_date() {
        return social_date;
    }

    public void setSocial_date(Date social_date) {
        this.social_date = social_date;
    }

    public Integer getIntention_company() {
        return intention_company;
    }

    public void setIntention_company(Integer intention_company) {
        this.intention_company = intention_company;
    }

    public Integer getIntention_provider() {
        return intention_provider;
    }

    public void setIntention_provider(Integer intention_provider) {
        this.intention_provider = intention_provider;
    }

}
