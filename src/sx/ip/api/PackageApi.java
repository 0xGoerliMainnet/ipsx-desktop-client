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
import java.util.List;
import sx.ip.models.ProxyPackage;

/**
 * Interface responsible for describe the all user api methods
 */
public interface PackageApi {

    /**
     * Base ulr for User API.
     */
    public static String packageApiUrl = "https://api.ip.sx/api/packages";

    /**
     * Method responsible to retrieve Proxy packages.
     *
     * @throws UnirestException
     *
     * @return List<ProxyPackage>
     */
    public List<ProxyPackage> retrievePackages() throws UnirestException, Exception;

    }
