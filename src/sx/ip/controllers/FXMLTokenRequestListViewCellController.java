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
package sx.ip.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.models.TokenRequest;
import sx.ip.utils.ProxyUtils;

/**
 * Class responsible for hold all useful functions.
 */
public class FXMLTokenRequestListViewCellController extends ListCell<TokenRequest> {

    /**
     * Class logger
     */
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLTokenRequestListViewCellController.class);

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblAddr;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader loader;

    @Override
    protected void updateItem(TokenRequest item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLTokenRequestListViewCell.fxml"), ProxyUtils.getBundle());
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            Locale locale = new Locale("en", "US");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
            String date = dateFormat.format(item.getCreated_at());

            lblStatus.setText(item.getStatus());
            lblAmount.setText(item.getAmount_requested());
            lblAddr.setText(item.getAddress());
            lblDate.setText(date);

            setText(null);
            setGraphic(anchorPane);
        }
    }

}
