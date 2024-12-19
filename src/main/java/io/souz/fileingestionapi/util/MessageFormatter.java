package io.souz.fileingestionapi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageFormatter {

    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", Locale.getDefault()).getString(messageKey);
    }

}
