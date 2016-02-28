/*
 * COPYRIGHT 2015 SEAGATE LLC
 *
 * THIS DRAWING/DOCUMENT, ITS SPECIFICATIONS, AND THE DATA CONTAINED
 * HEREIN, ARE THE EXCLUSIVE PROPERTY OF SEAGATE TECHNOLOGY
 * LIMITED, ISSUED IN STRICT CONFIDENCE AND SHALL NOT, WITHOUT
 * THE PRIOR WRITTEN PERMISSION OF SEAGATE TECHNOLOGY LIMITED,
 * BE REPRODUCED, COPIED, OR DISCLOSED TO A THIRD PARTY, OR
 * USED FOR ANY PURPOSE WHATSOEVER, OR STORED IN A RETRIEVAL SYSTEM
 * EXCEPT AS ALLOWED BY THE TERMS OF SEAGATE LICENSES AND AGREEMENTS.
 *
 * YOU SHOULD HAVE RECEIVED A COPY OF SEAGATE'S LICENSE ALONG WITH
 * THIS RELEASE. IF NOT PLEASE CONTACT A SEAGATE REPRESENTATIVE
 * http://www.seagate.com/contact
 *
 * Original author:  Arjun Hariharan <arjun.hariharan@seagate.com>
 * Original creation date: 12-Dec-2015
 */
package com.seagates3.response.generator;

import com.seagates3.model.AccessKey;
import com.seagates3.model.Account;
import com.seagates3.model.User;
import com.seagates3.response.ServerResponse;
import com.seagates3.response.formatter.xml.XMLResponseFormatter;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.LinkedHashMap;

public class AccountResponseGenerator extends AbstractResponseGenerator {

    public ServerResponse generateCreateResponse(Account account, User root,
            AccessKey rootAccessKey) {
        LinkedHashMap responseElements = new LinkedHashMap();
        responseElements.put("AccountId", account.getId());
        responseElements.put("AccountName", account.getName());
        responseElements.put("RootUserName", root.getName());
        responseElements.put("AccessKeyId", rootAccessKey.getId());
        responseElements.put("RootSecretKeyId", rootAccessKey.getSecretKey());
        responseElements.put("Status", rootAccessKey.getStatus());

        return (ServerResponse) new XMLResponseFormatter().formatCreateResponse("CreateAccount",
                "Account", responseElements, "0000");
    }

    @Override
    public ServerResponse entityAlreadyExists() {
        String errorMessage = "The request was rejected because it attempted "
                + "to create an account that already exists.";

        return formatResponse(HttpResponseStatus.CONFLICT,
                "EntityAlreadyExists", errorMessage);
    }
}