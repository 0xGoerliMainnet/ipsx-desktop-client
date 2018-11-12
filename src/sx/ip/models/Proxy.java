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
 * Class that will hold Proxy information.
 */
public class Proxy {
    
    private Integer id;
    private Integer user_id;
    private String user_ip;
    private String ip;
    private String port;
    private String country;
    private String traffic;
    private String usage;
    private String status;
    private String ttl;
    private String duration;
    private String cost;
    private Integer package_id;
    private Date start_date;
    private Date end_date;
    private Date created_at;
    private Date updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Integer getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Integer package_id) {
        this.package_id = package_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
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
    
    /**
    * Create a <code>Proxy</code> that will hold all the Proxy information.
    *
    * @param updated_at
    *            The last update date
    * @param user_id
    *            The user id
     * @param user_ip
     *           The user ip address
     * @param ip
     *           The proxy ip address
     * @param port
     *           The proxy port number
     * @param country
     *           The proxy country
     * @param traffic
     *           The proxy used traffic
     * @param usage
     *           The proxy usage time left
    * @param created_at
    *            The wallet creation date
     * @param ttl
     *           The proxy TTL
     * @param duration
     *           The proxy duration time
     * @param cost
     *           The proxy cost
     * @param package_id
     *           The package id related to this proxy
     * @param start_date
     *           The proxy start date
     * @param end_date
     *          The proxy end date
    * @param id
    *            The proxy id
    * @param status
    *            The proxy status
    */
    
    public Proxy(Integer id, Integer user_id, String user_ip, String ip, String port, String country, String traffic, String usage, String status, String ttl, String duration, String cost, Integer package_id, Date start_date, Date end_date, Date created_at, Date updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.user_ip = user_ip;
        this.ip = ip;
        this.port = port;
        this.country = country;
        this.traffic = traffic;
        this.usage = usage;
        this.status = status;
        this.ttl = ttl;
        this.duration = duration;
        this.cost = cost;
        this.package_id = package_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }   
        

    
}
