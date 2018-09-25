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


/**
 * Class that will storage hold Ethereum Wallet information.
 */
public class Country {
    
    private Integer id;
    private String code;
    private String name;
    private Integer phonecode;
    
    /**
    * Create a <code>ETHWallet</code> that will hold all the wallet information.
    *
    * @param id
    *            The country id
    * @param code
    *            The country code
    * @param name
    *            The country name
    * @param phonecode
    *            The country phonecode
    */
    public Country(Integer id, String code, String name, Integer phonecode) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.phonecode = phonecode;
    }

    /**
     * @return the wallet address
     */
    @Override
    public String toString() {
        return name;
    }
    
    
}
