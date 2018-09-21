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
import java.util.List;
import java.util.ResourceBundle;
import sx.ip.controllers.NavController;
import sx.ip.models.ProxyPackage;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author hygor
 */
public class PackageApiImpl implements PackageApi {

    /**
     * The ResourceBundle instance.
     */
    ResourceBundle rb = ProxyUtils.getBundle();

    @Override
    public List<ProxyPackage> retrievePackages() throws UnirestException, Exception {
        Type type = new TypeToken<List<ProxyPackage>>() {
        }.getType();
        ArrayList<ProxyPackage> packageArray = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = Unirest.get(PackageApi.packageApiUrl)
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .queryString("access_token", NavController.accessToken)
                    .asJson();

            Gson g = new Gson();
            packageArray = g.fromJson(response.getBody().toString(), type);

            return packageArray;

        } catch (UnirestException | JsonSyntaxException e) {
            throw e;
        }
    }

}
