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
package sx.ip.utils;

import com.jfoenix.validation.base.ValidatorBase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextInputControl;

/**
 *
 * Special char validator.
 */
public class ETHWalletValidator extends ValidatorBase {
    
    public static final Pattern VALID_ETH_WALLET_ADDRESS_REGEX = 
    Pattern.compile("^0x[a-fA-F0-9]{40}$", Pattern.CASE_INSENSITIVE);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }
    
    /**
     * Validate if the text has double quotes.
     */
    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
         if (text != null || !text.trim().isEmpty()) {
            if(!validate(text)){
                hasErrors.set(true);
            }else {
                hasErrors.set(false);
            }
        } 
    }
    
    public static boolean validate(String ethStr) {
        Matcher matcher = VALID_ETH_WALLET_ADDRESS_REGEX.matcher(ethStr);
        return matcher.find();
}
}