package com.example.onlineshop.exception;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ErrorLocalization {
    private final MessageSource messageSource;
    private final Translate translate;

    @Autowired
    public ErrorLocalization(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    public String getLocalizedErrorMessage(String errorCode) {
        String message = messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
        String translatedMessage = translateText(message);
        return translatedMessage != null ? translatedMessage : message;
    }

    private String translateText(String text) {
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("es"));
        return translation.getTranslatedText();
    }
}



